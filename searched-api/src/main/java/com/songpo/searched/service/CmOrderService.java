package com.songpo.searched.service;

import com.songpo.searched.cache.OrderCache;
import com.songpo.searched.cache.ProductRepositoryCache;
import com.songpo.searched.cache.ProductCache;
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
    @Autowired
    private OrderCache orderCache;

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
            //订单加入redis
            orderCache.put(slOrder.getId(), slOrder);
            if (co > 0) {
                for (SlOrderDetail slOrderDetail : orderDetail.getSlOrderDetails()) {
                    //查询redis
                    SlProductRepository repository = repositoryCache.get("com.songpo.seached:repository:" + slOrderDetail.getRepositoryId());
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
                        SlProduct slProduct = productCache.get("com.songpo.seached:product:time-limit:" + repository.getProductId());
                        //如果没有 就查一遍
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
                            }});
                            //向redis中加入过期时间
                            try {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date now = new Date();
                                //活动商品的结束时间减去当前时间就为了的到它的时间段
                                Long times = (format.parse(slActivityProduct.getEndTime()).getTime() - now.getTime());
                                //把活动商品的唯一主键当做redis中的key,加入过期时间times,单位:秒
                                productCache.put(slActivityProduct.getId(), slProduct, times / 1000, TimeUnit.SECONDS);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //slActivityProduct 不为空 && 这个key的过期时间>0
                            if (slActivityProduct != null && productCache.getRedisTemplate().getExpire("com.songpo.seached:product:time-limit:" + slActivityProduct.getId()) > 0) {
                                // 查询当前用户该商品的已生成订单的商品数量之和
                                int counts = this.cmOrderMapper.selectOrdersCount(repository.getProductId(), user.getId(), slActivityProduct.getId());
                                //单用户购买显示数量 - 他已经下单的数量 > 这次加入订单的数量
                                if (slActivityProduct.getRestrictCount() - counts > slOrderDetail.getQuantity()) {
                                    //库存的数量 > 他这次加入订单的数量
                                    if (repository.getCount() - slOrderDetail.getQuantity() >= 0) {
                                        // 钱相加 用于统计和添加到订单表扣除总钱里边
                                        money += repository.getPrice().doubleValue() * slOrderDetail.getQuantity();
                                        // 了豆相加  用于统计和添加到订单表扣除了豆里边
                                        pulse += repository.getSilver() * slOrderDetail.getQuantity();
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
                                            //判断是否为首单
                                            if (c == 0) {
                                                // 如果是第一单的情况下 需要加上 首单奖励
                                                setPlaceOrderReturnPulse(finalRepository.getPlaceOrderReturnPulse() + finalRepository.getFirstOrderPulse());
                                            } else {
                                                // 返了豆数量只限纯金钱模式
                                                setPlaceOrderReturnPulse(finalRepository.getPlaceOrderReturnPulse());
                                            }
                                        }});
                                        // 当前库存 - 本次该商品规格下单库存
                                        int count = finalRepository.getCount() - slOrderDetail.getQuantity();
                                        // 商品库存 - 本次加入订单的数量
                                        int activityProductCount = slActivityProduct.getCount() - slOrderDetail.getQuantity();
                                        // 如果库存为0 的话就下架了
                                        Example example = new Example(SlActivityProduct.class);
                                        example.createCriteria()
                                                .andGreaterThan("count", 0)
                                                .andEqualTo("id", slActivityProduct.getId());
                                        this.activityProductMapper.updateByExampleSelective(new SlActivityProduct() {{
                                            if (activityProductCount == 0) {
                                                setEnabled(false);
                                            }
                                            //活动总商品上架数量 - 本次购买的数量
                                            setCount(activityProductCount);
                                        }}, example);
                                        finalRepository.setCount(count);
                                        // 更新redis中该商品规格的库存
                                        repositoryCache.put(repository.getId(), finalRepository);
                                        Example example1 = new Example(SlProductRepository.class);
                                        example.createCriteria()
                                                // 比0大的库存
                                                .andGreaterThan("count", 0)
                                                .andEqualTo("id", finalRepository.getId());
                                        //更新数据库该商品规格的库存
                                        this.productRepositoryService.updateByExampleSelective(new SlProductRepository() {{
                                            setCount(repositoryCache.get(finalRepository.getId()).getCount());
                                        }}, example1);
                                        double finalMoney = money;
                                        int finalPulse = pulse;
                                        // 更新订单总价和总豆
                                        this.orderService.updateByPrimaryKeySelective(new SlOrder() {{
                                            setId(slOrder.getId());
                                            setActivityProductId(slActivityProduct.getActivityId());// 活动id
                                            setTotalAmount(BigDecimal.valueOf(finalMoney));
                                            setDeductTotalPulse(finalPulse);
                                        }});
                                        message.setMsg("订单生成成功");
                                        message.setSuccess(true);
                                    } else {
                                        message.setMsg("当前库存不足");
                                        break;
                                    }
                                } else {
                                    message.setMsg("已超过最大购买数量");
                                    break;
                                }
                            } else {
                                message.setMsg("活动商品时间错误");
                                break;
                            }
                        } else {
                            message.setMsg("商品已下架");
                            break;
                        }
                    }
                }
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
        // 推荐奖励拼团奖励了豆数量
        int rewardsPulse = 0;
        // 推荐奖励拼团奖励金额
        BigDecimal rewardsMoney = null;
        if (null != user) {
            //reids 查询仓库
            SlProductRepository repository = repositoryCache.get("com.songpo.seached:repository:" + detail.getRepositoryId());
            if (StringUtils.isEmpty(repository)) {
                // 根据仓库ID 去查询商品的详细信息(选好规格的价格,金豆等等)
                repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                    setId(detail.getRepositoryId());
                }});
                repositoryCache.put(repository.getId(), repository);
            }
            if (null != repository) {
                SlProduct product = productCache.get("com.songpo.seached:product:time-limit:" + repository.getProductId());
                if (StringUtils.isEmpty(product)) {
                    SlProductRepository finalRepository = repository;
                    product = this.productService.selectOne(new SlProduct() {{
                        setId(finalRepository.getProductId());
                        setSoldOut(false);
                    }});
                }
                if (null != product) {
                    //查询活动商品信息
                    SlActivityProduct slActivityProduct = this.cmOrderMapper.selectActivityProductByActivityAndRepositoryId(repository.getId(), activityId, product.getId());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date now = new Date();
                        Long times = (format.parse(slActivityProduct.getEndTime()).getTime() - now.getTime());
                        productCache.put(slActivityProduct.getId(), product, times / 1000, TimeUnit.SECONDS);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
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
                    if (productCache.getRedisTemplate().getExpire("com.songpo.seached:product:time-limit:" + slActivityProduct.getId()) > 0) {
                        //此活动拼团商品当前用户的拼单下单数量
                        Integer count = this.cmOrderMapper.groupOrdersByUser(product.getId(), activityId, user.getId());
                        // 商品限制购买单数 - 当前用户的该商品下单量 >= 本次下单的商品数量
                        if (slActivityProduct.getRestrictCount() - count >= detail.getQuantity()) {
                            // 本规格下的库存 >= 本次下单的商品数量
                            if (repository.getCount() >= detail.getQuantity()) {
                                String serialNumber;
                                slOrder.setId(UUID.randomUUID().toString());
                                slOrder.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                // 前台传回来有本团的订单号(证明他是团员)
                                if (StringUtils.hasLength(detail.getSerialNumber())) {
                                    //查询有没有该订单的人
                                    Boolean flag = this.orderService.exist(new SlOrder() {{
                                        setSerialNumber(detail.getSerialNumber());
                                    }});
                                    serialNumber = detail.getSerialNumber();
                                    //如果传过来的数据不存在
                                    if (flag) {
                                        Boolean f = this.orderService.exist(new SlOrder() {{
                                            setSerialNumber(detail.getSerialNumber());
                                            setUserId(user.getId());
//                                            setActivityProductId(slActivityProduct.getActivityId());
                                        }});
                                        // 他是不是参加过该订单号的拼团
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
                                            Double d = repository.getPrice().doubleValue() * detail.getQuantity();
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
                                                //订单加入redis
                                                orderCache.put(slOrder.getId(), slOrder,300L,TimeUnit.SECONDS);
                                            }
                                            String finalSerialNumber = serialNumber;
                                            // TODO ----------- 待商确分享奖励 -------------------
//                                            BigDecimal finalRewardsMoney = rewardsMoney;
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
                                                // TODO ----------- 待商确分享奖励 -------------------
//                                                setRewardsMoney(finalRewardsMoney);// 推荐奖励的返的金额
//                                                setRebatePulse(finalRewardsPulse);// 推荐奖励的返的了豆
                                                // 查询当前用户的支付订单
                                                int c = orderService.selectCount(new SlOrder() {{
                                                    setUserId(user.getId());
                                                    // 已支付
                                                    setPaymentState(1);
                                                }});
                                                //判断是否为首单
                                                if (c == 0) {
                                                    // 如果是第一单的情况下 需要加上 首单奖励
                                                    setPlaceOrderReturnPulse(finalRepository1.getPlaceOrderReturnPulse() + finalRepository1.getFirstOrderPulse());
                                                } else {
                                                    // 返了豆数量只限纯金钱模式
                                                    setPlaceOrderReturnPulse(finalRepository1.getPlaceOrderReturnPulse());
                                                }
                                            }});
                                            // 当前库存 - 本次该商品规格下单库存
                                            int count2 = finalRepository1.getCount() - detail.getQuantity();
                                            int activityProductCount = slActivityProduct.getCount() - detail.getQuantity();
                                            // 如果库存为0 的话就下架了
                                            Example example = new Example(SlActivityProduct.class);
                                            example.createCriteria()
                                                    .andGreaterThan("count", 0)
                                                    .andEqualTo("id", slActivityProduct.getId());
                                            this.activityProductMapper.updateByExampleSelective(new SlActivityProduct() {{
                                                if (activityProductCount == 0) {
                                                    setEnabled(false);
                                                }
                                                //活动总商品上架数量 - 本次购买的数量
                                                setCount(activityProductCount);
                                            }}, example);
                                            finalRepository1.setCount(count2);
                                            // 更新redis中该商品规格的库存
                                            repositoryCache.put(repository.getId(), finalRepository1);
                                            Example example1 = new Example(SlProductRepository.class);
                                            example.createCriteria()
                                                    // 比0大的库存
                                                    .andGreaterThan("count", 0)
                                                    .andEqualTo("id", finalRepository1.getId());
                                            //更新数据库该商品规格的库存
                                            this.productRepositoryService.updateByExampleSelective(new SlProductRepository() {{
                                                setCount(repositoryCache.get(finalRepository1.getId()).getCount());
                                            }}, example1);
                                            // 更新订单总价和总豆
                                            this.orderService.updateByPrimaryKeySelective(new SlOrder() {{
                                                setId(slOrder.getId());
                                                setActivityProductId(slActivityProduct.getActivityId());// 活动id
                                            }});
                                            message.setMsg("订单生成成功");
                                            message.setSuccess(true);
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
                                        //订单加入redis
                                        orderCache.put(slOrder.getId(), slOrder,300L,TimeUnit.SECONDS);
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
                                        // TODO ----------- 待商确分享奖励 -------------------
//                                                setRewardsMoney(finalRewardsMoney);// 推荐奖励的返的金额
//                                                setRebatePulse(finalRewardsPulse);// 推荐奖励的返的了豆
                                        // 查询当前用户的支付订单
                                        int c = orderService.selectCount(new SlOrder() {{
                                            setUserId(user.getId());
                                            // 已支付
                                            setPaymentState(1);
                                        }});
                                        //判断是否为首单
                                        if (c == 0) {
                                            // 如果是第一单的情况下 需要加上 首单奖励
                                            setPlaceOrderReturnPulse(finalRepository1.getPlaceOrderReturnPulse() + finalRepository1.getFirstOrderPulse());
                                        } else {
                                            // 返了豆数量只限纯金钱模式
                                            setPlaceOrderReturnPulse(finalRepository1.getPlaceOrderReturnPulse());
                                        }
                                    }});
                                    // 当前库存 - 本次该商品规格下单库存
                                    int count2 = finalRepository1.getCount() - detail.getQuantity();
                                    int activityProductCount = slActivityProduct.getCount() - detail.getQuantity();
                                    // 如果库存为0 的话就下架了
                                    Example example = new Example(SlActivityProduct.class);
                                    example.createCriteria()
                                            .andGreaterThan("count", 0)
                                            .andEqualTo("id", slActivityProduct.getId());
                                    this.activityProductMapper.updateByExampleSelective(new SlActivityProduct() {{
                                        if (activityProductCount == 0) {
                                            setEnabled(false);
                                        }
                                        //活动总商品上架数量 - 本次购买的数量
                                        setCount(activityProductCount);
                                    }}, example);
                                    finalRepository1.setCount(count2);
                                    // 更新redis中该商品规格的库存
                                    repositoryCache.put(repository.getId(), finalRepository1);
                                    Example example1 = new Example(SlProductRepository.class);
                                    example.createCriteria()
                                            // 比0大的库存
                                            .andGreaterThan("count", 0)
                                            .andEqualTo("id", finalRepository1.getId());
                                    //更新数据库该商品规格的库存
                                    this.productRepositoryService.updateByExampleSelective(new SlProductRepository() {{
                                        setCount(repositoryCache.get(finalRepository1.getId()).getCount());
                                    }}, example1);
                                    // 更新订单总价和总豆
                                    this.orderService.updateByPrimaryKeySelective(new SlOrder() {{
                                        setId(slOrder.getId());
                                        setActivityProductId(slActivityProduct.getActivityId());// 活动id
                                    }});
                                    message.setMsg("订单生成成功");
                                    message.setSuccess(true);
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
            List<Map<String, Object>> orderInfo = this.cmOrderMapper.selectMyOrderInfo(user.getId(), orderId);
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

    /**
     * 取消订单操作
     *
     * @param orderId
     * @return
     */
    public void cancelAnOrder(String orderId) {
        SlUser user = loginUserService.getCurrentLoginUser();
        if (null != user) {
            Example example = new Example(SlOrder.class);
            example.createCriteria()
                    .andEqualTo("id", orderId)
                    .andEqualTo("userId", user.getId());
            orderService.updateByExampleSelective(new SlOrder() {{
                //取消订单
                setPaymentState(102);
            }}, example);
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
            }
        }
    }
}