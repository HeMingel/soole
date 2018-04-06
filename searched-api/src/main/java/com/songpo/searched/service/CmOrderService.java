package com.songpo.searched.service;

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

@Slf4j
@Transactional
@Service
public class CmOrderService {

    @Autowired
    private UserService userService;
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
    private SlActivityProductRepositoryMapper activityProductRepositoryMapper;
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
            if (StringUtils.hasLength(shippingAddressId)) {
                String orderNum = OrderNumGeneration.getOrderIdByUUId();// 生成订单编号
                slOrder.setId(UUID.randomUUID().toString());
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
                    slOrder.setCounty(slUserAddress.getCounty()); //订单区的收货地址
                    slOrder.setDetailed(slUserAddress.getDetailed()); // 订单详细收货地址
                    slOrder.setConsigneename(slUserAddress.getName());// 收货人姓名
                    slOrder.setConsigneephone(slUserAddress.getPhone());// 收货人的联系方式
                    int co = orderService.insertSelective(slOrder);// 插入订单表
                    if (co > 0) {
                        for (SlOrderDetail slOrderDetail : orderDetail.getSlOrderDetails()) {
                            if (null != slOrderDetail.getRepositoryId()) {
                                SlProductRepository repository = null;
                                repository = repositoryCache.get(slOrderDetail.getRepositoryId());//查询redis
                                if (StringUtils.isEmpty(repository)) {
                                    // 根据仓库ID 去查询商品的详细信息(选好规格的价格,金豆等等)
                                    repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                                        setId(slOrderDetail.getRepositoryId());
                                    }});
                                    repositoryCache.put(repository.getId(), repository);//没有的话查询数据库放到redis
                                }
                                if (null != repository && slOrderDetail.getQuantity() > 0) {// 仓库存在 并且加入购物车的商品大于0
                                    SlProduct slProduct = null;
                                    slProduct = productCache.get(repository.getProductId());
                                    if (StringUtils.isEmpty(slProduct)) {
                                        SlProductRepository finalRepository = repository;
                                        slProduct = this.productService.selectOne(new SlProduct() {{
                                            setId(finalRepository.getProductId());
                                            setSoldOut(false);
                                        }});
                                        productCache.put(slProduct.getId(), slProduct);
                                    }
                                    if (null != slProduct) {
                                        SlProduct finalSlProduct = slProduct;
                                        SlActivityProduct slActivityProduct = activityProductMapper.selectOne(new SlActivityProduct() {{
                                            setActivityId(activityId);
                                            setProductId(finalSlProduct.getId());
                                            setEnabled(false);
                                        }});
                                        int counts = this.cmOrderMapper.selectOrdersCount(repository.getProductId(), user.getId(), slActivityProduct.getId());
                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        try {
                                            if (new Date().getTime() > format.parse(slActivityProduct.getBeginTime()).getTime()
                                                    && format.parse(slActivityProduct.getEndTime()).getTime() > new Date().getTime())
                                            {
                                                //统计该用户此商品加入订单的数量 目的是:限制每个用户购买数量{此商品的限定单数-用户此商品下单的数量 >=本次下单此商品的数量
                                                if (slActivityProduct.getActivityId().equals("19D76AB0-6A63-4E18-AAB6-2F3D2F86A90B")) {
                                                    slProduct.setRestrictCount(slProduct.getRestrictCount());// 如果是无活动就product表中的限定单数
                                                } else {
                                                    slProduct.setRestrictCount(slActivityProduct.getRestrictCount());// 不是就取activity_product中的限定单数
                                                }
                                                if (slProduct.getRestrictCount() - counts > slOrderDetail.getQuantity()) {
                                                    if (repository.getCount() - slOrderDetail.getQuantity() >= 0) {
                                                        money += repository.getPrice().doubleValue(); // 钱相加 用于统计和添加到订单表扣除总钱里边
                                                        pulse += repository.getSilver(); // 了豆相加  用于统计和添加到订单表扣除了豆里边
                                                        SlProductRepository finalRepository = repository;
                                                        orderDetailService.insertSelective(new SlOrderDetail() {{
                                                            setId(UUID.randomUUID().toString());
                                                            setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                                            setCreator(slOrder.getUserId()); // 拼团订单去这个属性判断是哪个人的
                                                            setProductName(finalSlProduct.getName());// 商品名称
                                                            setProductImageUrl(finalSlProduct.getImageUrl());// 商品图片
                                                            setOrderId(slOrder.getId()); // 订单ID
                                                            setQuantity(slOrderDetail.getQuantity()); // 商品数量
                                                            setPrice(finalRepository.getPrice()); // 单个商品价格
                                                            setProductId(finalRepository.getProductId());// 商品ID
                                                            setShopId(finalRepository.getShopId());// 店铺唯一标识
                                                            setRepositoryId(finalRepository.getId()); // 店铺仓库ID
                                                            setDeductTotalSilver(finalRepository.getSilver()); // 扣除单个商品了豆数量
                                                            setBuyerMessage(slOrderDetail.getBuyerMessage());// 添加买家留言
                                                            if (finalRepository.getPlaceOrderReturnPulse() > 0) {
                                                                setPlaceOrderReturnPulse(finalRepository.getRebatePulse());// 返了豆数量只限纯金钱模式
                                                            }
                                                            setSerialNumber(orderNum); // 订单编号
                                                            if (finalSlProduct.getSalesModeId().equals("3")) {
                                                                setPresellShipmentsDays(finalSlProduct.getPresellShipmentsDays());//向订单中添加预约天数
                                                            }
                                                            if (finalSlProduct.getSalesModeId().equals("4")) {// 消费奖励
                                                                if (finalRepository.getReturnCashMoney().doubleValue() > 0) {
                                                                    setReturnCashMoney(finalRepository.getReturnCashMoney()); // 消费返利金额
                                                                }
                                                            }
                                                            if (finalSlProduct.getSalesModeId().equals("5")) {// 一元购助力所需人数
                                                                setGroupPeople(finalSlProduct.getGroupPeople());
                                                            }
                                                            if (slActivityProduct.getActivityId().equals("4E39B37C-21D1-4BC2-F1CE-FED600214E64")) {
                                                                setRewardsMoney(finalRepository.getRewardsMoney());// 推荐返利的金额
                                                                setRebatePulse(finalRepository.getRebatePulse());// 推荐返利的了豆数量
                                                            }
                                                            int c = orderService.selectCount(new SlOrder() {{
                                                                setUserId(user.getId());
                                                                setPaymentState(1);// 已支付
                                                            }});
                                                            if (c == 0) {
                                                                setPlaceOrderReturnPulse(finalRepository.getPlaceOrderReturnPulse() + finalRepository.getFirstOrderPulse());
                                                            } else {
                                                                setPlaceOrderReturnPulse(finalRepository.getPlaceOrderReturnPulse());
                                                            }
                                                        }});
                                                        finalRepository.setCount(finalRepository.getCount() - slOrderDetail.getQuantity());// 当前库存 - 本次下单库存
                                                        repositoryCache.put(repository.getId(), finalRepository);// 先更新redis 的库存
                                                        Example example = new Example(SlProductRepository.class);
                                                        example.createCriteria()
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
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        message.setMsg("商品已下架");
                                    }
                                }
                            }
                        }
                    }
                    message.setMsg("添加订单成功");
                    message.setSuccess(true);
                } else {
                    message.setMsg("收货地址不存在");
                }
            } else {
                message.setMsg("收货地址不能为空");
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
        int rewardsPulse = 0;// 推荐奖励拼团奖励了豆数量
        BigDecimal rewardsMoney = null;// 推荐奖励拼团奖励金额
        if (null != user) {
            //查询仓库
            SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                setId(detail.getRepositoryId()); // 根据仓库ID 去查询商品的详细信息(选好规格的价格,金豆等等)
            }});
            if (null != repository) {
                SlProduct slProduct = this.productService.selectOne(new SlProduct() {{
                    setId(repository.getProductId());
                    setSoldOut(false);
                }});
                if (null != slProduct) {
                    Integer count = this.cmOrderMapper.groupOrdersByUser(slProduct.getId(), activityId, user.getId());
                    // 这一步是为了判断此规格商品是否属于活动商品 (是:是取活动拼团开始时间跟结束时间 否:下面就不用判断当前时间是否是活动时间内了)
                    if (activityId != "19D76AB0-6A63-4E18-AAB6-2F3D2F86A90B") {
                        SlActivityProduct slActivityProduct = this.cmOrderMapper.selectActivityProductByActivityAndRepositoryId(repository.getId(), activityId);
                        restrictCount = slActivityProduct.getRestrictCount();
                        this.activityProductMapper.updateByPrimaryKeySelective(new SlActivityProduct() {{
                            setId(slActivityProduct.getId());
                            setOrdersNum(slActivityProduct.getOrdersNum() + 1);// 此商品已拼单人数 + 1
                        }});
                        if (activityId.equals("4E39B37C-21D1-4BC2-F1CE-FED600214E64")) {
                            rewardsMoney = repository.getRewardsMoney();// 推荐奖励返利润的金额数量
                            rewardsPulse = repository.getRebatePulse();// 推荐奖励的返利润的了豆数量
                        }
                    } else {
                        restrictCount = slProduct.getRestrictCount();
                    }
                    if (restrictCount - count >= detail.getQuantity()) {// 商品限制购买单数 - 当前用户的该商品下单量 >= 本次下单的商品数量
                        if (repository.getCount() >= detail.getQuantity()) {// 本规格下的库存 >= 本次下单的商品数量
                            String serialNumber = null;
                            if (StringUtils.hasLength(shippingAddressId)) {
                                slOrder.setId(UUID.randomUUID().toString());
                                slOrder.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                if (StringUtils.hasLength(detail.getSerialNumber())) {
                                    serialNumber = detail.getSerialNumber();
                                    slOrder.setSerialNumber(serialNumber);// 前台传回来有本团的订单号(证明他是团员)
                                    String finalSerialNumber1 = serialNumber;
                                    int count1 = this.orderService.selectCount(new SlOrder() {{
                                        setSerialNumber(finalSerialNumber1);
                                    }});
                                    if (count1 + 1 <= detail.getGroupPeople()) {
                                        slOrder.setSpellGroupStatus(1);
                                    }
                                } else {
                                    serialNumber = OrderNumGeneration.getOrderIdByUUId();
                                    slOrder.setSerialNumber(serialNumber);// 前台传回来的是空的(证明他是团长)
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
                                orderDetailService.insertSelective(new SlOrderDetail() {{
                                    setId(UUID.randomUUID().toString());
                                    setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                    setCreator(slOrder.getUserId()); // 拼团订单去这个属性判断是哪个人的
                                    setOrderId(slOrder.getId()); // 订单ID
                                    setQuantity(detail.getQuantity()); // 商品数量
                                    setPrice(repository.getPrice()); // 单个商品价格
                                    setProductId(repository.getProductId());// 商品ID
                                    setShopId(repository.getShopId());// 店铺唯一标识
                                    setRepositoryId(repository.getId()); // 店铺仓库ID
                                    setPlaceOrderReturnPulse(repository.getRebatePulse());// 返了豆数量只限纯金钱模式
                                    setProductName(repository.getProductName());// 下单时的商品名称
                                    setProductImageUrl(repository.getProductImageUrl());// 下单时的商品图片
                                    setSerialNumber(finalSerialNumber); // 订单编号
                                    setGroupPeople(slProduct.getGroupPeople());// 无活动所需人数
                                    setRewardsMoney(finalRewardsMoney);// 推荐奖励的返的金额
                                    setRebatePulse(finalRewardsPulse);// 推荐奖励的返的了豆
                                    setPlaceOrderReturnPulse(repository.getPlaceOrderReturnPulse());// 下单获得了豆
                                }});
                                // 从他购买商品的此规格中减去此次购买的数量
                                this.productRepositoryService.updateByPrimaryKeySelective(new SlProductRepository() {{
                                    setId(repository.getId());
                                    setCount(repository.getCount() - detail.getQuantity());
                                }});
                            } else {
                                message.setMsg("地址不存在");
                            }
                        } else {
                            message.setMsg("当前库存不足");
                        }
                    } else {
                        message.setMsg("超出单个用户购买量");
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