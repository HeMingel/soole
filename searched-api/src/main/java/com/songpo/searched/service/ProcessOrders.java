package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.cache.OrderCache;
import com.songpo.searched.cache.UserCache;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.*;
import com.songpo.searched.rabbitmq.NotificationService;
import com.songpo.searched.typehandler.MessageTypeEnum;
import com.songpo.searched.util.Arith;
import com.songpo.searched.util.LocalDateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
    @Autowired
    private CmOrderMapper cmOrderMapper;
    @Autowired
    private SlActivityProductMapper activityProductMapper;
    @Autowired
    private SlTransactionDetailMapper transactionDetailMapper;
    @Autowired
    private OrderCache orderCache;
    @Autowired
    private ShopService shopService;

    public static final Logger log = LoggerFactory.getLogger(ProcessOrders.class);

    /**
     * 支付后订单处理
     *
     * @param orderId
     * @return
     */
    @Transactional
    public void processOrders(String orderId, int payType) {
        String date = null;
        int pulse = 0;
        SlOrder order = this.orderService.selectOne(new SlOrder() {{
            setId(orderId);
        }});
        if (null != order) {
            if (order.getPaymentState() == 2) {
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
                        // 支付时间戳
                        setPayTimeStamp(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
                    }}, example);
                    List<SlOrderDetail> details = orderDetailService.select(new SlOrderDetail() {{
                        setOrderId(order.getId());
                    }});
                    orderCache.evict(orderId);
                    if (details.size() > 0) {
                        for (SlOrderDetail detail : details) {
                            Example example1 = new Example(SlOrderDetail.class);
                            example1.createCriteria()
                                    .andEqualTo("orderId", order.getId())
                                    .andEqualTo("creator", user.getId());
                            orderDetailService.updateByExampleSelective(new SlOrderDetail() {{
                                // 明细中改成代发货状态
                                setShippingState(3);
                            }}, example1);
                            switch (detail.getType()) {
                                // 拼团订单
                                case 2:
                                    // 查询已支付的该拼团的订单数量
                                    int count = this.cmOrderMapper.groupOrdersByUser(order.getSerialNumber());
                                    // 如果拼团人数够了
                                    if (count == detail.getGroupPeople()) {
                                        Example e = new Example(SlOrder.class);
                                        e.createCriteria()
                                                .andEqualTo("serialNumber", order.getSerialNumber())
                                                .andEqualTo("paymentState", 1);
                                        // 把该订单号的所有拼团状态改成成功状态
                                        orderService.updateByExampleSelective(new SlOrder() {{
                                            // 改成拼团成功
                                            setSpellGroupStatus(2);
                                        }}, e);
                                    } else if (count < detail.getGroupPeople()) {
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
                                    e.setOrderByClause("number_of_periods ASC");
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
                                            if (StringUtils.isBlank(date)) {
                                                detail1.setReturnTime(
                                                        LocalDateTimeUtils.formatTime(LocalDateTimeUtils.plus(LocalDateTime.now(),
                                                                record.getReturnedNumber(),
                                                                ChronoUnit.DAYS), "yyyy年MM月dd日 HH:mm:ss"));
                                                date = detail1.getReturnTime();
                                            } else {
                                                detail1.setReturnTime(
                                                        LocalDateTimeUtils.formatTime(LocalDateTimeUtils.plus(LocalDateTime.parse(date),
                                                                record.getReturnedNumber(),
                                                                ChronoUnit.DAYS), "yyyy年MM月dd日 HH:mm:ss"));
                                            }
                                            this.returnsDetailMapper.insertSelective(detail1);
                                        }
                                    }
                                    break;
                            }
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
                            SlShop shop = shopService.selectOne(new SlShop() {{
                                setId(detail.getShopId());
                            }});
                            if (null != shop) {
                                Double money = Arith.mul(detail.getPrice().doubleValue(), detail.getQuantity());
                                Double tm = shop.getTotalSales().doubleValue() + money;
                                shopService.updateByPrimaryKeySelective(new SlShop() {{
                                    setId(shop.getId());
                                    setTotalSales(BigDecimal.valueOf(tm));
                                }});
                            }
                            JSONObject object = new JSONObject();
                            object.put("avatar", user.getAvatar());
                            object.put("nickName", user.getNickName());
                            object.put("productName", detail.getProductName());
                            object.put("salesModeId", product.getSalesModeId());
                            SlActivityProduct activityProduct = activityProductMapper.selectOne(new SlActivityProduct() {{
                                setId(detail.getActivityProductId());
                            }});
                            object.put("activityId", activityProduct.getActivityId());
                            object.put("productId", detail.getProductId());
//                    String context = user.getAvatar() + user.getNickName() + "购买" + detail.getProductName() + "成功!";
                            //系统通知
                            notificationService.sendGlobalMessage(object.toJSONString(), MessageTypeEnum.SYSTEM);
                        }
                        if (payType != 3) {
                            // 加入明细表
                            transactionDetailMapper.insertSelective(new SlTransactionDetail() {{
                                // 目标id
                                setTargetId(user.getId());
                                // 订单id
                                setOrderId(orderId);
                                // 购物类型
                                setType(200);
                                // 扣除金额
                                setMoney(new BigDecimal(order.getTotalAmount().toString()));
                                // 钱
                                setDealType(3);
                                // 插入时间
                                setCreateTime(new Date());
                                // 支出
                                setTransactionType(1);
                            }});
                        }
                        if (pulse > 0) {
                            // 加入明细表
                            int finalPulse = pulse;
                            transactionDetailMapper.insertSelective(new SlTransactionDetail() {{
                                // 目标id
                                setTargetId(user.getId());
                                // 订单id
                                setOrderId(orderId);
                                // 购物
                                setType(200);
                                // 扣除金额
                                setSilver(finalPulse);
                                // 银豆
                                setDealType(6);
                                // 插入时间
                                setCreateTime(new Date());
                                // 支出
                                setTransactionType(2);
                            }});
                            int silver = user.getSilver() + pulse;
                            userService.updateByPrimaryKeySelective(new SlUser() {{
                                setId(user.getId());
                                setSilver(silver);
                            }});
                            user.setSilver(silver);
                            userCache.put(user.getClientId(), user);
                        }
                    }
                }
            }
        }
    }
}
