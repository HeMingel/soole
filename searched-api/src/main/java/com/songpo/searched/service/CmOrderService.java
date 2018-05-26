package com.songpo.searched.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.alipay.service.AliPayService;
import com.songpo.searched.cache.OrderCache;
import com.songpo.searched.cache.ProductCache;
import com.songpo.searched.cache.ProductRepositoryCache;
import com.songpo.searched.cache.UserCache;
import com.songpo.searched.constant.ActivityConstant;
import com.songpo.searched.constant.SalesModeConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.*;
import com.songpo.searched.rabbitmq.NotificationService;
import com.songpo.searched.typehandler.MessageTypeEnum;
import com.songpo.searched.util.*;
import com.songpo.searched.wxpay.service.WxPayService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.security.MessageDigest;
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

    /**
     * 多商品下单
     *
     * @param request
     * @param detail            商品1的规格id|商品1的商品数量|商品1买家留言|分享人id(如果是分享奖励的话)
     * @param shippingAddressId 当前用户选择的收货地址id
     * @return
     */
    @Transactional
    public BusinessMessage addOrder(HttpServletRequest request, String[] detail, String shippingAddressId) {
        log.debug("request = [" + request + "], detail = [" + detail + "], shippingAddressId = [" + shippingAddressId + "]");
        BusinessMessage message = new BusinessMessage();
        double money = 0.00;
        int pulse = 0;
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
                            //查询redis
//                            SlProductRepository repository = repositoryCache.get(repositoryId);
//                            //如果redis中没有这个仓库信息
//                            if (StringUtils.isEmpty(repository)) {
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
//                                if (StringUtils.isEmpty(slProduct)) {
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
                                                //库存的数量 > 他这次加入订单的数量
                                                if (repository.getCount() - quantity >= 0) {
                                                    // 钱相加 用于统计和添加到订单表扣除总钱里边
                                                    money += repository.getPrice().doubleValue() * quantity;
                                                    // 如果邮费不为空
                                                    if (slProduct.getPostage().doubleValue() > 0) {
                                                        money = money + slProduct.getPostage().doubleValue();
                                                    }
                                                    // 了豆相加  用于统计和添加到订单表扣除了豆里边
                                                    if (repository.getSilver() > 0) {
                                                        pulse += repository.getSilver() * quantity;
                                                    }
//                                                SlProductRepository finalRepository = repository;
                                                    orderDetailService.insertSelective(new SlOrderDetail() {{
                                                        setId(formatUUID32());
                                                        setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
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
                                                        setPrice(repository.getPrice());
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
                                                    // 忘了这块是要表达什么 ?? 如果是新人专享活动的话
//                                        if (slActivityProduct.getActivityId().equals(ActivityConstant.NEW_PEOPLE_ACTIVITY)) {
//                                            Example example1 = new Example(SlActivityProduct.class);
//                                            example1.createCriteria()
//                                                    .andEqualTo("productId", slProduct.getId())
//                                                    .andGreaterThan("count", 0);
//                                            this.activityProductMapper.updateByExampleSelective(new SlActivityProduct() {{
//                                                if (activityProductCount == 0) {
//                                                    setEnabled(false);
//                                                }
//                                                //活动总商品上架数量 - 本次购买的数量
//                                                setCount(activityProductCount);
//                                            }}, example1);
//                                        } else {}
                                                    // 如果库存为0 的话就下架了
                                                    Example example = new Example(SlActivityProduct.class);
                                                    example.createCriteria()
                                                            .andGreaterThan("count", 0)
                                                            .andEqualTo("id", slActivityProduct.getId());
                                                    // 商品总库存 - 本次加入订单的数量
                                                    int activityProductCount = slActivityProduct.getCount() - quantity;
                                                    this.activityProductMapper.updateByExampleSelective(new SlActivityProduct() {{
                                                        if (activityProductCount == 0) {
                                                            setEnabled(false);
                                                        }
                                                        //活动总商品上架数量 - 本次购买的数量
                                                        setCount(slActivityProduct.getCount() - quantity);
                                                    }}, example);
                                                    // 当前库存 - 本次该商品规格下单库存
                                                    repository.setCount(repository.getCount() - quantity);
                                                    // 更新redis中该商品规格的库存
                                                    repositoryCache.put(repository.getId(), repository);
                                                    Example example1 = new Example(SlProductRepository.class);
                                                    example1.createCriteria()
                                                            // 比0大的库存
                                                            .andGreaterThan("count", 0)
                                                            .andEqualTo("id", repository.getId());
                                                    //更新数据库该商品规格的库存
                                                    this.productRepositoryService.updateByExampleSelective(new SlProductRepository() {{
                                                        setCount(repository.getCount() - quantity);
                                                    }}, example1);
                                                    message.setMsg("订单生成成功");
                                                    message.setSuccess(true);
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
                        Map<String, String> map = new HashMap<>();
                        map.put("order_num", slOrder.getId());
                        map.put("total_amount", String.valueOf(money));
                        map.put("deduct_total_pulse", String.valueOf(pulse));
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
                                            int spellGroupType) {
        log.debug("request = [" + request + "], response = [" + response + "], repositoryId = [" + repositoryId + "], quantity = [" + quantity + "]");
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (null != user) {
//                SlProductRepository repository = new SlProductRepository();
//                //1.先从redis中去取该商品规格的详细参数
//                repository = this.repositoryCache.get(repositoryId);
//                //2.如果repository为null就去数据库中查询一遍放入repository对象中
//                if (StringUtils.isEmpty(repository)) {
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
//                    if (StringUtils.isEmpty(slProduct)) {
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
                                // 本规格下的库存 >= 本次下单的商品数量
                                if (repository.getCount() >= quantity) {
                                    //  ====== 拼团订单 ======
                                    //8.如果销售模式是拼团订单的话
                                    if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_GROUP) {
                                        //8(1).如果是拼团订单的话 拼团订单不为空 && 开团团主不为空的情况下
                                        if (!StringUtils.isEmpty(serialNumber) && !StringUtils.isEmpty(groupMaster)) {
                                            //查询这个团主的订单是否存在
                                            int count1 = this.orderService.selectCount(new SlOrder() {{
                                                setUserId(groupMaster);
                                                setSerialNumber(serialNumber);
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
                                                    message = processingOrders(user.getId(), serialNumber, activityProduct, groupMaster, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 2, buyerMessage, spellGroupType);
                                                } else {
                                                    message.setMsg("您已参加过该团,请勿重复参加");
                                                    return message;
                                                }
                                            } else {
                                                message.setMsg("订单失效或不存在");
                                                return message;
                                            }
                                        } else {
                                            // ==== 如果是他自己开的团 ======
                                            //生成订单号
                                            String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                            message = processingOrders(user.getId(), orderNum, activityProduct, user.getId(), shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 2, buyerMessage, spellGroupType);
                                        }
                                    }
                                    // ====== 如果是预售模式 ======
                                    else if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_PRESELL) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 3, buyerMessage, 1);
                                    }
                                    // ====== 如果是助力购 ======
                                    else if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_ONE) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 4, buyerMessage, 1);
                                    }
                                    // ====== 消费返利 ======
                                    else if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_REBATE) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 5, buyerMessage, 1);
                                        //  ====== 豆赚 ======
                                    } else if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_BEANS) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 6, buyerMessage, 1);
                                        //  ====== 普通商品 ======
                                    } else if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_NORMAL) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 1, buyerMessage, 1);
                                    }
                                    if (message.getSuccess() == false) {
                                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                        message.setData("");
                                        return message;
                                    }
                                } else {
                                    log.debug("当前规格的商品,库存不足");
                                    message.setMsg("当前规格的商品,库存不足");
                                    return message;
                                }
                            } else {
                                log.debug("已超出该商品的下单商品数量");
                                message.setMsg("已超出该商品的下单商品数量");
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
     * @param orderId 订单id
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
                                            int spellGroupType) {
        BusinessMessage message = new BusinessMessage();
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
        // 订单类型
//        slOrder.setType(type);
        // 如果是拼团订单
//        if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_GROUP) {
//            // 查询该订单号的所有订单 && 支付成功状态
//            int count2 = this.orderService.selectCount(new SlOrder() {{
//                setSerialNumber(serialNumber);
//                setPaymentState(1);
//            }});
//            // 如果单数 + 他自己 <=所需人数
//            if (count2 + 1 <= activityProduct.getPeopleNum()) {
//                // 拼团状态为拼团中状态
//                slOrder.setSpellGroupStatus(1);
//            }
//        }
        // 该商品的规格价格 * 加入购物车中的数量 = 该用户本次加入商品的价格
        double price = 0.00;
        if (spellGroupType == 1) {
            price = repository.getPrice().doubleValue();
        } else {
            price = repository.getPersonalPrice().doubleValue();
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
            orderDetailService.insertSelective(new SlOrderDetail() {{
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
                if (spellGroupType == 1) {
                    setPrice(repository.getPrice());
                } else {
                    setPrice(repository.getPersonalPrice());
                }
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
            }});
            // 商品上架数量 - 本次加入订单的数量
            int activityProductCount = activityProduct.getCount() - quantity;
            // 如果库存为0 的话就下架了
            Example example = new Example(SlActivityProduct.class);
            example.createCriteria()
                    .andGreaterThan("count", 0)
                    .andEqualTo("id", activityProduct.getId());
            this.activityProductMapper.updateByExampleSelective(new SlActivityProduct() {{
                if (activityProductCount == 0) {
                    setEnabled(false);
                }
                //活动总商品上架数量 - 本次购买的数量
                setCount(activityProductCount);
            }}, example);

            // 当前库存 - 本次该商品规格下单库存
            int cou = repository.getCount() - quantity;
            repository.setCount(cou);
            // 更新redis中该商品规格的库存
            repositoryCache.put(repository.getId(), repository);
            // 再更新数据库中的库存
            Example example1 = new Example(SlProductRepository.class);
            example1.createCriteria().andEqualTo("id", repository.getId()).andGreaterThan("count", 0);
            //更新数据库该商品规格的库存
            this.productRepositoryService.updateByExampleSelective(new SlProductRepository() {{
                setCount(cou);
            }}, example1);
            message.setMsg("订单生成成功");
            message.setSuccess(true);
            Map<String, String> map = new HashMap<>();
            map.put("order_num", slOrder.getId());
            map.put("total_amount", money.toString());
            map.put("deduct_total_pulse", slOrder.getDeductTotalPulse().toString());
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
                //并且是待发货状态
                setShippingState(3);
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
            message = checkTheOrder(orderId, user);
            if (message.getSuccess() == true) {
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
        } else {
            message.setMsg("请登录");
        }
        return message;
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
            message = checkTheOrder(orderId, user);
            if (message.getSuccess() == true) {
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
    public BusinessMessage<Map> onlyPulsePay(String orderId) {
        BusinessMessage<Map> message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        message = checkTheOrder(orderId, user);
        if (message.getSuccess() == true) {
            if (message.getData().get("money").toString().equals("0.00")) {
                processOrders.processOrders(orderId, 3);
            }
        }
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
}