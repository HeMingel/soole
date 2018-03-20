package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.cache.ShoppingCartCache;
import com.songpo.searched.domain.CMGoods;
import com.songpo.searched.domain.CMShoppingCart;
import com.songpo.searched.domain.ProductDto;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.CmProductMapper;
import com.songpo.searched.mapper.CmProductTypeMapper;
import com.songpo.searched.mapper.SpecificationNameMapper;
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
    private CmProductService cmProductService;

    @Autowired
    private MyShoppingCartService myShoppingCartService;

    @Autowired
    private ShoppingCartCache cache;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepositoryService productRepositoryService;

    @Autowired
    private SpecificationNameMapper specificationNameMapper;

    @Autowired
    private CmProductTypeMapper cmProductTypeMapper;
    @Autowired
    private CmProductMapper mapper;

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

        if (null != homeType && !StringUtils.isEmpty(homeType.getContent())) {
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
        if (null != teamworkType && !StringUtils.isEmpty(teamworkType.getContent())) {
            PageInfo<SlProduct> products = this.cmProductService.selectByActionId(teamworkType.getContent(), 1, 10);
            data.put("teamworkProducts", products);
        }

        // 获取预售商品列表
        SlSystemConfig preSaleType = this.systemConfigService.selectOne(new SlSystemConfig() {{
            setName("PRE_SALE_PRODUCT");
        }});
        if (null != preSaleType && !StringUtils.isEmpty(preSaleType.getContent())) {
            PageInfo<SlProduct> products = this.cmProductService.selectByActionId(preSaleType.getContent(), 1, 10);
            data.put("preSaleProducts", products);
        }

        return data;
    }

    /**
     * 获取用户端分类数据
     *
     * @return
     */
    public JSONObject getClassificationData(String parentId,String goodsType, Integer screenType, Integer page, Integer size,String name) {
        JSONObject data = new JSONObject();

        // 获取所有一级商品分类列表
        List<SlProductType> productTypes = this.productTypeService.select(new SlProductType() {{
            setParentId(null);
        }});
        data.put("productTypes", productTypes);
        //通过商品分类parentId 查询二级分类

        List<Map<String, Object>> productCategoryDtos = this.cmProductTypeMapper.findCategoryByParentId(parentId);
        data.put("productTypes", productCategoryDtos);

        //筛选商品
        PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
        List<ProductDto> productDtos = this.mapper.screenGoods(goodsType, screenType,name);
        data.put("products", new PageInfo<>(productDtos));
        //banner图
        // 获取广告轮播图列表

        List<SlActionNavigation> bannerList = this.actionNavigationService.select(new SlActionNavigation() {{
            // 设置类型为 商品页
            setTypeId("1");
        }});
        data.put("banner", bannerList);
        //商品分类首页推荐商品
        List<ProductDto> recommendProducts = this.mapper.findRecommendProduct();
        data.put("recommendProducts",recommendProducts);

        return data;
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
                CMShoppingCart pojo = this.cache.get(uid);
                List<CMGoods> list = new ArrayList<>();
                CMGoods CMGoods = null;
                if (null != pojo)
                {
                    for (CMGoods sc : pojo.getCarts())
                    {
                        if (StringUtils.hasLength(sc.getGoodId()))
                        {
                            SlProduct slProduct = this.productService.selectOne(new SlProduct() {{
                                setId(sc.getGoodId());
                                setIsSoldout(false);
                            }});
                            if (null != slProduct)
                            {
                                CMGoods = new CMGoods();
                                CMGoods.setGoodName(slProduct.getName());// 商品名称
                                CMGoods.setCounts(sc.getCounts());// 加入购物车商品的数量
                                CMGoods.setImageUrl(slProduct.getImageUrl()); // 商品图片
                                SlProductRepository repository = this.productRepositoryService.selectOne(new SlProductRepository() {{
                                    setProductId(sc.getGoodId());
                                }});
                                CMGoods.setPulse(repository.getPulse());// 了豆
                                CMGoods.setSaleType(repository.getSaleType());// 销售类型前端根据销售类型去拼接两个字段 5钱6乐豆7钱+了豆
                                CMGoods.setPrice(repository.getPrice());// 商品价格
                                /**
                                 * 查询标签名称 返回null的话 前台就显示失效
                                 */
                                CMGoods.setTagName(this.specificationNameMapper.findSpecificationContentBySpecificationId(sc.getSpecificationId(), sc.getGoodId()));
                                list.add(CMGoods);
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
