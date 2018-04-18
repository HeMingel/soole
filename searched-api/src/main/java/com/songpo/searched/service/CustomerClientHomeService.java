package com.songpo.searched.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.cache.ShoppingCartCache;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.CMGoods;
import com.songpo.searched.domain.CMShoppingCart;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 刘松坡
 */
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

    @Autowired
    private SlHotKeywordsMapper slHotKeywordsMapper;

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

        // 获取首页视频信息
        List<Map<String, Object>> videoList = this.actionNavigationMapper.selectByConfigKey("CUSTOMER_CLIENT_HOME_VIDEO");
        data.put("videoInfo", videoList.get(0));

        // 获取热词
        List<SlHotKeywords> hotKeywordsList = this.slHotKeywordsMapper.selectAll();
        data.put("hotKeywords", hotKeywordsList);

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
        return data;
    }

    /**
     * 获取用户端购物车数据
     *
     * @return
     */
    public BusinessMessage getShoppingCartData(String uid) {
        BusinessMessage message = new BusinessMessage<>();
        if (StringUtils.hasLength(uid)) {
            SlUser user = this.userService.selectOne(new SlUser() {{
                setId(uid);
            }});
            if (null != user) {
                CMShoppingCart pojo = this.cache.get(user.getId());
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
                                    cmGoods.setSilver(repository.getSilver());// 了豆(银豆,目前只扣除银豆)
                                    cmGoods.setSaleType(Integer.parseInt(slProduct.getSalesModeId()));// 销售类型前端根据销售类型去拼接两个字段
                                    cmGoods.setPrice(repository.getPrice());// 商品价格
                                    cmGoods.setSpecificationName(repository.getProductDetailGroupName());// 查询组合规格名称
                                    cmGoods.setShopId(sc.getShopId());// 店铺id
                                    cmGoods.setShopName(sc.getShopName());// 店铺名称
                                    cmGoods.setRemainingqty(repository.getCount());// 商品剩余数量 返回0的话 前台就显示失效
                                    cmGoods.setSoldOut(slProduct.getSoldOut());// 商品是否下架 true:已下架前台就显示失效  false:未下架
                                    cmGoods.setRebatePulse(repository.getPlaceOrderReturnPulse());// 纯金钱商品返了豆数量
                                    cmGoods.setMyBeansCounts(user.getCoin() + user.getSilver()); // 我剩余豆子总和金豆加银豆
                                    list.add(cmGoods);
                                } else {
                                    message.setSuccess(true);
                                    list.add(new CMGoods());
                                }
                            }
                        }
                    }
                    pojo.setCarts(list);// 把查询好的list 加入pojo中
                    message.setMsg("查询成功");
                    message.setData(pojo);
                    message.setSuccess(true);
                } else {
                    message.setData(null);
                    message.setSuccess(true);
                    message.setMsg("查询状态为空");
                }
            }
        }
        return message;
    }
}
