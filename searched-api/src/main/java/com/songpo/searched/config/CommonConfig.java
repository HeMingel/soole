package com.songpo.searched.config;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.SlActivityProductMapper;
import com.songpo.searched.mapper.SlReturnsDetailMapper;
import com.songpo.searched.mapper.SlSignInMapper;
import com.songpo.searched.rabbitmq.NotificationService;
import com.songpo.searched.service.*;
import com.songpo.searched.typehandler.MessageTypeEnum;
import com.songpo.searched.util.LocalDateTimeUtils;
import com.songpo.searched.wxpay.service.WxPayService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class CommonConfig {

    public static final Logger log = LoggerFactory.getLogger(CommonConfig.class);

    @Autowired
    private SlSignInMapper slSignInMapper;
    @Autowired
    private SlReturnsDetailMapper returnsDetailMapper;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProcessOrders processOrders;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private UserService userService;
    @Autowired
    private SlActivityProductMapper activityProductMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 0 0 * * *")
    public void aTask() {
        updSignState();
        updOrderPreSaleState();
        updOrderConfirmReceipt();
        updReturnsDetailOrderPreSaleState();
        virtualOrderNotice();
    }

    /**
     * 定时更新签到天数
     */
    void updSignState() {
        try {
            List<SlSignIn> signs = slSignInMapper.select(new SlSignIn() {{
                setSignNum(7);
            }});
            for (SlSignIn sign : signs) {
                Example example = new Example(SlSignIn.class);
                example.createCriteria()
                        .andEqualTo("userId", sign.getUserId());
                this.slSignInMapper.deleteByExample(example);
            }
        } catch (Exception e) {
            log.error("更新失败", e);
        }
    }

    /**
     * 更新用户预售返现状态
     */
    void updOrderPreSaleState() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        today.format(df);
        List<SlReturnsDetail> list = this.returnsDetailMapper.selectAll();
        for (SlReturnsDetail detail : list) {
            LocalDate ldt = LocalDate.parse(detail.getReturnTime().substring(0, 10), df);
            if (ldt.equals(today)) {
                returnsDetailMapper.updateByPrimaryKeySelective(new SlReturnsDetail() {{
                    setId(detail.getId());
                    // 当前时间和返现时间相同的话把状态改为可返
                    setReturnedStatus(4);
                }});
            }
            if (detail.getReturnedStatus().equals(4)) {
                // 如果返现时间+3天 并且状态还是可返状态
                if (ldt.plusDays(3).equals(today)) {
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
     * 7天自动确认收货
     */
    void updOrderConfirmReceipt() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<SlOrderDetail> detailList = this.orderDetailService.select(new SlOrderDetail() {{
            setShippingState(4);
        }});
        for (SlOrderDetail detail : detailList) {
            if (StringUtils.isNotBlank(detail.getShippingTime())) {
                LocalDate ldt = LocalDate.parse(detail.getShippingTime().substring(0, 10), df);
                //如果发货时间 + 7天
                if (ldt.plusDays(7).equals(today)) {
                    orderDetailService.updateByPrimaryKeySelective(new SlOrderDetail() {{
                        setId(detail.getId());
                        // 已完成/已收货
                        setShippingState(5);
                    }});
                }
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
        Example example = new Example(SlReturnsDetail.class);
        example.createCriteria()
                //商家已返状态
                .andEqualTo("returnedStatus", 1)
                //但是用户并没有确认收货的
                .andEqualTo("confirmReceipt", false);
        List<SlReturnsDetail> list = this.returnsDetailMapper.selectByExample(example);
        for (SlReturnsDetail detail : list) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate time = LocalDate.now();
            LocalDate ldt = LocalDate.parse(detail.getReturnTime().substring(0, 10), df);
            Duration duration = Duration.between(ldt, time);
            if (duration.toDays() == 3) {
                Example example1 = new Example(SlReturnsDetail.class);
                example1.setOrderByClause("return_time DESC");
                example1.createCriteria()
                        .andEqualTo("userId", detail.getOrderId())
                        .andEqualTo("orderId", detail.getOrderId());
                List<SlReturnsDetail> list1 = this.returnsDetailMapper.selectByExample(example1);
                if (list1.size() > 0) {
                    //如果本次确认收货的预售订单==最后一次返现的预售订单id
                    if (list1.get(0).getId().equals(detail.getId())) {
                        this.orderDetailService.updateByPrimaryKeySelective(new SlOrderDetail() {{
                            setId(detail.getId());
                            // 已完成/未评价
                            setShippingState(5);
                        }});
                        //把改订单号的所有订单更新为已完成状态
                        this.returnsDetailMapper.updateByExampleSelective(new SlReturnsDetail() {{
                            setReturnedStatus(5);
                            setConfirmReceipt(false);
                        }}, example1);
                    } else {
                        this.returnsDetailMapper.updateByPrimaryKeySelective(new SlReturnsDetail() {{
                            setId(detail.getId());
                            //本期已返状态
                            setConfirmReceipt(true);
                        }});
                    }
                }
            }
        }
    }

    /**
     * 处理未支付订单
     * 24小时前订单关闭
     * 24小时内订单，调用微信订单查询接口，查询订单支付状态，并处理系统订单
     * 来源 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_2&index=4
     */
    @Scheduled(cron = "0/10 * *  * * ? ")
    void updateOrderPayStatus() {
        List<SlOrder> orderList = orderService.select(new SlOrder() {{
            setPaymentState(2);
            setStatus(1);
        }});
        //时间分隔点
        Date compareDate = LocalDateTimeUtils.addHour(new Date(), -24);
        //支付超时时间列表
        List<SlOrder> payOverTimeOrderList = new ArrayList<>();
        //移除不需要的订单
        if (orderList != null && orderList.size() > 0) {
            List<SlOrder> removeOrderList = new ArrayList<>();
            for (SlOrder order : orderList) {
                //时间判断，24小时未支付订单需要关闭
                if (order.getCreatedAt().before(compareDate)) {
                    removeOrderList.add(order);
                    payOverTimeOrderList.add(order);
                } else {
                    //比较大小，总金额为0的过滤
                    if (order.getTotalAmount().compareTo(new BigDecimal(0)) <= 0) {
                        removeOrderList.add(order);
                    }
                }
            }
            if (removeOrderList.size() > 0) {
                orderList.removeAll(removeOrderList);
            }
        }
        /****** 更新微信支付订单支付状态 ******/
        if (orderList != null && orderList.size() > 0) {
            for (SlOrder order : orderList) {
                if (order != null && StringUtils.isNotBlank(order.getId())) {
                    log.debug("更新订单{}的支付状态", order.getId());
                    //请求微信订单查询接口并处理订单数据
                    try {
                        Map<String, String> result = wxPayService.orderQuery("", order.getId());
                        if (result != null && result.get("return_code").equals("SUCCESS") && result.get("result_code").equals("SUCCESS") && result.get("trade_state").equals("SUCCESS")) {
                            processOrders.processOrders(order.getId(), 1);
                        }
                    } catch (Exception e) {
                        log.error("更新订单" + order.getId() + "支付状态失败", e);
                        continue;
                    }
                }
            }
        }
        /****** 支付超时关闭订单 ******/
        if (payOverTimeOrderList.size() > 0) {
            for (SlOrder order : payOverTimeOrderList) {
                order.setPaymentState(101);
                order.setCreatedAt(null);
                order.setUpdatedAt(null);
                orderService.updateByPrimaryKeySelective(order);
            }
        }
    }

    /**
     * 虚拟下单通知
     */
    public void virtualOrderNotice(){
        List<SlOrderDetail> orderDetails = orderDetailService.selectAll();
        if(orderDetails!=null &&orderDetails.size()>0){
            int size = orderDetails.size();
            Random rand = new Random();
            int i = rand.nextInt(size);
            SlOrderDetail orderDetail = orderDetails.get(i);
            String creator = orderDetail.getCreator();
            String productId = orderDetail.getProductId();

            SlUser user = userService.selectByPrimaryKey(creator);
            SlProduct product = productService.selectByPrimaryKey(productId);
            JSONObject object = new JSONObject();
            object.put("avatar", user.getAvatar());
            object.put("nickName", user.getNickName());
            object.put("productName", orderDetail.getProductName());
            object.put("salesModeId", product.getSalesModeId());
            SlActivityProduct activityProduct = activityProductMapper.selectOne(new SlActivityProduct() {{
                setId(orderDetail.getActivityProductId());
            }});
            object.put("activityId", activityProduct.getActivityId());
            object.put("productId", orderDetail.getProductId());
//                    String context = user.getAvatar() + user.getNickName() + "购买" + detail.getProductName() + "成功!";
            //系统通知
            notificationService.sendGlobalMessage(object.toJSONString(), MessageTypeEnum.SYSTEM);
        }else {
            return ;
        }
    }

}
