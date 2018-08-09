package com.songpo.searched.config;

import com.songpo.searched.cache.ProductRepositoryCache;
import com.songpo.searched.constant.BaseConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.*;
import com.songpo.searched.service.*;
import com.songpo.searched.util.HttpUtil;
import com.songpo.searched.util.LocalDateTimeUtils;
import com.songpo.searched.util.SLStringUtils;
import com.songpo.searched.wxpay.service.WxPayService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
    private CmOrderService cmOrderService;
    @Autowired
    private ProductRepositoryService productRepositoryService;
    @Autowired
    private ProductRepositoryCache repositoryCache;
    @Autowired
    private SharingLinksService sharingLinksService;
    @Autowired
    private CmActivitySeckillMapper cmActivitySeckillMapper;
    @Autowired
    private SlActivitySeckillMapper slActivitySeckillMapper;
    @Autowired
    private SlProductMapper slProductMapper;
    @Autowired
    private SlSeckillRemindMapper slSeckillRemindMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CmSeckillRemindMapper cmSeckillRemindMapper;
    @Autowired
    public Environment env;


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
        //默认收货日期
        int defaultDay = BaseConstant.DEFAULT_RECEIVING_DATE;
        //延迟收货日期
        int delayDay = BaseConstant.DELAY_DATE + defaultDay;
        List<SlOrderDetail> detailList = this.orderDetailService.select(new SlOrderDetail() {{
            setShippingState(4);
            setIsDelayed(0);
        }});

        //如果发货时间默认值7天
        changeOrderDetailState(detailList,defaultDay);
        //延迟发货列表
        List<SlOrderDetail> delaylList = this.orderDetailService.select(new SlOrderDetail() {{
            setShippingState(4);
            setIsDelayed(1);
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
        for (SlOrderDetail detail : list)
            if (StringUtils.isNotBlank(detail.getShippingTime())) {
                log.debug("自动更新订单{}为已收货状态，当前订单自动收货天数为{}天", detail.getOrderId(), days);
                LocalDate ldt = LocalDate.parse(detail.getShippingTime().substring(0, 10), df);
                //如果发货时间
                if (ldt.plusDays(days).equals(today)) {
                    orderDetailService.updateByPrimaryKeySelective(new SlOrderDetail() {{
                        setId(detail.getId());
                        // 已完成/已收货
                        setShippingState(5);
                    }});
                    if (6 == detail.getType()) {
                        cmOrderService.returnCoinToShop(detail.getOrderId());
                    }
                    //分享奖励 将红包分享红包设置为可领取状态
                    cmOrderService.changeRedPacketResult(detail);
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
    @Scheduled(cron = "0 0/1 *  * * ? ")
    void updateOrderPayStatus() {
        List<SlOrder> orderList = orderService.select(new SlOrder() {{
            setPaymentState(2);
            setStatus(1);
        }});
        //时间分隔点
        Date compareDate = LocalDateTimeUtils.addMinute(new Date(), -30);
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
                        //处理拍卖订单
//                        Map<String, String> saleResult = wxPayService.orderQuery("", order.getOutOrderNumber());
//                        if (saleResult != null && saleResult.get("return_code").equals("SUCCESS") && saleResult.get("result_code").equals("SUCCESS") && saleResult.get("trade_state").equals("SUCCESS")) {
//                            processOrders.saleOrders(order.getOutOrderNumber(), 1);
//                        }
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
                //失效订单库存退回
                getProductCountBack(order);
            }
        }

    }


    /**
     * 处理拼团超时订单
     * 24小时后超时订单失效并返回钱
     */
    @Scheduled(cron = "0 * * * * ? ")
    void updateOrderSpellStatus() {
        List<SlOrder> orderList = orderService.select(new SlOrder() {{
            setSpellGroupStatus(1);
            setPaymentState(1);
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
                        //把失效订单中的商品数量加到商品库存中去
                            getProductCountBack(order);
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

    /**
     *  把失效订单中的商品数量加到商品库存中去
     */
    public void getProductCountBack(SlOrder order) {
        try {
            List<SlOrderDetail> list = orderDetailService.select(new SlOrderDetail() {{
                setOrderId(order.getId());
            }});
            for (SlOrderDetail detail : list) {
                SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                    setId(detail.getRepositoryId());
                }});
                // 把订单中的商品数量加到商品库存中去
                this.productRepositoryService.updateByPrimaryKeySelective(new SlProductRepository() {{
                    setId(repository.getId());
                    setCount(repository.getCount() + detail.getQuantity());
                    //限时秒杀返回库存
                    if (detail.getType().equals("8")){
                        setSekillCount(repository.getSekillCount() + detail.getQuantity());
                    }
                }});
                repository.setCount(repository.getCount() + detail.getQuantity());

                //更新redis
                this.repositoryCache.put(repository.getId(), repository);
            }

        }catch (Exception e) {
            log.debug("失效订单{}中的商品数量加到商品库存失败",order.getId(),e);
        }
    }

    /**
     * 设置链接失效 每五分钟一次
     */
    @Scheduled(cron = "0 0/5 * * * ? ")
    void setSharingLinksFailure() {

        List<SlSharingLinks> list = sharingLinksService.select( new SlSharingLinks(){{
            setIsFailure((byte)2);
        }});
        //时间分隔点
        Date compareDate = LocalDateTimeUtils.addHour(new Date(), -24);
        for (SlSharingLinks links :list) {
            if (links.getCreatedAt().before(compareDate)){
               int reuslt =  sharingLinksService.updateByPrimaryKeySelective(new SlSharingLinks(){{
                    setId(links.getId());
                    setIsFailure((byte)1);
                }});
               if (reuslt > 0 ) {
                   log.debug("更新分享链接ID为:{}为失效状态",links.getId());
               }
            }
        }
    }


    /**
     * 给以前购买的区块链商品（助力购物）返回搜了贝
     */
//   @Scheduled(cron = "0 3 0 12 7 *")
//    void returnSLBFormPowerShoppingScheduled() {
//        log.debug("=============================================start===================================================");
//            cmOrderService.returnSLBFormPowerShopping();
//        log.debug("=============================================end===================================================");
//    }

    /**
     * 定时转换sl_slb_type搜了贝数
     */
//    @Scheduled(cron = "0 7 0 12 7 *")
//    void changeSlbScheduled() {
//        log.debug("转换sl_slb_type搜了贝数开始");
//            cmOrderService.changeSlbScheduled();
//        log.debug("转换sl_slb_type搜了贝数结束");
//    }

    /**
     * 每天凌晨限时秒杀过期商品设置成普通商品
     */
    @Scheduled(cron = "0 0 0 * * ?")
    void setSeckillEnable() {
        log.debug("定时任务---限时秒杀开始----->");
        //获取当前日期前一天
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        //查询所有过期的限时秒杀活动列表
        List<SlActivitySeckill> seckills = cmActivitySeckillMapper.listOutOfDate(df.format(date.getTime()));
        if (seckills.size() > 0) {
            for (SlActivitySeckill seckill : seckills) {
                //更新商品品销售模式为普通商品
                log.debug("商品ID {} 由限时秒杀更改为普通商品", seckill.getProductOldId());
                slProductMapper.updateByPrimaryKeySelective(new SlProduct() {{
                    setId(seckill.getProductOldId());
                    setSalesModeId("6");
                }});
                //失效
                seckill.setEnable(false);
                slActivitySeckillMapper.updateByPrimaryKeySelective(seckill);

            }
        }

        log.debug("定时任务---限时秒杀结束----->");
    }

    /**
     * 每天23：57推送
     */
    @Scheduled(cron = "0 57 23 * * ?")
    void sendRemindForSeckill() {
        log.debug("定时任务---限时秒杀推送提醒开始----->");
        String currentDate = SLStringUtils.getDataTime();
        List<SlSeckillRemind> list = cmSeckillRemindMapper.listRemind(currentDate);
        if (list.size() > 0) {
            for (SlSeckillRemind remind : list) {
                try {
                    SlProduct product = slProductMapper.selectByPrimaryKey(remind.getProductOldId());
                    SlUser user = userService.selectByPrimaryKey(remind.getUserId());
                    String content = "尊敬的搜了会员 " + user.getNickName() + " ，你关注的限时秒杀商品 " + product.getName() + " 还有三分钟就开始发售了，千万别错过哦";
                    String title = "限时秒杀";
                    processOrders.sendPush(user.getUsername().toString(), content, 1, title);
                } catch (Exception e) {
                    log.error("限时秒杀推送异常", e);
                }
                //修改提醒失效
                finally {
                    remind.setEnable(false);
                    slSeckillRemindMapper.updateByPrimaryKey(remind);
                }

            }
        }
        log.debug("定时任务---限时秒杀推送提醒结束----->");

    }

    /**
     * 处理未支付国际订单
     * 30分钟前订单关闭
     * 30分钟内订单，调用国际订单查询接口，查询订单支付状态，并处理系统订单
     * 来源 http://soolepay.fisyst.com/Order/queryorder?orderid=
     */
    @Scheduled(cron = "0/10 * *  * * ? ")
    void updateInterOrderPayStatus() {
        log.debug("国际订单处理开始.....");
        List<SlOrder> orderList = orderService.select(new SlOrder() {{
            setPaymentState(2);
            setStatus(1);
        }});
        //时间分隔点
        Date compareDate = LocalDateTimeUtils.addMinute(new Date(), -30);
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
        /****** 更新国际支付订单状态 ******/
        if (orderList != null && orderList.size() > 0) {
            for (SlOrder order : orderList) {
                if (order != null && StringUtils.isNotBlank(order.getId())) {
                    log.debug("更新订单{}的支付状态", order.getId());
                    //请求微信订单查询接口并处理订单数据
                    try {
//                        String result = HttpUtil.doPost(env.getProperty("international.pay"),order.getId());
                        String url = env.getProperty("international.pay");
                        String result = HttpUtil.doGetNotTimeOut(url+order.getId());
                        if ("1".equals(result) ) {
                            processOrders.processOrders(order.getId(), 6);
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
                //失效订单库存退回
                getProductCountBack(order);
            }
        }
        log.debug("国际订单处理结束.....");
    }
}
