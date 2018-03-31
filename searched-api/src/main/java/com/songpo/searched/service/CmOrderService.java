package com.songpo.searched.service;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.CMSlOrderDetail;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.CmOrderMapper;
import com.songpo.searched.mapper.SlUserAddressMapper;
import com.songpo.searched.util.OrderNumGeneration;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
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

    /**
     * 新增预下单订单
     *
     * @param slOrder
     * @param orderDetail
     * @param shippngAddressId
     * @return
     */
    @Transactional
    public BusinessMessage addOrder(SlOrder slOrder, CMSlOrderDetail orderDetail, String shippngAddressId) {
        Logger log = LoggerFactory.getLogger(SlOrderDetail.class);
        BusinessMessage message = new BusinessMessage();
        double money = 0.00;
        int pulse = 0;
        try {
            SlUser user = loginUserService.getCurrentLoginUser();
            if (null != user) {
                if (StringUtils.hasLength(shippngAddressId)) {
                    slOrder.setId(UUID.randomUUID().toString());
                    slOrder.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    String orderNum = OrderNumGeneration.getOrderIdByUUId();// 生成订单编号
                    slOrder.setSerialNumber(orderNum);// 订单编号
                    SlUserAddress slUserAddress = this.slUserAddressMapper.selectOne(new SlUserAddress() {{
                        setId(shippngAddressId);
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
                        for (SlOrderDetail slOrderDetail : orderDetail.getSlOrderDetails()) {
                            if (null != slOrderDetail.getRepositoryId()) {
                                SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                                    setId(slOrderDetail.getRepositoryId()); // 根据仓库ID 去查询商品的详细信息(选好规格的价格,金豆等等)
                                }});
                                // TODO 修改报错的代码
                                if (null != repository && null != slOrderDetail.getQuantity()) {
                                    money += repository.getPrice().doubleValue(); // 钱相加 用于统计和添加到订单表扣除总钱里边
                                    pulse += repository.getSilver(); // 了豆相加  用于统计和添加到订单表扣除了豆里边
                                    if (slOrder.getType() == 2) {
                                        // TODO 注释错误代码
//                                        String orderNums = this.cmOrderMapper.findGroup(repository.getProductId());
                                    }
                                    orderDetailService.insertSelective(new SlOrderDetail() {{
                                        setId(UUID.randomUUID().toString());
                                        setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                                        setCreator(slOrder.getUserId()); // 拼团订单去这个属性判断是哪个人的
                                        setOrderId(slOrder.getId()); // 订单ID
                                        setQuantity(slOrderDetail.getQuantity()); // 商品数量
                                        setPrice(repository.getPrice()); // 单个商品价格
                                        setProductId(repository.getProductId());// 商品ID
                                        setShopId(repository.getShopId());// 店铺唯一标识
                                        setRepositoryId(repository.getId()); // 店铺仓库ID
                                        setDeductTotalSilver(repository.getSilver()); // 扣除单个商品了豆数量
                                        // TODO 注释掉报错的代码
//                                        setReturnPulse(repository.getRebatePulse());// 返了豆数量只限纯金钱模式
                                        //setProduceName(repository.getProductName());// 下单时的商品名称
                                        setProductImageUrl(repository.getProductImageUrl());// 下单时的商品图片
                                    }});
                                }
                            }
                        }
                        BigDecimal amount = BigDecimal.valueOf(money);
                        Example example = new Example(SlOrder.class);
                        example.createCriteria().andEqualTo("id", slOrder.getId());
                        int totalPulse = pulse;
                        //统计好总价和总豆再次更新好订单表
                        orderService.updateByExampleSelective(new SlOrder() {{
                            setTotalAmount(amount);
                            setDeductTotalPulse(totalPulse);
                        }}, example);
                        message.setData(1);
                        message.setSuccess(true);
                        message.setMsg("创建订单成功");
                    } else {
                        message.setMsg("订单不存在");
                    }
                } else {
                    message.setMsg("收货地址不能为空");
                }
            } else {
                message.setMsg("用户不存在");
            }
        } catch (Exception e) {
            log.debug("error:{}", e.getMessage());
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
            SlUser user = this.userService.selectOne(new SlUser() {{
                setClientId(clientId);
            }});
            if (null != user) {
                List<Map<String, Object>> list = this.cmOrderMapper.findList(user.getId());
                List<String> userAvatarList = new ArrayList<>();
                Map<String, Object> userAvatar = new HashMap<>();
                for (Map map : list) {
                    Object type = map.get("type");
                    Object serialNumber = map.get("serialNumber");
                    if (type.equals(2)) {
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
