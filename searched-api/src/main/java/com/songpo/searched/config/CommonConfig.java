package com.songpo.searched.config;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.InsMktCouponCmpgnBaseDTO;
import com.songpo.searched.constant.BaseConstant;
import com.songpo.searched.domain.BusinessMessage;
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
    private NotificationService notificationService;
    @Autowired
    private CmProductService productService;
    @Autowired
    private CmOrderService cmOrderService;

    @Scheduled(cron = "0 0 0 * * *")
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
        int defaultDay = BaseConstant.DEFAULT_RECEIVING_DATE;
        int delayDay = BaseConstant.DELAY_DATE + defaultDay;
        LocalDate today = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<SlOrderDetail> detailList = this.orderDetailService.select(new SlOrderDetail() {{
            setShippingState(4);
        }});

        //如果发货时间默认值7天
        changeOrderDetailState(detailList,defaultDay);
        //延迟发货列表
        List<SlOrderDetail> delaylList = this.orderDetailService.select(new SlOrderDetail() {{
            setShippingState(4);
        }});
        changeOrderDetailState(delaylList,delayDay);
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

    void changeOrderDetailState(List<SlOrderDetail> list,int days) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (SlOrderDetail detail : list) {
            if (StringUtils.isNotBlank(detail.getShippingTime())) {
                log.debug("自动更新订单{}为已收货状态，当前订单自动收货天数为{}天", detail.getOrderId(),days);
                LocalDate ldt = LocalDate.parse(detail.getShippingTime().substring(0, 10), df);
                //如果发货时间
                if (ldt.plusDays(days).equals(today)) {
                    orderDetailService.updateByPrimaryKeySelective(new SlOrderDetail() {{
                        setId(detail.getId());
                        // 已完成/已收货
                        setShippingState(5);
                    }});
                }
            }
        }
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
     * 虚拟下单通知,每10秒一次
     */
//    @Scheduled(cron = "0/5 * * * * ? ")
//    public void virtualOrderNotice() throws InterruptedException {
//        Random rand = new Random();
////        List<SlOrderDetail> orderDetails = orderDetailService.selectAll();
//        List<Map<String,Object>> activityProductList= productService.simpleActivityProduct();
//        List<SlUser> userList = userService.selectAll();
//
//        if(activityProductList!=null && activityProductList.size()>0){
//            int index = rand.nextInt(activityProductList.size());
//            Map<String, Object> activityProductMap = activityProductList.get(index);
//            String productId = (String) activityProductMap.get("productId");
//            String activityId = (String) activityProductMap.get("activityId");
//            String salesModeId = (String) activityProductMap.get("salesModeId");
//            String productName = (String) activityProductMap.get("productName");
//            String product_image_url = (String) activityProductMap.get("product_image_url");
//            String username="...";
//            String avatar="jwefda";
//            if(userList!=null && userList.size()>0){
//                SlUser user = userList.get(rand.nextInt(userList.size()));
//                username=user.getNickName();
//                avatar=user.getAvatar();
//            }
//            JSONObject object = new JSONObject();
//            object.put("avatar", avatar);
//            object.put("nickName", username);
//            object.put("productName", productName);
//            object.put("salesModeId",salesModeId);
//            object.put("activityId", activityId);
//            object.put("productId", productId);
////                    String context = user.getAvatar() + user.getNickName() + "购买" + detail.getProductName() + "成功!";
//            //随机通知间隔
//            long maxNoticePlus= rand.nextInt(12)*1000;
//            Thread.sleep(maxNoticePlus);
//            //系统通知
//            notificationService.sendGlobalMessage(object.toJSONString(), MessageTypeEnum.SYSTEM);
//        }else {
//            return;
//        }
//        if(orderDetails!=null &&orderDetails.size()>0){
//
//            int size = orderDetails.size();
//
//            int i = rand.nextInt(size);
//            SlOrderDetail orderDetail = orderDetails.get(i);
//            String creator = orderDetail.getCreator();
//            String productId = orderDetail.getProductId();
//
//            SlUser user = userService.selectByPrimaryKey(creator);
//            SlProduct product = productService.selectByPrimaryKey(productId);
//            JSONObject object = new JSONObject();
//            object.put("avatar", user.getAvatar());
//            object.put("nickName", user.getNickName());
//            object.put("productName", orderDetail.getProductName());
//            object.put("salesModeId", product.getSalesModeId());
//
//        }else {
//            return ;
//        }
//    }


    /**
     * 处理拼团超时订单
     * 24小时后超时订单失效并返回钱
     */
    @Scheduled(cron = "0 * * * * ? ")
    void updateOrderSpellStatus() {
        List<SlOrder> orderList = orderService.select(new SlOrder() {{
            setSpellGroupStatus(1);
            setStatus(1);
        }});
        //时间分隔点
        Date compareDate = LocalDateTimeUtils.addHour(new Date(), -24);
        if (orderList != null && orderList.size() > 0) {
            for (SlOrder order : orderList) {
                //时间判断，24小时未拼团成功并且付款时间大于2018-06-15 00:00:00的订单需要关闭
                String str = "2018-06-15 00:00:00";
                if ((LocalDateTimeUtils.stringToDate(order.getPayTime()).after(LocalDateTimeUtils.stringToDate(str)))&&(order.getCreatedAt().before(compareDate))){
                    if (order != null && StringUtils.isNotBlank(order.getId())) {
                        log.debug("更新订单{}的拼团状态", order.getId());
                        //请求CmOrderService接口并处理订单数据
                        try {
                        BusinessMessage message = cmOrderService.refundOrder(order.getId());
                        } catch (Exception e) {
                            log.error("更新订单" + order.getId() + "拼团状态失败", e);
                            continue;
                        }
                    }
                }else{
                    continue;
                }
            }
        }
    }
}
