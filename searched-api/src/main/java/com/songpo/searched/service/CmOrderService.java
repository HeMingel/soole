package com.songpo.searched.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.alipay.service.AliPayService;
import com.songpo.searched.cache.OrderCache;
import com.songpo.searched.cache.ProductCache;
import com.songpo.searched.cache.ProductRepositoryCache;
import com.songpo.searched.cache.UserCache;
import com.songpo.searched.constant.ActivityConstant;
import com.songpo.searched.constant.BaseConstant;
import com.songpo.searched.constant.SalesModeConstant;
import com.songpo.searched.constant.VirtualUserConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.*;
import com.songpo.searched.rabbitmq.NotificationService;
import com.songpo.searched.typehandler.*;
import com.songpo.searched.util.*;
import com.songpo.searched.wxpay.service.WxPayService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service

public class CmOrderService {

    public static final Logger log = LoggerFactory.getLogger(CmOrderService.class);

    @Autowired
    private ProductRepositoryService productRepositoryService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CmOrderMapper cmOrderMapper;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private SlUserAddressMapper slUserAddressMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private SlActivityProductMapper activityProductMapper;
    @Autowired
    private ProductRepositoryCache repositoryCache;
    @Autowired
    private ProductCache productCache;
    @Autowired
    private OrderCache orderCache;
    @Autowired
    private SlReturnsDetailMapper returnsDetailMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SlMessageMapper messageMapper;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private SlEmsMapper emsMapper;
    @Autowired
    private HttpRequest expressUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private UserCache userCache;
    @Autowired
    private SlTransactionDetailMapper transactionDetailMapper;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProcessOrders processOrders;
    @Autowired
    private TransactionDetailService transactionDetailService;
    @Autowired
    private SlProductNoMailMapper slProductNoMailMapper;
    @Autowired
    private  ProductNoMailService productNoMailService;
    @Autowired
    private CmRedPacketMapper cmRedPacketMapper;
    @Autowired
    private SlOrderExtendMapper slOrderExtendMapper;
    @Autowired
    private  RedPacketService redPacketService;
    @Autowired
    private SlSlbTypeService slSlbTypeService;
    @Autowired
    private UserSlbService  userSlbService;
    @Autowired
    private SlSlbTransactionMapper slbTransactionMapper;
    @Autowired
    private SlTotalPoolMapper slTotalPoolMapper;
    @Autowired
    private SlTotalPoolDetailMapper slTotalPoolDetailMapper;
    @Autowired
    private CmTotalPoolService cmTotalPoolService;
    @Autowired
    private SlActivitySeckillMapper slActivitySeckillMapper;
    @Autowired
    private SlProductRepositoryMapper slProductRepositoryMapper;
    @Autowired
    private  ThirdPartyWalletService thirdPartyWalletService;
    @Autowired
    private SlPhoneZoneMapper slPhoneZoneMapper;
    @Autowired
    public Environment env;
    @Autowired
    private SlOrderHandleMapper slOrderHandleMapper;
    @Autowired
    private  CmOrderHandleMapper cmOrderHandleMapper;
    @Autowired
    private  CmSlbTransactionMapper cmSlbTransactionMapper;
    @Autowired
    private SlOrderMapper slOrderMapper;
    /**
     * 多商品下单
     *
     * @param request
     * @param detail            商品1的规格id|商品1的商品数量|商品1买家留言|分享人id(如果是分享奖励的话)
     * @param shippingAddressId 当前用户选择的收货地址id
     * @param postFee           邮费
     * @param inviterId         邀请人ID
     * @return
     */
    @Transactional
    public BusinessMessage addOrder(HttpServletRequest request, String[] detail, String shippingAddressId, String postFee, int inviterId) {
        log.debug("request = [" + request + "], detail = [" + detail + "], shippingAddressId = [" + shippingAddressId + "]");
        BusinessMessage message = new BusinessMessage();
        double money = 0.00+Double.parseDouble(postFee);

        int pulse = 0;
        SlUser user = loginUserService.getCurrentLoginUser();
        if (null != user) {
            //查询邀请人并且是区块链团队长
            int finalInviterId = inviterId;
            SlUser slUser = userService.selectOne(new SlUser() {{
                setUsername(finalInviterId);
                setCaptain(2);
            }});
            if(inviterId==user.getUsername()||slUser==null||inviterId<=0){
                inviterId=7777;
            }

            SlOrder slOrder = new SlOrder();
            String orderNum = OrderNumGeneration.getOrderIdByUUId();// 生成订单编号
            slOrder.setId(formatUUID32());
            slOrder.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            slOrder.setSerialNumber(orderNum);// 订单编号
            slOrder.setUserId(user.getId());// 用户Id
            SlUserAddress slUserAddress = this.slUserAddressMapper.selectOne(new SlUserAddress() {{
                setId(shippingAddressId);
                setUserId(user.getId());
            }});
            if (null != slUserAddress) {
                slOrder.setProvince(slUserAddress.getProvince());// 订单省的地址
                slOrder.setCity(slUserAddress.getCity()); // 订单市的收货地址
                slOrder.setCounty(slUserAddress.getCounty()); // 订单区的收货地址
                slOrder.setDetailed(slUserAddress.getDetailed()); // 订单详细收货地址
                slOrder.setConsigneename(slUserAddress.getName());// 收货人姓名
                slOrder.setConsigneephone(slUserAddress.getPhone());// 收货人的联系方式
                int co = orderService.insertSelective(slOrder);// 插入订单表
                //订单加入redis 有效期为一天
                orderCache.put(slOrder.getId(), slOrder, 1L, TimeUnit.DAYS);
                if (co > 0) {
                    outer:
                    for (String aa : detail) {
                        String[] bb = aa.split(",");
                        inner:
                        for (String cc : bb) {
                            String repositoryId = cc.split("\\|")[0];
                            int quantity = Integer.valueOf(cc.split("\\|")[1]);
                            String buyerMessage = cc.split("\\|")[2];
                            String activityProductId = cc.split("\\|")[3];
                            //查询redis
//                            SlProductRepository repository = repositoryCache.get(repositoryId);
//                            //如果redis中没有这个仓库信息
//                            if (SLStringUtils.isEmpty(repository)) {
//                                // 根据仓库ID 去查询商品的详细信息(选好规格的价格,金豆等等)
//                                repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
//                                    setId(repositoryId);
//                                }});
//                                //向redis中加入这个仓库信息
//                                repositoryCache.put(repository.getId(), repository);//没有的话查询数据库放到redis
//                            }
                            SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                                setId(repositoryId);
                            }});
                            // 仓库存在 并且加入订单的商品大于0
                            if (null != repository && quantity > 0) {
                                //查询redis中有没有这个商品信息
//                                SlProduct slProduct = productCache.get(repository.getProductId());
//                                //如果没有 就查一遍
//                                if (SLStringUtils.isEmpty(slProduct)) {
//                                    SlProductRepository finalRepository1 = repository;
//                                    slProduct = this.productService.selectOne(new SlProduct() {{
//                                        setId(finalRepository1.getProductId());
//                                        setSoldOut(true);
//                                    }});
//                                }
                                SlProduct slProduct = this.productService.selectOne(new SlProduct() {{
                                    setId(repository.getProductId());
                                    setSoldOut(true);
                                }});
                                if (null != slProduct) {
//                                    SlProduct finalSlProduct = slProduct;
                                    //查询活动商品信息
                                    SlActivityProduct slActivityProduct = this.cmOrderMapper.selectActivityProductByRepositoryId(repository.getId(), activityProductId);
                                    if (null != slActivityProduct) {
                                        //如果是无活动就不需要校验时间是否符合
                                        if (slActivityProduct.getActivityId().equals(ActivityConstant.NO_ACTIVITY)) {
                                            //无活动就没有活动到期这一说
                                            productCache.put(slProduct.getId(), slProduct);
                                        } else {
                                            // 算出失效时间 活动结束时间 - 当前时间
                                            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                            LocalDate ldt = LocalDate.parse(slActivityProduct.getEndTime(), df);
                                            ZoneId zone = ZoneId.systemDefault();
                                            Instant instant = ldt.atStartOfDay().atZone(zone).toInstant();
                                            Date date = Date.from(instant);
                                            // 把查询出来的商品信息放入redis中 插入失效时间
                                            productCache.put(slProduct.getId(), slProduct);
                                            productCache.expireAt(slProduct.getId(), date);
                                        }
                                        // 判断当前活动是否在有效期内
                                        if (productCache.hasKey(slProduct.getId())) {
                                            // 查询当前用户该商品的已生成订单的商品数量之和
                                            int counts = this.cmOrderMapper.selectOrdersCount(slProduct.getId(), user.getId(), slActivityProduct.getId());
                                            //单用户购买限制数量 - 他已经下单的商品数量 >= 这次加入订单的数量
                                            if (slActivityProduct.getRestrictCount() - counts >= quantity) {
                                                SlActivitySeckill seckill = null;
                                                if (slProduct.getSalesModeId().equals("8")){
                                                    //秒杀表总库存
                                                     seckill = slActivitySeckillMapper.selectOne(new SlActivitySeckill(){{
                                                        setProductOldId(slProduct.getId());
                                                         setEnable(true);
                                                    }});
                                                    if (seckill.getTotalCount() - quantity < 0) {
                                                        log.error("当前秒杀库存不足");
                                                        message.setMsg(slProduct.getName() + "当前秒杀库存不足");
                                                        message.setSuccess(false);
                                                        break outer;
                                                    }
                                                }
                                                //库存的数量 > 他这次加入订单的数量
                                                if (repository.getCount() - quantity >= 0) {
                                                    //修改价格 如果是限时秒杀就改成秒杀价
                                                    BigDecimal price = slProduct.getSalesModeId().equals("8") ? repository.getSeckillPrice() : repository.getPrice();
                                                    // 钱相加 用于统计和添加到订单表扣除总钱里边
                                                    money += price.doubleValue() * quantity;
                                                    // 如果邮费不为空
//                                                    if (slProduct.getPostage().doubleValue() > 0) {
//                                                        money = money + slProduct.getPostage().doubleValue();
//                                                    }
                                                    // 了豆相加  用于统计和添加到订单表扣除了豆里边
                                                    /**
                                                     * 预售模式修改 由 豆+钱  改为纯钱模式
                                                     */
                                                    if (repository.getSilver() > 0 && !"2".equals(slProduct.getSalesModeId()) ) {
                                                        pulse += repository.getSilver() * quantity;
                                                    }
//                                                SlProductRepository finalRepository = repository;
                                                    int finalInviterId1 = inviterId;

                                                    orderDetailService.insertSelective(new SlOrderDetail() {{
                                                        setId(formatUUID32());
                                                        setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                                        //邀请人ID
                                                        setInviterId(finalInviterId1);
                                                        // 拼团订单去这个属性判断是哪个人的
                                                        setCreator(slOrder.getUserId());
                                                        // 商品名称
                                                        setProductName(slProduct.getName());
                                                        // 商品图片
                                                        setProductImageUrl(slProduct.getImageUrl());
                                                        // 订单ID
                                                        setOrderId(slOrder.getId());
                                                        // 邮费
                                                        setPostFee(slProduct.getPostage());
                                                        // 商品数量
                                                        setQuantity(quantity);
                                                        // 单个商品价格
                                                        setPrice(price);
                                                        // 商品规格名称
                                                        setProductDetailGroupName(repository.getProductDetailGroupName());
                                                        // 活动id
                                                        setActivityProductId(slActivityProduct.getId());
                                                        // 商品ID
                                                        setProductId(repository.getProductId());
                                                        // 店铺唯一标识
                                                        setShopId(repository.getShopId());
                                                        // 店铺仓库ID
                                                        setRepositoryId(repository.getId());
                                                        // 扣除单个商品了豆数量
                                                        setDeductTotalSilver(repository.getSilver());
                                                        // 添加买家留言
                                                        setBuyerMessage(buyerMessage);
                                                        // 订单类型
                                                        switch (Integer.valueOf(slProduct.getSalesModeId())) {
                                                            //拼团
                                                            case SalesModeConstant.SALES_MODE_GROUP:
                                                                setType(2);
                                                                break;
                                                            case SalesModeConstant.SALES_MODE_PRESELL:
                                                                setType(3);
                                                                break;
                                                            case SalesModeConstant.SALES_MODE_BEANS:
                                                                setType(6);
                                                                break;
                                                            case SalesModeConstant.SALES_MODE_REBATE:
                                                                setType(5);
                                                                break;
                                                            case SalesModeConstant.SALES_MODE_ONE:
                                                                setType(4);
                                                                break;
                                                            case SalesModeConstant.SALES_MODE_NORMAL:
                                                                setType(1);
                                                                break;
                                                            case SalesModeConstant.SALES_MODE_SECKILL:
                                                                setType(8);
                                                                break;
                                                            case SalesModeConstant.SALES_MODE_SHARE:
                                                                setType(7);
                                                                break;
                                                        }
                                                        // 订单编号
                                                        setSerialNumber(orderNum);
                                                        //  消费奖励
                                                        if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_REBATE) {
                                                            if (repository.getReturnCashMoney().compareTo(new BigDecimal(0)) > 0) {
                                                                // 消费返利金额
                                                                setReturnCashMoney(repository.getReturnCashMoney());
                                                            }
                                                        }
                                                        //  分享奖励
                                                        if (slActivityProduct.getActivityId().equals(ActivityConstant.RECOMMEND_AWARDS_ACTIVITY)) {
                                                            // 插入分享人id
                                                            String shareOfPeopleId = cc.split("\\|")[4];
                                                            setShareOfPeopleId(shareOfPeopleId);
                                                            //分享奖励金额
                                                            setReturnCashMoney(repository.getReturnCashMoney());
                                                        }
                                                        // 查询当前用户的支付订单
                                                        Boolean flag = orderService.exist(new SlOrder() {{
                                                            setUserId(user.getId());
                                                            // 已支付
                                                            setPaymentState(1);
                                                        }});
                                                        //  新人专享
                                                        //判断是否为首单
                                                        if (flag.equals(false) && slActivityProduct.getActivityId().equals(ActivityConstant.NEW_PEOPLE_ACTIVITY)) {
                                                            // 如果是第一单的情况下 需要加上 首单奖励
                                                            setPlaceOrderReturnPulse(repository.getPlaceOrderReturnPulse() * quantity + repository.getFirstOrderPulse());
                                                        } else {
                                                            // 返了豆数量只限纯金钱模式
                                                            setPlaceOrderReturnPulse(repository.getPlaceOrderReturnPulse() * quantity);
                                                        }
                                                    }});
                                                    // 如果库存为0 的话就下架了
                                                    Example example = new Example(SlActivityProduct.class);
                                                    example.createCriteria()
                                                            .andGreaterThan("count", 0)
                                                            .andEqualTo("id", slActivityProduct.getId());
                                                    // 商品总库存 - 本次加入订单的数量
                                                    int activityProductCount = slActivityProduct.getCount() - quantity;
                                                    this.activityProductMapper.updateByExampleSelective(new SlActivityProduct() {{
                                                       /**
                                                        * 注释原因:商品库存为0不自动下架
                                                       if (activityProductCount == 0) {
                                                            setEnabled(false);
                                                        }*/
                                                        //活动总商品上架数量 - 本次购买的数量
                                                        setCount(slActivityProduct.getCount() - quantity);
                                                    }}, example);
                                                    /**
                                                    this.productService.updateByPrimaryKeySelective(new SlProduct(){{
                                                        setId(slProduct.getId());
                                                        if (activityProductCount == 0) {
                                                            setSoldOut(false);
                                                        }
                                                    }});
                                                     */
                                                    // 当前库存 - 本次该商品规格下单库存
                                                    repository.setCount(repository.getCount() - quantity);
                                                    //更新限时秒杀的库存
                                                    if (slProduct.getSalesModeId().equals("8")){
                                                        //更新秒杀表总库存
                                                        seckill.setTotalCount(seckill.getTotalCount()-quantity);
                                                        slActivitySeckillMapper.updateByPrimaryKey(seckill);
                                                        //设置规格表库存
                                                        repository.setSekillCount(repository.getSekillCount()-quantity);
                                                        slProductRepositoryMapper.updateByPrimaryKeySelective( new SlProductRepository(){{
                                                            setId(repository.getId());
                                                            setSekillCount(repository.getSekillCount());
                                                        }});
                                                    }
                                                    // 更新redis中该商品规格的库存
                                                    repositoryCache.put(repository.getId(), repository);
                                                    // 更新数据库该商品规格的库存
                                                    int updateCount = this.cmOrderMapper.reduceNumber(repository.getId(), repository.getCount());

                                                    // 把虚拟销量加上
                                                    productService.updateByPrimaryKeySelective(new SlProduct() {{
                                                        setId(slProduct.getId());
                                                        setSalesVirtual(slProduct.getSalesVirtual() + quantity);
                                                    }});
                                                    if (updateCount > 0) {
                                                        message.setMsg("订单生成成功");
                                                        message.setSuccess(true);
                                                    }
                                                } else {
                                                    log.error("当前库存不足");
                                                    message.setMsg(slProduct.getName() + "当前库存不足");
                                                    message.setSuccess(false);
                                                    break outer;
                                                }
                                            } else {
                                                log.error("已超过最大购买数量");
                                                message.setMsg(slProduct.getName() + "已超过最大购买数量");
                                                message.setSuccess(false);
                                                break outer;
                                            }
                                        } else {
                                            log.error("活动商品时间错误");
                                            message.setMsg(slProduct.getName() + "活动商品时间错误");
                                            message.setSuccess(false);
                                            break outer;
                                        }
                                    } else {
                                        log.error("商品已下架");
                                        message.setMsg(slProduct.getName() + "商品已下架");
                                        message.setSuccess(false);
                                        break outer;
                                    }
                                } else {
                                    log.error("该商品不存在或已下架");
                                    message.setMsg(slProduct.getName() + "该商品不存在或已下架");
                                    message.setSuccess(false);
                                    break outer;
                                }
                            }
                        }
                    }
                    if (message.getSuccess() == false) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    } else {
                        // 更新订单总价和总豆
                        double finalMoney = money;
                        int finalPulse = pulse;
                        this.orderService.updateByPrimaryKeySelective(new SlOrder() {{
                            setId(slOrder.getId());
                            setTotalAmount(BigDecimal.valueOf(finalMoney));
                            setDeductTotalPulse(finalPulse);
                        }});
                        String sign = MD5Util.md5encode(slOrder.getId()+MD5Util.md5encode(slOrder.getId()+String.valueOf(money),"utf-8"),"utf-8");
                        Map<String, String> map = new HashMap<>();
                        map.put("order_num", slOrder.getId());
                        map.put("total_amount", String.valueOf(money));
                        map.put("deduct_total_pulse", String.valueOf(pulse));
                        map.put("sign", String.valueOf(sign));
                        message.setData(map);
                    }
                }
            } else {
                log.error("收货地址不存在");
                message.setMsg("收货地址不存在");
                return message;
            }
        } else {
            log.error("用户不存在");
            message.setMsg("用户不存在");
            return message;
        }
        return message;
    }

    /**
     * 单商品下单
     *
     * @param request
     * @param response
     * @param repositoryId      规格id
     * @param quantity          数量
     * @param shareOfPeopleId   分享人Id
     * @param serialNumber      (如果是拼别人的团,要传开团的订单号)
     * @param groupMaster       (如果是拼别人的团,要传开团人的Id)
     * @param shippingAddressId 用户地址id
     * @param buyerMessage      买家留言
     * @param activityProductId 活动商品Id
     * @param spellGroupType    1 : 普通活动价 2:个人价
     * @param virtualOpen        1 : 正常用户开团 2:虚拟用户开团
     * @param postFee             邮费
     * @param inviterId             邀请人ID
     * @return
     */
    @Transactional
    public BusinessMessage purchaseAddOrder(HttpServletRequest request,
                                            HttpServletResponse response,
                                            String repositoryId,
                                            Integer quantity,
                                            String shareOfPeopleId,
                                            String serialNumber,
                                            String groupMaster,
                                            String shippingAddressId,
                                            String buyerMessage,
                                            String activityProductId,
                                            int spellGroupType,
                                            Integer virtualOpen,
                                            String postFee, int inviterId) {
        log.debug("request = [" + request + "], response = [" + response + "], repositoryId = [" + repositoryId + "], quantity = [" + quantity + "], postFee = ["+postFee+"]");
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (null != user) {
            //查询邀请人并且是区块链团队长
            int finalInviterId = inviterId;
            SlUser slUser = userService.selectOne(new SlUser() {{
                setUsername(finalInviterId);
                setCaptain(2);
            }});
            if(inviterId==user.getUsername()||slUser==null||inviterId<=0){
                inviterId=7777;
            }

//                SlProductRepository repository = new SlProductRepository();
//                //1.先从redis中去取该商品规格的详细参数
//                repository = this.repositoryCache.get(repositoryId);
//                //2.如果repository为null就去数据库中查询一遍放入repository对象中
//                if (SLStringUtils.isEmpty(repository)) {
//                    repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
//                        setId(repositoryId);
//                    }});
//                    //3.把查询出来的商品规格放入redis中
//                    this.repositoryCache.put(repositoryId, repository);
//                }
            SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                setId(repositoryId);
            }});
            //4.如果查询出来不为空就去查询商品信息
            if (null != repository) {
//                    SlProduct slProduct = new SlProduct();
//                    //5.先从redis中取商品信息的详情
//                    slProduct = this.productCache.get(repository.getProductId());
//                    //6.如果为空就从数据库中查询一下商品信息
//                    if (SLStringUtils.isEmpty(slProduct)) {
//                        SlProductRepository finalRepository = repository;
//                        slProduct = this.productService.selectOne(new SlProduct() {{
//                            setId(finalRepository.getProductId());
//                        }});
//                    }
                SlProduct slProduct = this.productService.selectOne(new SlProduct() {{
                    setId(repository.getProductId());
                }});
                //7.如果商品存在的话
                if (null != slProduct) {
                    //把邮费加上
                    slProduct.setPostage(BigDecimal.valueOf(Double.parseDouble(postFee)));
                    // 把虚拟销量加上
                    productService.updateByPrimaryKeySelective(new SlProduct() {{
                        setId(slProduct.getId());
                        setSalesVirtual(slProduct.getSalesVirtual() + quantity);
                    }});
                    //查询活动商品信息
                    SlActivityProduct activityProduct = this.cmOrderMapper.selectActivityProductByRepositoryId(repositoryId, activityProductId);
                    if (null != activityProduct) {
                        //如果是无活动就不需要校验时间是否符合
                        if (activityProduct.getActivityId().equals(ActivityConstant.NO_ACTIVITY)) {
                            //无活动就没有活动到期这一说
                            productCache.put(slProduct.getId(), slProduct);
                        } else {
                            // 算出失效时间 活动结束时间 - 当前时间
                            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDate ldt = LocalDate.parse(activityProduct.getEndTime(), df);
                            ZoneId zone = ZoneId.systemDefault();
                            Instant instant = ldt.atStartOfDay().atZone(zone).toInstant();
                            Date date = Date.from(instant);
                            // 把查询出来的商品信息放入redis中 插入失效时间
                            productCache.put(slProduct.getId(), slProduct);
                            productCache.expireAt(slProduct.getId(), date);
                        }
                        // 判断当前活动是否在有效期内
                        if (productCache.hasKey(slProduct.getId())) {
                            // 此活动拼团商品当前用户的下单商品数量
                            int count = this.cmOrderMapper.selectOrdersCount(slProduct.getId(), user.getId(), activityProduct.getId());
                            // 本次下单的商品数量 + 当前用户的该商品下单量 <= 商品限制购买单数
                            if (quantity + count <= activityProduct.getRestrictCount()) {
                                SlActivitySeckill seckill = null;
                                if (slProduct.getSalesModeId().equals("8")){
                                    //秒杀表总库存
                                    seckill = slActivitySeckillMapper.selectOne(new SlActivitySeckill(){{
                                        setProductOldId(slProduct.getId());
                                        setEnable(true);
                                    }});
                                    if (seckill.getTotalCount() - quantity < 0) {
                                        log.error("当前秒杀库存不足");
                                        message.setMsg(slProduct.getName() + "当前秒杀库存不足");
                                        message.setSuccess(false);
                                        return message;
                                    }
                                }
                                // 本规格下的库存 >= 本次下单的商品数量
                                if (repository.getCount() >= quantity) {
                                    //  ====== 拼团订单 ======
                                    //8.如果销售模式是拼团订单的话
                                    Integer saleModes = Integer.parseInt(slProduct.getSalesModeId());
                                    if ( saleModes  == SalesModeConstant.SALES_MODE_GROUP) {
                                        //如果是虚拟开团 则按照个人拼团流程走 价格使用拼团价
                                        if(2==(virtualOpen!=2?1:virtualOpen)){
                                            // ==== 按照自己开团流程走 ======
                                            //生成订单号
                                            String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                            message = processingOrders(user.getId(), orderNum, activityProduct, user.getId(), shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 2, buyerMessage, spellGroupType, inviterId,virtualOpen);
                                            //加入虚拟开团的用户在remark字段添加虚拟团主头像
                                            Map<String, String> map  = (Map<String, String>) message.getData();
                                            String orderId = map.get("order_num");
                                            VirtualUserConstant vuc = new VirtualUserConstant();
                                            String url = vuc.URLAVATAR+(int)(Math.random()*vuc.IMAGENUM)+".png";
                                            this.orderService.updateByPrimaryKeySelective(new SlOrder(){{
                                                setId(orderId);
                                                setRemark(url);
                                            }});
                                        }else{
                                            //8(1).如果是拼团订单的话 拼团订单不为空 && 开团团主不为空的情况下
                                            if (!StringUtils.isEmpty(serialNumber) && !StringUtils.isEmpty(groupMaster)) {
                                                //查询这个团主的订单是否存在
                                                int count1 = this.orderService.selectCount(new SlOrder() {{
                                                    setUserId(groupMaster);
                                                    setSerialNumber(serialNumber);
                                                    setSpellGroupStatus(1);
                                                }});
                                                //如果存在 && 只有一条
                                                if (count1 == 1) {
                                                    //查询当前用户是否参加过这次的团
                                                    Boolean f = this.orderService.exist(new SlOrder() {{
                                                        setSerialNumber(serialNumber);
                                                        setUserId(user.getId());
                                                    }});
                                                    //如果不存在的话
                                                    if (f.equals(false)) {
                                                        // ======= 是团员的话 =======
                                                        message = processingOrders(user.getId(), serialNumber, activityProduct, groupMaster, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 2, buyerMessage, spellGroupType, inviterId,virtualOpen);
                                                    } else {
                                                        message.setMsg("您已参加过该团,请勿重复参加");
                                                        return message;
                                                    }
                                                } else {
                                                    message.setMsg("拼团失败或不存在");
                                                    return message;
                                                }
                                            } else {
                                                // ==== 如果是他自己开的团 ======
                                                //生成订单号
                                                String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                                message = processingOrders(user.getId(), orderNum, activityProduct, user.getId(), shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 2, buyerMessage, spellGroupType, inviterId,virtualOpen);
                                            }
                                        }

                                    }
                                    // ====== 如果是预售模式 ======
                                    else if (saleModes  == SalesModeConstant.SALES_MODE_PRESELL) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 3, buyerMessage, 1, inviterId,virtualOpen);
                                    }
                                    // ====== 如果是助力购 ======
                                    else if (saleModes  == SalesModeConstant.SALES_MODE_ONE) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 4, buyerMessage, 1, inviterId,virtualOpen);
                                    }
                                    // ====== 消费返利 ======
                                    else if (saleModes  == SalesModeConstant.SALES_MODE_REBATE) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 5, buyerMessage, 1, inviterId,virtualOpen);
                                        //  ====== 豆赚 ======
                                    } else if (saleModes  == SalesModeConstant.SALES_MODE_BEANS) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 6, buyerMessage, 1, inviterId,virtualOpen);
                                        //  ====== 普通商品 ======
                                    } else if (saleModes  == SalesModeConstant.SALES_MODE_NORMAL) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 1, buyerMessage, 1, inviterId,virtualOpen);
                                        //  =====分享奖励商品 ====
                                    } else if (saleModes  == SalesModeConstant.SALES_MODE_SHARE ) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 7, buyerMessage, 1, inviterId,virtualOpen);
                                    }
                                    else if (saleModes  == SalesModeConstant.SALES_MODE_SECKILL) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 8, buyerMessage, 1, inviterId,virtualOpen);
                                    }
                                    if (message.getSuccess() == false) {
                                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                        message.setData("");
                                        return message;
                                    }
                                } else {log.debug("当前规格的商品,库存不足");
                                    message.setMsg("当前规格的商品,库存不足");
                                    message.setSuccess(false);
                                    return message;

                                }
                            } else {
                                log.debug("已超出该商品的下单商品数量");
                                message.setMsg("已超出该商品的下单商品数量");
                                message.setSuccess(false);
                                return message;
                            }
                        } else {
                            log.debug("活动商品时间错误");
                            message.setMsg("活动商品时间错误");
                            return message;
                        }
                    } else {
                        log.debug("商品已下架");
                        message.setMsg("商品已下架");
                        return message;
                    }
                } else {
                    log.debug("该商品不存在");
                    message.setMsg("该商品不存在");
                    return message;
                }
            } else {
                log.debug("该规格不存在");
                message.setMsg("该规格不存在");
                return message;
            }
        } else {
            log.debug("用户不存在");
            message.setMsg("用户不存在");
            return message;
        }
        return message;
    }


    /**
     * 查询我的订单列表
     *
     * @param status   订单状态
     * @param pageNum
     * @param pageSize
     * @return
     */
    public BusinessMessage findList(Integer status, Integer pageNum, Integer pageSize) {
        BusinessMessage message = new BusinessMessage();
        try {
            SlUser user = loginUserService.getCurrentLoginUser();
            if (null != user) {
                if (null == pageNum || pageNum <= 1) {
                    pageNum = 1;
                }
                if (null == pageSize || pageSize <= 1) {
                    pageSize = 10;
                }
                // 设置分页参数
                PageHelper.startPage(pageNum, pageSize);
                // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓以下按订单查询组合显示↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
//                Example example = new Example(SlOrder.class);
//                Example.Criteria criteria = example.createCriteria();
//                criteria.andEqualTo("userId", user.getId());
//                if (status != null) {
//                    criteria.andEqualTo("paymentState", status);
//                }
//                List<SlOrder> orders = this.orderService.selectByExample(example);
//                List<Map<String, List<SlOrderDetail>>> list = new ArrayList<>();
//                Map<String, List<SlOrderDetail>> map = new HashMap<>();
//                for (SlOrder order : orders) {
//                    List<SlOrderDetail> orderDetails = this.orderDetailService.select(new SlOrderDetail() {{
//                        setSerialNumber(order.getSerialNumber());
//                    }});
//                    map.put(order.getSerialNumber(), orderDetails);
//                    list.add(map);
//                }
                // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑一下按订单查询组合显示↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
                List<Map<String, Object>> list = this.cmOrderMapper.findList(user.getId(), status);
//                for (Map map : list) {
//                    Object type = map.get("type");
//                    Object serialNumber = map.get("serialNumber");
//                    if (type.equals(2)) {//拼团订单
//                        // 拼团订单筛选参与会员头像
//                        map.put("userAvatarList", this.cmOrderMapper.findUserAvatar(serialNumber));
//                    }
//                }
                message.setMsg("查询成功");
                message.setSuccess(true);
                message.setData(new PageInfo<>(list));
            } else {
                message.setMsg("用户不存在");
            }
        } catch (Exception e) {
            log.error("查询失败:{}", e);
        }
        return message;
    }

    /**
     * 我的订单详情(这里只是查的订单的一些详情,关联的商品信息前端从上一页面带过来)
     *
     * @param id
     * @return
     */
    public BusinessMessage orderInfo(String id) {
        log.debug("orderId = [" + id + "]");
        BusinessMessage message = new BusinessMessage();
        try {
            SlUser user = this.loginUserService.getCurrentLoginUser();
            Map<String, Object> orderInfo = this.cmOrderMapper.selectMyOrderInfo(user.getId(), id);
            if (null != orderInfo) {
                if (StringUtils.isNotBlank(orderInfo.get("activityProductId").toString())) {
                    SlActivityProduct activityProduct = this.activityProductMapper.selectOne(new SlActivityProduct() {{
                        setId(orderInfo.get("activityProductId").toString());
                    }});
                    if (activityProduct.getActivityId().equals(ActivityConstant.NO_ACTIVITY)) {
                        orderInfo.put("join", false);
                    } else {
                        orderInfo.put("join", true);
                    }
                } else {
                    message.setMsg("数据出错");
                    return message;
                }
                message.setData(orderInfo);
                message.setSuccess(true);
                message.setMsg("查询成功");
            }
        } catch (Exception e) {
            log.error("查询失败 {}", e);
        }
        return message;
    }

    /**
     * 取消订单/确定收货
     *
     * @param state   订单状态
     * @param orderId 订单id state = 5 参数为 orderDetailId  state = 102 参数为orderId
     * @return
     */
    public BusinessMessage cancelAnOrder(String orderId, String state) {
        log.debug("orderId = [" + orderId + "]");
        BusinessMessage message = new BusinessMessage();
        int p = 0;
        try {
            SlUser user = loginUserService.getCurrentLoginUser();
            if (null != user) {
                switch (Integer.parseInt(state)) {
                    case 102:
                        SlOrder order = orderService.selectOne(new SlOrder() {{
                            setId(orderId);
                        }});
                        if (null != order) {
                            Example example = new Example(SlOrder.class);
                            example.createCriteria()
                                    .andEqualTo("id", orderId)
                                    .andEqualTo("userId", user.getId());
                            orderService.updateByExampleSelective(new SlOrder() {{
                                //取消订单
                                setPaymentState(102);
                            }}, example);
                            List<SlOrderDetail> detailList = this.orderDetailService.select(new SlOrderDetail() {{
                                setOrderId(orderId);
                                //setIsVirtualSpellGroup((byte) 1);
                            }});
                            for (SlOrderDetail detail : detailList) {
                                p += detail.getPlaceOrderReturnPulse();
                                SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                                    setId(detail.getRepositoryId());
                                }});
                                // 把订单中的商品数量加到商品库存中去
                                this.productRepositoryService.updateByPrimaryKeySelective(new SlProductRepository() {{
                                    setId(repository.getId());
                                    setCount(repository.getCount() + detail.getQuantity());
                                }});
                                repository.setCount(repository.getCount() + detail.getQuantity());
                                //更新redis
                                this.repositoryCache.put(repository.getId(), repository);
                            }
                            message.setSuccess(true);
                            message.setMsg("取消成功");
                        } else {
                            message.setMsg("订单不存在");
                        }
                        break;
                    case 5:
                        Boolean ex = this.orderDetailService.exist(new SlOrderDetail() {{
                            setId(orderId);
                        }});
                        if (ex) {
                            Example example1 = new Example(SlOrderDetail.class);
                            example1.createCriteria()
                                    .andEqualTo("id", orderId)
                                    .andEqualTo("creator", user.getId());
                            orderDetailService.updateByExampleSelective(new SlOrderDetail() {{
                                //确认订单未评价
                                setShippingState(5);
                                //确认收货时间
                                setConfirmReceiptTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                            }}, example1);
                            //获取订单详情信息
                            SlOrderDetail slOrderDetail = this.orderDetailService.selectOne(new SlOrderDetail(){{
                                setId(orderId);
                            }});
                            //如果是纯豆商品 给店铺老板返都
                            if(6==slOrderDetail.getType()){
                                returnCoinToShop(slOrderDetail.getOrderId());
                            }
                            //分享奖励 将红包分享红包设置为可领取状态
                            changeRedPacketResult(slOrderDetail);

                            message.setMsg("确认收货成功");
                            message.setSuccess(true);
                        } else {
                            message.setMsg("该商品不存在");
                        }
                        break;
                }
            }
        } catch (Exception e) {
            log.error("操作失败 {}", e);
        }
        return message;
    }

    /**
     * 处理订单失效
     *
     * @param key redis返回回来的订单id
     */
    public void processOrderDisabled(String key) {
        log.debug("订单失效，标识：{}", key);
        int p = 0;
        if (!StringUtils.isEmpty(key)) {
            // 商品标识
            String orderId = key.substring(key.lastIndexOf(":") + 1);
            if (!StringUtils.isEmpty(orderId)) {
                SlOrder order = this.orderService.selectOne(new SlOrder() {{
                    setId(orderId);
                }});
                if (null != order) {
                    orderService.updateByPrimaryKeySelective(new SlOrder() {{
                        setId(orderId);
                        setPaymentState(101);
                    }});
                    // 查询该订单id关联的所有商品明细
                    List<SlOrderDetail> detailList = this.orderDetailService.select(new SlOrderDetail() {{
                        setOrderId(key);
                        //setIsVirtualSpellGroup((byte) 1);
                    }});
                    for (SlOrderDetail slOrderDetail : detailList) {
                        p += slOrderDetail.getPlaceOrderReturnPulse();
                        SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                            setId(slOrderDetail.getRepositoryId());
                        }});
                        //把该订单下的数量加回去
                        int count = repository.getCount() + slOrderDetail.getQuantity();
                        productRepositoryService.updateByPrimaryKeySelective(new SlProductRepository() {{
                            setId(repository.getId());
                            setCount(count);
                        }});
                        repository.setCount(count);
                        //更新redids
                        this.repositoryCache.put(repository.getId(), repository);
                    }
                }

            }
        }
    }


    /**
     * 逻辑处理订单
     *
     * @param userId            用户id
     * @param serialNumber      订单编号
     * @param activityProduct   活动商品
     * @param groupMaster       拼团团主id
     * @param shippingAddressId 收货地址id
     * @param repository        规格
     * @param quantity          商品数量
     * @param shareOfPeopleId   分享人id
     * @param slProduct         商品
     * @param type              订单类型
     * @param buyerMessage      买家留言
     * @param spellGroupType    价格1 : 普通活动价 2:个人价(只用作拼团价)
     * @param inviterId    价邀请人ID
     * @param virtualOpen        是否虚拟用户开团: 1 不是   2 是
     * @return
     */
    public BusinessMessage processingOrders(String userId,
                                            String serialNumber,
                                            SlActivityProduct activityProduct,
                                            String groupMaster,
                                            String shippingAddressId,
                                            SlProductRepository repository,
                                            Integer quantity,
                                            String shareOfPeopleId,
                                            SlProduct slProduct,
                                            int type,
                                            String buyerMessage,
                                            int spellGroupType,
                                            int inviterId,int virtualOpen) {
        BusinessMessage message = new BusinessMessage();
        /**
         * 2018年6月19日14:07:03
         * 预售模式改成纯钱模式 不再消耗金豆
         */

        if (type ==  3)  {
            repository.setSilver(0);
        }
        SlOrder slOrder = new SlOrder();
        // 订单id
        slOrder.setId(formatUUID32());
        // 创建时间
        slOrder.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // 用户Id
        slOrder.setUserId(userId);
        // 订单号
        slOrder.setSerialNumber(serialNumber);
        // 用户Id
        slOrder.setUserId(userId);
        // 团主Id
        slOrder.setGroupMaster(groupMaster);
        // 该商品的规格价格 * 加入购物车中的数量 = 该用户本次加入商品的价格
        double price = 0.00;
        if (spellGroupType == 1) {
            price = repository.getPrice().doubleValue();
        } else {
            price = repository.getPersonalPrice().doubleValue();
        }
        if(2==virtualOpen){
            slOrder.setVirtualOpen(2);
        }
        //修改价格 如果是限时秒杀就改成秒杀价
        if (slProduct.getSalesModeId().equals("8")){
            price = repository.getSeckillPrice().doubleValue();
        }

        Double d = price * quantity;
        BigDecimal money = new BigDecimal(d.toString());
        if (slProduct.getPostage().doubleValue() > 0) {
            money = BigDecimal.valueOf(Arith.add(money.doubleValue(), slProduct.getPostage().doubleValue()));
        }
        // 订单总价
        slOrder.setTotalAmount(money);
        // 查询订单地址信息
        SlUserAddress slUserAddress = this.slUserAddressMapper.selectOne(new SlUserAddress() {{
            setId(shippingAddressId);
            setUserId(userId);
        }});
        if (null != slUserAddress) {
            // 订单省的地址
            slOrder.setProvince(slUserAddress.getProvince());
            // 订单市的收货地址
            slOrder.setCity(slUserAddress.getCity());
            // 订单区的收货地址
            slOrder.setCounty(slUserAddress.getCounty());
            // 订单详细收货地址
            slOrder.setDetailed(slUserAddress.getDetailed());
            // 收货人姓名
            slOrder.setConsigneename(slUserAddress.getName());
            // 收货人的联系方式
            slOrder.setConsigneephone(slUserAddress.getPhone());
            // 了豆价格
            slOrder.setDeductTotalPulse(repository.getSilver() * quantity);
            // 插入订单表
            orderService.insertSelective(slOrder);
            // 订单加入redis 有效时间为24小时
            orderCache.put(slOrder.getId(), slOrder, 1L, TimeUnit.DAYS);
            // 插入订单明细表
//            SlProductRepository finalRepository1 = repository;
//            SlProduct finalSlProduct = slProduct;
            double finalPrice = price;
            orderDetailService.insertSelective(new SlOrderDetail() {{
                //邀请人ID
                setInviterId(inviterId);
                // 订单明细id
                setId(formatUUID32());
                // 订单明细创建时间
                setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                // 用户id
                setCreator(slOrder.getUserId());
                // 订单ID
                setOrderId(slOrder.getId());
                // 商品数量
                setQuantity(quantity);
                // 邮费
                setPostFee(slProduct.getPostage());
                // 单个商品价格
                setPrice(new BigDecimal(finalPrice));
                // 商品ID
                setProductId(slProduct.getId());
                // 店铺唯一标识
                setShopId(slProduct.getShopId());
                // 店铺仓库ID
                setRepositoryId(repository.getId());
                // 活动id
                setActivityProductId(activityProduct.getId());
                // 返了豆数量只限纯金钱模式
                setPlaceOrderReturnPulse(repository.getPlaceOrderReturnPulse());
                // 下单时的商品名称
                setProductName(slProduct.getName());
                // 商品所需了豆
                setDeductTotalSilver(repository.getSilver());
                // 下单时的商品图片
                setProductImageUrl(slProduct.getImageUrl());
                // 添加买家留言
                setBuyerMessage(buyerMessage);
                // 订单类型
                setType(type);
                // 订单编号
                setSerialNumber(serialNumber);
                // 商品规格名称
                setProductDetailGroupName(repository.getProductDetailGroupName());
                if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_GROUP) {
                    if (!groupMaster.equals(userId)) {
                        SlOrderDetail detail = orderDetailService.selectOne(new SlOrderDetail() {{
                            setCreator(groupMaster);
                            setSerialNumber(serialNumber);
                        }});
                        setGroupPeople(detail.getGroupPeople());
                    } else {
                        // 拼团所需人数
                        setGroupPeople(activityProduct.getPeopleNum());
                    }
                }
                // 分享奖励
                // 如果是分享奖励的情况下
                if (activityProduct.getActivityId().equals(ActivityConstant.RECOMMEND_AWARDS_ACTIVITY)) {
                    // 插入分享人id
                    setShareOfPeopleId(shareOfPeopleId);
                }
                //  新人奖励
                // 查询当前用户是否为首单
                Boolean flag = orderService.exist(new SlOrder() {{
                    setUserId(userId);
                    // 已支付
                    setPaymentState(1);
                }});
                // 是首单的情况 && 该商品活动是新人奖励
                if (flag.equals(false) && activityProduct.getActivityId().equals(ActivityConstant.NEW_PEOPLE_ACTIVITY)) {
                    // 如果是第一单的情况下 需要加上 首单奖励
                    setPlaceOrderReturnPulse(repository.getPlaceOrderReturnPulse() * quantity + repository.getFirstOrderPulse());
                } else {
                    // 返了豆数量只限纯金钱模式
                    setPlaceOrderReturnPulse(repository.getPlaceOrderReturnPulse() * quantity);
                }
                // 消费返利
                if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_REBATE) {
                    if (repository.getReturnCashMoney().doubleValue() > 0) {
                        // 消费返利金额
                        setReturnCashMoney(repository.getReturnCashMoney());
                    }
                }
                //setIsVirtualSpellGroup((byte) 1);
            }});
            // 商品上架数量 - 本次加入订单的数量
            int activityProductCount = activityProduct.getCount() - quantity;
            // 如果库存为0 的话就下架了
            Example example = new Example(SlActivityProduct.class);
            example.createCriteria()
                    .andGreaterThan("count", 0)
                    .andEqualTo("id", activityProduct.getId());
            this.activityProductMapper.updateByExampleSelective(new SlActivityProduct() {{

                /**
                 *注释原因： 商品库存为0不要自动下架
                 * 2018年7月7日13:42:07
                if (activityProductCount == 0) {
                    setEnabled(false);
                }
                 */
                //活动总商品上架数量 - 本次购买的数量
                setCount(activityProductCount);
            }}, example);
            /**
            this.productService.updateByPrimaryKeySelective(new SlProduct(){{
                setId(slProduct.getId());

                if (activityProductCount == 0) {
                    setSoldOut(false);
                }
            }});
            */

            //更新限时秒杀的库存
            if (slProduct.getSalesModeId().equals("8")){
                SlActivitySeckill  seckill = slActivitySeckillMapper.selectOne(new SlActivitySeckill(){{
                    setProductOldId(slProduct.getId());
                    setEnable(true);
                }});
                //更新秒杀表总库存
                seckill.setTotalCount(seckill.getTotalCount()-quantity);
                slActivitySeckillMapper.updateByPrimaryKey(seckill);
                //设置规格表库存
                repository.setSekillCount(repository.getSekillCount()-quantity);
                slProductRepositoryMapper.updateByPrimaryKeySelective( new SlProductRepository(){{
                    setId(repository.getId());
                    setSekillCount(repository.getSekillCount());
                }});
            }
            // 当前库存 - 本次该商品规格下单库存
            int cou = repository.getCount() - quantity;
            repository.setCount(cou);
            // 更新redis中该商品规格的库存
            repositoryCache.put(repository.getId(), repository);

            //更新数据库该商品规格的库存
            int updateCount = this.cmOrderMapper.reduceNumber(repository.getId(), cou);
            if (updateCount > 0) {
                message.setMsg("订单生成成功");
                message.setSuccess(true);
            }
            String sign = MD5Util.md5encode(slOrder.getId()+MD5Util.md5encode(slOrder.getId()+money.toString(),"utf-8"),"utf-8");
            Map<String, String> map = new HashMap<>();
            map.put("order_num", slOrder.getId());
            map.put("total_amount", money.toString());
            map.put("deduct_total_pulse", slOrder.getDeductTotalPulse().toString());
            map.put("sign", sign);
            message.setData(map);
        } else {
            message.setMsg("用户地址不存在");
            return message;
        }
        return message;
    }

    /**
     * 删除订单
     *
     * @param detailId 订单明细id
     * @param orderId  订单id
     */
    public void deleteOrder(String orderId, String detailId) {
        SlUser user = loginUserService.getCurrentLoginUser();
        try {
            if (null != user) {
                Example example = new Example(SlOrderDetail.class);
                example.createCriteria()
                        .andEqualTo("id", detailId)
                        .andEqualTo("creator", user.getId())
                        .andEqualTo("orderId", orderId);
                this.orderService.delete(new SlOrder() {{
                    setId(orderId);
                    setUserId(user.getId());
                }});
                this.orderDetailService.deleteByExample(example);
            }

        } catch (Exception e) {
            log.error("删除失败 {}", e);
        }
    }
    /**
     * 逻辑删除订单（order_detail暂时未做处理）
     *
     * @param orderId  订单id
     */
    public void logicalDeleteOrder(String orderId) {
        SlUser user = loginUserService.getCurrentLoginUser();
        try {
            if (null != user) {
                this.orderService.updateByPrimaryKeySelective(new SlOrder() {{
                    setId(orderId);
                    setUserId(user.getId());
                    setStatus(0);
                }});
            }

        } catch (Exception e) {
            log.error("逻辑删除订单失败 {}", e);
        }
    }

    /**
     * 预售订单
     *
     * @param status  返现状态
     * @param pageNum
     * @param status
     * @return
     */
    public BusinessMessage preSaleOrderList(Integer status, Integer pageNum, Integer pageSize) {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        try {
            if (null != user) {
                if (null == pageNum || pageNum <= 1) {
                    pageNum = 1;
                }
                if (null == pageSize || pageSize <= 1) {
                    pageSize = 10;
                }
                // 设置分页参数
                PageHelper.startPage(pageNum, pageSize);
                List<SlReturnsDetail> list = this.cmOrderMapper.selectReturnsDetail(status, user.getId());
                List<Map<String, Object>> mapList = new ArrayList<>();
                if (list.size() != 0) {
                    for (SlReturnsDetail returnsDetail : list) {
                        if (null != status && status == 2) {
                            if (returnsDetail.getReturnedStatus() == 2) {
                                SlOrder order = this.orderService.selectOne(new SlOrder() {{
                                    setId(returnsDetail.getOrderId());
                                }});
                                SlOrderDetail detail = this.orderDetailService.selectOne(new SlOrderDetail() {{
                                    setOrderId(order.getId());
                                    //预售
                                    setType(3);
                                }});
                                Map<String, Object> shop = this.cmOrderMapper.selectShopUserName(detail.getShopId());
                                Map<String, Object> map = new HashMap<>();
                                map.put("returnsDetailId", returnsDetail.getId());
                                // 店铺的账号
                                map.put("owner", shop.get("userName"));
                                // 店铺的名字
                                map.put("shop_name", shop.get("shopName"));
                                // 订单编号
                                map.put("serial_number", order.getSerialNumber());
                                // 商品标题
                                map.put("product_name", detail.getProductName());
                                // 商品图片
                                map.put("product_image_url", detail.getProductImageUrl());
                                // 预售订单单价
                                map.put("price", detail.getPrice());
                                // 订单合计价格
                                map.put("total_amount", order.getTotalAmount());
                                // 预售订单总了豆
                                map.put("deduct_total_pulse", detail.getDeductTotalSilver());
                                // 预售订单商品数量
                                map.put("quantity", detail.getQuantity());
                                // 预售商品邮费
                                map.put("post_fee", detail.getPostFee());
                                // 该订单的返钱状态
                                map.put("status", returnsDetail.getReturnedStatus());
                                // 订单id
                                map.put("orderId", order.getId());
                                // 商品明细id
                                map.put("orderDetailId", detail.getId());
                                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                LocalDateTime time = LocalDateTime.now();
                                LocalDateTime ldt = LocalDateTime.parse(returnsDetail.getReturnTime(), df);
                                Duration duration = Duration.between(time, ldt);
                                // 返现时间差
                                map.put("shipments_days", duration.toDays());
                                mapList.add(map);
                            }
                        } else {
                            SlOrder order = this.orderService.selectOne(new SlOrder() {{
                                setId(returnsDetail.getOrderId());
                            }});
                            SlOrderDetail detail = this.orderDetailService.selectOne(new SlOrderDetail() {{
                                setOrderId(order.getId());
                                //预售
                                setType(3);
                            }});
                            Map<String, Object> shop = this.cmOrderMapper.selectShopUserName(detail.getShopId());
                            Map<String, Object> map = new HashMap<>();
                            map.put("returnsDetailId", returnsDetail.getId());
                            // 店铺的账号
                            map.put("owner", shop.get("userName"));
                            // 店铺的名字
                            map.put("shop_name", shop.get("shopName"));
                            // 订单编号
                            map.put("serial_number", order.getSerialNumber());
                            // 商品标题
                            map.put("product_name", detail.getProductName());
                            // 商品图片
                            map.put("product_image_url", detail.getProductImageUrl());
                            // 预售订单单价
                            map.put("price", detail.getPrice());
                            // 订单合计价格
                            map.put("total_amount", order.getTotalAmount());
                            // 预售订单总了豆
                            map.put("deduct_total_pulse", detail.getDeductTotalSilver());
                            // 预售订单商品数量
                            map.put("quantity", detail.getQuantity());
                            // 预售商品邮费
                            map.put("post_fee", detail.getPostFee());
                            // 该订单的返钱状态
                            map.put("status", returnsDetail.getReturnedStatus());
                            // 订单id
                            map.put("orderId", order.getId());
                            // 商品明细id
                            map.put("orderDetailId", detail.getId());
                            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime time = LocalDateTime.now();
                            LocalDateTime ldt = LocalDateTime.parse(returnsDetail.getReturnTime(), df);
                            Duration duration = Duration.between(time, ldt);
                            // 返现时间差
                            map.put("shipments_days", duration.toDays());
                            mapList.add(map);
                        }
                    }
                }
                if (status == null) {
                    List<SlOrderDetail> detailList = this.orderDetailService.select(new SlOrderDetail() {{
                        setType(3);
                        setCreator(user.getId());
                        setShippingState(0);
                    }});
                    if (detailList.size() > 0) {
                        for (SlOrderDetail detail : detailList) {
                            SlOrder order = orderService.selectByPrimaryKey(detail.getOrderId());
                            if (order != null) {
                                Map<String, Object> map = new HashMap<>();
                                Map<String, Object> shop = this.cmOrderMapper.selectShopUserName(detail.getShopId());
                                SlProduct product = this.productService.selectOne(new SlProduct() {{
                                    setId(detail.getProductId());
                                }});
                                if (null != product) {
                                    SlActivityProduct activityProduct = this.activityProductMapper.selectByPrimaryKey(detail.getActivityProductId());
                                    map.put("shop_name", shop.get("shopName"));
                                    map.put("product_name", detail.getProductName());
                                    map.put("product_image_url", detail.getProductImageUrl());
                                    map.put("productId", detail.getProductId());
                                    map.put("quantity", detail.getQuantity());
                                    map.put("paymentState", order.getPaymentState());
                                    map.put("price", detail.getPrice());
                                    map.put("pulse", detail.getDeductTotalSilver());
                                    map.put("post_fee", detail.getPostFee());
                                    map.put("shopImageUrl", shop.get("image_url"));
                                    map.put("salesModeId", product.getSalesModeId());
                                    map.put("orderId", order.getId());
                                    map.put("orderDetailId", detail.getId());
                                    map.put("type", detail.getType());
                                    map.put("serial_number", detail.getSerialNumber());
                                    map.put("total_amount", order.getTotalAmount());
                                    map.put("deduct_total_pulse", order.getDeductTotalPulse());
                                    map.put("activityId", activityProduct.getActivityId());
                                    mapList.add(map);
                                }
                            }
                        }
                    }
                }
                message.setMsg("查询成功");
                message.setSuccess(true);
                message.setData(new PageInfo<>(mapList));
            } else {
                message.setMsg("用户不存在");
                log.error("用户不存在");
            }
        } catch (Exception e) {
            log.error("查询失败 {}", e);
        }
        return message;
    }

    /**
     * 提醒发货
     *
     * @param orderId 订单id
     */
    public BusinessMessage remindingTheShipments(String orderId) {
        BusinessMessage message = new BusinessMessage();
        try {
            SlUser user = loginUserService.getCurrentLoginUser();
            if (null != user) {
                int count = this.orderService.selectCount(new SlOrder() {{
                    setId(orderId);
                }});
                if (count == 1) {
                    SlOrderDetail detail = this.orderDetailService.selectOne(new SlOrderDetail() {{
                        setOrderId(orderId);
                        setType(3);
                    }});
                    if (null != detail) {
                        List<SlMessage> list = this.messageMapper.select(new SlMessage() {{
                            setSourceId(user.getId());
                            setTargetId(detail.getShopId());
                        }});
                        if (list.size() > 0) {
                            for (SlMessage message1 : list) {
                                if (message1.getCreateTime().substring(0, 10).equals(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
                                    message.setMsg("今日已提醒");
                                } else {
                                    String content = user.getName() + ":提醒发货,订单号:" + detail.getSerialNumber();
                                    notificationService.sendToQueue(user.getId(), detail.getShopId(), content, MessageTypeEnum.STORE_REMINDING);
                                    message.setMsg("提醒成功");
                                    message.setSuccess(true);
                                }
                            }
                        } else {
                            String content = user.getName() + ":提醒发货,订单号:" + detail.getSerialNumber();
                            notificationService.sendToQueue(user.getId(), detail.getShopId(), content, MessageTypeEnum.STORE_REMINDING);
                            message.setMsg("提醒成功");
                            message.setSuccess(true);
                        }
                    }
                } else {
                    message.setMsg("订单错误,或不存在");
                }
            }
        } catch (Exception e) {
            log.error("提醒失败 {}", e);
        }
        return message;
    }

    /**
     * 预售确认收货
     *
     * @param returnsDetailId 返现明细id
     * @param orderId         订单id
     */
    public BusinessMessage presellPremises(String returnsDetailId, String orderId) {
        BusinessMessage message = new BusinessMessage();
        try {
            SlUser user = loginUserService.getCurrentLoginUser();
            // 查询有没有这个订单
            SlOrderDetail detail = this.orderDetailService.selectOne(new SlOrderDetail() {{
                setOrderId(orderId);
                //并且是已发货状态
                setShippingState(4);
                //用户id
                setCreator(user.getId());
            }});
            // 有这个订单明细
            if (null != detail) {
                Example example = new Example(SlReturnsDetail.class);
                example.setOrderByClause("return_time DESC");
                example.createCriteria()
                        .andEqualTo("userId", user.getId())
                        .andEqualTo("orderId", orderId);
                List<SlReturnsDetail> list = this.returnsDetailMapper.selectByExample(example);
                if (list.size() > 0) {
                    //如果本次确认收货的预售订单==最后一次返现的预售订单id
                    if (list.get(0).getId().equals(returnsDetailId)) {
                        this.orderDetailService.updateByPrimaryKeySelective(new SlOrderDetail() {{
                            setId(detail.getId());
                            // 已完成/未评价
                            setShippingState(5);
                        }});
                        //把改订单号的所有订单更新为已完成状态
                        this.returnsDetailMapper.updateByExampleSelective(new SlReturnsDetail() {{
                            setReturnedStatus(5);
                            setConfirmReceipt(false);
                        }}, example);
                    } else {
                        this.returnsDetailMapper.updateByPrimaryKeySelective(new SlReturnsDetail() {{
                            setId(returnsDetailId);
                            //本期已返状态
                            setConfirmReceipt(true);
                        }});
                    }
                    message.setSuccess(true);
                    message.setMsg("确认订单成功");
                } else {
                    message.setMsg("预售订单不存在");
                }
            } else {
                message.setMsg("该订单不存在");
            }
        } catch (Exception e) {
            log.error("预售订单确认收货失败 {}", e);
        }
        return message;
    }

    /**
     * 快递100 接口
     *
     * @param emsId       快递ems表id
     * @param expressCode 快递单号
     * @return
     */
    public BusinessMessage<JSONObject> searchExpress(Integer emsId, String expressCode) {
        BusinessMessage<JSONObject> message = new BusinessMessage<>(false);
        if (emsId == null) {
            message.setMsg("emsId不能为空");
            return message;
        }
        SlUser user = loginUserService.getCurrentLoginUser();
        List<SlOrderDetail> detailList = this.orderDetailService.select(new SlOrderDetail() {{
            // 物流单号唯一
            setShipNumber(expressCode);
            setCreator(user.getId());
        }});
        //存在这个订单
        if (detailList.size() > 0) {
            if (null != user) {
                SlOrderDetail detail = detailList.get(0);
                SlEms ems = emsMapper.selectOne(new SlEms() {{
                    setId(emsId);
                }});
                if (null != ems) {
                    String key = "zVPqiGqq4017";
                    String param = "{\"com\":\"" + ems.getCode() + "\",\"num\":\"" + expressCode + "\"}";
                    String customer = "E9FB3E608B94E4E881B3CA86F7F35FFF";
                    //MD5.encode(param+key+customer);
                    MessageDigest MD5 = null;
                    String sign = MD5Util.md5encode(param + key + customer, "UTF-8");
                    Map<String, Object> params = new HashMap<>();
                    params.put("param", param);
                    params.put("sign", sign.toUpperCase());
                    params.put("customer", customer);
                    String resp;
                    try {
                        resp = expressUtils.postData("http://poll.kuaidi100.com/poll/query.do", params, "utf-8");
                        if (StringUtils.isNotBlank(resp)) {
                            JSONObject expressData = JSON.parseObject(resp);
                            JSONObject data = new JSONObject();
                            //快递名称
                            data.put("name", ems.getName());
                            //快递单号
                            data.put("shipNumber", detail.getShipNumber());
                            //订单状态
                            data.put("shippingState", detail.getShippingState());
                            //商品图片
                            data.put("productImg", detail.getProductImageUrl());
                            //快递信息
                            data.put("expressData", expressData.getJSONArray("data"));
                            //签收状态
                            data.put("expressState", expressData.get("state"));
                            message.setData(data);
                            message.setSuccess(true);
                        }
                    } catch (Exception e) {
                        log.error("快递信息查询失败 {}", e);
                        e.printStackTrace();
                    }
                } else {
                    message.setMsg("该订单不存在");
                }
            } else {
                message.setMsg("查询失败");
            }
        } else {
            message.setMsg("订单不存在");
        }
        return message;
    }

//    public Map<String, String> wechatAppPayTest(HttpServletRequest req, String productName) {
//        return wxPayService.unifiedOrderByApp(null, productName, null, null, OrderNumGeneration.generateOrderId(), "", "1", ClientIPUtil.getClientIP(req), "", "", "", "", "", "");
//    }
//
//    public String alipayAppPayTest(String productName) {
//        int suffix = new Random().nextInt(3);
//        return this.aliPayService.appPay("", "0.0" + suffix, "", "", productName, productName, OrderNumGeneration.generateOrderId(), "", "", "", "", null, null, null, "", "", null, null, null, null, null, "");
//    }
//
//    public AlipayTradeWapPayResponse alipayH5PayTest(String productName) {
//        int suffix = new Random().nextInt(3);
//        return this.aliPayService.wapPay(productName, productName, OrderNumGeneration.generateOrderId(), null, null, "0.0" + suffix, null, null, null, null, "", "", null, null, null, null, null, null, null, null, null, null, null, null);
//    }

    /**
     * 支付宝支付
     *
     * @param orderId 订单id
     * @return
     */
    @Transactional
    public BusinessMessage<Map> alipayAppPay(String orderId) {
        BusinessMessage<Map> message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        Map<String, String> map = new HashMap<>();
        if (null != user) {
            SlOrder slOrder = slOrderMapper.selectOne(new SlOrder(){{
                setOutOrderNumber(orderId);
            }});
            if (null == slOrder){
                message = checkTheOrder(orderId, user);
                if (message.getSuccess() == true) {
                /*Example example = new Example(SlOrderDetail.class);
                example.createCriteria().andEqualTo("orderId", orderId);
                orderDetailService.updateByExampleSelective(new SlOrderDetail() {{
                    setIsVirtualSpellGroup((byte) 0);
                }}, example);*/
                    String money = message.getData().get("money").toString();
                    String serialNumber = message.getData().get("serialNumber").toString();
                    String str = this.aliPayService.appPay("15d", money, "", "", null, "搜了购物支付 - " + serialNumber, orderId, "", "", "", "", null, null, null, "", "", null, null, null, null, null, "");
                    if (StringUtils.isNotBlank(str)) {
                        message.setData(null);
                        map.put("alipay", str);
                        message.setData(map);
                        message.setSuccess(true);
                    }
                } else {
                    return message;
                }
            }else {
                String money = slOrder.getTotalAmount().toString();
                String serialNumber = slOrder.getSerialNumber();
                String str = this.aliPayService.appPay("15d", money, "", "", null, "搜了购物支付 - " + serialNumber, orderId, "", "", "", "", null, null, null, "", "", null, null, null, null, null, "");
                if (StringUtils.isNotBlank(str)) {
                    message.setData(null);
                    map.put("alipay", str);
                    message.setData(map);
                    message.setSuccess(true);
                }
            }

        } else {
            message.setMsg("请登录");
        }
        return message;
    }

    /**
     * 校验支付密码
     *
     * @param userId
     * @param payPassword
     * @return
     */
    public Boolean verifyPaymentPassword(String userId, String payPassword) {
        Boolean falg = false;
        SlUser user = userService.selectByPrimaryKey(userId);
        if (null != user) {
            if (user.getPayPassword().equals(payPassword)) {
                falg = true;
            }
        }
        return falg;
    }

    /**
     * 微信支付
     *
     * @param req
     * @param orderId 订单id
     * @return
     */
    @Transactional
    public BusinessMessage<Map> wechatAppPay(HttpServletRequest req, String orderId) {
        BusinessMessage<Map> message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        Map<String, String> map = new HashMap<>();
        if (null != user) {
            SlOrder slOrder = slOrderMapper.selectOne(new SlOrder(){{
                setOutOrderNumber(orderId);
            }});
            if (null == slOrder){
                message = checkTheOrder(orderId, user);
                if (message.getSuccess() == true) {
                /*Example example = new Example(SlOrderDetail.class);
                example.createCriteria().andEqualTo("orderId", orderId);
                orderDetailService.updateByExampleSelective(new SlOrderDetail() {{
                    setIsVirtualSpellGroup((byte) 0);
                }}, example);*/
                    String money = message.getData().get("money").toString();
                    String serialNumber = message.getData().get("serialNumber").toString();
                    double mo = Arith.mul(Double.parseDouble(money), 100);
                    String total_fee = String.valueOf(Math.round(mo));
                    map = wxPayService.unifiedOrderByApp(null, "搜了购物支付 - " + serialNumber, null, null, orderId, "", total_fee, ClientIPUtil.getClientIP(req), "", "", "", "", "", "");
                    log.debug("微信预下单返回数据：{}", map);
                    if (map.size() > 0) {
                        message.setData(null);
                        message.setData(map);
                        message.setSuccess(true);
                    }
                } else {
                    return message;
                }
            }else {
                String money = slOrder.getTotalAmount().toString();
                String serialNumber = slOrder.getSerialNumber();
                double mo = Arith.mul(Double.parseDouble(money), 100);
                String total_fee = String.valueOf(Math.round(mo));
                map = wxPayService.unifiedOrderByApp(null, "搜了购物支付 - " + serialNumber, null, null, orderId, "", total_fee, ClientIPUtil.getClientIP(req), "", "", "", "", "", "");
                log.debug("微信预下单返回数据：{}", map);
                if (map.size() > 0) {
                    message.setData(null);
                    message.setData(map);
                    message.setSuccess(true);
                }
            }

        } else {
            message.setMsg("请登录");
        }
        return message;
    }

    /**
     * 校验扣除了豆
     *
     * @param orderId 订单id
     * @return
     */
    public BusinessMessage<Map> checkTheOrder(String orderId, SlUser user) {
        log.debug("orderId = [" + orderId + "], user = [" + user + "]");
        BusinessMessage<Map> message = new BusinessMessage();
        int count = 0;
        if (null != user) {
            SlOrder order = orderService.selectOne(new SlOrder() {{
                setId(orderId);
                setUserId(user.getId());
                setPaymentState(2);
            }});
            if (null != order) {
                List<SlOrderDetail> orderDetails = orderDetailService.select(new SlOrderDetail() {{
                    setOrderId(orderId);
                    setCreator(user.getId());
                   // setIsVirtualSpellGroup((byte) 1);
                }});
                if (orderDetails.size() > 0) {
                    //如果是云易购物商品直接不扣豆
                    if(orderDetails.get(0).getType()!=3){
                        if (order.getDeductTotalPulse() > 0) {
                            if ((user.getSilver() + user.getCoin()) >= order.getDeductTotalPulse()) {
                                if (user.getSilver() >= order.getDeductTotalPulse()) {
                                    int pulse = user.getSilver() - order.getDeductTotalPulse();
                                    count = userService.updateByPrimaryKeySelective(new SlUser() {{
                                        setId(user.getId());
                                        setSilver(pulse);
                                    }});
                                    user.setSilver(pulse);
                                    userCache.put(user.getClientId(), user);
                                    transactionDetailMapper.insertSelective(new SlTransactionDetail() {{
                                        // 目标id
                                        setTargetId(user.getId());
                                        // 订单id
                                        setOrderId(order.getId());
                                        // 购物类型
                                        setType(200);
                                        // 创建时间
                                        setCreateTime(new Date());
                                        // 扣除银豆数量
                                        setSilver(order.getDeductTotalPulse());
                                        // 银豆
                                        setDealType(6);
                                        // 支出
                                        setTransactionType(1);
                                    }});
                                } else {
                                    int p = order.getDeductTotalPulse() - user.getSilver();
                                    int c = user.getCoin() - p;
                                    // 银豆记录
                                    transactionDetailMapper.insertSelective(new SlTransactionDetail() {{
                                        // 目标id
                                        setTargetId(user.getId());
                                        // 订单id
                                        setOrderId(order.getId());
                                        // 购物类型
                                        setType(200);
                                        // 创建时间
                                        setCreateTime(new Date());
                                        // 扣除银豆数量
                                        setSilver(user.getSilver());
                                        // 银豆
                                        setDealType(6);
                                        // 支出
                                        setTransactionType(1);
                                    }});
                                    // 金豆记录
                                    transactionDetailMapper.insertSelective(new SlTransactionDetail() {{
                                        // 目标id
                                        setTargetId(user.getId());
                                        // 订单id
                                        setOrderId(order.getId());
                                        // 创建时间
                                        setCreateTime(new Date());
                                        // 购物类型
                                        setType(200);
                                        // 扣除金豆数量
                                        setCoin(p);
                                        // 金豆
                                        setDealType(5);
                                        // 支出
                                        setTransactionType(1);
                                    }});
                                    count = userService.updateByPrimaryKeySelective(new SlUser() {{
                                        setId(user.getId());
                                        setCoin(c);
                                        setSilver(0);
                                    }});
                                    user.setSilver(0);
                                    user.setCoin(c);
                                    userCache.put(user.getClientId(), user);
                                }
                                // 给店铺老板加上金豆
                                for (SlOrderDetail detail : orderDetails) {
                                    SlShop shop = this.shopService.selectOne(new SlShop() {{
                                        setId(detail.getShopId());
                                    }});
                                    if (null != shop) {
                                        SlUser user1 = this.userService.selectOne(new SlUser() {{
                                            setId(shop.getOwnerId());
                                        }});
                                        if (null != user1) {
                                            int silvers = detail.getDeductTotalSilver() * detail.getQuantity();
                                            int p = user1.getCoin() + silvers;
                                            user1.setCoin(p);
                                            userCache.put(user1.getClientId(), user1);
                                            userService.updateByPrimaryKeySelective(new SlUser() {{
                                                setId(user1.getId());
                                                setCoin(p);
                                            }});
                                            // 金豆记录
                                            transactionDetailMapper.insertSelective(new SlTransactionDetail() {{
                                                // 目标id
                                                setTargetId(user1.getId());
                                                // 订单id
                                                setOrderId(order.getId());
                                                // 创建时间
                                                setCreateTime(new Date());
                                                // 购物类型店主收入
                                                setType(300);
                                                // 增加金豆数量
                                                setCoin(silvers);
                                                // 金豆
                                                setDealType(5);
                                                // 收入
                                                setTransactionType(2);
                                            }});
                                        }
                                    }
                                }
                            } else {
                                message.setMsg("当前用户了豆数量不足");
                            }
                        } else if (order.getDeductTotalPulse() == 0) {
                            SlOrderDetail detail = orderDetails.get(0);
                            SlProduct product = productService.selectOne(new SlProduct() {{
                                setId(detail.getProductId());
                            }});
                            if (null != product) {
                                if (product.getSalesModeId().equals(SalesModeConstant.SALES_MODE_GROUP)) {
                                    int c = this.orderService.selectCount(new SlOrder() {{
                                        setSerialNumber(order.getSerialNumber());
                                        setPaymentState(1);
                                    }});
                                    if (c == detail.getGroupPeople()) {
                                        message.setMsg("该拼团已结束");
                                    } else {
                                        message.setSuccess(true);
                                    }
                                } else {
                                    message.setSuccess(true);
                                }
                            }
                        }
                    }else{
                        //如果是云易购物则设置count=1
                        count=1;
                    }

                    if (count == 1) {
                        message.setSuccess(true);
                    }
                    Map<String, String> map = new HashMap<>();
                    map.put("money", order.getTotalAmount().toString());
                    map.put("serialNumber", order.getSerialNumber());
                    message.setData(map);
                } else {
                    message.setMsg("订单出错");
                }
            } else {
                message.setMsg("订单已失效或不存在");
            }
        } else {
            message.setMsg("请登录");
        }
        return message;
    }
    /**
     * 校验扣除了豆2
     *
     * @param orderId 订单id
     * @return
     */
    public BusinessMessage<Map> checkTheOrderForOnlyPulsePay(String orderId, SlUser user) {
        log.debug("orderId = [" + orderId + "], user = [" + user + "]");
        BusinessMessage<Map> message = new BusinessMessage();
        int count = 0;
        if (null != user) {
            SlOrder order = orderService.selectOne(new SlOrder() {{
                setId(orderId);
                setUserId(user.getId());
                setPaymentState(2);
            }});
            if (null != order) {
                List<SlOrderDetail> orderDetails = orderDetailService.select(new SlOrderDetail() {{
                    setOrderId(orderId);
                    setCreator(user.getId());
                    //setIsVirtualSpellGroup((byte) 1);
                }});
                if (orderDetails.size() > 0) {
                    if (order.getDeductTotalPulse() > 0) {
                        if ((user.getSilver() + user.getCoin()) >= order.getDeductTotalPulse()) {
                            if (user.getSilver() >= order.getDeductTotalPulse()) {
                                int pulse = user.getSilver() - order.getDeductTotalPulse();
                                count = userService.updateByPrimaryKeySelective(new SlUser() {{
                                    setId(user.getId());
                                    setSilver(pulse);
                                }});
                                user.setSilver(pulse);
                                userCache.put(user.getClientId(), user);
                                transactionDetailMapper.insertSelective(new SlTransactionDetail() {{
                                    // 目标id
                                    setTargetId(user.getId());
                                    // 订单id
                                    setOrderId(order.getId());
                                    // 购物类型
                                    setType(200);
                                    // 创建时间
                                    setCreateTime(new Date());
                                    // 扣除银豆数量
                                    setSilver(order.getDeductTotalPulse());
                                    // 银豆
                                    setDealType(6);
                                    // 支出
                                    setTransactionType(1);
                                }});
                            } else {
                                int p = order.getDeductTotalPulse() - user.getSilver();
                                int c = user.getCoin() - p;
                                // 银豆记录
                                transactionDetailMapper.insertSelective(new SlTransactionDetail() {{
                                    // 目标id
                                    setTargetId(user.getId());
                                    // 订单id
                                    setOrderId(order.getId());
                                    // 购物类型
                                    setType(200);
                                    // 创建时间
                                    setCreateTime(new Date());
                                    // 扣除银豆数量
                                    setSilver(user.getSilver());
                                    // 银豆
                                    setDealType(6);
                                    // 支出
                                    setTransactionType(1);
                                }});
                                // 金豆记录
                                transactionDetailMapper.insertSelective(new SlTransactionDetail() {{
                                    // 目标id
                                    setTargetId(user.getId());
                                    // 订单id
                                    setOrderId(order.getId());
                                    // 创建时间
                                    setCreateTime(new Date());
                                    // 购物类型
                                    setType(200);
                                    // 扣除金豆数量
                                    setCoin(p);
                                    // 金豆
                                    setDealType(5);
                                    // 支出
                                    setTransactionType(1);
                                }});
                                count = userService.updateByPrimaryKeySelective(new SlUser() {{
                                    setId(user.getId());
                                    setCoin(c);
                                    setSilver(0);
                                }});
                                user.setSilver(0);
                                user.setCoin(c);
                                userCache.put(user.getClientId(), user);
                            }
                        } else {
                            message.setMsg("当前用户了豆数量不足");
                        }
                    } else if (order.getDeductTotalPulse() == 0) {
                        SlOrderDetail detail = orderDetails.get(0);
                        SlProduct product = productService.selectOne(new SlProduct() {{
                            setId(detail.getProductId());
                        }});
                        if (null != product) {
                            if (product.getSalesModeId().equals(SalesModeConstant.SALES_MODE_GROUP)) {
                                int c = this.orderService.selectCount(new SlOrder() {{
                                    setSerialNumber(order.getSerialNumber());
                                    setPaymentState(1);
                                }});
                                if (c == detail.getGroupPeople()) {
                                    message.setMsg("该拼团已结束");
                                } else {
                                    message.setSuccess(true);
                                }
                            } else {
                                message.setSuccess(true);
                            }
                        }
                    }
                    if (count == 1) {
                        message.setSuccess(true);
                    }
                    Map<String, String> map = new HashMap<>();
                    map.put("money", order.getTotalAmount().toString());
                    map.put("serialNumber", order.getSerialNumber());
                    message.setData(map);
                } else {
                    message.setMsg("订单出错");
                }
            } else {
                message.setMsg("订单已失效或不存在");
            }
        } else {
            message.setMsg("请登录");
        }
        return message;
    }

    /**
     * 纯了豆支付
     *
     * @param orderId 订单id
     * @return
     */
    @Transactional
    public BusinessMessage<Map> onlyPulsePay(String orderId, String payPassword) {
        BusinessMessage<Map> message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (null != user) {
            message = checkTheOrderForOnlyPulsePay(orderId, user);
            if (message.getSuccess() == true) {
                /*Example example = new Example(SlOrderDetail.class);
                example.createCriteria().andEqualTo("orderId", orderId);
                orderDetailService.updateByExampleSelective(new SlOrderDetail() {{
                    setIsVirtualSpellGroup((byte) 0);
                }}, example);*/
                if (message.getData().get("money").toString().equals("0.00")) {
                    processOrders.processOrders(orderId, 3);
                }
            }
//            Boolean falg = verifyPaymentPassword(user.getId(), payPassword);
//            if (falg) {
//
//            }

//            else {
//                message.setMsg("支付密码错误");
//            }
        } else {
            message.setMsg("请登录");
        }
        return message;
    }

    /**
     * 分享订单奖励
     * 1、第一次分享奖励订单支付金额的16%银豆
     * 2、第二次分享奖励50银豆
     * 3、支付时间5分钟内可以领取红包
     *
     * @param orderId 分享的订单ID
     * @return
     */
    @Transactional
    public BusinessMessage shareAward(String orderId) {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (user == null || StringUtils.isBlank(user.getId())) {
            message.setMsg("获取当前登录用户信息失败");
            return message;
        }
        SlOrder order = orderService.selectByPrimaryKey(orderId);
        if (order == null || StringUtils.isBlank(order.getId()) || !order.getUserId().equals(user.getId())) {
            message.setMsg("订单不存在");
            return message;
        }
        if (order.getPaymentState() != 1) {
            message.setMsg("订单支付后才可以分享获得奖励");
            return message;
        }
        if (order.getShareCount() >= 2) {
            message.setMsg("已经获取过分享奖励");
            return message;
        }
        /** 订单支付5分钟内可以领取红包 **/
        if (LocalDateTimeUtils.parse(order.getPayTime()).before(LocalDateTimeUtils.addMinute(new Date(), -5))) {
            message.setMsg("已经超过获取奖励时间，无法获取奖励");
            return message;
        }
        List<SlOrderDetail> orderDetailList = orderDetailService.select(new SlOrderDetail() {{
            setOrderId(orderId);
        }});
        //订单支付现金金额
        BigDecimal totalAmount = new BigDecimal(0);
        if (orderDetailList != null && orderDetailList.size() > 0) {
            for (SlOrderDetail orderDetail : orderDetailList) {
                /** 普通商品 可以获取分享奖励 **/
                if (orderDetail.getType().equals(OrderDetailTypeEnum.NORMAL_ORDER.getValue())) {
                    totalAmount = totalAmount.add(orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getQuantity())));
                }
            }
        }
        if (totalAmount.compareTo(new BigDecimal(0)) <= 0) {
            message.setMsg("只有普通可以获取分享奖励");
            return message;
        }
        //奖励金额
        int award = 0;
        if (order.getShareCount() == 0) {
            award = (int) Math.floor(totalAmount.multiply(new BigDecimal(0.16)).doubleValue());
        } else if (order.getShareCount() == 1) {
            award = 50;
        }
        /******** 记录奖励 *******/
        SlTransactionDetail transactionDetail = new SlTransactionDetail();
        transactionDetail.setTargetId(order.getUserId());
        transactionDetail.setOrderId(orderId);
        transactionDetail.setType(ExpenditureTypeEnum.SHARE_AWARD.getValue());
        transactionDetail.setCoin(award);
        transactionDetail.setDealType(TransactionCurrencyTypeEnum.SILVER.getValue());
        transactionDetail.setTransactionType(TransactionTypeEnum.INCOME.getValue());
        transactionDetail.setTransactionStatus(TransactionStatusEnum.EFFICIENT.getValue().byteValue());
        transactionDetailService.insertSelective(transactionDetail);
        //更新USER表信息
        user.setCoin(user.getCoin() + award);
        userService.updateByPrimaryKeySelective(user);
        //更新资金池信息
        cmTotalPoolService.updatePool(award,null,null,2,null,user.getId(),3);
        //更新订单分享次数
        order.setShareCount(order.getShareCount() + 1);
        order.setCreatedAt(null);
        order.setUpdatedAt(null);
        orderService.updateByPrimaryKeySelective(order);
        message.setData(award);
        message.setSuccess(true);
        log.debug("userId = {}分享orderId = {}获取银豆奖励{}个", user.getId(), orderId, award);
        return message;
    }

    /**
     *根据ID获取订单
     * @param orderId
     * @return
     */
    @Transactional
    public BusinessMessage getOrderById (String orderId) {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (user == null || StringUtils.isBlank(user.getId())) {
            message.setMsg("获取当前登录用户信息失败");
            message.setSuccess(false);
            return message;
        }
        SlOrder order = orderService.selectByPrimaryKey(orderId);
        if (order == null || StringUtils.isBlank(order.getId()) || !order.getUserId().equals(user.getId())) {
            message.setMsg("订单不存在");
            message.setSuccess(false);
            return message;
        }
        message.setData(order);
        message.setSuccess(true);
        return message;
    }

    /**
     * 生成订单ID，32位长度
     *
     * @return
     */
    private String formatUUID32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }
    /**
     *   /**
     *      * alipay.trade.refund(统一收单交易退款接口)
     *      *
     *      * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，支付宝将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。 交易超过约定时间（签约时设置的可退款时间）的订单无法进行退款 支付宝退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。一笔退款失败后重新提交，要采用原来的退款单号。总退款金额不能超过用户实际支付金额
     *      * 来源 https://docs.open.alipay.com/api_1/alipay.trade.refund/
     *      *
     *      * @param outTradeNo    String	特殊可选	64	原支付请求的商户订单号,和支付宝交易号不能同时为空
     *      * @param tradeNo    String	特殊可选	64	支付宝交易号，和商户订单号不能同时为空
     *      * @param refundAmount    Price	必选	9	需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
     *      * @param refundReason    String	可选	256	退款的原因说明
     *      * @param outRequestNo    String	可选	64	标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
     *      * @param operatorId    String	可选	30	商户的操作员编号
     *      * @param storeId    String	可选	32	商户的门店编号
     *      * @param terminalId    String	可选	32	商户的终端编号
     *      * @return 响应信息
     */
    @Transactional
    public BusinessMessage refundOrder (String orderId) {
        BusinessMessage message = new BusinessMessage();
        //获取订单信息
        SlOrder order = this.orderService.selectOne(new SlOrder(){{
            setId(orderId);
            setPaymentState(1);
        }});
        if (null != order) {
            int paymentChannel = order.getPaymentChannel();
            Map<String,String> map  = new HashMap<>();
            //微信支付
            if(paymentChannel == 1) {
                //String transactionId = order.getId();
                String outTradeNo = order.getId();
                String outRefundNo = OrderNumGeneration.getOrderIdByUUId();
                BigDecimal totalAmount = order.getTotalAmount().multiply(new BigDecimal(100));
                int  totalFee = totalAmount.setScale( 0, BigDecimal.ROUND_DOWN ).intValue();
                String totalFeeStr = String.valueOf(totalFee);
                String refundDesc = "拼团失败";
                map=wxPayService.refund(null,outTradeNo,outRefundNo,totalFeeStr,totalFeeStr,null,refundDesc,null);
                if (map.get("return_msg").equals("OK")) {
                    message.setSuccess(true);
                }
            }//支付宝支付
            else if (paymentChannel == 2){
                String  outTradeNo = order.getId();
                String refundAmount = String.valueOf(order.getTotalAmount().doubleValue());
                String refundReason = "拼团失败";
                AlipayTradeRefundResponse response = aliPayService.refund(outTradeNo,null,refundAmount,refundReason,null,null,null,null);
                String strResponse = response.getCode();
                if ("10000".equals(strResponse)) {
                    message.setSuccess(true);
                }
            }

        }else{
            message.setMsg("订单不存在或订单未支付");
        }
        //退款成功修改订单状态
        if (message.getSuccess() == true ) {
            order.setSpellGroupStatus(0);
            order.setPaymentState(101);
            order.setId(orderId);
            this.orderService.updateByPrimaryKeySelective(order);
            List<SlOrderDetail> detailsList = orderDetailService.select(new SlOrderDetail(){{
                setOrderId(order.getId());
            }});
            // 更改orderDetial表shipping_state状态
            for (SlOrderDetail slOrderDetail : detailsList ) {
                orderDetailService.updateByPrimaryKeySelective( new SlOrderDetail(){{
                    setId(slOrderDetail.getId());
                    setShippingState(0);
                }});
            }
        }
        return message;
    }
    /**
     * 商品邮费
     *
     * @param ship      运费修改  1.包邮 2.部分地区不包邮
     * @param id          用户地址ID
     * @param  productIds  产品ID
     * @return
     */
    @Transactional
    public BusinessMessage shopShip(int ship,String id,String productIds){
        BusinessMessage message = new BusinessMessage();
        JSONObject data = new JSONObject();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (null != user) {
            Double postFee = 0.00;
            //多商品id , 分割
            String[] proId = productIds.split(",");
            for(String productId : proId ){
                SlProduct slProduct = this.productService.selectOne(new SlProduct(){{setId(productId);}});
                if(null !=slProduct){
                    //判断是否包邮 1.包邮 2.部分地区不包邮
                    if ("2".equals(slProduct.getShip()==null?"":slProduct.getShip().toString())){

                        SlUserAddress slUserAddress = this.slUserAddressMapper.selectOne(new SlUserAddress(){{setId(id);}});
                        //获取不包邮的地区
                        List<SlProductNoMail> list = slProductNoMailMapper.select(new SlProductNoMail(){{setProductId(productId);}});
                        if(list.size()>0){
                            for(SlProductNoMail slProductNoMail : list){
                                if(slUserAddress.getProvince().contains(slProductNoMail.getNoShipArea())){
                                    postFee=Arith.add(slProductNoMail.getAreamoney().doubleValue(),postFee);
                                    break;
                                }
                            }
                        }
                    }

                    if (null == slProduct.getShip()||"0".equals(slProduct.getShip()==null?"":slProduct.getShip().toString())){
                        postFee=Arith.add(slProduct.getPostage().doubleValue(),postFee);
                    }
                }else {
                    log.debug("该商品不存在");
                    message.setMsg("该商品不存在");
                    message.setSuccess(false);
                    return message;
                }
            }
            data.put("postage",postFee);
            message.setData(data);
        }else {
            log.debug("用户不存在");
            message.setMsg("用户不存在");
            message.setSuccess(false);
            return message;
        }
        message.setSuccess(true);
        return message;
    }

    /**
     * 延迟收货
     * @date  2018年6月19日15:47:19
     * @author mingel
     * @param orderId
     * @return
     */
    @Transactional
    public BusinessMessage delayedDelivery(String orderId){
        BusinessMessage message = new BusinessMessage();
        SlUser slUser = loginUserService.getCurrentLoginUser();
        if ( null != slUser ) {
            Example example = new Example(SlOrderDetail.class);
            example.createCriteria()
                    .andEqualTo("orderId", orderId)
                    .andEqualTo("creator",slUser.getId());
            int result = this.orderDetailService.updateByExampleSelective(new SlOrderDetail(){{
                    setIsDelayed(1);
            }},example);
            if (result > 0) {
                log.debug("更新成功");
                message.setMsg("更新成功");
                message.setSuccess(true);
                return message;
            } else {
                log.debug("更新失败");
                message.setMsg("更新失败");
                message.setSuccess(false);
                return message;
            }
        }else {
            log.debug("用户不存在");
            message.setMsg("用户不存在");
            message.setSuccess(false);
            return message;
        }
    }

    //给店铺老板加豆
    @Transactional
    public void returnCoinToShop(String orderId) {
        // 给店铺老板加上金豆
        SlOrder order = orderService.selectOne(new SlOrder() {{
            setId(orderId);
            setPaymentState(1);
        }});
        if (null != order) {
            List<SlOrderDetail> orderDetails = orderDetailService.select(new SlOrderDetail() {{
                setOrderId(orderId);
                //setIsVirtualSpellGroup((byte) 0);
            }});
            for (SlOrderDetail detail : orderDetails) {
                SlShop shop = this.shopService.selectOne(new SlShop() {{
                    setId(detail.getShopId());
                }});
                if (null != shop) {
                    SlUser user1 = this.userService.selectOne(new SlUser() {{
                        setId(shop.getOwnerId());
                    }});
                    if (null != user1) {
                        int silvers = detail.getDeductTotalSilver() * detail.getQuantity();
                        /**
                         * 扣除10%金豆 转入平台账号(账号名称100)
                         * 2018年6月14日20:02:44  mingel
                         */
                        int surplusSilvers = (int) (silvers * 0.9);
                        //手续费 10%金豆
                        int poundage = silvers - surplusSilvers;
                        int p = user1.getCoin() + surplusSilvers;
                        user1.setCoin(p);
                        userCache.put(user1.getClientId(), user1);
                        userService.updateByPrimaryKeySelective(new SlUser() {{
                            setId(user1.getId());
                            setCoin(p);
                        }});
                        //更新平台账号金豆数量
                        SlUser platform = userService.selectOne(new SlUser() {{
                            setUsername(100);
                        }});
                        Integer newCoin = platform.getCoin() + poundage;
                        userService.updateByPrimaryKeySelective(new SlUser() {{
                            setId(platform.getId());
                            setCoin(newCoin);
                        }});
                        //平台金豆记录
                        transactionDetailMapper.insertSelective(new SlTransactionDetail() {{
                            // 目标id
                            setTargetId(platform.getId());
                            // 订单id
                            setOrderId(order.getId());
                            // 创建时间
                            setCreateTime(new Date());
                            // 购物类型店主收入
                            setType(300);
                            // 增加金豆数量
                            setCoin(poundage);
                            // 金豆
                            setDealType(5);
                            // 收入
                            setTransactionType(2);
                        }});
                        //店铺金豆记录
                        transactionDetailMapper.insertSelective(new SlTransactionDetail() {{
                            // 目标id
                            setTargetId(user1.getId());
                            // 订单id
                            setOrderId(order.getId());
                            // 创建时间
                            setCreateTime(new Date());
                            // 购物类型店主收入
                            setType(300);
                            // 增加金豆数量
                            setCoin(surplusSilvers);
                            // 金豆
                            setDealType(5);
                            // 收入
                            setTransactionType(2);
                        }});
                    }
                }
            }
        }
    }

    /**
     * 分享奖励 将红包分享红包设置为可领取状态
     *
     * @param detail
     */
    public void  changeRedPacketResult (SlOrderDetail detail) {
        try {
            if (7 == detail.getType()) {
                SlOrderExtend slOrderExtend = slOrderExtendMapper.selectOne(new SlOrderExtend() {{
                    setOrderId(detail.getOrderId());
                    setServiceType((byte) 1);
                }});
                if (slOrderExtend != null) {
                    List<SlRedPacket> redPacketList = cmRedPacketMapper.listByOrderExtendId(slOrderExtend.getId());
                    if (redPacketList != null) {
                        for (SlRedPacket slRedPacket : redPacketList) {
                            redPacketService.updateByPrimaryKeySelective(new SlRedPacket() {{
                                    setId(slRedPacket.getId());
                                    setResult((byte) 5);
                            }});
                        }
                        log.debug("更新红包状态成功,订单ID：{}",detail.getOrderId());
                    }
                }

            }
        }catch ( Exception e ) {
            log.error("更新红包状态错误,订单ID：{}",detail.getOrderId(),e);
        }
    }

    /**
     * 保存购买人搜了贝以及交易记录
     * @param slSlbType
     * @param slOrder
     * @param orderDetail
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveSlbBuy(SlSlbType slSlbType,SlOrder slOrder,SlOrderDetail orderDetail){
        BigDecimal slb = slSlbType.getPresentNum().multiply(BigDecimal.valueOf(orderDetail.getQuantity()));
        SlUser slUser = userService.selectByPrimaryKey(slOrder.getUserId());
        SlPhoneZone slPhoneZone = slPhoneZoneMapper.selectOne(new SlPhoneZone(){{
            setZone(slUser.getZone()==""?"中国大陆":slUser.getZone());
        }});
        //用户信息里存在手机号
//        if (null != slUser.getPhone()){
//            //查看用户是否注册 true:已经注册 false:没有注册
//            if (thirdPartyWalletService.checkUserRegister(slUser.getPhone(), slPhoneZone.getMobilearea().toString())){
//                //获取钱包地址
//                String walletList = thirdPartyWalletService.getWalletList(slUser.getPhone(),slPhoneZone.getMobilearea().toString());
//                if ("".equals(walletList)){
//                    saveOrderHandle(slOrder.getId()+"1", slUser.getId(),"获取不到用户钱包地址");
//                }else {
//                    Date begin  = LocalDateTimeUtils.stringToDate(slOrder.getPayTime());
//                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//                    String lockBeginDate = sf.format(begin);
//                    Date endDate = LocalDateTimeUtils.addMonth(begin,slSlbType.getReleaseBatch());
//                    String lockEndDate =  sf.format(endDate);
//                    //SLB锁仓资产转入
//                    String code = thirdPartyWalletService.transferToSlbSc(walletList,lockBeginDate,lockEndDate,slSlbType.getReleaseBatch().toString(),
//                            slSlbType.getReleasePercent().toString(),slb.toString(), slOrder.getId()+"1",slSlbType.getSlbState());
//                    if (Integer.valueOf(code)<0){
//                        saveOrderHandle(slOrder.getId()+"1", slUser.getId(),"SLB锁仓资产转入失败");
//                    }
//                }
//            }else {
//                //用户钱包注册
//                String res = thirdPartyWalletService.UserRegister(slUser.getPhone(), BaseConstant.WALLET_DEFAULT_LOGIN_PASSWORD, slPhoneZone.getMobilearea().toString());
//                //注册成功
//                if ("0".equals(res)){
//                    //获取钱包地址
//                    String walletList = thirdPartyWalletService.getWalletList(slUser.getPhone(),slPhoneZone.getMobilearea().toString());
//                    if ("".equals(walletList)){
//                        saveOrderHandle(slOrder.getId()+"1", slUser.getId(),"获取不到用户钱包地址");
//                    }else {
//                        Date begin  = LocalDateTimeUtils.stringToDate(slOrder.getPayTime());
//                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//                        String lockBeginDate = sf.format(begin);
//                        Date endDate = LocalDateTimeUtils.addMonth(begin,slSlbType.getReleaseBatch());
//                        String lockEndDate =  sf.format(endDate);
//                        //SLB锁仓资产转入
//                        String code = thirdPartyWalletService.transferToSlbSc(walletList,lockBeginDate,lockEndDate,slSlbType.getReleaseBatch().toString(),
//                                slSlbType.getReleasePercent().toString(),slb.toString(), slOrder.getId()+"1",slSlbType.getSlbState());
//                        if (Integer.valueOf(code)<0){
//                            saveOrderHandle(slOrder.getId()+"1", slUser.getId(),"SLB锁仓资产转入失败");
//                        }
//                    }
//                }else{
//                    saveOrderHandle(slOrder.getId()+"1", slUser.getId(),"用户钱包注册失败");
//                }
//            }
//        }else {
//            saveOrderHandle(slOrder.getId()+"1", slUser.getId(),"用户没有绑定手机号");
//        }

        SlSlbTransaction slSlbTransaction1 = new SlSlbTransaction();
        slSlbTransaction1.setTargetId(slOrder.getUserId());
        slSlbTransaction1.setOrderId(slOrder.getId());
        slSlbTransaction1.setSlbType(slSlbType.getSlbType());
        slSlbTransaction1.setType(2);
        slSlbTransaction1.setSlb(slb);
        slSlbTransaction1.setTransactionType(2);
        slbTransactionMapper.insertSelective(slSlbTransaction1);

        //搜了贝有此用户 修改搜了贝 ; 没有此用户 添加
        SlUserSlb slUserSlb1 = this.userSlbService.selectOne(new SlUserSlb(){{
            setUserId(slOrder.getUserId());
            setSlbType(slSlbType.getSlbType());
        }});
        if(null != slUserSlb1){
            userSlbService.updateByPrimaryKeySelective(new SlUserSlb(){{
                setId(slUserSlb1.getId());
                setSlb(slUserSlb1.getSlb().add(slb));
                }});
        }else {
            SlUserSlb slUserSlb = new SlUserSlb();
            slUserSlb.setUserId(slOrder.getUserId());
            slUserSlb.setSlb(slb);
            slUserSlb.setSlbType(slSlbType.getSlbType());
            userSlbService.insert(slUserSlb);
        }

        //资金池搜了贝修改以及搜了贝交易记录
        //savePoolSlb(slb,slOrder.getUserId(),orderDetail.getOrderId(),5);
        cmTotalPoolService.updatePool(null,null,slb,2,slOrder.getId(),slOrder.getUserId(),5);
    }

    /**
     *   保存邀请人搜了贝以及交易记录
     * @param slUser
     * @param slOrder
     * @param slSlbType
     * @param bean
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveSlbInvite( SlUser slUser, SlOrder slOrder,SlSlbType slSlbType,BigDecimal bean){
        SlPhoneZone slPhoneZone = slPhoneZoneMapper.selectOne(new SlPhoneZone(){{
            setZone(slUser.getZone()==""?"中国大陆":slUser.getZone());
        }});
        //用户信息里存在手机号
//        if (null != slUser.getPhone()){
//            //查看用户是否注册 true:已经注册 false:没有注册
//            if (thirdPartyWalletService.checkUserRegister(slUser.getPhone(), slPhoneZone.getMobilearea().toString())){
//                //获取钱包地址
//                String walletList = thirdPartyWalletService.getWalletList(slUser.getPhone(),slPhoneZone.getMobilearea().toString());
//                if ("".equals(walletList)){
//                    saveOrderHandle(slOrder.getId()+"2", slUser.getId(),"获取不到用户钱包地址");
//                }else {
//                    Date begin  = LocalDateTimeUtils.stringToDate(slOrder.getPayTime());
//                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//                    String lockBeginDate = sf.format(begin);
//                    Date endDate = LocalDateTimeUtils.addMonth(begin,slSlbType.getReleaseBatch());
//                    String lockEndDate =  sf.format(endDate);
//                    //SLB锁仓资产转入
//                   String code =  thirdPartyWalletService.transferToSlbSc(walletList,lockBeginDate,lockEndDate,slSlbType.getReleaseBatch().toString(),
//                            slSlbType.getReleasePercent().toString(),bean.toString(), slOrder.getId()+"2",slSlbType.getSlbState());
//                    if (Integer.valueOf(code)<0){
//                        saveOrderHandle(slOrder.getId()+"2", slUser.getId(),"SLB锁仓资产转入失败");
//                    }
//                }
//            }else {
//                //用户钱包注册
//                String res = thirdPartyWalletService.UserRegister(slUser.getPhone(), BaseConstant.WALLET_DEFAULT_LOGIN_PASSWORD, slPhoneZone.getMobilearea().toString());
//                //注册成功
//                if ("0".equals(res)){
//                    //获取钱包地址
//                    String walletList = thirdPartyWalletService.getWalletList(slUser.getPhone(),slPhoneZone.getMobilearea().toString());
//                    if ("".equals(walletList)){
//                        saveOrderHandle(slOrder.getId()+"2", slUser.getId(),"获取不到用户钱包地址");
//                    }else {
//                        Date begin  = LocalDateTimeUtils.stringToDate(slOrder.getPayTime());
//                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//                        String lockBeginDate = sf.format(begin);
//                        Date endDate = LocalDateTimeUtils.addMonth(begin,slSlbType.getReleaseBatch());
//                        String lockEndDate =  sf.format(endDate);
//                        //SLB锁仓资产转入
//                        String code = thirdPartyWalletService.transferToSlbSc(walletList,lockBeginDate,lockEndDate, slSlbType.getReleaseBatch().toString(),
//                                slSlbType.getReleasePercent().toString(),bean.toString(), slOrder.getId()+"2",slSlbType.getSlbState());
//                        if (Integer.valueOf(code)<0){
//                            saveOrderHandle(slOrder.getId()+"2", slUser.getId(),"SLB锁仓资产转入失败");
//                        }
//                    }
//                }else {
//                    saveOrderHandle(slOrder.getId()+"2", slUser.getId(),"用户钱包注册失败");
//                }
//            }
//        }else {
//            saveOrderHandle(slOrder.getId()+"2", slUser.getId(),"用户没有绑定手机号");
//        }

        SlSlbTransaction slSlbTransaction1 = new SlSlbTransaction();
        slSlbTransaction1.setTargetId(slUser.getId());
        slSlbTransaction1.setOrderId(slOrder.getId());
        slSlbTransaction1.setSlbType(slSlbType.getSlbType());
        slSlbTransaction1.setType(3);
        slSlbTransaction1.setSlb(bean);
        slSlbTransaction1.setTransactionType(2);
        slbTransactionMapper.insertSelective(slSlbTransaction1);

        //搜了贝有此用户 修改搜了贝 ; 没有此用户 添加
        SlUserSlb slUserSlb1 = this.userSlbService.selectOne(new SlUserSlb(){{
            setUserId(slUser.getId());
            setSlbType(slSlbType.getSlbType());
        }});
        if(null != slUserSlb1){
            userSlbService.updateByPrimaryKeySelective(new SlUserSlb(){{
                setId(slUserSlb1.getId());
                setSlb(slUserSlb1.getSlb().add(bean));}});
        }else {
            SlUserSlb slUserSlb = new SlUserSlb();
            slUserSlb.setUserId(slUser.getId());
            slUserSlb.setSlb(bean);
            slUserSlb.setSlbType(slSlbType.getSlbType());
            userSlbService.insert(slUserSlb);
        }
        cmTotalPoolService.updatePool(null,null,bean,2,slOrder.getId(),slOrder.getUserId(),8);
      //  savePoolSlb(bean,slOrder.getUserId(),slOrder.getId(),8);
    }
    /**
     *
     * 给以前购买的区块链商品（助力购物）返回搜了贝
     */
    @Transactional(rollbackFor = Exception.class)
    public  void  returnSLBFormPowerShopping () {
        try {
            //获取搜了贝商品集合
            List<SlOrderDetail> detailList = orderDetailService.select( new SlOrderDetail(){{
                setType(4);
            }});
            Date now = new Date();
            for (SlOrderDetail slOrderDetail : detailList){

                if (slOrderDetail.getCreatedAt().before(now)){
                    BigDecimal price = slOrderDetail.getPrice();
                    SlSlbType slSlbType = slSlbTypeService.selectOne(new SlSlbType() {{
                        setPrice(price);
                    }});
                    if (slSlbType != null ) {
                        //给购买人返贝
                        SlOrder slOrder = orderService.selectByPrimaryKey(slOrderDetail.getOrderId());
                         if (slOrder.getPaymentState() == 1 ) {
                             saveSlbBuy(slSlbType,slOrder,slOrderDetail);
                             log.debug("给订单ID{}返回搜了贝成功,该商品的订单支付状态为{}",slOrderDetail.getOrderId(),slOrder.getPaymentState());
                             //给邀请人返贝
                             if (slOrderDetail.getInviterId() != null && slOrderDetail.getInviterId()!= 0 ) {
                                 SlUser user = userService.selectOne( new SlUser(){{
                                     setUsername(slOrderDetail.getInviterId());
                                 }});
                                 //平台账号
                                 SlUser platform = userService.selectOne( new SlUser(){{
                                    setUsername(7777);
                                 }});
                                    if (user != null) {
                                        BigDecimal slb = price.multiply(new BigDecimal(0.05)).
                                                multiply(new BigDecimal(slOrderDetail.getQuantity()));
                                        saveSlbInvite(user,slOrder,slSlbType,slb);
                                        log.debug("给邀请人{}返回搜了贝成功",user.getUsername());
                                    } else{
                                        BigDecimal slb = price.multiply(new BigDecimal(0.05)).
                                                multiply(new BigDecimal(slOrderDetail.getQuantity()));
                                        saveSlbInvite(platform,slOrder,slSlbType,slb);
                                        log.debug("给邀请人{}返回搜了贝成功",platform.getUsername());
                                    }


                             }
                         }
                    }
                }
            }
        } catch (Exception e) {
            log.error("给订单返回搜了贝失败",e);
        }
    }

    /**
     *
     * 给以前购买的区块链商品（助力购物）导入钱包APP中的搜了贝
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public  void  transferSlbToWallet () {
        try {
            //获取已经购买搜了贝商品集合1 7-12凌晨之前
            List<SlOrderDetail> detailList1 = cmOrderMapper.getSlbOrderDetail("1");
            dealWithOldOrder(detailList1,"1");
            //获取已经购买搜了贝商品集合2 7-12凌晨之后
            List<SlOrderDetail> detailList2 = cmOrderMapper.getSlbOrderDetail("2");
            dealWithOldOrder(detailList2,"2");
            //获取网页注册的用户得到的搜了贝
            List<SlSlbTransaction> slSlbTransactions = cmSlbTransactionMapper.selectSlb();
            dealWebOldSlb(slSlbTransactions);
        }catch (Exception e) {
            log.error("给订单返回搜了贝失败",e);
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public  void  transferSlbToWalletBefore () {
        try {
            //获取已经购买搜了贝商品集合1 7-12凌晨之前
            List<SlOrderDetail> detailList1 = cmOrderMapper.getSlbOrderDetail("1");
            dealWithOldOrder(detailList1,"1");
        }catch (Exception e) {
            log.error("给订单返回搜了贝失败",e);
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public  void  transferSlbToWalletAfter () {
        try {
            //获取已经购买搜了贝商品集合2 7-12凌晨之后
            List<SlOrderDetail> detailList2 = cmOrderMapper.getSlbOrderDetail("2");
            dealWithOldOrder(detailList2,"2");
        }catch (Exception e) {
            log.error("给订单返回搜了贝失败",e);
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public  void  transferSlbToWalletUser () {
        try {
            //获取网页注册的用户得到的搜了贝
            List<SlSlbTransaction> slSlbTransactions = cmSlbTransactionMapper.selectSlb();
            dealWebOldSlb(slSlbTransactions);
        }catch (Exception e) {
            log.error("给订单返回搜了贝失败",e);
        }
    }

    /**
     * 处理之前区块链订单，给购买这和消费返SLB到钱包
     * @param lists 区块链订单集合
     * @param compareDate 时间段
     */
    public void dealWithOldOrder(List<SlOrderDetail> lists,String compareDate){
        for (SlOrderDetail detail : lists) {
            //用户
            SlUser user = userService.selectByPrimaryKey(detail.getCreator());
            //邀请人
            SlUser inviter =  userService.selectOne( new SlUser(){{
                setUsername(detail.getInviterId());
            }});
            SlOrder order = orderService.selectByPrimaryKey(detail.getOrderId());
           //SLB参数表
            SlSlbType slSlbType = slSlbTypeService.selectOne( new SlSlbType(){{
                setPrice(detail.getPrice());
            }});
            //转入SLB返回代码
            String returnCode = null;
            //给邀请人返的SLB
            BigDecimal bean = order.getTotalAmount().multiply(new BigDecimal(0.05));
            //给消费者
            if (user != null && !SLStringUtils.isEmpty(user.getPhone())) {
                SlPhoneZone slPhoneZone =  slPhoneZoneMapper.selectOne(new SlPhoneZone(){{
                    setZone(user.getZone());
                }});
                //查询用户是否注册钱包APP接口
                Boolean isRegister = thirdPartyWalletService.checkUserRegister(user.getPhone(), slPhoneZone.getMobilearea().toString());
                //没有用户就开始注册
                if (!isRegister) {
                    //查询用户手机号地区代码
                    String codeStr = thirdPartyWalletService.UserRegister(user.getPhone(),BaseConstant.WALLET_DEFAULT_LOGIN_PASSWORD,slPhoneZone.getMobilearea().toString());
                    Integer code = Integer.parseInt(codeStr);
                    if (code != 0) {
                        saveOrderHandle(detail.getOrderId()+"1", user.getId(), "用户注册失败");
                        continue;
                    }
                }
                //获取用户钱包接口
                String userWallet = thirdPartyWalletService.getWalletList(user.getPhone(),slPhoneZone.getMobilearea().toString());
                if (!SLStringUtils.isEmpty(userWallet)){
                    //SLB总额
                    BigDecimal amount = new BigDecimal(0);
                    //类型
                    String  batchType = null;
                    //购买数量
                    Integer quantity = detail.getQuantity() == null ? 0 : detail.getQuantity();
                    //开始日期
                    String payTime = order.getPayTime();
                    Date payDate = LocalDateTimeUtils.stringToDate(payTime);
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
                    String lockBeginDate =  sf.format(payDate);
                    Date endDate  = LocalDateTimeUtils.addMonth(payDate,slSlbType.getReleaseBatch());
                    String lockEndDate =sf.format(endDate);
                    //订单sn
                    String  orderSn = detail.getOrderId()+"1";
                    String price =  detail.getPrice().stripTrailingZeros().toPlainString();
                    //如果是7.12凌晨之前
                    if (compareDate.equals("1")){
                        if (price.equals("20000")) {
                            amount = new BigDecimal(400000).multiply(new BigDecimal(quantity));
                        } else if (price.equals("10000")) {
                            amount = new BigDecimal(125000).multiply(new BigDecimal(quantity));
                        } else if (price.equals("5000")) {
                            amount = new BigDecimal(31250).multiply(new BigDecimal(quantity));
                        } else if (price.equals("1000")) {
                            amount = new BigDecimal(3030).multiply(new BigDecimal(quantity));
                        } else if (price.equals("500")) {
                            amount = new BigDecimal(892).multiply(new BigDecimal(quantity));
                        } else {
                            continue;
                        }
                        returnCode = thirdPartyWalletService.transferToSlbSc(userWallet,lockBeginDate,lockEndDate,
                                slSlbType.getReleaseBatch().toString(),slSlbType.getReleasePercent().toPlainString(),
                                amount.stripTrailingZeros().toPlainString(),orderSn,slSlbType.getSlbState());
                    }
                    //如果是7.12凌晨之后
                    else if (compareDate.equals("2")){
                        if (slSlbType != null){
                            amount = slSlbType.getPresentNum().multiply(new BigDecimal(detail.getQuantity()));
                            returnCode =  thirdPartyWalletService.transferToSlbSc(userWallet,lockBeginDate,lockEndDate,
                                    slSlbType.getReleaseBatch().toString(),slSlbType.getReleasePercent().toPlainString(),
                                    amount.stripTrailingZeros().toPlainString(),orderSn,slSlbType.getSlbState());
                        }else {
                            continue;
                        }
                    }

                }else {
                    saveOrderHandle(detail.getOrderId()+"1", user.getId(), "用户获取钱包地址失败");
                    continue;
                }
            }
            //给邀请人返贝
            if (inviter != null && !SLStringUtils.isEmpty(inviter.getPhone())) {
                saveSlbInvite(inviter,order,slSlbType,bean);
            }
            if ("0".equals(returnCode) ){
                log.debug("用户装入SLB成功-----------------用户id：{}",user == null ? "用户为空": user.getId());
            } else {
                saveOrderHandle(detail.getOrderId()+"1", user.getId(), "用户转入SLB到钱包失败");
            }
        }

    }
    /**
     * 处理之前web网页注册赠送的搜了贝
     * @param slSlbTransactions 页注册赠送的搜了贝集合
     */
    public void dealWebOldSlb(List<SlSlbTransaction> slSlbTransactions){
        for (SlSlbTransaction slSlbTransaction : slSlbTransactions) {
            //用户
            SlUser user = userService.selectByPrimaryKey(slSlbTransaction.getTargetId());

            //SLB参数表
            SlSlbType slSlbType = slSlbTypeService.selectOne( new SlSlbType(){{
                setSlbType(2);
            }});
            //生成订单号
            String  orderId = formatUUID32()+"B";
            //给消费者
            if (user != null && !SLStringUtils.isEmpty(user.getPhone())) {
                SlPhoneZone slPhoneZone =  slPhoneZoneMapper.selectOne(new SlPhoneZone(){{
                    setZone(user.getZone());
                }});
                //查询用户是否注册钱包APP接口
                Boolean isRegister = thirdPartyWalletService.checkUserRegister(user.getPhone(), slPhoneZone.getMobilearea().toString());
                //没有用户就开始注册
                if (!isRegister) {
                    //查询用户手机号地区代码
                    String codeStr = thirdPartyWalletService.UserRegister(user.getPhone(),BaseConstant.WALLET_DEFAULT_LOGIN_PASSWORD,slPhoneZone.getMobilearea().toString());
                    Integer code = Integer.parseInt(codeStr);
                    if (code != 0) {
                        saveOrderHandle(orderId,user.getId(),"用户注册失败");
                        continue;
                    }
                }
                //获取用户钱包接口
                String userWallet = thirdPartyWalletService.getWalletList(user.getPhone(),slPhoneZone.getMobilearea().toString());
                if (!SLStringUtils.isEmpty(userWallet)){
                    //开始日期
                    Date payDate = slSlbTransaction.getCreatedAt();
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
                    String lockBeginDate =  sf.format(payDate);
                    Date endDate  = LocalDateTimeUtils.addMonth(payDate,24);
                    String lockEndDate =sf.format(endDate);
                    String  returnCode = thirdPartyWalletService.transferToSlbSc(userWallet,lockBeginDate,lockEndDate,
                            slSlbType.getReleaseBatch().toString(),slSlbType.getReleasePercent().toPlainString(),
                            "1",orderId,slSlbType.getSlbState());
                    if (!"0".equals(returnCode)){
                        saveOrderHandle(orderId,user.getId(),"SLB资产装入失败");
                    }
                }else {
                    saveOrderHandle(orderId,user.getId(),"获取用户钱包地址失败");
                }

            }else {
                saveOrderHandle(orderId,user.getId(),"手机号没有绑定");
                continue;
            }
        }
    }
    /**
     * A轮订单录入
     *
     * @param id          搜了ID
     * @param slbType     哪一轮
     * @param totalAmount 订单总金额
     * @param quantity    购买数量
     * @param inviterId   邀请人ID
     * @param date        支付时间
     * @param checkName   审核人
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BusinessMessage enterOrder(Integer id, String slbType, Double totalAmount, Integer quantity, Integer inviterId, String  date, String checkName ) {
        log.debug(" id = [" + id + "], slbType = ["+slbType+"] totalAmount = ["+totalAmount+"], quantity = ["+quantity+"], inviterId = ["+inviterId+"], checkName = ["+checkName+"]");
        BusinessMessage message = new BusinessMessage();
        if (id == null ) {
            message.setMsg("购买人搜了ID不能为空");
            message.setSuccess(false);
            return message;
        }
        if (inviterId == null)  {
            message.setMsg("邀请人搜了ID不能为空");
            message.setSuccess(false);
            return message;
        }
        if (slbType == null)  {
            message.setMsg("轮数不能为空");
            message.setSuccess(false);
            return message;
        }
        //支付时间 yyyy-mm-dd 改为 yyyy-mm-dd HH:mm:dd
        String payTime = date+" 00:00:00";
        //购买人信息
        SlUser slUserBuy = userService.selectOne(new SlUser() {{
            setUsername(id);
        }});
        //邀请人信息
        Integer finalInviterId = inviterId;
        SlUser slUserInvite = userService.selectOne(new SlUser() {{
            setUsername(finalInviterId);
        }});
        //如果邀请人不存在 使用公司账号7777
        if (null == slUserInvite){
            inviterId = 7777;
        }
//        生成订单号
        String orderNum = OrderNumGeneration.getOrderIdByUUId();
        String productId ;
        if ("A".equals(slbType)){
            productId = BaseConstant.PRODUCT_ID_A;
        }else if ("B".equals(slbType)){
            productId = BaseConstant.PRODUCT_ID_B;
        }else if ("C".equals(slbType)){
            productId = BaseConstant.PRODUCT_ID_C;
        }else if ("D".equals(slbType)){
            productId = BaseConstant.PRODUCT_ID_D;
        }else {
            productId = BaseConstant.PRODUCT_ID_E;
        }
        //获取商品信息
        SlProduct slProduct = productService.selectByPrimaryKey(productId);
        //获取活动信息
        SlActivityProduct slActivityProduct = activityProductMapper.selectOne(new SlActivityProduct(){{
            setProductId(productId);
        }});
        //获取规格信息
        SlProductRepository slProductRepository = productRepositoryService.selectOne(new SlProductRepository(){{
            setProductId(productId);
        }});
        if(null != slUserBuy){
            //订单表 数据保存
            String finalPayTime = payTime;
            Integer result = orderService.insertSelective(new SlOrder(){{
                setSerialNumber(orderNum);
                setUserId(slUserBuy.getId());
                setTotalAmount(new BigDecimal(totalAmount));
                setPaymentState(1);
                setPaymentChannel(5);
                setPayTime(finalPayTime);
                setPayTimeStamp(LocalDateTimeUtils.stringToDate(payTime).getTime()/1000);
                setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }});
            SlOrder slOrder = orderService.selectOne(new SlOrder(){{
                setSerialNumber(orderNum);
                setUserId(slUserBuy.getId());
            }});
            Integer finalInviterId1 = inviterId;
            //订单明细表 数据保存
            orderDetailService.insertSelective(new SlOrderDetail(){{
                setId(formatUUID32());
                setOrderId(slOrder.getId());
                setSerialNumber(OrderNumGeneration.getOrderIdByUUId());
                setShopId(slProduct.getShopId());
                setRepositoryId(slProductRepository.getId());
                setProductId(slProduct.getId());
                setProductName(slProduct.getName());
                setProductImageUrl(slProduct.getImageUrl());
                setProductDetailGroupName(slProductRepository.getProductDetailGroupName());
                setShippingState(3);
                setQuantity(quantity);
                setPrice(slActivityProduct.getPrice());
                setCreator(slUserBuy.getId());
                setCreateTime(payTime);
                setActivityProductId(slActivityProduct.getId());
                setType(4);
                setCheckState((byte)2);
                setCheckName(checkName);
                setInviterId(finalInviterId1);
                setCreatedAt(LocalDateTimeUtils.stringToDate(payTime));
            }});
            //订单表插入成功后 给邀请人返10%金额+5%搜了贝
            if(result>0){
                processOrders.fanMoney(slOrder.getId());
            }
            log.debug("订单录入成功");
            message.setMsg("订单录入成功");
            message.setSuccess(true);
        }else{
            log.debug("购买人不存在");
            message.setMsg("购买人不存在");
            message.setSuccess(false);
            return message;
        }
        return message;
    }
    /**
     * 定时转换sl_slb_type搜了贝数
     */
    @Transactional(rollbackFor = Exception.class)
    public  void  changeSlbScheduled () {
        try {
            this.cmOrderMapper.changeSlbScheduled();
            log.debug("转换sl_slb_type搜了贝数成功");
        } catch (Exception e) {
            log.error("转换sl_slb_type搜了贝数失败",e);
        }

    }
    /**
     * 多商品下单 限时秒杀
     *
     * @param request
     * @param detail            商品1的规格id|商品1的商品数量|商品1买家留言|分享人id(如果是分享奖励的话)
     * @param shippingAddressId 当前用户选择的收货地址id
     * @param postFee           邮费
     * @return
     */
    @Transactional
    public BusinessMessage limitTimeOrder(HttpServletRequest request, String[] detail, String shippingAddressId, String postFee) {
        log.debug("request = [" + request + "], detail = [" + detail + "], shippingAddressId = [" + shippingAddressId + "]");
        BusinessMessage message = new BusinessMessage();
        double money = 0.00+Double.parseDouble(postFee);
        SlUser user = loginUserService.getCurrentLoginUser();
        if (null != user) {

            SlOrder slOrder = new SlOrder();
            String orderNum = OrderNumGeneration.getOrderIdByUUId();// 生成订单编号
            slOrder.setId(formatUUID32());
            slOrder.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            slOrder.setSerialNumber(orderNum);// 订单编号
            slOrder.setUserId(user.getId());// 用户Id
            SlUserAddress slUserAddress = this.slUserAddressMapper.selectOne(new SlUserAddress() {{
                setId(shippingAddressId);
                setUserId(user.getId());
            }});
            if (null != slUserAddress) {
                slOrder.setProvince(slUserAddress.getProvince());// 订单省的地址
                slOrder.setCity(slUserAddress.getCity()); // 订单市的收货地址
                slOrder.setCounty(slUserAddress.getCounty()); // 订单区的收货地址
                slOrder.setDetailed(slUserAddress.getDetailed()); // 订单详细收货地址
                slOrder.setConsigneename(slUserAddress.getName());// 收货人姓名
                slOrder.setConsigneephone(slUserAddress.getPhone());// 收货人的联系方式
                int co = orderService.insertSelective(slOrder);// 插入订单表
                //订单加入redis 有效期为一天
                orderCache.put(slOrder.getId(), slOrder, 1L, TimeUnit.DAYS);
                if (co > 0) {
                    outer:
                    for (String aa : detail) {
                        String[] bb = aa.split(",");
                        inner:
                        for (String cc : bb) {
                            String repositoryId = cc.split("\\|")[0];
                            int quantity = Integer.valueOf(cc.split("\\|")[1]);
                            String buyerMessage = cc.split("\\|")[2];
                            String activityProductId = cc.split("\\|")[3];
                            SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                                setId(repositoryId);
                            }});
                            // 仓库存在 并且加入订单的商品大于0
                            if (null != repository && quantity > 0) {
                                SlProduct slProduct = this.productService.selectOne(new SlProduct() {{
                                    setId(repository.getProductId());
                                    setSoldOut(true);
                                }});
                                if (null != slProduct) {
                                    //查询活动商品信息
                                    SlActivitySeckill slActivitySeckill = this.slActivitySeckillMapper.selectOne(new SlActivitySeckill(){{
                                        setProductOldId(repository.getProductId());
                                        setEnable(true);
                                    }});
                                    SlActivityProduct slActivityProduct = this.cmOrderMapper.selectActivityProductByRepositoryId(repository.getId(), activityProductId);
                                    if (null != slActivitySeckill) {
                                        // 算出失效时间 活动结束时间 - 当前时间
//                                        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                                        LocalDate ldt = LocalDate.parse(slActivitySeckill.getEndTime().toString(), df);
//                                        ZoneId zone = ZoneId.systemDefault();
//                                        Instant instant = ldt.atStartOfDay().atZone(zone).toInstant();
//                                        Date date = Date.from(instant);
                                        // 把查询出来的商品信息放入redis中 插入失效时间
//                                        productCache.put(slProduct.getId(), slProduct);
//                                        productCache.expireAt(slProduct.getId(), date);
                                        Long plusTime = slActivitySeckill.getEndTime().getTime() - System.currentTimeMillis();
                                        // 把查询出来的商品信息放入redis中 插入失效时间
                                        productCache.put(slProduct.getId(), slProduct);
                                        productCache.expire(slProduct.getId(),plusTime,TimeUnit.SECONDS);
                                        // 判断当前活动是否在有效期内
                                        if (productCache.hasKey(slProduct.getId())) {
                                            // 查询当前用户该商品的已生成订单的商品数量之和
                                            int counts = this.cmOrderMapper.selectOrdersCount(slProduct.getId(), user.getId(), slActivityProduct.getId());
                                            //单用户购买限制数量 - 他已经下单的商品数量 >= 这次加入订单的数量
                                            if (slActivityProduct.getRestrictCount() - counts >= quantity) {
                                                //库存的数量 > 他这次加入订单的数量
                                                if (repository.getSekillCount() - quantity >= 0) {
                                                    // 钱相加 用于统计和添加到订单表扣除总钱里边
                                                    money += slActivitySeckill.getSeckillPrice().doubleValue() * quantity;

                                                    orderDetailService.insertSelective(new SlOrderDetail() {{
                                                        setId(formatUUID32());
                                                        setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                                        //邀请人ID
                                                        setInviterId(7777);
                                                        // 拼团订单去这个属性判断是哪个人的
                                                        setCreator(slOrder.getUserId());
                                                        // 商品名称
                                                        setProductName(slProduct.getName());
                                                        // 商品图片
                                                        setProductImageUrl(slProduct.getImageUrl());
                                                        // 订单ID
                                                        setOrderId(slOrder.getId());
                                                        // 邮费
                                                        setPostFee(slProduct.getPostage());
                                                        // 商品数量
                                                        setQuantity(quantity);
                                                        // 单个商品价格
                                                        setPrice(slActivitySeckill.getSeckillPrice());
                                                        // 商品规格名称
                                                        setProductDetailGroupName(repository.getProductDetailGroupName());
                                                        // 活动id
                                                        setActivityProductId(slActivityProduct.getId());
                                                        // 商品ID
                                                        setProductId(repository.getProductId());
                                                        // 店铺唯一标识
                                                        setShopId(repository.getShopId());
                                                        // 店铺仓库ID
                                                        setRepositoryId(repository.getId());
                                                        // 扣除单个商品了豆数量
                                                        setDeductTotalSilver(repository.getSilver());
                                                        // 添加买家留言
                                                        setBuyerMessage(buyerMessage);
                                                        // 订单类型
                                                        setType(8);
                                                        // 订单编号
                                                        setSerialNumber(orderNum);
                                                        //  消费奖励
                                                        if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_REBATE) {
                                                            if (repository.getReturnCashMoney().compareTo(new BigDecimal(0)) > 0) {
                                                                // 消费返利金额
                                                                setReturnCashMoney(repository.getReturnCashMoney());
                                                            }
                                                        }
                                                        //  分享奖励
                                                        if (slActivityProduct.getActivityId().equals(ActivityConstant.RECOMMEND_AWARDS_ACTIVITY)) {
                                                            // 插入分享人id
                                                            String shareOfPeopleId = cc.split("\\|")[4];
                                                            setShareOfPeopleId(shareOfPeopleId);
                                                            //分享奖励金额
                                                            setReturnCashMoney(repository.getReturnCashMoney());
                                                        }
                                                        // 查询当前用户的支付订单
                                                        Boolean flag = orderService.exist(new SlOrder() {{
                                                            setUserId(user.getId());
                                                            // 已支付
                                                            setPaymentState(1);
                                                        }});
                                                        //  新人专享
                                                        //判断是否为首单
                                                        if (flag.equals(false) && slActivityProduct.getActivityId().equals(ActivityConstant.NEW_PEOPLE_ACTIVITY)) {
                                                            // 如果是第一单的情况下 需要加上 首单奖励
                                                            setPlaceOrderReturnPulse(repository.getPlaceOrderReturnPulse() * quantity + repository.getFirstOrderPulse());
                                                        } else {
                                                            // 返了豆数量只限纯金钱模式
                                                            setPlaceOrderReturnPulse(repository.getPlaceOrderReturnPulse() * quantity);
                                                        }
                                                    }});
                                                    // 如果库存为0 的话就下架了
                                                    Example example = new Example(SlActivityProduct.class);
                                                    example.createCriteria()
                                                            .andGreaterThan("count", 0)
                                                            .andEqualTo("id", slActivityProduct.getId());
                                                    // 商品总库存 - 本次加入订单的数量
                                                    this.activityProductMapper.updateByExampleSelective(new SlActivityProduct() {{
                                                        //活动总商品上架数量 - 本次购买的数量
                                                        setCount(slActivityProduct.getCount() - quantity);
                                                    }}, example);

                                                    //sl_activity_seckill修改库存
                                                    this.slActivitySeckillMapper.updateByPrimaryKeySelective(new SlActivitySeckill(){{
                                                        setId(slActivitySeckill.getId());
                                                        setTotalCount(slActivitySeckill.getTotalCount() - quantity);
                                                    }});
                                                    // 当前库存 - 本次该商品规格下单库存
                                                    repository.setSekillCount(repository.getSekillCount() - quantity);
                                                    // 更新redis中该商品规格的库存
                                                    repositoryCache.put(repository.getId(), repository);
                                                    // 更新数据库该商品规格的库存
                                                    int updateCount = this.cmOrderMapper.reduceNumber(repository.getId(), repository.getSekillCount());
                                                    // 把虚拟销量加上
                                                    productService.updateByPrimaryKeySelective(new SlProduct() {{
                                                        setId(slProduct.getId());
                                                        setSalesVirtual(slProduct.getSalesVirtual() + quantity);
                                                    }});
                                                    if (updateCount > 0) {
                                                        message.setMsg("订单生成成功");
                                                        message.setSuccess(true);
                                                    }
                                                } else {
                                                    log.error("当前库存不足");
                                                    message.setMsg(slProduct.getName() + "当前库存不足");
                                                    message.setSuccess(false);
                                                    break outer;
                                                }
                                            } else {
                                                log.error("已超过最大购买数量");
                                                message.setMsg(slProduct.getName() + "已超过最大购买数量");
                                                message.setSuccess(false);
                                                break outer;
                                            }
                                        } else {
                                            log.error("活动商品时间错误");
                                            message.setMsg(slProduct.getName() + "活动商品时间错误");
                                            message.setSuccess(false);
                                            break outer;
                                        }
                                    } else {
                                        log.error("商品已下架");
                                        message.setMsg(slProduct.getName() + "商品已下架");
                                        message.setSuccess(false);
                                        break outer;
                                    }
                                } else {
                                    log.error("该商品不存在或已下架");
                                    message.setMsg(slProduct.getName() + "该商品不存在或已下架");
                                    message.setSuccess(false);
                                    break outer;
                                }
                            }
                        }
                    }
                    if (message.getSuccess() == false) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    } else {
                        // 更新订单总价和总豆
                        double finalMoney = money;
                        this.orderService.updateByPrimaryKeySelective(new SlOrder() {{
                            setId(slOrder.getId());
                            setTotalAmount(BigDecimal.valueOf(finalMoney));
                            setDeductTotalPulse(0);
                        }});
                        Map<String, String> map = new HashMap<>();
                        map.put("order_num", slOrder.getId());
                        map.put("total_amount", String.valueOf(money));
                        message.setData(map);
                    }
                }
            } else {
                log.error("收货地址不存在");
                message.setMsg("收货地址不存在");
                return message;
            }
        } else {
            log.error("用户不存在");
            message.setMsg("用户不存在");
            return message;
        }
        return message;
    }
    /**
     * 单商品下单 限时秒杀
     *
     * @param request
     * @param response
     * @param repositoryId      规格id
     * @param quantity          数量
     * @param shareOfPeopleId   分享人Id
     * @param shippingAddressId 用户地址id
     * @param buyerMessage      买家留言
     * @param activityProductId 活动商品Id
     * @param postFee             邮费
     * @return
     */
    @Transactional
    public BusinessMessage limitTimeOrderOnly(HttpServletRequest request,
                                            HttpServletResponse response,
                                            String repositoryId,
                                            Integer quantity,
                                            String shareOfPeopleId,
                                            String shippingAddressId,
                                            String buyerMessage,
                                            String activityProductId,
                                            String postFee) {
        log.debug("request = [" + request + "], response = [" + response + "], repositoryId = [" + repositoryId + "], quantity = [" + quantity + "], postFee = ["+postFee+"]");
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (null != user) {
            SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                setId(repositoryId);
            }});
            //如果查询出来不为空就去查询商品信息
            if (null != repository) {
                SlProduct slProduct = this.productService.selectOne(new SlProduct() {{
                    setId(repository.getProductId());
                }});
                //如果商品存在的话
                if (null != slProduct) {
                    //把邮费加上
                    slProduct.setPostage(BigDecimal.valueOf(Double.parseDouble(postFee)));
                    // 把虚拟销量加上
                    productService.updateByPrimaryKeySelective(new SlProduct() {{
                        setId(slProduct.getId());
                        setSalesVirtual(slProduct.getSalesVirtual() + quantity);
                    }});
                    //查询活动商品信息
                    SlActivitySeckill slActivitySeckill = this.slActivitySeckillMapper.selectOne(new SlActivitySeckill(){{
                        setProductOldId(repository.getProductId());
                        setEnable(true);
                    }});
                    SlActivityProduct activityProduct = this.cmOrderMapper.selectActivityProductByRepositoryId(repositoryId, activityProductId);
                    if (null != slActivitySeckill) {
                        // 算出失效时间 活动结束时间 - 当前时间
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                        Long hao = slActivitySeckill.getEndTime().getTime() - System.currentTimeMillis();
//                        LocalDate ldt = LocalDate.parse(slActivitySeckill.getEndTime().toString(), df);
//                        ZoneId zone = ZoneId.systemDefault();
//                        Instant instant = ldt.atStartOfDay().atZone(zone).toInstant();
//                        Date date = Date.from(instant);
                        Date date = new Date(slActivitySeckill.getEndTime().getTime() - System.currentTimeMillis());
                        Long plusTime = slActivitySeckill.getEndTime().getTime() - System.currentTimeMillis();
                        // 把查询出来的商品信息放入redis中 插入失效时间
                        productCache.put(slProduct.getId(), slProduct);
//                        productCache.expireAt(slProduct.getId(), date);
                        productCache.expire(slProduct.getId(),plusTime,TimeUnit.SECONDS);
                        // 判断当前活动是否在有效期内
                        if (productCache.hasKey(slProduct.getId())) {
                            // 此活动商品当前用户的下单商品数量
                            int count = this.cmOrderMapper.selectOrdersCount(slProduct.getId(), user.getId(), activityProduct.getId());
                            // 本次下单的商品数量 + 当前用户的该商品下单量 <= 商品限制购买单数
                            if (quantity + count <= activityProduct.getRestrictCount()) {
                                // 本规格下的库存 >= 本次下单的商品数量
                                if (repository.getSekillCount() >= quantity) {
                                    String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                    SlOrder slOrder = new SlOrder();
                                    // 订单id
                                    slOrder.setId(formatUUID32());
                                    // 创建时间
                                    slOrder.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                    // 用户Id
                                    slOrder.setUserId(user.getId());
                                    // 订单号
                                    slOrder.setSerialNumber(orderNum);
                                    // 该商品的规格价格 * 加入购物车中的数量 = 该用户本次加入商品的价格
                                    double price = repository.getSeckillPrice().doubleValue();
                                    Double d = price * quantity;
                                    BigDecimal money = new BigDecimal(d.toString());
                                    if (slProduct.getPostage().doubleValue() > 0) {
                                        money = BigDecimal.valueOf(Arith.add(money.doubleValue(), slProduct.getPostage().doubleValue()));
                                    }
                                    // 订单总价
                                    slOrder.setTotalAmount(money);
                                    // 查询订单地址信息
                                    SlUserAddress slUserAddress = this.slUserAddressMapper.selectOne(new SlUserAddress() {{
                                        setId(shippingAddressId);
                                        setUserId(user.getId());
                                    }});
                                    if (null != slUserAddress) {
                                        // 订单省的地址
                                        slOrder.setProvince(slUserAddress.getProvince());
                                        // 订单市的收货地址
                                        slOrder.setCity(slUserAddress.getCity());
                                        // 订单区的收货地址
                                        slOrder.setCounty(slUserAddress.getCounty());
                                        // 订单详细收货地址
                                        slOrder.setDetailed(slUserAddress.getDetailed());
                                        // 收货人姓名
                                        slOrder.setConsigneename(slUserAddress.getName());
                                        // 收货人的联系方式
                                        slOrder.setConsigneephone(slUserAddress.getPhone());
                                        // 了豆价格
                                        slOrder.setDeductTotalPulse(0);
                                        // 插入订单表
                                        orderService.insertSelective(slOrder);
                                        // 订单加入redis 有效时间为24小时
                                        orderCache.put(slOrder.getId(), slOrder, 1L, TimeUnit.DAYS);
                                        // 插入订单明细表
                                        orderDetailService.insertSelective(new SlOrderDetail() {{
                                            //邀请人ID
                                            setInviterId(7777);
                                            // 订单明细id
                                            setId(formatUUID32());
                                            // 订单明细创建时间
                                            setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                            // 用户id
                                            setCreator(slOrder.getUserId());
                                            // 订单ID
                                            setOrderId(slOrder.getId());
                                            // 商品数量
                                            setQuantity(quantity);
                                            // 邮费
                                            setPostFee(slProduct.getPostage());
                                            // 单个商品价格
                                            setPrice(slActivitySeckill.getSeckillPrice());
                                            // 商品ID
                                            setProductId(slProduct.getId());
                                            // 店铺唯一标识
                                            setShopId(slProduct.getShopId());
                                            // 店铺仓库ID
                                            setRepositoryId(repository.getId());
                                            // 活动id
                                            setActivityProductId(activityProduct.getId());
                                            // 返了豆数量只限纯金钱模式
                                            setPlaceOrderReturnPulse(repository.getPlaceOrderReturnPulse());
                                            // 下单时的商品名称
                                            setProductName(slProduct.getName());
                                            // 商品所需了豆
                                            setDeductTotalSilver(repository.getSilver());
                                            // 下单时的商品图片
                                            setProductImageUrl(slProduct.getImageUrl());
                                            // 添加买家留言
                                            setBuyerMessage(buyerMessage);
                                            // 订单类型
                                            setType(8);
                                            // 订单编号
                                            setSerialNumber(orderNum);
                                            // 商品规格名称
                                            setProductDetailGroupName(repository.getProductDetailGroupName());
                                            //创建人
                                            setCreator(user.getId());
                                            //拼团人数 默认
                                            setGroupPeople(activityProduct.getPeopleNum());

                                            // 分享奖励
                                            // 如果是分享奖励的情况下
                                            if (activityProduct.getActivityId().equals(ActivityConstant.RECOMMEND_AWARDS_ACTIVITY)) {
                                                // 插入分享人id
                                                setShareOfPeopleId(shareOfPeopleId);
                                            }
                                            //  新人奖励
                                            // 查询当前用户是否为首单
                                            Boolean flag = orderService.exist(new SlOrder() {{
                                                setUserId(user.getId());
                                                // 已支付
                                                setPaymentState(1);
                                            }});
                                            // 是首单的情况 && 该商品活动是新人奖励
                                            if (flag.equals(false) && activityProduct.getActivityId().equals(ActivityConstant.NEW_PEOPLE_ACTIVITY)) {
                                                // 如果是第一单的情况下 需要加上 首单奖励
                                                setPlaceOrderReturnPulse(repository.getPlaceOrderReturnPulse() * quantity + repository.getFirstOrderPulse());
                                            } else {
                                                // 返了豆数量只限纯金钱模式
                                                setPlaceOrderReturnPulse(repository.getPlaceOrderReturnPulse() * quantity);
                                            }
                                            // 消费返利
                                            if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_REBATE) {
                                                if (repository.getReturnCashMoney().doubleValue() > 0) {
                                                    // 消费返利金额
                                                    setReturnCashMoney(repository.getReturnCashMoney());
                                                }
                                            }

                                        }});
                                        // 商品上架数量 - 本次加入订单的数量
                                        int activityProductCount = activityProduct.getCount() - quantity;
                                        // 如果库存为0 的话就下架了
                                        Example example = new Example(SlActivityProduct.class);
                                        example.createCriteria()
                                                .andGreaterThan("count", 0)
                                                .andEqualTo("id", activityProduct.getId());
                                        this.activityProductMapper.updateByExampleSelective(new SlActivityProduct() {{
                                            setCount(activityProductCount);
                                        }}, example);

                                        // 当前库存 - 本次该商品规格下单库存
                                        int cou = repository.getSekillCount() - quantity;
                                        repository.setSekillCount(cou);
                                        //sl_activity_seckill修改库存
                                        this.slActivitySeckillMapper.updateByPrimaryKeySelective(new SlActivitySeckill(){{
                                            setId(slActivitySeckill.getId());
                                            setTotalCount(slActivitySeckill.getTotalCount() - quantity);
                                        }});
                                        // 当前库存 - 本次该商品规格下单库存
                                        repository.setSekillCount(repository.getSekillCount() - quantity);
                                        // 更新redis中该商品规格的库存
                                        repositoryCache.put(repository.getId(), repository);
                                        //更新数据库该商品规格的库存
                                        int updateCount = this.cmOrderMapper.reduceNumber(repository.getId(), cou);
                                        if (updateCount > 0) {
                                            message.setMsg("订单生成成功");
                                            message.setSuccess(true);
                                        }
                                        // 把虚拟销量加上
                                        productService.updateByPrimaryKeySelective(new SlProduct() {{
                                            setId(slProduct.getId());
                                            setSalesVirtual(slProduct.getSalesVirtual() + quantity);
                                        }});
                                        Map<String, String> map = new HashMap<>();
                                        map.put("order_num", slOrder.getId());
                                        map.put("total_amount", money.toString());
                                        message.setData(map);
                                    } else {
                                        message.setMsg("用户地址不存在");
                                        return message;
                                    }
                                } else {log.debug("当前规格的商品,库存不足");
                                    message.setMsg("当前规格的商品,库存不足");
                                    message.setSuccess(false);
                                    return message;

                                }
                            } else {
                                log.debug("已超出该商品的下单商品数量");
                                message.setMsg("已超出该商品的下单商品数量");
                                message.setSuccess(false);
                                return message;
                            }
                        } else {
                            log.debug("活动商品时间错误");
                            message.setMsg("活动商品时间错误");
                            return message;
                        }
                    } else {
                        log.debug("商品已下架");
                        message.setMsg("商品已下架");
                        return message;
                    }
                } else {
                    log.debug("该商品不存在");
                    message.setMsg("该商品不存在");
                    return message;
                }
            } else {
                log.debug("该规格不存在");
                message.setMsg("该规格不存在");
                return message;
            }
        } else {
            log.debug("用户不存在");
            message.setMsg("用户不存在");
            return message;
        }
        return message;
    }

    /**
     * 保存钱包异常订单
     */
    public void saveOrderHandle(String orderId, String userId, String message){
        log.debug("保存钱包异常订单开始啦");
        try {
            cmOrderHandleMapper.insertOrderHandle(orderId, userId, message);
        }catch (Exception e){
            log.error("存钱包异常订单异常:", e);
        }
    }
}