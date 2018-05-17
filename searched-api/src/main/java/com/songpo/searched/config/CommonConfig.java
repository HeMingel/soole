package com.songpo.searched.config;

import com.songpo.searched.entity.SlOrderDetail;
import com.songpo.searched.entity.SlReturnsDetail;
import com.songpo.searched.entity.SlSignIn;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlReturnsDetailMapper;
import com.songpo.searched.mapper.SlSignInMapper;
import com.songpo.searched.service.LoginUserService;
import com.songpo.searched.service.OrderDetailService;
import com.songpo.searched.service.OrderService;
import com.songpo.searched.service.ProcessOrders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class CommonConfig {

    public static final Logger log = LoggerFactory.getLogger(CommonConfig.class);

    @Autowired
    private SlSignInMapper slSignInMapper;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private SlReturnsDetailMapper returnsDetailMapper;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProcessOrders processOrders;
//    @Autowired
//    private WXPay wxPay;

    @Autowired
    private Environment env;

    @Scheduled(cron = "0 0 0  * * ? ")   //每10秒执行一次
    public void aTask() {
        updSignState();
        updOrderPreSaleState();
        updOrderConfirmReceipt();
        updReturnsDetailOrderPreSaleState();
    }

    /**
     * 定时更新签到天数
     */
    void updSignState() {
        try {
            Example example = new Example(SlSignIn.class);
            example.createCriteria().andEqualTo("signNum", 7);
            // 更新连续签到天数
            int count = this.slSignInMapper.updateByExampleSelective(new SlSignIn() {{
                setSignNum(0);
            }}, example);
            if (count == 1) {
                SlUser user = loginUserService.getCurrentLoginUser();
                this.slSignInMapper.delete(new SlSignIn() {{
                    setUserId(user.getId());
                }});
            }
        } catch (Exception e) {
            log.error("更新失败", e);
        }
    }

    /**
     * 更新用户预售返现状态
     */
    void updOrderPreSaleState() {
        LocalTime localTime = LocalTime.now();
        localTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<SlReturnsDetail> list = this.returnsDetailMapper.selectAll();
        for (SlReturnsDetail detail : list) {
            if (detail.getReturnTime().equals(localTime)) {
                returnsDetailMapper.updateByPrimaryKeySelective(new SlReturnsDetail() {{
                    setId(detail.getId());
                    // 当前时间和返现时间相同的话把状态改为可返
                    setReturnedStatus(4);
                }});
            }
            if (detail.getReturnedStatus().equals(4)) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate ldt = LocalDate.parse(detail.getReturnTime(), df);
                // 如果返现时间+3天 并且状态还是可返状态
                if (ldt.plusDays(3).equals(localTime)) {
                    // 把状态改为已逾期
                    returnsDetailMapper.updateByPrimaryKeySelective(new SlReturnsDetail() {{
                        setId(detail.getId());
                        setReturnedStatus(3);
                    }});
                }
            }
        }
    }

    /**
     * 7天自动确认收货(普通商品)
     */
    void updOrderConfirmReceipt() {
        LocalTime localTime = LocalTime.now();
        localTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<SlOrderDetail> detailList = this.orderDetailService.select(new SlOrderDetail() {{
            setShippingState(4);
        }});
        for (SlOrderDetail detail : detailList) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ldt = LocalDate.parse(detail.getShippingTime(), df);
            //如果发货时间 + 7天
            if (ldt.plusDays(7).equals(localTime)) {
                orderDetailService.updateByPrimaryKeySelective(new SlOrderDetail() {{
                    setId(detail.getId());
                    // 已完成/已收货
                    setShippingState(5);
                }});
            }
        }


//        for (SlReturnsDetail detail : list) {
//            if (detail.getReturnTime().equals(localTime)) {
//                returnsDetailMapper.updateByPrimaryKeySelective(new SlReturnsDetail() {{
//                    setId(detail.getId());
//                    // 当前时间和返现时间相同的话把状态改为可返
//                    setReturnedStatus(4);
//                }});
//            }
//            if (detail.getReturnedStatus().equals(4)) {
//                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                LocalDate ldt = LocalDate.parse(detail.getReturnTime(), df);
//                // 如果返现时间+3天 并且状态还是可返状态
//                if (ldt.plusDays(3).equals(localTime)) {
//                    // 把状态改为已逾期
//                    returnsDetailMapper.updateByPrimaryKeySelective(new SlReturnsDetail() {{
//                        setId(detail.getId());
//                        setReturnedStatus(3);
//                    }});
//                }
//            }
//        }
    }

    /**
     * 预售订单确认收货(商家发货3天,自动确认收货)
     */
    void updReturnsDetailOrderPreSaleState() {
        Example e = new Example(SlReturnsDetail.class);
        e.setOrderByClause("return_time ASC");
        List<SlReturnsDetail> list = this.returnsDetailMapper.selectByExample(e);
        for (SlReturnsDetail detail : list) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime ldt = LocalDateTime.parse(detail.getReturnTime(), df);
            Duration duration = Duration.between(ldt, time);
            if (duration.toDays() == 3) {
                Example example = new Example(SlReturnsDetail.class);
                example.createCriteria()
                        .andEqualTo("id", detail.getId())
                        //商家已返状态
                        .andEqualTo("returnedStatus", 1)
                        //但是用户并没有确认收货的
                        .andEqualTo("confirmReceipt", false);
                int count = returnsDetailMapper.updateByExampleSelective(new SlReturnsDetail() {{
                    setConfirmReceipt(true);
                }}, example);
                if (count == 1) {
                    if (detail.getId().equals(list.get(0).getId())) {
                        Example example1 = new Example(SlOrderDetail.class);
                        example1.createCriteria().andEqualTo("orderId", detail.getOrderId());
                        this.orderDetailService.updateByExampleSelective(new SlOrderDetail() {{
                            // 已完成/未评价
                            setShippingState(5);
                        }}, example1);
                        //把改订单号的所有订单更新为已完成状态
                        this.returnsDetailMapper.updateByExampleSelective(new SlReturnsDetail() {{
                            setReturnedStatus(5);
                        }}, example);
                    }
                }
            }
        }
    }

    /**
     * 更新微信支付订单支付状态
     * 定时执行方法，调用微信订单查询接口，查询订单支付状态，并处理系统订单
     * 来源 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_2&index=4
     */
//    @Scheduled(cron = "0/10 * *  * * ? ")
//    void updateWXPayStatus() {
//        List<SlOrder> orderList = orderService.select(new SlOrder() {{
//            setPaymentState(2);
//            setStatus(1);
//        }});
//        //移除不需要的订单
//        if (orderList != null && orderList.size() > 0) {
//            List<SlOrder> removeOrderList = new ArrayList<>();
//            for (SlOrder order : orderList) {
//                //比较大小，总金额为0的过滤
//                if (order.getTotalAmount().compareTo(new BigDecimal("0")) <= 0) {
//                    removeOrderList.add(order);
//                } else {
//                    //只需要一个小时内的订单
//                    Date compareDate = LocalDateTimeUtils.addHour(new Date(), 1);
//                    if (order.getCreatedAt().before(compareDate)) {
//                        removeOrderList.add(order);
//                    }
//                }
//            }
//            if (removeOrderList.size() > 0) {
//                orderList.removeAll(removeOrderList);
//            }
//        }
//        if (orderList != null && orderList.size() > 0) {
//            for (SlOrder order : orderList) {
//                if (order != null && StringUtils.isNotBlank(order.getId())) {
//                    //生成微信请求参数
//                    Map<String, String> reqData = new HashMap<>();
//                    reqData.put("appid", env.getProperty("sp.pay.wxpay.appId"));
//                    reqData.put("mch_id", env.getProperty("sp.pay.wxpay.mchId"));
//                    reqData.put("out_trade_no", order.getId());
//                    //请求微信订单查询接口并处理订单数据
//                    try {
////                        Map<String, String> result = wxPay.orderQuery(reqData);
////                        if (result != null && result.get("return_code").equals("SUCCESS") && result.get("trade_state").equals("SUCCESS")) {
////                            processOrders.processOrders(order.getId(), 1);
////                        }
//                    } catch (Exception e) {
//                        log.error("更新订单" + order.getId() + "支付状态失败", e);
//                        continue;
//                    }
//                }
//            }
//        }
//        Map<String, String> reqData = new HashMap<>();
//    }
}
