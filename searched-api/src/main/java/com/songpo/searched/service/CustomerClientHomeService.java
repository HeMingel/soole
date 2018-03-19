package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.cache.ShoppingCartCache;
import com.songpo.searched.domain.MyShoppingCartPojo;
import com.songpo.searched.domain.ShoppingCart;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.SpecificationNameMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘松坡
 */
@Slf4j
@Service
public class CustomerClientHomeService {

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ActionNavigationService actionNavigationService;

    @Autowired
    private ActionNavigationTypeService actionNavigationTypeService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private CmProductService cmproductService;

    @Autowired
    private MyShoppingCartService myShoppingCartService;

    @Autowired
    private ShoppingCartCache cache;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private SpecificationNameMapper specificationNameMapper;

    /**
     * 获取首页所有数据
     *
     * @return
     */
    public JSONObject getHomeData() {
        JSONObject data = new JSONObject();

        // 获取所有一级商品分类列表
        List<SlProductType> productTypes = this.productTypeService.select(new SlProductType() {{
            setParentId(null);
        }});
        data.put("productTypes", productTypes);

        // 获取首页配置主键
        SlSystemConfig homeType = this.systemConfigService.selectOne(new SlSystemConfig() {{
            setName("CUSTOMER_CLIENT_HOME");
        }});

        JSONObject banner = new JSONObject();

        if (null != homeType && !StringUtils.isEmpty(homeType.getContent()))
        {
            // 获取广告轮播图列表
            List<SlActionNavigation> bannerList = this.actionNavigationService.select(new SlActionNavigation() {{
                // 设置位置编号
                setSerialNumber(1);
                // 设置类型为首页
                setTypeId(homeType.getContent());
            }});
            banner.put("data", bannerList);

            // 获取轮播图播放速度
            SlActionNavigationType bannerSpeed = this.actionNavigationTypeService.selectByPrimaryKey(homeType.getId());
            banner.put("speed", bannerSpeed.getSpeed());

            data.put("banner", banner);

            // 获取入口列表
            List<SlActionNavigation> gatewayList = this.actionNavigationService.select(new SlActionNavigation() {{
                // 设置位置编号
                setSerialNumber(2);
                // 设置类型为首页
                setTypeId(homeType.getId());
            }});
            data.put("gateway", gatewayList);

            // 获取活动列表
            List<SlActionNavigation> actionList = this.actionNavigationService.select(new SlActionNavigation() {{
                // 设置位置编号
                setSerialNumber(3);
                // 设置类型为首页
                setTypeId(homeType.getId());
            }});
            data.put("action", actionList);
        }

        // 获取拼团商品列表
        SlSystemConfig teamworkType = this.systemConfigService.selectOne(new SlSystemConfig() {{
            setName("TEAMWORK_PRODUCT");
        }});
        if (null != teamworkType && !StringUtils.isEmpty(teamworkType.getContent()))
        {
            List<SlProduct> products = this.cmproductService.selectByActionId(teamworkType.getContent());
            data.put("teamworkProducts", products);
        }

        // 获取预售商品列表
        SlSystemConfig preSaleType = this.systemConfigService.selectOne(new SlSystemConfig() {{
            setName("PRE_SALE_PRODUCT");
        }});
        if (null != preSaleType && !StringUtils.isEmpty(preSaleType.getContent()))
        {
            List<SlProduct> products = this.cmproductService.selectByActionId(teamworkType.getContent());
            data.put("preSaleProducts", products);
        }

        return data;
    }

    /**
     * 获取用户端分类数据
     *
     * @return
     */
    public JSONObject getClassificationData() {
        return null;
    }

    /**
     * 获取用户端购物车数据
     *
     * @return
     */
    public JSONObject getShoppingCartData(String uid) {
        JSONObject object = new JSONObject();
        if (StringUtils.hasLength(uid))
        {
            SlUser user = this.userService.selectOne(new SlUser() {{
                setId(uid);
            }});
            if (null != user)
            {
                MyShoppingCartPojo pojo = this.cache.get(uid);
                List<ShoppingCart> list = new ArrayList<>();
                ShoppingCart shoppingCart = null;
                if (null != pojo)
                {
                    for (ShoppingCart sc : pojo.getCarts())
                    {
                        if (StringUtils.hasLength(sc.getGoodId()))
                        {
                            SlProduct slProduct = this.productService.selectOne(new SlProduct() {{
                                setId(sc.getGoodId());
                                setIsSoldout(false);
                            }});
                            if (null != slProduct)
                            {
                                shoppingCart = new ShoppingCart();
                                shoppingCart.setGoodName(slProduct.getName());// 商品名称
                                shoppingCart.setCounts(sc.getCounts());// 加入购物车商品的数量
                                shoppingCart.setImageUrl(slProduct.getImageUrl()); // 商品图片
                                SlRepository repository = this.repositoryService.selectOne(new SlRepository() {{
                                    setSpecificationId(sc.getSpecificationId());
                                    setProductId(sc.getGoodId());
                                }});
                                shoppingCart.setPulse(repository.getPulse());// 了豆
                                shoppingCart.setSaleType(repository.getSaleType());// 销售类型前端根据销售类型去拼接两个字段 5钱6乐豆7钱+了豆
                                shoppingCart.setPrice(repository.getPrice());// 商品价格
                                /**
                                 * 查询标签名称 返回null的话 前台就显示失效
                                 */
                                shoppingCart.setTagName(this.specificationNameMapper.findSpecificationContentBySpecificationId(sc.getSpecificationId(), sc.getGoodId()));
                                list.add(shoppingCart);
                            }
                        }
                    }
                }
                pojo.setCarts(list);// 把查询好的list 加入pojo中
                object.put("data", pojo);
            }
        }
        return object;
    }
}
