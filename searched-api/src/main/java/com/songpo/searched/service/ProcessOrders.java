package com.songpo.searched.service;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlOrder;
import com.songpo.searched.entity.SlOrderDetail;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlActivityProductMapper;
import com.songpo.searched.util.Arith;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;

public class ProcessOrders {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private SlActivityProductMapper slActivityProductMapper;
    @Autowired
    private UserService userService;

    public static final Logger log = LoggerFactory.getLogger(ProcessOrders.class);

    /**
     * 支付后订单处理
     *
     * @param orderId
     * @return
     */
    public BusinessMessage processOrders(String orderId, String activityProductId) {
        BusinessMessage message = new BusinessMessage();
        try {
            SlUser user = loginUserService.getCurrentLoginUser();
            if (null != user) {
                SlOrder order = this.orderService.selectByPrimaryKey(orderId);
                if (null != order) {
                    Example example = new Example(SlOrder.class);
                    example.createCriteria()
                            .andEqualTo("id", orderId)
                            .andEqualTo("paymentState", 2)
                            .andEqualTo("userId", user.getId());
                    orderService.updateByExampleSelective(new SlOrder() {{
                        // 改成已支付
                        setPaymentState(1);
                    }}, example);
                    Example example1 = new Example(SlOrderDetail.class);
                    example1.createCriteria().andEqualTo("orderId", orderId)
                            .andEqualTo("creator", user.getId());
                    orderDetailService.updateByExampleSelective(new SlOrderDetail() {{
                        // 明细中改成代发货状态
                        setShippingState(3);
                    }}, example);
                    SlOrderDetail detail = orderDetailService.selectOne(new SlOrderDetail() {{
                        setOrderId(orderId);
                        setActivityProductId(activityProductId);
                    }});
                    // 加上了奖励了豆数量
                    if (detail.getPlaceOrderReturnPulse() > 0) {
                        userService.updateByPrimaryKeySelective(new SlUser() {{
                            setId(user.getId());
                            setSilver(user.getSilver() + detail.getPlaceOrderReturnPulse());
                        }});
                    }
                    switch (order.getType()) {
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
                                } else {
                                    orderService.updateByPrimaryKeySelective(new SlOrder() {{
                                        setId(orderId);
                                        //拼团中
                                        setSpellGroupStatus(1);
                                    }});
                                }
                            } else {
                                orderService.updateByPrimaryKeySelective(new SlOrder() {{
                                    setId(orderId);
                                    //拼团中
                                    setSpellGroupStatus(1);
                                }});
                            }
                            break;
                        // 消费奖励
                        case 5:
                            // 如果分享人不为空
                            if (StringUtils.isNotBlank(detail.getShareOfPeopleId())) {
                                SlUser shareOfPeople = userService.selectOne(new SlUser() {{
                                    setId(detail.getShareOfPeopleId());
                                }});
                                if (shareOfPeople != null) {
                                    // 分享人获得商家额外利润的90%
                                    userService.updateByPrimaryKeySelective(new SlUser() {{
                                        setId(detail.getShareOfPeopleId());
                                        setMoney(BigDecimal.valueOf(Arith.add(Arith.mul(detail.getRewardsMoney().doubleValue(), 0.9), shareOfPeople.getMoney().doubleValue())));
                                    }});
                                } else {
                                    message.setMsg("分享人不存在");
                                }
                                // 被分享人获得商家额外利润的10%
                                userService.updateByPrimaryKeySelective(new SlUser() {{
                                    setId(user.getId());
                                    setMoney(BigDecimal.valueOf(Arith.add(Arith.mul(detail.getRewardsMoney().doubleValue(), 0.1), user.getMoney().doubleValue())));
                                }});
                            } else {
                                message.setMsg("分享人为空");
                            }
                    }
                } else {
                    message.setMsg("该订单不存在");
                }
            } else {
                message.setMsg("请登录");
            }
        } catch (Exception e) {
            log.error("更新订单失败", e);
        }
        return message;
    }
}
