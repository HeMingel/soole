package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.CmProduct;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.mapper.CmProductCommentMapper;
import com.songpo.searched.mapper.CmProductMapper;
import com.songpo.searched.mapper.SlActivityProductMapper;
import com.songpo.searched.mapper.SlProductSaleModeOrderCountMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    private CmProductCommentMapper cmProductCommentMapper;
    @Autowired
    private SlActivityProductMapper slActivityProductMapper;
    @Autowired
    private SlProductSaleModeOrderCountMapper slProductSaleModeOrderCountMapper;


    /**
     * 根据活动唯一标识符分页查询商品列表
     *
     * @param name         商品名称
     * @param salesModeId  销售模式唯一标识符
     * @param longitudeMin 最小经度
     * @param longitudeMax 最大经度
     * @param latitudeMin  最小维度
     * @param latitudeMax  最大维度
     * @param sortByPrice  按商品价格排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param sortByRating 按店铺评分排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param priceMin     价格区间最小值，默认为空。如果只有最小值，则选择大于等于此价格
     * @param priceMax     价格区间最大值，默认为空。如果只有最大值，则选择小于等于此价格
     * @param pageNum      页码
     * @param pageSize     容量
     * @return 商品分页列表
     */
    public PageInfo<Map<String, Object>> selectBySalesMode(String name,
                                                           String salesModeId,
                                                           Double longitudeMin,
                                                           Double longitudeMax,
                                                           Double latitudeMin,
                                                           Double latitudeMax,
                                                           String sortByPrice,
                                                           String sortByRating,
                                                           Integer priceMin,
                                                           Integer priceMax,
                                                           Integer pageNum,
                                                           Integer pageSize) {
        if (null == pageNum || pageNum <= 1) {
            pageNum = 1;
        }

        if (null == pageSize || pageSize <= 1) {
            pageSize = 10;
        }

        // 排序规则字符串
        String[] orderStrArray = new String[]{"DESC", "desc", "ASC", "asc"};

        // 过滤价格排序规则中的非法字符
        if (!StringUtils.containsAny(sortByPrice, orderStrArray)) {
            sortByPrice = StringUtils.trimToEmpty(sortByPrice);
        }

        // 过滤评分排序规则中的非法字符
        if (!StringUtils.containsAny(sortByRating, orderStrArray)) {
            sortByRating = StringUtils.trimToEmpty(sortByRating);
        }

        // 设置分页参数
        PageHelper.startPage(pageNum, pageSize);

        // 执行查询
        List<Map<String, Object>> list = this.mapper.selectBySalesMode(name, salesModeId, longitudeMin, longitudeMax, latitudeMin, latitudeMax, sortByPrice, sortByRating, priceMin, priceMax);

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
    public BusinessMessage<List<CmProduct>> recommendProduct() {
        BusinessMessage<List<CmProduct>> businessMessage = new BusinessMessage<>();
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
     * @param page 商品当前页
     * @param size 每页容量
     * @return 商品列表
     */
    public BusinessMessage screenGoods(String goodsType, String name, Integer screenType, Integer saleMode, Integer page, Integer size) {
        log.debug("查询 商品分类Id:{},筛选条件:{},页数:{},条数:{},商品名称:{}", goodsType, screenType, page, size, name);
        BusinessMessage<PageInfo> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);

            if (goodsType != null || screenType != null || name != null) {
                List<Map<String, Object>> list = this.mapper.screenGoods(goodsType, screenType, saleMode, name);
                if (list.size() > 0) {
                    businessMessage.setMsg("查询成功");
                    businessMessage.setSuccess(true);
                    businessMessage.setData(new PageInfo<>(list));
                } else {
                    businessMessage.setMsg("查询无数据!");
                    businessMessage.setSuccess(true);
                }
            } else {
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
     * @param goodsId 商品Id
     * @return 商品详情列表
     */
    public BusinessMessage goodsDetail(String goodsId) {
        JSONObject data = new JSONObject();
        log.debug("查询 商品Id{}", goodsId);
        BusinessMessage<Object> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            //商品基础信息
            Map map = this.mapper.goodsBaseInfo(goodsId);
            data.put("productBase", map);
            if (map.isEmpty()) {
                businessMessage.setMsg("未查到商品相关信息");
                businessMessage.setSuccess(true);
            } else {
                //商品图片
                List<Map<String, Object>> mapImageUrls = this.mapper.goodsImageUrl(goodsId);
                data.put("productImages", mapImageUrls);
                //商品评论
                List<Map<String, Object>> mapComments = this.cmProductCommentMapper.goodsComments(goodsId, null);
                Map goodsComment = mapComments.get(0);
                //如果第一条数据有图 则查询图片 没有图 直接返回第一条数据
                if (goodsComment.get("status").equals(4)) {
                    //查询评论图片
                    mapComments.get(0).get("id").toString();
                    List<Map<String, Object>> commentImages = this.cmProductCommentMapper.commentImages(goodsComment.get("id").toString());
                    List<Object> list = new ArrayList<>();
                    list.add(commentImages);
                    list.add(goodsComment);
                    data.put("productComments", list);
                } else {
                    data.put("productComments", goodsComment);
                }

            }
            businessMessage.setSuccess(true);
            businessMessage.setData(data);
            businessMessage.setMsg("查询成功");
        } catch (Exception e) {
            businessMessage.setMsg("查询异常");
            log.error("查询商品异常", e);
        }
        return businessMessage;
    }

    /**
     * 根据商品Id 查询商品规格
     *
     * @param id  商品ID
     * @return 商品规格
     */
    public BusinessMessage goodsSpecification(String id) {
        log.debug("商品规格详情,商品id:{}", id);
        BusinessMessage<Object> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            //搜索商品规格Detail
            List<Map<String, Object>> specificationDetailMap = this.mapper.goodsSpecificationDetail(id);
            if (specificationDetailMap.size() > 0) {
                //查询商品规格名称
                List<Map<String, Object>> specificationMap = this.mapper.goodsSpecification(id);
                List<Object> list = new ArrayList<>();
                for (int i = 0; i < specificationMap.size(); i++) {
                    Map<String,Object> map = new HashMap<>(16);
                    map.put("specification", specificationMap.get(i));
                    for (int j = 0; j < specificationDetailMap.size(); j++) {
                        if (specificationDetailMap.get(j).get("specification_id").equals(specificationMap.get(i).get("specification_id"))) {
                            map.put("specificationDetail", specificationDetailMap.get(j));
                        }
                    }
                    list.add(map);
                }
                //step 1查询商品对应的库存
                List<Map<String, Object>> goodsRepositoryList = this.mapper.goodsRepository(id);
                //step 2根据商品分组查询对应的规格信息
                if (goodsRepositoryList.size() > 0) {
                    List<Object> lists = new ArrayList<>();
                    for (int i = 0; i < goodsRepositoryList.size(); i++) {
                        Map<String,Object> map = new HashMap<>(16);
                        List<Map<String, Object>> goodsRepositorySpecification = this.mapper.goodsRepositorySpecification(goodsRepositoryList.get(i).get("product_detail_group_serial_number").toString());
                        map.put("goodsRepositorySpecification", goodsRepositorySpecification);
                        map.put("goodsRepository", goodsRepositoryList.get(i));
                        lists.add(map);
                    }
                    list.add(lists);
                }

                businessMessage.setSuccess(true);
                businessMessage.setMsg("查询成功");
                businessMessage.setData(list);
            } else {
                businessMessage.setMsg("该商品无规格");
            }
        } catch (Exception e) {
            log.error("查询商品规格异常", e);
            businessMessage.setMsg("查询异常");
        }
        return businessMessage;
    }

    /**
     * 热门推荐商品 四个
     *
     * @param id 商品Id
     * @return
     */
    public BusinessMessage hotGoods(String id) {
        BusinessMessage<List<Map<String, Object>>> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            Map<String, Object> goodsBaseInfo = this.mapper.goodsBaseInfo(id);
            //根据商品销售模式查询出去本商品的四个商品
            List<Map<String, Object>> hotGoods = this.mapper.hotGoods(goodsBaseInfo.get("productId").toString(), goodsBaseInfo.get("sales_mode_id").toString());
            if (hotGoods.size() > 0) {
                businessMessage.setMsg("查询成功");
                businessMessage.setData(hotGoods);
                businessMessage.setSuccess(true);
            } else {
                businessMessage.setMsg("查询无数据");
                businessMessage.setSuccess(false);
            }
        } catch (Exception e) {
            businessMessage.setMsg("查询热门商品推荐失败");
        }
        return businessMessage;
    }
}
