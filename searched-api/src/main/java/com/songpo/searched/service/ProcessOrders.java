package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.cache.OrderCache;
import com.songpo.searched.cache.UserCache;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.*;
import com.songpo.searched.rabbitmq.NotificationService;
import com.songpo.searched.typehandler.MessageTypeEnum;
import com.songpo.searched.util.Arith;
import com.songpo.searched.util.HttpUtil;
import com.songpo.searched.util.LocalDateTimeUtils;
import com.songpo.searched.util.StreamTool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
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
    @Autowired
    private TransactionDetailService transactionDetailService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private Environment environment;
    @Autowired
    private SlMessageCenterMapper slMessageCenterMapper;
    @Autowired
    private SlSlbTypeService slSlbTypeService;
    @Autowired
    private  CmOrderService cmOrderService;
    @Autowired
    public Environment env;

    public static final Logger log = LoggerFactory.getLogger(ProcessOrders.class);

    /**
     * 支付后订单处理
     *
     * @param orderId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
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
                                    // 如果拼团人数够了 或者是虚拟用户开的团
                                    if (count == detail.getGroupPeople()||2==order.getVirtualOpen()) {
                                        Example e = new Example(SlOrder.class);
                                        e.createCriteria()
                                                .andEqualTo("serialNumber", order.getSerialNumber())
                                                .andEqualTo("paymentState", 1);
                                        // 把该订单号的所有拼团状态改成成功状态
                                        orderService.updateByExampleSelective(new SlOrder() {{
                                            // 改成拼团成功
                                            setSpellGroupStatus(2);
                                        }}, e);
                                        Example e1 = new Example(SlOrderDetail.class);
                                        e1.createCriteria()
                                                .andEqualTo("serialNumber", order.getSerialNumber());
                                        //拼团成功更改所有订单的发货状态
                                        orderDetailService.updateByExampleSelective( new SlOrderDetail(){{
                                            setShippingState(3);
                                        }},e1);
                                    } else if (count < detail.getGroupPeople()) {
                                        orderService.updateByPrimaryKeySelective(new SlOrder() {{
                                            setId(order.getId());
                                            // 拼团中
                                            setSpellGroupStatus(1);
                                        }});
                                        //拼团中shipping_state状态改成0
                                        orderDetailService.updateByPrimaryKeySelective( new SlOrderDetail(){{
                                            setId(detail.getId());
                                            setShippingState(0);
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
                                            //期数
                                            detail1.setNumberOfPeriods(record.getNumberOfPeriods());
                                            // 返款金额
                                            detail1.setReturnMoney(record.getReturnedMoney());
                                            // 创建时间
                                            detail1.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                            detail1.setPresellReturnedRecordId(record.getId());
                                            if (StringUtils.isBlank(date)) {
                                                detail1.setReturnTime(
                                                        LocalDateTimeUtils.formatTime(LocalDateTimeUtils.plus(LocalDateTime.now(),
                                                                record.getReturnedNumber(),
                                                                ChronoUnit.DAYS), "yyyy-MM-dd HH:mm:ss"));
                                                date = detail1.getReturnTime();
                                            } else {
                                                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                                detail1.setReturnTime(
                                                        LocalDateTimeUtils.formatTime(LocalDateTimeUtils.plus(LocalDateTime.parse(date, df),
                                                                record.getReturnedNumber(),
                                                                ChronoUnit.DAYS), "yyyy-MM-dd HH:mm:ss"));
                                            }
                                            this.returnsDetailMapper.insertSelective(detail1);
                                        }
                                    }
                                    break;
                                    default:
                                        break;
                            }
                            SlProduct product = this.productService.selectOne(new SlProduct() {{
                                setId(detail.getProductId());
                            }});
                            if (null != product) {
                                productService.updateByPrimaryKeySelective(new SlProduct() {{
                                    setId(product.getId());
                                    setSalesVolume(product.getSalesVolume() + detail.getQuantity());
                                }});
                            }
                            if (detail.getPrice().compareTo(BigDecimal.valueOf(0)) > 0) {
                                SlShop shop = shopService.selectOne(new SlShop() {{
                                    setId(detail.getShopId());
                                }});
                                if (null != shop) {
                                    Double money = Arith.mul(detail.getPrice().doubleValue(), detail.getQuantity());
                                    Double tm = (shop.getTotalSales() == null ? 0 : shop.getTotalSales().doubleValue()) + money;
                                    shopService.updateByPrimaryKeySelective(new SlShop() {{
                                        setId(shop.getId());
                                        setTotalSales(BigDecimal.valueOf(tm));
                                    }});
                                }
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
                            try {
                                notificationService.sendGlobalMessage(object.toJSONString(), MessageTypeEnum.SYSTEM);
                            } catch (Exception e) {
                                log.error("发送订单通知失败，{}", e);
                            }
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

                      /** 注释原因- 和分享后奖励16%冲突
                       * 2018年6月26日16:25:21
                       * heming
                       *  if (pulse > 0) {
                            // 加入明细表
                            int finalPulse = pulse;
                            transactionDetailMapper.insertSelective(new SlTransactionDetail() {{
                                // 目标id
                                setTargetId(user.getId());
                                // 订单id
                                setOrderId(orderId);
                                // 购物
                                setType(200);
                                // 返豆
                                setSilver(finalPulse);
                                // 银豆
                                setDealType(6);
                                // 插入时间
                                setCreateTime(new Date());
                                // 收入
                                setTransactionType(2);
                            }});
                            int silver = user.getSilver() + pulse;
                            userService.updateByPrimaryKeySelective(new SlUser() {{
                                setId(user.getId());
                                setSilver(silver);
                            }});
                            user.setSilver(silver);
                            userCache.put(user.getClientId(), user);

                        } **/
                    }
                }
                //给邀请人返钱+豆
                fanMoney(orderId);
            }
        }
    }

    /**
     *给邀请人返10%金额+5%搜了贝
     */
    @Transactional(rollbackFor = Exception.class)
    public void fanMoney(String orderId) {
        try {
            //获取订单表数据
            SlOrder slOrder = this.orderService.selectOne(new SlOrder() {{
                setId(orderId);
            }});
            //获取订单详情表数据
            List<SlOrderDetail> orderDetails = this.orderDetailService.select(new SlOrderDetail() {{
                setOrderId(orderId);
            }});
            //获取购买人信息
            SlUser slUserBuy = this.userService.selectOne(new SlUser(){{setId(slOrder.getUserId());}});

            for (SlOrderDetail orderDetail : orderDetails){
                //获取邀请人信息
                SlUser slUser = this.userService.selectOne(new SlUser() {{
                    setUsername(orderDetail.getInviterId());
                }});
                //订单金额的10%
                BigDecimal fanMoney = BigDecimal.valueOf(orderDetail.getPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity())).doubleValue() * 0.1);
                //订单金额的5%
                BigDecimal bean = BigDecimal.valueOf(orderDetail.getPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity())).doubleValue() * 0.05);

                slUser.setMoney(slUser.getMoney().add(fanMoney));
                if (orderDetail.getType() == 4) {
                    //给邀请人余额打钱
                    userService.updateByPrimaryKey(slUser);
                    //在order表的reMark字段记录
                    slOrder.setRemark("返给邀请人" + fanMoney + "元以及" + bean + "搜了贝");
                    orderService.updateByPrimaryKey(slOrder);
                    //1.保存金钱交易明细
                    SlTransactionDetail detail = new SlTransactionDetail();
                    detail.setSourceId(slOrder.getUserId());
                    detail.setTargetId(slUser.getId());
                    detail.setOrderId(orderId);
                    detail.setType(104);
                    detail.setMoney(fanMoney);
                    detail.setDealType(1);
                    detail.setTransactionType(2);
                    detail.setCreateTime(new Date());
                    transactionDetailService.insertSelective(detail);

                    //2.保存搜了币交易明细
                    //2.1 保存邀请人搜了币交易明细
                    //获取搜了贝类型
                    SlSlbType slSlbType = this.slSlbTypeService.selectOne(new SlSlbType(){{
                        setProductId(orderDetail.getProductId());
//                        setPrice(orderDetail.getPrice());
                    }});
                    //保存邀请人搜了贝以及交易记录
                    cmOrderService.saveSlbInvite(slUser,slOrder,slSlbType,bean);

                    //2.2 保存购买人搜了币交易明细
                    cmOrderService.saveSlbBuy(slSlbType,slOrder,orderDetail);
                }
                if (5 != slOrder.getPaymentChannel()){
                    /**
                     * 极光推送
                     */
                    String content = "尊敬的队长,"+slUserBuy.getUsername()+"购买了商品"+slOrder.getTotalAmount().doubleValue()+"" +
                            "元,成功获得分润奖励"+fanMoney+"元、"+bean+"搜了币，请打开APP“我的账本-钱包”查看奖励余额";
                    sendPush(slUser.getUsername().toString(),content,2,"邀请返现");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 极光推送，调用PHP接口
     * @date  2018年6月20日16:15:05
     * @param username 推送用户标识，使用user表中的username字段作为标识
     * @param content  需要推送的内容
     * @param type  推送类型，目前支持 1.系统通知 2.账户通知 3.物流通知 4.拼团通知 5.区块链佣金通知 6-10备用
     * @param title 推送消息标题
     */
    public void  sendPush(String username ,String content,int type ,String title ) {
             SlUser slUser = userService.selectOne( new SlUser(){{
                 setUsername(Integer.parseInt(username));
             }});
            try {
                URL url = new URL(environment.getProperty("push.php.url"));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                StringBuffer params = new StringBuffer();
                // 提交模式
                conn.setRequestMethod("POST");
                // 是否输入参数
                conn.setDoOutput(true);
                // 表单参数与get形式一样
                params.append("user_id").append("=").append(slUser.getId()).append("&").append("username")
                        .append("=").append(username).append("&").append("type").append("=").append(type)
                        .append("&").append("content").append("=").append(content).append("&").append("title")
                        .append("=").append(title);
                byte[] bypes = params.toString().getBytes();
                // 输入参数
                conn.getOutputStream().write(bypes);
                InputStream inStream=conn.getInputStream();
                String result = new String(StreamTool.readInputStream(inStream), "utf-8");
                if (result.indexOf("消息推送成功") > 0) {
                    log.debug("推送用户ID为{}的消息成功",slUser.getId());
                    slMessageCenterMapper.insertSelective( new SlMessageCenter() {{
                        setUsername(username);
                        setTitle(title);
                        setContent(content);
                        setType((byte)2);
                    }});
                }else {
                    log.debug("推送用户ID为{}的消息失败",slUser.getId());
                }
            } catch (Exception e) {
                log.error("推送用户ID{}消息失败",slUser.getId(),e);
                e.printStackTrace();
            }
    }
    /**
     * 支付后拍卖订单处理
     *
     * @param orderId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void saleOrders(String orderId, int payType) {
        SlOrder order = this.orderService.selectOne(new SlOrder() {{
            setOutOrderNumber(orderId);
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
                    //支付后修改订单状态接口: http://39.107.241.218:8082/api/orderProcessing?orderId=***&type=***
                    //orderId :子订单表ID  type:支付类型 1微信 2支付宝
                    String url = env.getProperty("sale.pay")+"?orderId="+orderId+"&type="+payType;
                    HttpUtil.doGet(url);
                }
            }
        }
    }
}
