package com.songpo.searched.service;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlOrder;
import com.songpo.searched.entity.SlOrderDetail;
import com.songpo.searched.entity.SlProductRepository;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlOrderMapper;
import com.songpo.searched.util.OrderNumGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderService {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepositoryService productRepositoryService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private SlOrderMapper orderMapper;

    /**
     * 新增预下单订单
     *
     * @param slOrder
     * @param orderDetail
     * @return
     */
    public BusinessMessage addOrder(SlOrder slOrder, List<SlOrderDetail> orderDetail) {
        Logger log = LoggerFactory.getLogger(SlOrderDetail.class);
        BusinessMessage message = new BusinessMessage();
        double money = 0.00;
        int pulse = 0;
        try
        {
            if (StringUtils.hasLength(slOrder.getUserId()))
            {
                SlUser user = userService.selectOne(new SlUser() {{
                    setId(slOrder.getUserId());
                }});
                if (null != user)
                {
                    if (StringUtils.hasLength(slOrder.getShippngAddressId()))
                    {
                        slOrder.setSerialNumber(OrderNumGeneration.getOrderIdByUUId());// 生成订单编号
                        orderMapper.insertSelective(slOrder);
                        for (SlOrderDetail slOrderDetail : orderDetail)
                        {
                            if (StringUtils.hasLength(slOrderDetail.getProductId()))
                            {
                                SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                                    setId(slOrderDetail.getProductId());
                                }});
                                if (null != repository && StringUtils.hasLength(slOrderDetail.getQuantity()))
                                {
                                    money += repository.getPrice().doubleValue(); // 钱相加 用于统计和添加到订单表扣除总钱里边
                                    pulse += repository.getPulse(); // 了豆相加  用于统计和添加到订单表扣除了豆里边
                                    orderDetailService.insertSelective(new SlOrderDetail() {{
                                        setId(UUID.randomUUID().toString());
                                        setOrderId(slOrder.getId()); // 订单ID
                                        setQuantity(slOrderDetail.getQuantity()); // 商品数量
                                        setPrice(repository.getPrice()); // 单个商品价格
                                        setProductId(slOrderDetail.getProductId());// 商品ID
                                        setShopId(repository.getShopId());// 店铺唯一标识
                                        setRepositoryId(repository.getId()); // 店铺仓库ID
                                        setDeductPulse(repository.getPulse()); // 扣除单个商品了豆数量
                                    }});
                                }
                            }
                        }
                        BigDecimal amount = BigDecimal.valueOf(money);
                        Example example = new Example(SlOrder.class);
                        example.createCriteria().andEqualTo("id", slOrder.getId());
                        int totalPulse = pulse;
                        /**
                         * 统计好总价和总豆再次更新好订单表
                         */
                        orderMapper.updateByExampleSelective(new SlOrder() {{
                            setTotalAmount(amount);
                            setDeductTotalPulse(totalPulse);
                        }}, example);
                    } else
                    {
                        message.setMsg("收货地址不能为空");
                    }
                } else
                {
                    message.setMsg("用户不存在");
                }
            } else
            {
                message.setMsg("用户ID为空");
            }
        } catch (Exception e)
        {
            log.debug("error:", e.getMessage());
        }
        return message;
    }
}
