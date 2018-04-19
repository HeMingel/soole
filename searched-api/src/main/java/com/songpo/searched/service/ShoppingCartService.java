package com.songpo.searched.service;

import com.songpo.searched.cache.ShoppingCartCache;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.CMGoods;
import com.songpo.searched.domain.CMShoppingCart;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.entity.SlProductRepository;
import com.songpo.searched.entity.SlShop;
import com.songpo.searched.entity.SlUser;
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
                for (CMGoods goods : pojo.getCarts()) {
                    shoppingCart.getCarts().add(goods);
                }
                this.cache.put(user.getId(), shoppingCart);
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
     * @param repositoryId
     * @return
     */
    public BusinessMessage deleteMyShoppingCart(String repositoryId) {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (null != user) {
            CMShoppingCart pojo = this.cache.get(user.getId());
            if (null != pojo) {
                for (int i = 0; i < pojo.getCarts().size(); i++) {
                    //如果传过来的repositoryId == list中的某个repositoryId
                    if (repositoryId.equals(pojo.getCarts().get(i).getRepositoryId())) {
                        //就把这条list删除
                        pojo.getCarts().remove(i);
                    }
                    //把删除后的list重新覆盖
                    this.cache.put(user.getId(), pojo);
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
                List<CMGoods> goodsList = new ArrayList<>();
                CMShoppingCart cart = new CMShoppingCart();
                CMGoods cmGoods = null;
                if (null != pojo) {
                    for (CMGoods sc : pojo.getCarts()) {
                        if (StringUtils.isEmpty(sc.getGoodId())) {
                            message.setMsg("获取商品ID错误");
                        } else {
                            SlProduct slProduct = this.productService.selectOne(new SlProduct() {{
                                setId(sc.getGoodId());
                                setSoldOut(true);
                            }});
                            if (null != slProduct) {
                                cmGoods = new CMGoods();
                                SlShop slShop = this.shopService.selectOne(new SlShop() {{
                                    setId(slProduct.getShopId());
                                }});
                                if (list.size() > 0) {
                                    for (int i = 0; i < list.size(); i++) {
                                        List<CMGoods> list1 = new ArrayList<>();
                                        CMShoppingCart cart1 = list.get(i);
                                        if (cart1.getShopId().equals(slShop.getId())) {
                                            cmGoods.setGoodName(slProduct.getName());// 商品名称
                                            cmGoods.setCounts(sc.getCounts());// 加入购物车商品的数量
                                            SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                                                setId(sc.getRepositoryId());
                                                setProductId(sc.getGoodId());
                                            }});
                                            cmGoods.setGoodId(slProduct.getId());//商品id
                                            cmGoods.setRepositoryId(repository.getId());//规格id
                                            cmGoods.setImageUrl(repository.getProductImageUrl()); // 商品图片
                                            cmGoods.setSilver(repository.getSilver());// 了豆(银豆,目前只扣除银豆)
                                            cmGoods.setSaleType(Integer.parseInt(slProduct.getSalesModeId()));// 销售类型前端根据销售类型去拼接两个字段
                                            cmGoods.setPrice(repository.getPrice());// 商品价格
                                            cmGoods.setSpecificationName(repository.getProductDetailGroupName());// 查询组合规格名称
                                            cmGoods.setShopId(sc.getShopId());// 店铺id
                                            cmGoods.setShopName(slShop.getName());// 店铺名称
                                            cmGoods.setRemainingqty(repository.getCount());// 商品剩余数量 返回0的话 前台就显示失效
                                            cmGoods.setSoldOut(slProduct.getSoldOut());// 商品是否下架 true:已下架前台就显示失效  false:未下架
                                            cmGoods.setRebatePulse(repository.getPlaceOrderReturnPulse());// 纯金钱商品返了豆数量
                                            cmGoods.setMyBeansCounts(user.getCoin() + user.getSilver()); // 我剩余豆子总和金豆加银豆
                                            list1 = cart1.getCarts();
                                            list1.add(cmGoods);
                                            break;
                                        } else {
                                            List<CMGoods> list2 = new ArrayList<>();
                                            CMShoppingCart cart2 = new CMShoppingCart();
                                            cmGoods.setGoodName(slProduct.getName());// 商品名称
                                            cmGoods.setCounts(sc.getCounts());// 加入购物车商品的数量
                                            SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                                                setId(sc.getRepositoryId());
                                                setProductId(sc.getGoodId());
                                            }});
                                            cmGoods.setGoodId(slProduct.getId());//商品id
                                            cmGoods.setRepositoryId(repository.getId());//规格id
                                            cmGoods.setImageUrl(repository.getProductImageUrl()); // 商品图片
                                            cmGoods.setSilver(repository.getSilver());// 了豆(银豆,目前只扣除银豆)
                                            cmGoods.setSaleType(Integer.parseInt(slProduct.getSalesModeId()));// 销售类型前端根据销售类型去拼接两个字段
                                            cmGoods.setPrice(repository.getPrice());// 商品价格
                                            cmGoods.setSpecificationName(repository.getProductDetailGroupName());// 查询组合规格名称
                                            cmGoods.setShopId(sc.getShopId());// 店铺id
                                            cmGoods.setShopName(slShop.getName());// 店铺名称
                                            cmGoods.setRemainingqty(repository.getCount());// 商品剩余数量 返回0的话 前台就显示失效
                                            cmGoods.setSoldOut(slProduct.getSoldOut());// 商品是否下架 true:已下架前台就显示失效  false:未下架
                                            cmGoods.setRebatePulse(repository.getPlaceOrderReturnPulse());// 纯金钱商品返了豆数量
                                            cmGoods.setMyBeansCounts(user.getCoin() + user.getSilver()); // 我剩余豆子总和金豆加银豆
                                            list2.add(cmGoods);
                                            cart2.setCarts(list2);
                                            cart2.setShopId(slShop.getId());
                                            cart2.setShopName(slShop.getName());
                                            list.add(cart2);
                                            break;
                                        }
                                    }
                                } else {
                                    cmGoods.setGoodName(slProduct.getName());// 商品名称
                                    cmGoods.setCounts(sc.getCounts());// 加入购物车商品的数量
                                    SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                                        setId(sc.getRepositoryId());
                                        setProductId(sc.getGoodId());
                                    }});
                                    cmGoods.setGoodId(slProduct.getId());//商品id
                                    cmGoods.setRepositoryId(repository.getId());//规格id
                                    cmGoods.setImageUrl(repository.getProductImageUrl()); // 商品图片
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
                                    goodsList.add(cmGoods);
                                    cart.setShopId(slShop.getId());
                                    cart.setShopName(slShop.getName());
                                    cart.setCarts(goodsList);
                                    list.add(cart);
                                }
                            } else {
                                message.setSuccess(true);
                                list.add(new CMShoppingCart());
                            }
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
     * @param agoRepositoryId
     * @param repositoryId
     * @param counts
     * @return
     */
    public BusinessMessage editShoppingCarts(String agoRepositoryId, String repositoryId, Integer counts) {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (null != user) {
            CMShoppingCart pojo = this.cache.get(user.getId());
            if (null != pojo) {
                List<CMGoods> goodsList = new ArrayList<>();
                for (CMGoods goods : pojo.getCarts()) {
                    if (agoRepositoryId.equals(goods.getRepositoryId())) {
                        goods.setCounts(counts);
                        goods.setRepositoryId(repositoryId);
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

