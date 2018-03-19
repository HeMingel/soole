package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private CmProductService productService;

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
            List<SlProduct> products = this.productService.selectByActionId(teamworkType.getContent());
            data.put("teamworkProducts", products);
        }

        // 获取预售商品列表
        SlSystemConfig preSaleType = this.systemConfigService.selectOne(new SlSystemConfig() {{
            setName("PRE_SALE_PRODUCT");
        }});
        if (null != preSaleType && !StringUtils.isEmpty(preSaleType.getContent())) {
            List<SlProduct> products = this.productService.selectByActionId(teamworkType.getContent());
            data.put("preSaleProducts", products);
        }

        return data;
    }
}
