package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.constant.ActivityConstant;
import com.songpo.searched.constant.SalesModeConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlActivityProduct;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
    private SlProductMapper slProductMapper;
    @Autowired
    private SlProductSaleModeOrderCountMapper slProductSaleModeOrderCountMapper;
    @Autowired
    private SlActivityProductMapper activityProductMapper;


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
     * 根据分类查询商品  +  商品筛选  + 根据商品名称
     *
     * @param goodsType  商品分类ID
     * @param screenType 筛选类型
     * @param page       商品当前页
     * @param size       每页容量
     * @return 商品列表
     */
    public BusinessMessage screenGoods(String goodsType, String name, Integer screenType, Integer saleMode, Integer page, Integer size) {
        log.debug("查询 商品分类Id:{},筛选条件:{},页数:{},条数:{},商品名称:{}", goodsType, screenType, page, size, name);
        BusinessMessage<PageInfo> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);

            if (goodsType != null || screenType != null || name != null || saleMode != null) {
                List<Map<String, Object>> list = this.mapper.screenGoods(goodsType, screenType, saleMode, name, ActivityConstant.NO_ACTIVITY);
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
     * @param goodsId    商品Id
     * @param activityId 活动Id
     * @return 商品详情列表
     */
    public BusinessMessage goodsDetail(String goodsId, String activityId) {
        JSONObject data = new JSONObject();
        log.debug("查询 商品Id:{},活动Id:{}", goodsId, activityId);
        BusinessMessage<Object> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            //查询商品列表信息
            Map<String, Object> goodsBaseInfo = this.mapper.goodsBaseInfo(goodsId, activityId);
            if (goodsBaseInfo != null) {
                data.put("goodsBaseInfo", goodsBaseInfo);
                //商品图片
                List<Map<String, Object>> mapImageUrls = this.mapper.goodsImageUrl(goodsId);
                data.put("productImages", mapImageUrls);
                //商品评论
                List<Map<String, Object>> mapComments = this.cmProductCommentMapper.goodsComments(goodsId, null);
                if (mapComments.size() > 0) {
                    Map goodsComment = mapComments.get(0);
                    //如果第一条数据有图 则查询图片 没有图 直接返回第一条数据
                    String status = "status";
                    //商品评论1好 2中 3差 4有图
                    int type = 4;
                    if (goodsComment.get(status).equals(type)) {
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
                } else {
                    data.put("productComments", mapComments);
                }

                //   查询拼团信息
                //step 1 : 判断该商品销售模式 是否为拼团商品
                //step 2 : 如果是拼团商品 则查询该商品的 在该活动下的拼团信息
                int df = Integer.parseInt(goodsBaseInfo.get("sales_mode_id").toString());
                if (Integer.parseInt(goodsBaseInfo.get("sales_mode_id").toString()) == SalesModeConstant.SALES_MODE_GROUP) {
                    //未拼成订单集合
                    List<Map<String, Object>> orderList = this.mapper.selectGroupOrder(activityId, goodsId);
                    if (orderList.size() > 0) {
                        //遍历订单集合 查询首发人
                        List<Object> groupList = new ArrayList<>();
                        for (int i = 0; i < orderList.size(); i++) {
                            Map<String, Object> groupMapper = new HashMap<>(16);
                            Map<String, Object> groupMaster = this.mapper.findGroupPeople(orderList.get(i).get("group_master").toString());
                            groupMapper.put("groupMaster", groupMaster);
                            groupMapper.put("order", orderList.get(i));
                            groupList.add(groupMapper);
                        }
                        data.put("groupList", groupList);
                    } else {
                        data.put("groupList", null);
                    }
                }
                businessMessage.setMsg("查询完毕");
                businessMessage.setSuccess(true);
                businessMessage.setData(data);
            }
        } catch (Exception e) {
            businessMessage.setMsg("查询异常");
            log.error("查询商品异常", e);
        }
        return businessMessage;
    }

    /**
     * 根据商品Id 查询商品规格
     *
     * @param id 商品ID
     * @return 商品规格
     */
    public BusinessMessage goodsNormalSpecification(String id, String activityId) {
        log.debug("商品规格详情,商品id:{},活动Id", id, activityId);
        BusinessMessage<Object> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            //商品活动
            List<Map<String, Object>> goodsActivityList = this.mapper.goodsActivityList(id, activityId);
            List<Object> goodsRepositoryList = new ArrayList<>();
            if (goodsActivityList.size() > 0) {
                for (int i = 0; i < goodsActivityList.size(); i++) {
                    Map<String, Object> goodsRepository = this.mapper.goodsRepository(goodsActivityList.get(i).get("product_repository_id").toString());
                    goodsRepositoryList.add(goodsRepository);
                }
                businessMessage.setSuccess(true);
                businessMessage.setData(goodsRepositoryList);
                businessMessage.setMsg("查询成功");
            } else {
                businessMessage.setSuccess(true);
                businessMessage.setMsg("查询无数据");
            }
        } catch (Exception e) {
            log.error("查询商品规格异常", e);
            businessMessage.setMsg("查询异常");
        }
        return businessMessage;
    }

    /**
     * 热门推荐商品 四个 全部是普通商品
     * step.1 后台推送的情况下显示后台推送,不足是系统自推补足4个
     * step2 系统自推根据商品分类查询 销量最高的4个
     *
     * @param id 商品Id
     * @return 热门商品列表
     */
    public BusinessMessage hotGoods(String id) {
        BusinessMessage<Object> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            //查询后台推送推荐商品
            List<Map<String, Object>> backStageGoods = this.mapper.backStageGoods(id, ActivityConstant.NO_ACTIVITY);
            int goodsSize = 4;
            if (backStageGoods != null && backStageGoods.size() < goodsSize) {
                //查询该商品商品类别
                Map<String, Object> systemGoodsType = this.mapper.systemGoodsType(id);
                // 根据商品类别查销量最好的四个普通商品
                List<Map<String, Object>> systemGoods = this.mapper.systemGoods(systemGoodsType.get("product_type_id").toString(), ActivityConstant.NO_ACTIVITY);
                if (systemGoods != null && systemGoods.size() >= goodsSize - backStageGoods.size()) {
                    for (int i = 0; i < goodsSize - backStageGoods.size(); i++) {
                        backStageGoods.add(systemGoods.get(i));
                    }
                } else {
                    for (int i = 0; i < systemGoods.size(); i++) {
                        backStageGoods.add(systemGoods.get(i));
                    }
                }
                businessMessage.setMsg("查询成功");
                businessMessage.setData(backStageGoods);
                businessMessage.setSuccess(true);
            } else if (backStageGoods.size() == goodsSize) {
                businessMessage.setMsg("查询成功");
                businessMessage.setData(backStageGoods);
                businessMessage.setSuccess(true);
            } else {
                Map<String, Object> systemGoodsType = this.mapper.systemGoodsType(id);
                List<Map<String, Object>> systemGoods = this.mapper.systemGoods(systemGoodsType.get("product_type_id").toString(), ActivityConstant.NO_ACTIVITY);
                if (systemGoods != null) {
                    businessMessage.setMsg("查询成功");
                    businessMessage.setData(systemGoods);
                    businessMessage.setSuccess(true);
                }
            }
        } catch (Exception e) {
            log.error("查询热门商品推荐异常", e);
            businessMessage.setMsg("查询热门商品推荐失败");
        }
        return businessMessage;
    }

    /**
     * 根据名称查询店铺商品
     *
     * @param shopId    店铺Id
     * @param goodsName 商品名称
     * @return 商品列表
     */
    public BusinessMessage shopGoods(String shopId, String goodsName) {
        BusinessMessage<Object> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            Example example = new Example(SlProduct.class);
            example.createCriteria().andEqualTo("shopId", shopId).andLike("name", "%" + goodsName + "%");
            List<SlProduct> slProductList = this.slProductMapper.selectByExample(example);

            if (slProductList.size() > 0) {
                List<Object> goodsList = new ArrayList<>();
                for (int i = 0; i < slProductList.size(); i++) {
                    Map<String, Object> activityProduct = new HashMap<>();
                    Example apExample = new Example(SlActivityProduct.class);
                    apExample.createCriteria().andEqualTo("productId", slProductList.get(i).getId()).andEqualTo("enabled", 1);
                    List<SlActivityProduct> activityProductList = this.activityProductMapper.selectByExample(apExample);
                    activityProduct.put("activityProduct", activityProductList);
                    activityProduct.put("goodsType", slProductList.get(i).getSalesModeId());
                    goodsList.add(activityProduct);
                }
                businessMessage.setSuccess(true);
                businessMessage.setMsg("查询成功");
                businessMessage.setData(goodsList);
            } else {
                businessMessage.setMsg("查询无数据");
                businessMessage.setSuccess(true);
            }
        } catch (Exception e) {
            log.error("查询店铺商品异常", e);
            businessMessage.setMsg("查询店铺商品异常");
        }
        return businessMessage;
    }

    /**
     * 处理秒杀商品限时结束事件
     */
    @RabbitListener(queues = "queue_com.songpo.seached:product:time-limit")
    public void processProductUndercarriage(String slActivityProductId) {
        log.debug("秒杀商品限时结束，标识：{}", slActivityProductId);
        activityProductMapper.updateByPrimaryKeySelective(new SlActivityProduct() {{
            setId(slActivityProductId);
            setEnabled(true);
        }});
    }

}
