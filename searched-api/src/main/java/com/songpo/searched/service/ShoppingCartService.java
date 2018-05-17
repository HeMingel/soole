package com.songpo.searched.service;

import com.songpo.searched.cache.ShoppingCartCache;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.CMGoods;
import com.songpo.searched.domain.CMShoppingCart;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.SlActivityProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartCache cache;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepositoryService productRepositoryService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private SlActivityProductMapper activityProductMapper;

    /**
     * 新增购物车
     *
     * @param pojo
     * @return
     */
    public BusinessMessage addMyShoppingCart(CMShoppingCart pojo) {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (null != user) {
            CMShoppingCart shoppingCart = this.cache.get(user.getId());
            if (!StringUtils.isEmpty(shoppingCart) && shoppingCart.getCarts().size() > 0) {
                String repositoryId = pojo.getCarts().get(0).getRepositoryId();
                SlProductRepository repository = productRepositoryService.selectOne(new SlProductRepository() {{
                    setId(repositoryId);
                }});
                if (null != repository) {
                    int count = pojo.getCarts().get(0).getCounts();
                    for (CMGoods goods : shoppingCart.getCarts()) {
                        if (goods.getRepositoryId().equals(repositoryId)) {
                            pojo.getCarts().get(0).setCounts(count + goods.getCounts());
                        } else {
                            pojo.getCarts().add(goods);
                        }
                    }
                    this.cache.put(user.getId(), pojo);
                } else {
                    message.setMsg("该商品规格不存在");
                    return message;
                }
            } else {
                this.cache.put(user.getId(), pojo);
            }
            message.setMsg("添加成功");
            message.setSuccess(true);
            message.setData(1);
        } else {
            message.setMsg("传入的用户ID不存在");
        }
        return message;
    }

    /**
     * 删除 list中的某个value
     *
     * @param repositoryIds
     * @return
     */
    public BusinessMessage deleteMyShoppingCart(String[] repositoryIds) {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (null != user) {
            CMShoppingCart pojo = this.cache.get(user.getId());
            if (null != pojo) {
                for (int i = 0; i < pojo.getCarts().size(); i++) {
                    //如果传过来的repositoryId == list中的某个repositoryId
                    for (String repositoryId : repositoryIds) {
                        if (repositoryId.equals(pojo.getCarts().get(i).getRepositoryId())) {
                            //就把这条list删除
                            pojo.getCarts().remove(i);
                        }
                        //把删除后的list重新覆盖
                        this.cache.put(user.getId(), pojo);
                    }
                }
                message.setMsg("删除成功");
                message.setSuccess(true);
            } else {
                message.setMsg("找不到购物车商品");
            }
        }
        return message;
    }

    /**
     * 查询购物车
     *
     * @return
     */
    public BusinessMessage findCart() {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (null != user) {
            CMShoppingCart pojo = this.cache.get(user.getId());
            if (pojo != null) {
                List<CMShoppingCart> list = new ArrayList<>();
                CMGoods cmGoods = null;
                if (null != pojo) {
                    for (CMGoods sc : pojo.getCarts()) {
                        List<CMGoods> goodsList = new ArrayList<>();
                        CMShoppingCart cart = new CMShoppingCart();
                        SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                            setId(sc.getRepositoryId());
                        }});
                        if (null != repository) {
                            SlProduct slProduct = this.productService.selectOne(new SlProduct() {{
                                setId(repository.getProductId());
                            }});
                            if (null != slProduct) {
                                SlActivityProduct activityProduct = this.activityProductMapper.selectOne(new SlActivityProduct() {{
                                    setProductId(slProduct.getId());
                                    setActivityId(sc.getActivityId());
                                }});
                                if (null != activityProduct) {
                                    if (true == activityProduct.getEnabled()) {
                                        cmGoods = new CMGoods();
                                        SlShop slShop = this.shopService.selectOne(new SlShop() {{
                                            setId(slProduct.getShopId());
                                        }});
                                        cmGoods.setGoodName(slProduct.getName());// 商品名称
                                        cmGoods.setCounts(sc.getCounts());// 加入购物车商品的数量
                                        cmGoods.setGoodId(slProduct.getId());//商品id
                                        cmGoods.setRepositoryId(repository.getId());//规格id
                                        cmGoods.setImageUrl(slProduct.getImageUrl()); // 商品图片
                                        cmGoods.setSilver(repository.getSilver());// 了豆(银豆,目前只扣除银豆)
                                        cmGoods.setSaleType(Integer.parseInt(slProduct.getSalesModeId()));// 销售类型前端根据销售类型去拼接两个字段
                                        cmGoods.setPrice(repository.getPrice());// 商品价格
                                        cmGoods.setSpecificationName(repository.getProductDetailGroupName());// 查询组合规格名称
                                        cmGoods.setRemainingqty(repository.getCount());// 商品剩余数量 返回0的话 前台就显示失效
                                        cmGoods.setSoldOut(slProduct.getSoldOut());// 商品是否下架 true:已下架前台就显示失效  false:未下架
                                        cmGoods.setRebatePulse(repository.getPlaceOrderReturnPulse());// 纯金钱商品返了豆数量
                                        cmGoods.setMyBeansCounts(user.getCoin() + user.getSilver()); // 我剩余豆子总和金豆加银豆
                                        cmGoods.setShopId(sc.getShopId());// 店铺id
                                        cmGoods.setShopName(slShop.getName());// 店铺名称
                                        cmGoods.setRestrictCount(activityProduct.getRestrictCount());//限制购买数量
                                        cmGoods.setActivityId(activityProduct.getActivityId());// 活动id
                                        cmGoods.setPostAge(slProduct.getPostage());//邮费
                                        cmGoods.setActivityProductId(activityProduct.getId());//活动商品id
                                        goodsList.add(cmGoods);
                                        cart.setShopId(slShop.getId());
                                        cart.setShopName(slShop.getName());
                                        cart.setCarts(goodsList);
                                        list.add(cart);
                                    } else {
                                        // 加入购物车中的商品如果查不到的话就把这个规格id删除掉
                                        String[] str = new String[]{sc.getRepositoryId()};
                                        deleteMyShoppingCart(str);
                                    }
                                } else {
                                    // 加入购物车中的商品如果查不到的话就把这个规格id删除掉
                                    String[] str = new String[]{sc.getRepositoryId()};
                                    deleteMyShoppingCart(str);
                                }
                            } else {
                                // 加入购物车中的商品如果查不到的话就把这个规格id删除掉
                                String[] str = new String[]{sc.getRepositoryId()};
                                deleteMyShoppingCart(str);
                            }
                        } else {
                            // 加入购物车中的商品如果查不到的话就把这个规格id删除掉
                            String[] str = new String[]{sc.getRepositoryId()};
                            deleteMyShoppingCart(str);
                        }
                    }
                }
                message.setMsg("查询成功");
                message.setData(list);
                message.setSuccess(true);
            } else {
                message.setData(null);
                message.setSuccess(true);
                message.setMsg("查询状态为空");
            }
        } else {
            message.setMsg("传入用户ID不存在");
        }
        return message;
    }

    /**
     * 修改购物车商品信息
     *
     * @param repository
     * @return
     */
    public BusinessMessage editShoppingCarts(String[] repository) {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (null != user) {
            CMShoppingCart pojo = this.cache.get(user.getId());
            if (null != pojo) {
                List<CMGoods> goodsList = new ArrayList<>();
                for (CMGoods goods : pojo.getCarts()) {
                    for (String re : repository) {
                        String[] aa = re.split(",");
                        for (String bb : aa) {
                            String repositoryId = bb.split("\\|")[0];
                            Integer count = Integer.valueOf(bb.split("\\|")[1]);
                            if (repositoryId.equals(goods.getRepositoryId())) {
                                if (count != null && count > 0) {
                                    goods.setCounts(count);
                                }
                                //  修改规格
//                        if (!StringUtils.isEmpty(repositoryId)) {
//                            goods.setRepositoryId(repositoryId);
//                            SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
//                                setId(repositoryId);
//                            }});
//                            goods.setGoodId(repository.getProductId());
//                        }
                            }
                        }
                    }
                    goodsList.add(goods);
                }
                pojo.setCarts(goodsList);
                this.cache.put(user.getId(), pojo);
                message.setMsg("修改成功");
                message.setSuccess(true);
            } else {
                message.setMsg("找不到购物车商品");
            }
        }
        return message;
    }
}

