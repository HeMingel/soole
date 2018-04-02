package com.songpo.searched.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.cache.ShoppingCartCache;
import com.songpo.searched.constant.SalesModeConstant;
import com.songpo.searched.domain.CMGoods;
import com.songpo.searched.domain.CMShoppingCart;
import com.songpo.searched.domain.CmProduct;
import com.songpo.searched.entity.SlActionNavigation;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.entity.SlProductRepository;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 刘松坡
 */
@Slf4j
@Service
public class CustomerClientHomeService {

    @Autowired
    private CmProductTypeService productTypeService;

    @Autowired
    private ActionNavigationService actionNavigationService;

    @Autowired
    private ActionNavigationTypeService actionNavigationTypeService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartCache cache;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepositoryService productRepositoryService;

    @Autowired
    private CmOrderMapper specificationNameMapper;

    @Autowired
    private CmActionNavigationMapper actionNavigationMapper;

    @Autowired
    private CmProductTypeMapper cmProductTypeMapper;

    @Autowired
    private CmProductMapper cmProductMapper;

    @Autowired
    private SlProductMapper productMapper;

    /**
     * 获取首页所有数据
     *
     * @return
     */
    public JSONObject getHomeData() {
        JSONObject data = new JSONObject();

        // 获取所有一级商品分类列表
        List<Map<String, Object>> productTypes = this.productTypeService.findAll(null);
        data.put("productTypes", productTypes);

        // 获取广告轮播图列表
        List<Map<String, Object>> bannerList = this.actionNavigationMapper.selectByConfigKey("CUSTOMER_CLIENT_HOME_BANNER");
        data.put("banners", bannerList);

        // 获取入口列表
        List<Map<String, Object>> gatewayList = this.actionNavigationMapper.selectByConfigKey("CUSTOMER_CLIENT_HOME_SALES_MODE");
        data.put("gateways", gatewayList);

        // 获取活动列表
        List<Map<String, Object>> actionList = this.actionNavigationMapper.selectByConfigKey("CUSTOMER_CLIENT_HOME_ACTIVITY");
        data.put("actions", actionList);

        // 获取推荐商品列表
        List<SlProduct> productList = this.productMapper.select(new SlProduct() {{
            setRecommend(1);
        }});
        data.put("products", productList);

        return data;
    }

    /**
     * 获取用户端分类数据
     *
     * @return
     */
    public JSONObject getClassificationData() {
        JSONObject data = new JSONObject();

        // 获取所有一级商品分类列表
        List<Map<String, Object>> productTypes = this.productTypeService.findAll(null);
        JSONArray productTypeJsonArray = new JSONArray();
        if (null != productTypes) {
            productTypes.forEach(types -> productTypeJsonArray.add(new JSONObject() {{
                put("productType", types);

                // 查询子分类
                put("subTypes", productTypeService.findAll(types.get("id").toString()));
            }}));
        }
        data.put("productTypes", productTypeJsonArray);
        //banner图
        // 获取广告轮播图列表
        List<SlActionNavigation> bannerList = this.actionNavigationService.select(new SlActionNavigation() {{
            // 设置类型为 商品页
            setTypeId("1");
        }});
        data.put("banner", bannerList);
        //商品分类首页推荐商品
        List<Map<String,Object>> recommendProducts = this.cmProductMapper.findRecommendProduct();
        data.put("recommendProducts", recommendProducts);

        return data;
    }

    /**
     * 获取用户端购物车数据
     *
     * @return
     */
    public JSONObject getShoppingCartData(String uid) {
        JSONObject object = new JSONObject();
        if (StringUtils.hasLength(uid)) {
            SlUser user = this.userService.selectOne(new SlUser() {{
                setId(uid);
            }});
            if (null != user) {
                CMShoppingCart pojo = this.cache.get(uid);
                List<CMGoods> list = new ArrayList<>();
                CMGoods cmGoods = null;
                if (null != pojo) {
                    for (CMGoods sc : pojo.getCarts()) {
                        if (StringUtils.hasLength(sc.getGoodId())) {
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
//                                cmGoods.setPulse(repository.getPulse());// 了豆
                                // TODO 注释报错的位置
//                                cmGoods.setSaleType(slProduct.getSaleType());// 销售类型前端根据销售类型去拼接两个字段 5钱6乐豆7钱+了豆
                                cmGoods.setPrice(repository.getPrice());// 商品价格
                                cmGoods.setSpecificationName(repository.getProductDetailGroupName());// 查询组合规格名称
                                cmGoods.setShopId(sc.getShopId());// 店铺id
                                cmGoods.setShopName(sc.getShopName());// 店铺名称
                                cmGoods.setRemainingqty(repository.getCount());// 商品剩余数量 返回0的话 前台就显示失效
                                list.add(cmGoods);
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
