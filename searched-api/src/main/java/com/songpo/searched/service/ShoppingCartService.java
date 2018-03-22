package com.songpo.searched.service;

import com.songpo.searched.cache.ShoppingCartCache;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.CMGoods;
import com.songpo.searched.domain.CMShoppingCart;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.entity.SlProductRepository;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SpecificationNameMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartService {
    @Autowired
    private SpecificationNameMapper specificationNameMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartCache cache;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepositoryService productRepositoryService;
    @Autowired
    private ShopService shopService;

    /**
     * 新增购物车
     *
     * @param pojo
     * @return
     */
    public BusinessMessage addmyShoppingCart(CMShoppingCart pojo) {
        BusinessMessage message = new BusinessMessage();
        if (StringUtils.isEmpty(pojo.getUserId())) {
            message.setMsg("用户ID为空");
        } else {
            SlUser user = this.userService.selectOne(new SlUser() {{
                setId(pojo.getUserId());
            }});
            if (null != user) {
                this.cache.put(pojo.getUserId(), pojo);
                message.setMsg("添加成功");
                message.setSuccess(true);
                message.setData(1);
            } else {
                message.setMsg("传入的用户ID不存在");
            }
        }
        return message;
    }

    /**
     * 查询购物车
     *
     * @param uid
     * @return
     */
    public BusinessMessage findCart(String uid) {
        BusinessMessage message = new BusinessMessage();
        if (StringUtils.isEmpty(uid)) {
            message.setMsg("用户ID为空");
            message.setData(null);
        } else {
            SlUser user = this.userService.selectOne(new SlUser() {{
                setId(uid);
            }});
            if (null != user) {
                CMShoppingCart pojo = this.cache.get(uid);
                if (pojo != null) {
                    List<CMGoods> list = new ArrayList<>();
                    CMGoods cmGoods = null;
                    if (null != pojo) {
                        for (CMGoods sc : pojo.getCarts()) {
                            if (StringUtils.isEmpty(sc.getGoodId())) {
                                message.setMsg("获取商品ID错误");
                            } else {
                                SlProduct slProduct = this.productService.selectOne(new SlProduct() {{
                                    setId(sc.getGoodId());
                                    setSoldOut(false);
                                }});
                                if (null != slProduct) {
                                    cmGoods = new CMGoods();
                                    cmGoods.setGoodName(slProduct.getName());// 商品名称
                                    cmGoods.setCounts(sc.getCounts());// 加入购物车商品的数量
                                    cmGoods.setImageUrl(slProduct.getImageUrl()); // 商品图片
                                    SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                                        setId(sc.getRepositoryId());
                                        setProductId(sc.getGoodId());
                                    }});
//                                    cmGoods.setPulse(repository.getPulse());// 了豆
                                    cmGoods.setSaleType(slProduct.getSaleType());// 销售类型前端根据销售类型去拼接两个字段 5钱6乐豆7钱+了豆
                                    cmGoods.setPrice(repository.getPrice());// 商品价格
                                    cmGoods.setSpecificationName(repository.getProductDetailGroupName());// 查询组合规格名称
                                    cmGoods.setShopId(sc.getShopId());// 店铺id
                                    cmGoods.setShopName(sc.getShopName());// 店铺名称
                                    cmGoods.setRemainingqty(repository.getCount());// 商品剩余数量 返回0的话 前台就显示失效
                                    list.add(cmGoods);
                                } else {
                                    message.setMsg("商品已下架");
                                    message.setSuccess(true);
                                    list.add(new CMGoods());
                                }
                            }
                        }
                    }
                    pojo.setCarts(list);// 把查询好的list 加入pojo中
                    message.setMsg("查询状态为空");
                    message.setData(null);
                    message.setSuccess(true);
                } else {
                    message.setData(pojo);
                    message.setSuccess(true);
                    message.setMsg("查询成功");
                }
            } else {
                message.setMsg("传入用户ID不存在");
            }
        }
        return message;
    }
}
