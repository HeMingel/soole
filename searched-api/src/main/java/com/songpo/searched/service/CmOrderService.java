package com.songpo.searched.service;

import com.songpo.searched.cache.ProductRepositoryCache;
import com.songpo.searched.cache.ProductCache;
import com.songpo.searched.constant.ActivityConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.CMSlOrderDetail;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.*;
import com.songpo.searched.util.OrderNumGeneration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Transactional
@Service
public class CmOrderService {
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

    /**
     * 新增预下单订单
     *
     * @param slOrder
     * @param orderDetail
     * @param shippingAddressId
     * @return
     */
    public BusinessMessage addOrder(SlOrder slOrder, CMSlOrderDetail orderDetail, String shippingAddressId, String activityId) {
        BusinessMessage message = new BusinessMessage();
        double money = 0.00;
        int pulse = 0;
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
                    // 仓库存在 并且加入购物车的商品大于0
                    if (null != repository && slOrderDetail.getQuantity() > 0) {
                        //查询redis中有没有这个商品信息
                        SlProduct slProduct = productCache.get(repository.getProductId());
                        //如果没有
                        if (StringUtils.isEmpty(slProduct)) {
                            SlProductRepository finalRepository = repository;
                            slProduct = this.productService.selectOne(new SlProduct() {{
                                setId(finalRepository.getProductId());
                                setSoldOut(false);
                            }});
                        }
                        if (null != slProduct) {
                            SlProduct finalSlProduct = slProduct;
                            //查询活动商品信息
                            SlActivityProduct slActivityProduct = activityProductMapper.selectOne(new SlActivityProduct() {{
                                setActivityId(activityId);
                                setProductId(finalSlProduct.getId());
                                setEnabled(false);
                            }});
                            // 查询当前用户该商品的已生成订单的商品数量
                            int counts = this.cmOrderMapper.selectOrdersCount(repository.getProductId(), user.getId(), slActivityProduct.getId());
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //向redis中加入过期时间
                            try {
                                //活动商品的结束时间减去开始时间就为了的到它的时间段
                                Long times = (format.parse(slActivityProduct.getEndTime()).getTime()) - (format.parse(slActivityProduct.getBeginTime()).getTime());
                                //把活动商品的唯一主键当做redis中的key,加入过期时间,单位秒
                                productCache.put(slActivityProduct.getId(), slProduct, times / 1000, TimeUnit.SECONDS);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //slActivityProduct 不为空 && 这个key的过期时间>0
                            if (slActivityProduct != null && productCache.getRedisTemplate().getExpire(slActivityProduct.getId()) > 0) {
                                //单用户购买显示数量 - 他已经下单的数量 > 这次加入订单的数量
                                if (slActivityProduct.getRestrictCount() - counts > slOrderDetail.getQuantity()) {
                                    //库存的数量 > 他这次加入订单的数量
                                    if (repository.getCount() - slOrderDetail.getQuantity() >= 0) {
                                        // 钱相加 用于统计和添加到订单表扣除总钱里边
                                        money += repository.getPrice().doubleValue();
                                        // 了豆相加  用于统计和添加到订单表扣除了豆里边
                                        pulse += repository.getSilver();
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
                                            if (finalSlProduct.getSalesModeId().equals("3")) {
                                                //向订单中添加预约天数
                                                setPresellShipmentsDays(slActivityProduct.getPresellShipmentsDays());
                                            }
                                            // 消费奖励
                                            if (finalSlProduct.getSalesModeId().equals("4")) {
                                                if (finalRepository.getReturnCashMoney().doubleValue() > 0) {
                                                    // 消费返利金额
                                                    setReturnCashMoney(finalRepository.getReturnCashMoney());
                                                }
                                            }
                                            // 一元购助力所需人数
                                            if (finalSlProduct.getSalesModeId().equals("5")) {
                                                // 拼团/助力购 所需人数
                                                setGroupPeople(slActivityProduct.getPeopleNum());
                                            }
                                            // TODO ----------------分享返利这块产品待商确--------------------
//                                                        if (slActivityProduct.getActivityId().equals(ActivityConstant.RECOMMEND_AWARDS_ACTIVITY)) {
//                                                            setRewardsMoney(finalRepository.getRewardsMoney());// 推荐返利的金额
//                                                            setRebatePulse(finalRepository.getRebatePulse());// 推荐返利的了豆数量
//                                                        }
                                            // 查询当前用户的支付订单
                                            int c = orderService.selectCount(new SlOrder() {{
                                                setUserId(user.getId());
                                                // 已支付
                                                setPaymentState(1);
                                            }});
                                            //判断是否为第一单
                                            if (c == 0) {
                                                // 如果是第一单的情况下 需要加上 首单奖励
                                                setPlaceOrderReturnPulse(finalRepository.getPlaceOrderReturnPulse() + finalRepository.getFirstOrderPulse());
                                            } else {
                                                // 返了豆数量只限纯金钱模式
                                                setPlaceOrderReturnPulse(finalRepository.getPlaceOrderReturnPulse());
                                            }
                                        }});
                                        // 当前库存 - 本次下单库存
                                        finalRepository.setCount(finalRepository.getCount() - slOrderDetail.getQuantity());
                                        // 先更新redis 的库存
                                        repositoryCache.put(repository.getId(), finalRepository);
                                        Example example = new Example(SlProductRepository.class);
                                        example.createCriteria()
                                                // 比0大的库存
                                                .andGreaterThan("count", 0)
                                                .andEqualTo("id", finalRepository.getId());
                                        this.productRepositoryService.updateByExampleSelective(new SlProductRepository() {{
                                            setCount(repositoryCache.get(finalRepository.getId()).getCount());
                                        }}, example);
                                        double finalMoney = money;
                                        int finalPulse = pulse;
                                        // 更新订单总价和总豆
                                        this.orderService.updateByPrimaryKeySelective(new SlOrder() {{
                                            setId(slOrder.getId());
                                            setActivityProductId(slActivityProduct.getActivityId());// 活动id
                                            setTotalAmount(BigDecimal.valueOf(finalMoney));
                                            setDeductTotalPulse(finalPulse);
                                        }});
                                    } else {
                                        message.setMsg("当前库存不足");
                                    }
                                } else {
                                    message.setMsg("已超过最大购买数量");
                                }
                            } else {
                                message.setMsg("活动商品时间错误");
                            }
                        } else {
                            message.setMsg("商品已下架");
                        }
                    }
                }
                message.setMsg("添加订单成功");
                message.setSuccess(true);
            } else {
                message.setMsg("收货地址不存在");
            }
        } else {
            message.setMsg("用户不存在");
        }
        return message;
    }

    /**
     * 拼团订单下单
     *
     * @param slOrder
     * @param detail
     * @param shippingAddressId
     * @return
     */
    public BusinessMessage addGroupOrder(SlOrder slOrder, SlOrderDetail detail, String shippingAddressId, String activityId) {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        int restrictCount = 0;
        // 推荐奖励拼团奖励了豆数量
        int rewardsPulse = 0;
        // 推荐奖励拼团奖励金额
        BigDecimal rewardsMoney = null;
        if (null != user) {
            //reids 查询仓库
            SlProductRepository repository = repositoryCache.get(detail.getRepositoryId());
            if (StringUtils.isEmpty(repository)) {
                // 根据仓库ID 去查询商品的详细信息(选好规格的价格,金豆等等)
                repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                    setId(detail.getRepositoryId());
                }});
                repositoryCache.put(repository.getId(), repository);
            }
            if (null != repository) {
                SlProduct product = productCache.get(repository.getProductId());
                if (StringUtils.isEmpty(product)) {
                    SlProductRepository finalRepository = repository;
                    product = this.productService.selectOne(new SlProduct() {{
                        setId(finalRepository.getProductId());
                        setSoldOut(false);
                    }});
                }
                if (null != product) {
                    //此活动拼团商品当前用户的拼单下单数量
                    Integer count = this.cmOrderMapper.groupOrdersByUser(product.getId(), activityId, user.getId());
                    //查询活动商品信息
                    SlActivityProduct slActivityProduct = this.cmOrderMapper.selectActivityProductByActivityAndRepositoryId(repository.getId(), activityId);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Long times = (format.parse(slActivityProduct.getEndTime()).getTime()) - (format.parse(slActivityProduct.getBeginTime()).getTime());
                        productCache.put(slActivityProduct.getId(), product, times / 1000, TimeUnit.SECONDS);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    restrictCount = slActivityProduct.getRestrictCount();
                    this.activityProductMapper.updateByPrimaryKeySelective(new SlActivityProduct() {{
                        setId(slActivityProduct.getId());
                        // 此商品已拼单人数 + 1
                        setOrdersNum(slActivityProduct.getOrdersNum() + 1);
                    }});
                    // TODO ----------分享返利这块产品待商确--------------------
//                        if (activityId.equals("4E39B37C-21D1-4BC2-F1CE-FED600214E64")) {
//                            rewardsMoney = repository.getRewardsMoney();// 推荐奖励返利润的金额数量
//                            rewardsPulse = repository.getRebatePulse();// 推荐奖励的返利润的了豆数量
//                        }
                    if (productCache.getRedisTemplate().getExpire(slActivityProduct.getId()) > 0) {
                        // 商品限制购买单数 - 当前用户的该商品下单量 >= 本次下单的商品数量
                        if (restrictCount - count >= detail.getQuantity()) {
                            // 本规格下的库存 >= 本次下单的商品数量
                            if (repository.getCount() >= detail.getQuantity()) {
                                String serialNumber;
                                slOrder.setId(UUID.randomUUID().toString());
                                slOrder.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                // 前台传回来有本团的订单号(证明他是团员)
                                if (StringUtils.hasLength(detail.getSerialNumber())) {
                                    Boolean flag = this.orderService.exist(new SlOrder() {{
                                        setSerialNumber(detail.getSerialNumber());
                                    }});
                                    serialNumber = detail.getSerialNumber();
                                    //如果传过来的数据不存在
                                    if (flag) {
                                        Boolean f = this.orderService.exist(new SlOrder() {{
                                            setSerialNumber(detail.getSerialNumber());
                                            setUserId(user.getId());
                                        }});
                                        // 他是不是参加过该商品的拼团
                                        if (f == false) {
                                            slOrder.setSerialNumber(serialNumber);
                                            //查询该订单号的所有订单 不管支付或未支付
                                            int count1 = this.orderService.selectCount(new SlOrder() {{
                                                setSerialNumber(serialNumber);
                                            }});
                                            // 如果单数 + 他自己 <=所需人数
                                            if (count1 + 1 <= detail.getGroupPeople()) {
                                                slOrder.setSpellGroupStatus(1);
                                            }
                                            slOrder.setUserId(user.getId());// 用户Id
                                            // 不过他传过来的是不是他自己的id 都要标明这个团是谁开的
                                            slOrder.setGroupMaster(slOrder.getGroupMaster());
                                            slOrder.setActivityProductId(activityId);//活动id
                                            slOrder.setType(2);// 拼团订单
                                            Double d = repository.getPrice().doubleValue() * detail.getQuantity().doubleValue();
                                            BigDecimal money = new BigDecimal(d.toString());
                                            slOrder.setTotalAmount(money);// 订单总价
                                            SlUserAddress slUserAddress = this.slUserAddressMapper.selectOne(new SlUserAddress() {{
                                                setId(shippingAddressId);
                                                setUserId(user.getId());
                                            }});
                                            if (null != slUserAddress) {
                                                slOrder.setProvince(slUserAddress.getProvince());// 订单省的地址
                                                slOrder.setCity(slUserAddress.getCity()); // 订单市的收货地址
                                                slOrder.setCounty(slUserAddress.getCounty()); //订单区的收货地址
                                                slOrder.setDetailed(slUserAddress.getDetailed()); // 订单详细收货地址
                                                slOrder.setConsigneename(slUserAddress.getName());// 收货人姓名
                                                slOrder.setConsigneephone(slUserAddress.getPhone());// 收货人的联系方式
                                                orderService.insertSelective(slOrder);// 插入订单表
                                            }
                                            String finalSerialNumber = serialNumber;
                                            BigDecimal finalRewardsMoney = rewardsMoney;
                                            int finalRewardsPulse = rewardsPulse;
                                            SlProductRepository finalRepository1 = repository;
                                            orderDetailService.insertSelective(new SlOrderDetail() {{
                                                setId(UUID.randomUUID().toString());
                                                setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                                setCreator(slOrder.getUserId()); // 拼团订单去这个属性判断是哪个人的
                                                setOrderId(slOrder.getId()); // 订单ID
                                                setQuantity(detail.getQuantity()); // 商品数量
                                                setPrice(finalRepository1.getPrice()); // 单个商品价格
                                                setProductId(finalRepository1.getProductId());// 商品ID
                                                setShopId(finalRepository1.getShopId());// 店铺唯一标识
                                                setRepositoryId(finalRepository1.getId()); // 店铺仓库ID
                                                setPlaceOrderReturnPulse(finalRepository1.getRebatePulse());// 返了豆数量只限纯金钱模式
                                                setProductName(finalRepository1.getProductName());// 下单时的商品名称
                                                setProductImageUrl(finalRepository1.getProductImageUrl());// 下单时的商品图片
                                                setSerialNumber(finalSerialNumber); // 订单编号
                                                setProductDetailGroupName(finalRepository1.getProductDetailGroupName());// 商品规格名称
                                                setGroupPeople(slActivityProduct.getPeopleNum());// 无活动所需人数
                                                setRewardsMoney(finalRewardsMoney);// 推荐奖励的返的金额
                                                setRebatePulse(finalRewardsPulse);// 推荐奖励的返的了豆
                                                setPlaceOrderReturnPulse(finalRepository1.getPlaceOrderReturnPulse());// 下单获得了豆
                                            }});
                                            // 从他购买商品的此规格中减去此次购买的数量
                                            this.productRepositoryService.updateByPrimaryKeySelective(new SlProductRepository() {{
                                                setId(finalRepository1.getId());
                                                setCount(finalRepository1.getCount() - detail.getQuantity());
                                            }});
                                        } else {
                                            message.setMsg("您已拼过该团");
                                        }
                                    } else {
                                        message.setMsg("订单编号不存在");
                                    }
                                } else {
                                    serialNumber = OrderNumGeneration.getOrderIdByUUId();
                                    // 前台传回来的是空的(证明他是团长)
                                    slOrder.setSerialNumber(serialNumber);
                                    slOrder.setUserId(user.getId());// 用户Id
                                    // 不过他传过来的是不是他自己的id 都要标明这个团是谁开的
                                    slOrder.setGroupMaster(slOrder.getGroupMaster());
                                    slOrder.setActivityProductId(activityId);//活动id
                                    slOrder.setType(2);// 拼团订单
                                    Double d = repository.getPrice().doubleValue() * detail.getQuantity().doubleValue();
                                    BigDecimal money = new BigDecimal(d.toString());
                                    slOrder.setTotalAmount(money);// 订单总价
                                    SlUserAddress slUserAddress = this.slUserAddressMapper.selectOne(new SlUserAddress() {{
                                        setId(shippingAddressId);
                                        setUserId(user.getId());
                                    }});
                                    if (null != slUserAddress) {
                                        slOrder.setProvince(slUserAddress.getProvince());// 订单省的地址
                                        slOrder.setCity(slUserAddress.getCity()); // 订单市的收货地址
                                        slOrder.setCounty(slUserAddress.getCounty()); //订单区的收货地址
                                        slOrder.setDetailed(slUserAddress.getDetailed()); // 订单详细收货地址
                                        slOrder.setConsigneename(slUserAddress.getName());// 收货人姓名
                                        slOrder.setConsigneephone(slUserAddress.getPhone());// 收货人的联系方式
                                        orderService.insertSelective(slOrder);// 插入订单表
                                    }
                                    String finalSerialNumber = serialNumber;
                                    BigDecimal finalRewardsMoney = rewardsMoney;
                                    int finalRewardsPulse = rewardsPulse;
                                    SlProductRepository finalRepository1 = repository;
                                    orderDetailService.insertSelective(new SlOrderDetail() {{
                                        setId(UUID.randomUUID().toString());
                                        setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                        setCreator(slOrder.getUserId()); // 拼团订单去这个属性判断是哪个人的
                                        setOrderId(slOrder.getId()); // 订单ID
                                        setQuantity(detail.getQuantity()); // 商品数量
                                        setPrice(finalRepository1.getPrice()); // 单个商品价格
                                        setProductId(finalRepository1.getProductId());// 商品ID
                                        setShopId(finalRepository1.getShopId());// 店铺唯一标识
                                        setRepositoryId(finalRepository1.getId()); // 店铺仓库ID
                                        setPlaceOrderReturnPulse(finalRepository1.getRebatePulse());// 返了豆数量只限纯金钱模式
                                        setProductName(finalRepository1.getProductName());// 下单时的商品名称
                                        setProductImageUrl(finalRepository1.getProductImageUrl());// 下单时的商品图片
                                        setSerialNumber(finalSerialNumber); // 订单编号
                                        setProductDetailGroupName(finalRepository1.getProductDetailGroupName());// 商品规格名称
                                        setGroupPeople(slActivityProduct.getPeopleNum());// 无活动所需人数
                                        setRewardsMoney(finalRewardsMoney);// 推荐奖励的返的金额
                                        setRebatePulse(finalRewardsPulse);// 推荐奖励的返的了豆
                                        setPlaceOrderReturnPulse(finalRepository1.getPlaceOrderReturnPulse());// 下单获得了豆
                                    }});
                                    // 从他购买商品的此规格中减去此次购买的数量
                                    this.productRepositoryService.updateByPrimaryKeySelective(new SlProductRepository() {{
                                        setId(finalRepository1.getId());
                                        setCount(finalRepository1.getCount() - detail.getQuantity());
                                    }});
                                }
                            } else {
                                message.setMsg("当前库存不足");
                            }
                        } else {
                            message.setMsg("超出单个用户购买量");
                        }
                    } else {
                        message.setMsg("活动商品时间错误");
                    }
                } else {
                    message.setMsg("找不到商品");
                }
            } else {
                message.setMsg("系统错误");
            }
        } else {
            message.setMsg("当前用户不存在");
        }
        return message;
    }

    /**
     * 查询我的订单列表
     *
     * @param clientId
     * @return
     */
    public BusinessMessage findList(String clientId) {
        log.debug("查询我的订单列表clientId:{}", clientId);
        BusinessMessage message = new BusinessMessage();
        try {
            SlUser user = loginUserService.getCurrentLoginUser();
            if (null != user) {
                List<Map<String, Object>> list = this.cmOrderMapper.findList(user.getId());
                List<String> userAvatarList = new ArrayList<>();
                Map<String, Object> userAvatar = new HashMap<>();
                for (Map map : list) {
                    Object type = map.get("type");
                    Object serialNumber = map.get("serialNumber");
                    if (type.equals(2)) {//拼团订单
                        // 拼团订单筛选参与会员头像
                        userAvatarList = this.cmOrderMapper.findUserAvatar(serialNumber);
                    }
                }
                userAvatar.put("userAvatarList", userAvatarList);
                list.add(userAvatar);
                message.setMsg("查询成功");
                message.setSuccess(true);
                message.setData(list);
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
     * @param orderId
     * @return
     */
    public BusinessMessage orderInfo(String orderId) {
        BusinessMessage message = new BusinessMessage();
        try {
            SlUser user = this.loginUserService.getCurrentLoginUser();
            Map<String, Object> orderInfo = this.cmOrderMapper.selectMyOrderInfo(user.getId(), orderId);
            if (null != orderInfo) {
                message.setData(orderInfo);
                message.setSuccess(true);
                message.setMsg("查询成功");
            }
        } catch (Exception e) {
            log.error("查询失败", e);
        }
        return message;
    }


}