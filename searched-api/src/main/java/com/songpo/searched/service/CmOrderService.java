package com.songpo.searched.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.cache.OrderCache;
import com.songpo.searched.cache.ProductCache;
import com.songpo.searched.cache.ProductRepositoryCache;
import com.songpo.searched.cache.ShoppingCartCache;
import com.songpo.searched.constant.ActivityConstant;
import com.songpo.searched.constant.SalesModeConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.CMSlOrderDetail;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.*;
import com.songpo.searched.rabbitmq.NotificationService;
import com.songpo.searched.typehandler.MessageTypeEnum;
import com.songpo.searched.util.OrderNumGeneration;
import com.songpo.searched.wxpay.controller.WxPayController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDate.now;

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
    private WxPayController wxPayController;
    @Autowired
    private ShoppingCartCache shoppingCartCache;
    @Autowired
    private SlReturnsDetailMapper returnsDetailMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SlMessageMapper messageMapper;

    /**
     * 多商品下单
     *
     * @param request
     * @param slOrder
     * @param orderDetail
     * @param shippingAddressId
     * @return
     */

    public BusinessMessage addOrder(HttpServletRequest request, SlOrder slOrder, CMSlOrderDetail orderDetail, String shippingAddressId) {
        log.debug("slOrder = [" + slOrder + "], orderDetail = [" + orderDetail + "], shippingAddressId = [" + shippingAddressId + "]");
        BusinessMessage message = new BusinessMessage();
        double money = 0.00;
        int pulse = 0;
        try {
            SlUser user = loginUserService.getCurrentLoginUser();
            if (null != user) {
                String orderNum = OrderNumGeneration.getOrderIdByUUId();// 生成订单编号
                slOrder.setId(UUID.randomUUID().toString());
                slOrder.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                slOrder.setSerialNumber(orderNum);// 订单编号
                slOrder.setUserId(user.getId());// 用户Id
                SlUserAddress slUserAddress = this.slUserAddressMapper.selectOne(new SlUserAddress() {{
                    setId(shippingAddressId);
                    setUserId(user.getId());
                }});
                slOrder.setProvince(slUserAddress.getProvince());// 订单省的地址
                slOrder.setCity(slUserAddress.getCity()); // 订单市的收货地址
                slOrder.setCounty(slUserAddress.getCounty()); //订单区的收货地址
                slOrder.setDetailed(slUserAddress.getDetailed()); // 订单详细收货地址
                slOrder.setConsigneename(slUserAddress.getName());// 收货人姓名
                slOrder.setConsigneephone(slUserAddress.getPhone());// 收货人的联系方式
                int co = orderService.insertSelective(slOrder);// 插入订单表
                //订单加入redis 有效期为一天
                orderCache.put(slOrder.getId(), slOrder, 1L, TimeUnit.DAYS);
                if (co > 0) {
                    for (SlOrderDetail slOrderDetail : orderDetail.getSlOrderDetails()) {
                        //查询redis
                        SlProductRepository repository = repositoryCache.get(slOrderDetail.getRepositoryId());
                        //如果redis中没有这个仓库信息
                        if (StringUtils.isEmpty(repository)) {
                            // 根据仓库ID 去查询商品的详细信息(选好规格的价格,金豆等等)
                            repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                                setId(slOrderDetail.getRepositoryId());
                            }});
                            //向redis中加入这个仓库信息
                            repositoryCache.put(repository.getId(), repository);//没有的话查询数据库放到redis
                        }
                        // 仓库存在 并且加入订单的商品大于0
                        if (null != repository && slOrderDetail.getQuantity() > 0) {
                            //查询redis中有没有这个商品信息
                            SlProduct slProduct = productCache.get(repository.getProductId());
                            //如果没有 就查一遍
                            if (StringUtils.isEmpty(slProduct)) {
                                SlProductRepository finalRepository1 = repository;
                                slProduct = this.productService.selectOne(new SlProduct() {{
                                    setId(finalRepository1.getProductId());
                                    setSoldOut(true);
                                }});
                            }
                            if (null != slProduct) {
                                SlProduct finalSlProduct = slProduct;
                                //查询活动商品信息
                                SlActivityProduct slActivityProduct = this.cmOrderMapper.selectActivityProductByRepositoryId(repository.getId(), slOrderDetail.getActivityProductId());
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
                                    if (slActivityProduct.getRestrictCount() - counts >= slOrderDetail.getQuantity()) {
                                        //库存的数量 > 他这次加入订单的数量
                                        if (repository.getCount() - slOrderDetail.getQuantity() >= 0) {
                                            // 钱相加 用于统计和添加到订单表扣除总钱里边
                                            money += repository.getPrice().doubleValue() * slOrderDetail.getQuantity();
                                            // 如果邮费不为空
                                            if (repository.getPostFee().doubleValue() > 0) {
                                                money = money + repository.getPostFee().doubleValue();
                                            }
                                            // 了豆相加  用于统计和添加到订单表扣除了豆里边
                                            if (repository.getSilver() > 0) {
                                                pulse += repository.getSilver() * slOrderDetail.getQuantity();
                                            }
                                            SlProductRepository finalRepository = repository;
                                            orderDetailService.insertSelective(new SlOrderDetail() {{
                                                setId(UUID.randomUUID().toString());
                                                setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                                // 拼团订单去这个属性判断是哪个人的
                                                setCreator(slOrder.getUserId());
                                                // 商品名称
                                                setProductName(finalSlProduct.getName());
                                                // 商品图片
                                                setProductImageUrl(finalSlProduct.getImageUrl());
                                                // 订单ID
                                                setOrderId(slOrder.getId());
                                                // 商品数量
                                                setQuantity(slOrderDetail.getQuantity());
                                                // 单个商品价格
                                                setPrice(finalRepository.getPrice());
                                                // 商品规格名称
                                                setProductDetailGroupName(finalRepository.getProductDetailGroupName());
                                                // 活动id
                                                setActivityProductId(slActivityProduct.getActivityId());
                                                // 商品ID
                                                setProductId(finalRepository.getProductId());
                                                // 店铺唯一标识
                                                setShopId(finalRepository.getShopId());
                                                // 店铺仓库ID
                                                setRepositoryId(finalRepository.getId());
                                                // 扣除单个商品了豆数量
                                                setDeductTotalSilver(finalRepository.getSilver());
                                                // 添加买家留言
                                                setBuyerMessage(slOrderDetail.getBuyerMessage());
                                                // 订单编号
                                                setSerialNumber(orderNum);
                                                // TODO 消费奖励
                                                if (finalSlProduct.getSalesModeId().equals(SalesModeConstant.SALES_MODE_REBATE)) {
                                                    if (finalRepository.getReturnCashMoney().compareTo(new BigDecimal(0)) > 0) {
                                                        // 消费返利金额
                                                        setReturnCashMoney(finalRepository.getReturnCashMoney());
                                                    }
                                                }
                                                // TODO 分享奖励
                                                if (slActivityProduct.getActivityId().equals(ActivityConstant.RECOMMEND_AWARDS_ACTIVITY)) {
                                                    // 插入分享人id
                                                    setShareOfPeopleId(slOrderDetail.getShareOfPeopleId());
                                                }
                                                // 查询当前用户的支付订单
                                                Boolean flag = orderService.exist(new SlOrder() {{
                                                    setUserId(user.getId());
                                                    // 已支付
                                                    setPaymentState(1);
                                                }});
                                                // TODO 新人专享
                                                //判断是否为首单
                                                if (flag.equals(false)) {
                                                    // 如果是第一单的情况下 需要加上 首单奖励
                                                    setPlaceOrderReturnPulse(finalRepository.getPlaceOrderReturnPulse() + finalRepository.getFirstOrderPulse());
                                                } else {
                                                    // 返了豆数量只限纯金钱模式
                                                    setPlaceOrderReturnPulse(finalRepository.getPlaceOrderReturnPulse());
                                                }
                                            }});
                                            //TODO 忘了这块是要表达什么 ?? 如果是新人专享活动的话

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
                                            int activityProductCount = slActivityProduct.getCount() - slOrderDetail.getQuantity();
                                            this.activityProductMapper.updateByExampleSelective(new SlActivityProduct() {{
                                                if (activityProductCount == 0) {
                                                    setEnabled(false);
                                                }
                                                //活动总商品上架数量 - 本次购买的数量
                                                setCount(slActivityProduct.getCount() - slOrderDetail.getQuantity());
                                            }}, example);
                                            // 当前库存 - 本次该商品规格下单库存
                                            finalRepository.setCount(finalRepository.getCount() - slOrderDetail.getQuantity());
                                            // 更新redis中该商品规格的库存
                                            repositoryCache.put(repository.getId(), finalRepository);
                                            Example example1 = new Example(SlProductRepository.class);
                                            example1.createCriteria()
                                                    // 比0大的库存
                                                    .andGreaterThan("count", 0)
                                                    .andEqualTo("id", finalRepository.getId());
                                            //更新数据库该商品规格的库存
                                            this.productRepositoryService.updateByExampleSelective(new SlProductRepository() {{
                                                setCount(repositoryCache.get(finalRepository.getId()).getCount());
                                            }}, example1);
                                            message.setMsg("订单生成成功");
                                            message.setSuccess(true);
                                        } else {
                                            log.error("当前库存不足");
                                            message.setMsg("当前库存不足");
                                            message.setSuccess(false);
                                            break;
                                        }
                                    } else {
                                        log.error("已超过最大购买数量");
                                        message.setMsg("已超过最大购买数量");
                                        message.setSuccess(false);
                                        break;
                                    }
                                } else {
                                    log.error("活动商品时间错误");
                                    message.setMsg("活动商品时间错误");
                                    message.setSuccess(false);
                                    break;
                                }
                            } else {
                                log.error("该商品不存在或已下架");
                                message.setMsg("该商品不存在或已下架");
                                message.setSuccess(false);
                                break;
                            }
                        }
                    }
                    // 更新订单总价和总豆
                    double finalMoney = money;
                    int finalPulse = pulse;
                    this.orderService.updateByPrimaryKeySelective(new SlOrder() {{
                        setId(slOrder.getId());
                        setTotalAmount(BigDecimal.valueOf(finalMoney));
                        setDeductTotalPulse(finalPulse);
                    }});
                } else {
                    log.error("收货地址不存在");
                    message.setMsg("收货地址不存在");
                }
            } else {
                log.error("用户不存在");
                message.setMsg("用户不存在");
            }
        } catch (Exception e) {
            log.error("多商品下单失败", e);
        }
        return message;
    }

    /**
     * 查询我的订单列表
     *
     * @param status
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
                List<Map<String, Object>> list = this.cmOrderMapper.findList(user.getId(), status);
                for (Map map : list) {
                    Object type = map.get("type");
                    Object serialNumber = map.get("serialNumber");
                    if (type.equals(2)) {//拼团订单
                        // 拼团订单筛选参与会员头像
                        map.put("userAvatarList", this.cmOrderMapper.findUserAvatar(serialNumber));
                    }
                }
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
                if (orderInfo.get("activityProductId").equals(ActivityConstant.NO_ACTIVITY)) {
                    orderInfo.put("join", false);
                } else {
                    orderInfo.put("join", true);
                }
                message.setData(orderInfo);
                message.setSuccess(true);
                message.setMsg("查询成功");
            }
        } catch (Exception e) {
            log.error("查询失败", e);
        }
        return message;
    }

    /**
     * 取消订单/确定收货
     *
     * @param state
     * @param id
     * @return
     */
    public void cancelAnOrder(String id, String state) {
        log.debug("orderId = [" + id + "]");
        try {
            SlUser user = loginUserService.getCurrentLoginUser();
            if (null != user) {
                switch (Integer.parseInt(state)) {
                    case 102:
                        Example example = new Example(SlOrder.class);
                        example.createCriteria()
                                .andEqualTo("id", id)
                                .andEqualTo("userId", user.getId());
                        orderService.updateByExampleSelective(new SlOrder() {{
                            //取消订单
                            setPaymentState(102);
                        }}, example);
                        List<SlOrderDetail> detailList = this.orderDetailService.select(new SlOrderDetail() {{
                            setProductId(id);
                        }});
                        for (SlOrderDetail detail : detailList) {
                            SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                                setId(detail.getRepositoryId());
                            }});
                            // 把订单中的商品数量加到商品库存中去
                            this.productRepositoryService.updateByPrimaryKeySelective(new SlProductRepository() {{
                                setId(repository.getId());
                                setCount(repository.getCount() + detail.getQuantity());
                            }});
                            //更新redids
                            this.repositoryCache.put(repository.getId(), this.productRepositoryService.selectByPrimaryKey(repository.getId()));
                        }
                        break;
                    case 5:
                        Example example1 = new Example(SlOrderDetail.class);
                        example1.createCriteria()
                                .andEqualTo("id", id)
                                .andEqualTo("creator", user.getId());
                        orderDetailService.updateByExampleSelective(new SlOrderDetail() {{
                            //确认订单未评价
                            setShippingState(5);
                        }}, example1);
                }
            }
        } catch (Exception e) {
            log.error("操作失败");
        }
    }

    /**
     * 处理订单失效
     *
     * @param key
     */
    public void processOrderDisabled(String key) {
        log.debug("订单失效，标识：{}", key);
        if (!StringUtils.isEmpty(key)) {
            // 商品标识
            String orderId = key.substring(key.lastIndexOf(":") + 1);
            if (!StringUtils.isEmpty(orderId)) {
                orderService.updateByPrimaryKeySelective(new SlOrder() {{
                    setId(orderId);
                    setPaymentState(101);
                }});
                // 查询该订单id关联的所有商品明细
                List<SlOrderDetail> detailList = this.orderDetailService.select(new SlOrderDetail() {{
                    setOrderId(key);
                }});
                for (SlOrderDetail slOrderDetail : detailList) {
                    SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                        setId(slOrderDetail.getRepositoryId());
                    }});
                    //把该订单下的数量加回去
                    int count = repository.getCount() + slOrderDetail.getQuantity();
                    productRepositoryService.updateByPrimaryKeySelective(new SlProductRepository() {{
                        setId(repository.getId());
                        setCount(count);
                    }});
                    //更新redids
                    this.repositoryCache.put(repository.getId(), this.productRepositoryService.selectByPrimaryKey(repository.getId()));
                }
            }
        }
    }

    /**
     * 单商品下单
     *
     * @param request
     * @param response
     * @param repositoryId
     * @param quantity
     * @return
     */
    public BusinessMessage purchaseAddOrder(HttpServletRequest request, HttpServletResponse response, String repositoryId, Integer quantity, String shareOfPeopleId, String serialNumber, String groupMaster, String shippingAddressId, String buyerMessage, String activityProductId) {
        log.debug("request = [" + request + "], response = [" + response + "], repositoryId = [" + repositoryId + "], quantity = [" + quantity + "]");
        BusinessMessage message = new BusinessMessage();
        try {
            SlUser user = loginUserService.getCurrentLoginUser();
            if (!StringUtils.isEmpty(user)) {
                SlProductRepository repository = new SlProductRepository();
                //1.先从redis中去取该商品规格的详细参数
                repository = this.repositoryCache.get(repositoryId);
                //2.如果repository为null就去数据库中查询一遍放入repository对象中
                if (StringUtils.isEmpty(repository)) {
                    repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                        setId(repositoryId);
                    }});
                    //3.把查询出来的商品规格放入redis中
                    this.repositoryCache.put(repositoryId, repository);
                }
                //4.如果查询出来不为空就去查询商品信息
                if (!StringUtils.isEmpty(repository)) {
                    SlProduct slProduct = new SlProduct();
                    //5.先从redis中取商品信息的详情
                    slProduct = this.productCache.get(repository.getProductId());
                    //6.如果为空就从数据库中查询一下商品信息
                    if (StringUtils.isEmpty(slProduct)) {
                        SlProductRepository finalRepository = repository;
                        slProduct = this.productService.selectOne(new SlProduct() {{
                            setId(finalRepository.getProductId());
                        }});
                    }
                    //7.如果商品存在的话
                    if (!StringUtils.isEmpty(slProduct)) {
                        //查询活动商品信息
                        SlActivityProduct activityProduct = this.cmOrderMapper.selectActivityProductByRepositoryId(repositoryId, activityProductId);
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
                                    // TODO ====== 拼团订单 ======
                                    //8.如果销售模式是拼团订单的话
                                    if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_GROUP) {
                                        //8(1).如果是拼团订单的话 拼团订单不为空 && 开团团主不为空的情况下
                                        if (!StringUtils.isEmpty(serialNumber) && !StringUtils.isEmpty(groupMaster)) {
                                            //查询这个团主的订单是否存在
                                            int count1 = this.orderService.selectCount(new SlOrder() {{
                                                setGroupMaster(groupMaster);
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
                                                    //TODO ======= 是团员的话 =======
                                                    message = processingOrders(user.getId(), serialNumber, activityProduct, groupMaster, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 2, buyerMessage);
                                                } else {
                                                    message.setMsg("您已参加过该团,请勿重复参加");
                                                    return message;
                                                }
                                            } else {
                                                message.setMsg("订单错误");
                                                return message;
                                            }
                                        } else {
                                            //TODO ==== 如果是他自己开的团 ======
                                            //生成订单号
                                            String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                            message = processingOrders(user.getId(), orderNum, activityProduct, user.getId(), shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 2, buyerMessage);
                                        }
                                    }
                                    //TODO ====== 如果是预售模式 ======
                                    else if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_PRESELL) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 3, buyerMessage);
                                    }
                                    //TODO ====== 如果是助力购 ======
                                    else if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_ONE) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 4, buyerMessage);
                                    }
                                    //TODO ====== 消费返利 ======
                                    else if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_REBATE) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 5, buyerMessage);
                                        // TODO ====== 豆赚 ======
                                    } else if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_BEANS) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 6, buyerMessage);
                                        // TODO ====== 普通商品 ======
                                    } else if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_NORMAL) {
                                        //生成订单号
                                        String orderNum = OrderNumGeneration.getOrderIdByUUId();
                                        message = processingOrders(user.getId(), orderNum, activityProduct, null, shippingAddressId, repository, quantity, shareOfPeopleId, slProduct, 1, buyerMessage);
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
            }
        } catch (Exception e) {
            log.error("单商品下单失败");
        }
        return message;
    }

    /**
     * 逻辑处理订单
     *
     * @return
     */
    public BusinessMessage processingOrders(String userId, String serialNumber, SlActivityProduct activityProduct, String groupMaster, String shippingAddressId, SlProductRepository repository, Integer quantity, String shareOfPeopleId, SlProduct slProduct, int type, String buyerMessage) {
        BusinessMessage message = new BusinessMessage();
        SlOrder slOrder = new SlOrder();
        // 订单id
        slOrder.setId(UUID.randomUUID().toString());
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
        slOrder.setType(type);
        // 如果是拼团订单
        if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_GROUP) {
            // 查询该订单号的所有订单 && 支付成功状态
            int count2 = this.orderService.selectCount(new SlOrder() {{
                setSerialNumber(serialNumber);
                setPaymentState(1);
            }});
            // 如果单数 + 他自己 <=所需人数
            if (count2 + 1 <= activityProduct.getPeopleNum()) {
                // 拼团状态为拼团中状态
                slOrder.setSpellGroupStatus(1);
            }
        }
        // 该商品的规格价格 * 加入购物车中的数量 = 该用户本次加入商品的价格
        Double d = repository.getPrice().doubleValue() * quantity;
        BigDecimal money = new BigDecimal(d.toString());
        // 订单总价
        slOrder.setTotalAmount(money);
        // 查询订单地址信息
        SlUserAddress slUserAddress = this.slUserAddressMapper.selectOne(new SlUserAddress() {{
            setId(shippingAddressId);
            setUserId(userId);
        }});
        if (!StringUtils.isEmpty(slUserAddress)) {
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
            // 插入订单表
            orderService.insertSelective(slOrder);
            // 订单加入redis 有效时间为24小时
            orderCache.put(slOrder.getId(), slOrder, 24L, TimeUnit.HOURS);
            // 插入订单明细表
            SlProductRepository finalRepository1 = repository;
            SlProduct finalSlProduct = slProduct;
            orderDetailService.insertSelective(new SlOrderDetail() {{
                // 订单明细id
                setId(UUID.randomUUID().toString());
                // 订单明细创建时间
                setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                // 用户id
                setCreator(slOrder.getUserId());
                // 订单ID
                setOrderId(slOrder.getId());
                // 商品数量
                setQuantity(quantity);
                // 单个商品价格
                setPrice(finalRepository1.getPrice());
                // 商品ID
                setProductId(finalSlProduct.getId());
                // 店铺唯一标识
                setShopId(finalSlProduct.getShopId());
                // 店铺仓库ID
                setRepositoryId(finalRepository1.getId());
                // 活动id
                setActivityProductId(activityProduct.getActivityId());
                // 返了豆数量只限纯金钱模式
                setPlaceOrderReturnPulse(finalRepository1.getPlaceOrderReturnPulse());
                // 下单时的商品名称
                setProductName(finalSlProduct.getName());
                // 下单时的商品图片
                setProductImageUrl(finalSlProduct.getImageUrl());
                // 添加买家留言
                setBuyerMessage(buyerMessage);
                // 订单编号
                setSerialNumber(serialNumber);
                // 商品规格名称
                setProductDetailGroupName(finalRepository1.getProductDetailGroupName());
                if (slProduct.getSalesModeId().equals(SalesModeConstant.SALES_MODE_GROUP)) {
                    // 拼团所需人数
                    setGroupPeople(activityProduct.getPeopleNum());
                }
                //TODO 分享奖励
                // 如果是分享奖励的情况下
                if (activityProduct.getActivityId().equals(ActivityConstant.RECOMMEND_AWARDS_ACTIVITY)) {
                    // 插入分享人id
                    setShareOfPeopleId(shareOfPeopleId);
                }
                // TODO 新人奖励
                // 查询当前用户是否为首单
                Boolean flag = orderService.exist(new SlOrder() {{
                    setUserId(userId);
                    // 已支付
                    setPaymentState(1);
                }});
                // 是首单的情况 && 该商品活动是新人奖励
                if (flag.equals(false) && activityProduct.getActivityId().equals(ActivityConstant.NEW_PEOPLE_ACTIVITY)) {
                    // 如果是第一单的情况下 需要加上 首单奖励
                    setPlaceOrderReturnPulse(finalRepository1.getPlaceOrderReturnPulse() + finalRepository1.getFirstOrderPulse());
                } else {
                    // 返了豆数量只限纯金钱模式
                    setPlaceOrderReturnPulse(finalRepository1.getPlaceOrderReturnPulse());
                }
                //TODO 消费返利
                if (Integer.parseInt(slProduct.getSalesModeId()) == SalesModeConstant.SALES_MODE_REBATE) {
                    if (finalRepository1.getReturnCashMoney().doubleValue() > 0) {
                        // 消费返利金额
                        setReturnCashMoney(finalRepository1.getReturnCashMoney());
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
            int cou = finalRepository1.getCount() - quantity;
            finalRepository1.setCount(cou);
            // 更新redis中该商品规格的库存
            repositoryCache.put(repository.getId(), finalRepository1);
            // 再更新数据库中的库存
            Example example1 = new Example(SlProductRepository.class);
            example.createCriteria()
                    // 比0大的库存
                    .andGreaterThan("count", 0)
                    .andEqualTo("id", finalRepository1.getId());
            //更新数据库该商品规格的库存
            this.productRepositoryService.updateByExampleSelective(new SlProductRepository() {{
                setCount(repositoryCache.get(finalRepository1.getId()).getCount());
            }}, example1);
            message.setMsg("订单生成成功");
            message.setSuccess(true);
        } else {
            message.setMsg("用户地址不存在");
            return message;
        }
        return message;
    }

    /**
     * 删除订单
     *
     * @param detailId
     * @param shopId
     * @param orderNum
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
            log.error("删除失败", e);
        }
    }

    /**
     * 预售订单
     *
     * @param integer
     * @param pageNum
     * @param status
     * @return
     */
    // TODO  =======
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
                List<SlReturnsDetail> list = this.cmOrderMapper.selectReturnsDetail(status, false, user.getId());
                List<Map<String, Object>> mapList = new ArrayList<>();
                for (SlReturnsDetail returnsDetail : list) {
                    SlOrder order = this.orderService.selectOne(new SlOrder() {{
                        setId(returnsDetail.getOrderId());
                        //预售
                        setType(3);
                    }});
                    SlOrderDetail detail = this.orderDetailService.selectOne(new SlOrderDetail() {{
                        setOrderId(order.getId());
                    }});
                    Map<String, Object> shop = this.cmOrderMapper.selectShopUserName(detail.getShopId());
                    Map<String, Object> map = new HashMap<>();
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

                message.setMsg("查询成功");
                message.setSuccess(true);
                message.setData(new PageInfo<>(mapList));
            } else {
                message.setMsg("用户不存在");
                log.error("用户不存在");
            }
        } catch (Exception e) {
            log.error("查询失败", e);
        }
        return message;
    }

    /**
     * 提醒发货
     *
     * @param orderId
     */
    public BusinessMessage remindingTheShipments(String orderId) {
        BusinessMessage message = new BusinessMessage();
        try {
            SlUser user = loginUserService.getCurrentLoginUser();
            if (null != user) {
                SlOrderDetail detail = this.orderDetailService.selectOne(new SlOrderDetail() {{
                    setOrderId(orderId);
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
            }
        } catch (Exception e) {
            log.error("提醒失败", e);
        }
        return message;
    }
}