package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.cache.UserCache;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.SlPresellReturnedRecordMapper;
import com.songpo.searched.mapper.SlReturnsDetailMapper;
import com.songpo.searched.rabbitmq.NotificationService;
import com.songpo.searched.typehandler.MessageTypeEnum;
import com.songpo.searched.util.LocalDateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class ProcessOrders {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    private SlPresellReturnedRecordMapper presellReturnedRecordMapper;
    @Autowired
    private SlReturnsDetailMapper returnsDetailMapper;
    @Autowired
    private UserCache userCache;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ProductService productService;

    public static final Logger log = LoggerFactory.getLogger(ProcessOrders.class);

    /**
     * 支付后订单处理
     *
     * @param orderNum
     * @return
     */
    @Transactional
    public void processOrders(String orderNum, int payType) {
        String dete = null;
        SlOrder order = this.orderService.selectOne(new SlOrder() {{
            setSerialNumber(orderNum);
        }});
        if (null != order) {
            SlUser user = userService.selectByPrimaryKey(order.getUserId());
            if (null != user) {
                Example example = new Example(SlOrder.class);
                example.createCriteria()
                        .andEqualTo("id", order.getId())
                        .andEqualTo("paymentState", 2)
                        .andEqualTo("userId", user.getId());
                orderService.updateByExampleSelective(new SlOrder() {{
                    // 改成已支付
                    setPaymentState(1);
                    // 支付类型
                    setPaymentChannel(payType);
                    // 支付时间
                    setPayTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                }}, example);
                Example example1 = new Example(SlOrderDetail.class);
                example1.createCriteria()
                        .andEqualTo("orderId", order.getId())
                        .andEqualTo("creator", user.getId());
                orderDetailService.updateByExampleSelective(new SlOrderDetail() {{
                    // 明细中改成代发货状态
                    setShippingState(3);
                }}, example1);
                List<SlOrderDetail> details = orderDetailService.select(new SlOrderDetail() {{
                    setOrderId(order.getId());
                }});
                int pulse = 0;
                for (SlOrderDetail detail : details) {
                    // 加上了奖励了豆数量
                    if (detail.getPlaceOrderReturnPulse() > 0) {
                        pulse += detail.getPlaceOrderReturnPulse();
                    }
                    SlProduct product = this.productService.selectOne(new SlProduct() {{
                        setId(detail.getProductId());
                    }});
                    if (null != product) {
                        productService.updateByPrimaryKeySelective(new SlProduct() {{
                            setId(product.getId());
                            setSalesVolume(product.getSalesVolume() + 1);
                        }});
                    }
                }
                int silver = user.getSilver() + pulse;
                userService.updateByPrimaryKeySelective(new SlUser() {{
                    setId(user.getId());
                    setSilver(silver);
                }});
                user.setSilver(silver);
                userCache.put(user.getClientId(), user);
                if (details.size() == 1) {
                    SlOrderDetail detail = details.get(0);
                    switch (detail.getType()) {
                        // 拼团订单
                        case 2:
                            // 如果这个订单的拼团人不等于该用户
                            if (!order.getGroupMaster().equals(user.getId())) {
                                // 查询已支付的该拼团的订单数量
                                int count = this.orderService.selectCount(new SlOrder() {{
                                    //该订单号的拼团
                                    setSerialNumber(order.getSerialNumber());
                                    //拼团
                                    setType(2);
                                    //已支付
                                    setPaymentState(1);
                                }});
                                // 如果拼团人数够了
                                if (count + 1 == detail.getGroupPeople()) {
                                    Example e = new Example(SlOrder.class);
                                    e.createCriteria().andEqualTo("serialNumber", order.getSerialNumber());
                                    // 把该订单号的所有拼团状态改成成功状态
                                    orderService.updateByExampleSelective(new SlOrder() {{
                                        // 改成拼团成功
                                        setSpellGroupStatus(2);
                                    }}, e);
                                    orderDetailService.updateByExampleSelective(new SlOrderDetail() {{
                                        setShippingState(3);
                                    }}, e);
                                } else if (count + 1 < detail.getGroupPeople()) {
                                    orderService.updateByPrimaryKeySelective(new SlOrder() {{
                                        setId(order.getId());
                                        // 拼团中
                                        setSpellGroupStatus(1);
                                    }});
                                }
                            } else {
                                orderService.updateByPrimaryKeySelective(new SlOrder() {{
                                    setId(order.getId());
                                    // 拼团中
                                    setSpellGroupStatus(1);
                                }});
                            }
                            break;
                        //预售订单
                        case 3:
                            Example e = new Example(SlPresellReturnedRecord.class);
                            e.setOrderByClause("numberOfPeriods ASC");
                            e.createCriteria()
                                    .andEqualTo("type", 1)
                                    .andEqualTo("productRepositoryId", detail.getRepositoryId());
                            List<SlPresellReturnedRecord> list = this.presellReturnedRecordMapper.selectByExample(e);
                            if (list.size() > 0) {
                                for (SlPresellReturnedRecord record : list) {
                                    SlReturnsDetail detail1 = new SlReturnsDetail();
                                    detail1.setOrderId(order.getId());
                                    detail1.setUserId(user.getId());
                                    // 返款金额
                                    detail1.setReturnMoney(record.getReturnedMoney());
                                    // 创建时间
                                    detail1.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                    detail1.setPresellReturnedRecordId(record.getId());
                                    if (StringUtils.isBlank(dete)) {
                                        detail1.setReturnTime(
                                                LocalDateTimeUtils.formatTime(LocalDateTimeUtils.plus(LocalDateTime.now(),
                                                        record.getReturnedNumber(),
                                                        ChronoUnit.DAYS), "yyyy年MM月dd日 HH:mm:ss"));
                                        dete = detail1.getReturnTime();
                                    } else {
                                        detail1.setReturnTime(
                                                LocalDateTimeUtils.formatTime(LocalDateTimeUtils.plus(LocalDateTime.parse(dete),
                                                        record.getReturnedNumber(),
                                                        ChronoUnit.DAYS), "yyyy年MM月dd日 HH:mm:ss"));
                                    }
                                    this.returnsDetailMapper.insertSelective(detail1);
                                }
                            }
                            break;
                        // 消费奖励
//                        case 5:
                        // 如果分享人不为空
//                            if (StringUtils.isNotBlank(detail.getShareOfPeopleId())) {
//                                SlUser shareOfPeople = userService.selectOne(new SlUser() {{
//                                    setId(detail.getShareOfPeopleId());
//                                }});
//                                if (shareOfPeople != null) {
//                                    // 分享人获得商家额外利润的90%
//                                    userService.updateByPrimaryKeySelective(new SlUser() {{
//                                        setId(detail.getShareOfPeopleId());
//                                        setMoney(BigDecimal.valueOf(Arith.add(Arith.mul(detail.getRewardsMoney().doubleValue(), 0.9), shareOfPeople.getMoney().doubleValue())));
//                                    }});
//                                } else {
//                                    message.setMsg("分享人不存在");
//                                }
//                                // 被分享人获得商家额外利润的10%
//                                userService.updateByPrimaryKeySelective(new SlUser() {{
//                                    setId(user.getId());
//                                    setMoney(BigDecimal.valueOf(Arith.add(Arith.mul(detail.getRewardsMoney().doubleValue(), 0.1), user.getMoney().doubleValue())));
//                                }});
//                            } else {
//                                message.setMsg("分享人为空");
//                            }
                    }
                }
                for (SlOrderDetail detail : details) {
                    JSONObject object = new JSONObject();
                    object.put("avatar", user.getAvatar());
                    object.put("nickName", user.getNickName());
                    object.put("productName", detail.getProductName());
                    object.put("productId", detail.getProductId());
//                    String context = user.getAvatar() + user.getNickName() + "购买" + detail.getProductName() + "成功!";
                    //系统通知
                    notificationService.sendGlobalMessage(object.toJSONString(), MessageTypeEnum.SYSTEM);
                }
            }
        }
    }
}
