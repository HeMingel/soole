package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.CmProduct;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.mapper.CmProductMapper;
import com.songpo.searched.mapper.SlProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 自定义商品服务类
 * <p>
 * 主要提供处理跟商品相关的服务
 */
@Slf4j
@Service
public class CmProductService {

    @Autowired
    private CmProductMapper mapper;

    @Autowired
    private SlProductMapper slProductMapper;

    /**
     * 根据活动唯一标识符分页查询商品列表
     *
     * @param name 商品名称
     * @param salesModeId 销售模式唯一标识符
     * @param pageNum 页码
     * @param pageSize 容量
     * @return 商品分页列表
     */
    public PageInfo<Map<String, Object>> selectBySalesMode(String name, String salesModeId, Integer pageNum, Integer pageSize) {
        log.debug("查询商品列表，名称：{}，销售模式唯一标识符：{}，页码：{}，容量：{}", name, salesModeId, pageNum, pageSize);
        if (null == pageNum || pageNum <= 1) {
            pageNum = 1;
        }

        if (null == pageSize || pageSize <= 1) {
            pageSize = 10;
        }

        // 设置分页参数
        PageHelper.startPage(pageNum, pageSize);

        // 执行查询
        List<Map<String, Object>> list = this.mapper.selectBySalesMode(name, salesModeId);

        return new PageInfo<>(list);
    }

    /**
     * 拼团商品列表
     *
     * @param actionId 活动唯一标识符
     * @param pageNum  页码
     * @param pageSize 容量
     * @return 商品分页列表
     */
    public PageInfo<SlProduct> selectByAction(String actionId, Integer pageNum, Integer pageSize) {
        log.debug("根据活动唯一标识符查询商品列表，活动唯一标识符：{}，页码：{}，容量：{}", actionId, pageNum, pageSize);
        if (null == pageNum || pageNum <= 1) {
            pageNum = 1;
        }

        if (null == pageSize || pageSize <= 1) {
            pageSize = 10;
        }

        // 设置分页参数
        PageHelper.startPage(pageNum, pageSize);

        // 执行查询
        List<SlProduct> list = this.mapper.selectByAction(actionId);

        return new PageInfo<>(list);
    }

    /**
     * 商品分类首页推荐商品
     * 规则(按照时间排序 最新的6个)
     *
     * @return
     */
    public BusinessMessage recommendProduct() {
        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try {
            List<CmProduct> cmProducts = this.mapper.findRecommendProduct();
            if (cmProducts.size() > 0) {
                businessMessage.setMsg("查询成功");
                businessMessage.setSuccess(true);
                businessMessage.setData(cmProducts);
            } else {
                businessMessage.setMsg("查询无数据");
                businessMessage.setSuccess(true);
            }
        } catch (Exception e) {
            businessMessage.setMsg("查询失败");
            log.error("查询商品失败", e);
        }

        return businessMessage;
    }

    /**
     * 根据分类查询商品  +  商品筛选  + 根据商品名称
     *
     * @param goodsType  商品分类ID
     * @param screenType 筛选类型
     * @param page
     * @param size
     * @return
     */
    public BusinessMessage screenGoods(String goodsType, Integer screenType, Integer page, Integer size,String name) {
        log.debug("查询 商品分类Id:{},筛选条件:{},页数:{},条数:{},商品名称:{}",goodsType,screenType,page,size,name);
        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try {
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
                if(goodsType != null || screenType != null || name != null){
                    List<Map<String,Object>> list = this.mapper.screenGoods(goodsType, screenType, name);
                    if (list.size() > 0) {
                        businessMessage.setMsg("查询成功");
                        businessMessage.setSuccess(true);
                        businessMessage.setData(new PageInfo<>(list));
                    } else {
                        businessMessage.setMsg("查询无数据!");
                        businessMessage.setSuccess(true);
                    }
                }else{
                    businessMessage.setMsg("请传入正确参数");
                }

        } catch (Exception e) {
            businessMessage.setMsg("查询异常");
            log.error("查询商品异常", e);
        }
        return businessMessage;
    }

    /**
     * 根据商品Id 查询商品详情
     *
     * @param goodsId
     * @return
     */
    public BusinessMessage goodsDetail(String goodsId) {
        JSONObject data = new JSONObject();
        log.debug("查询 商品Id{}",goodsId);
        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try {
            //商品基础信息
             Map map = this.mapper.goodsBaseInfo(goodsId);
             data.put("productBase",map);
            if (map.isEmpty()) {
                businessMessage.setMsg("未查到商品相关信息");
                businessMessage.setSuccess(true);
            } else {
                List<Map<String,Object>>  mapImageUrls = this.mapper.goodsImageUrl(goodsId);
                data.put("productImages",mapImageUrls);
                List<Map<String,Object>> mapComments = this.mapper.goodsComments(goodsId);
            }

        } catch (Exception e) {
            businessMessage.setMsg("查询异常");
            log.error("查询商品异常", e);
        }
        return businessMessage;
    }

}
