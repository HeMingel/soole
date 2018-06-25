package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.constant.ActivityConstant;
import com.songpo.searched.constant.SalesModeConstant;
import com.songpo.searched.constant.VirtualUserConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlActivityProduct;
import com.songpo.searched.entity.SlMyCollection;
import com.songpo.searched.entity.SlPresellReturnedRecord;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.mapper.*;
import com.songpo.searched.typehandler.ProductEnum;
import com.songpo.searched.util.OrderNumGeneration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * 自定义商品服务类
 * <p>
 * 主要提供处理跟商品相关的服务
 */
@Service
public class CmProductService {

    public static final Logger log = LoggerFactory.getLogger(CmProductService.class);

    @Autowired
    private CmProductMapper mapper;
    @Autowired
    private CmProductCommentMapper cmProductCommentMapper;
    @Autowired
    private SlProductMapper slProductMapper;
    @Autowired
    private CmProductMapper cmProductMapper;
    @Autowired
    private SlProductSaleModeOrderCountMapper slProductSaleModeOrderCountMapper;
    @Autowired
    private SlActivityProductMapper activityProductMapper;
    @Autowired
    private SlPresellReturnedRecordMapper slPresellReturnedRecordMapper;
    @Autowired
    private SlProductTypeMapper slProductTypeMapper;
    @Autowired
    private SlMyCollectionMapper slMyCollectionMapper;

    /**
     * 根据活动唯一标识符分页查询商品列表
     *
     * @param name            商品名称
     * @param salesModeId     销售模式唯一标识符
     * @param activityId      活动Id
     * @param goodsTypeId     商品分类Id
     * @param goodsTypeStatus 商品分类标识 1:一级分类 2:二级分类
     * @param longitudeMin    最小经度
     * @param longitudeMax    最大经度
     * @param latitudeMin     最小维度
     * @param latitudeMax     最大维度
     * @param sortByPrice     按商品价格排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param sortByRating    按店铺评分排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param priceMin        价格区间最小值，默认为空。如果只有最小值，则选择大于等于此价格
     * @param priceMax        价格区间最大值，默认为空。如果只有最大值，则选择小于等于此价格
     * @param pageNum         页码
     * @param pageSize        容量
     * @param sortBySale      根据销售数量排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param synthesize      综合排序 (销量+评论数量)
     * @return 商品分页列表
     */
    public PageInfo selectBySalesMode(String name,
                                      String salesModeId,
                                      String activityId,
                                      String goodsTypeId,
                                      Integer goodsTypeStatus,
                                      Double longitudeMin,
                                      Double longitudeMax,
                                      Double latitudeMin,
                                      Double latitudeMax,
                                      String sortByPrice,
                                      String sortByRating,
                                      Integer priceMin,
                                      Integer priceMax,
                                      Integer pageNum,
                                      Integer pageSize,
                                      String sortBySale,
                                      String addressNow,
                                      Double longitudeNow,
                                      Double latitudeNow,
                                      String synthesize) {
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
        //过滤销售数量排序中的非法字符
        if (!StringUtils.containsAny(sortBySale, orderStrArray)) {
            sortBySale = StringUtils.trimToEmpty(sortBySale);
        }

        // 设置分页参数
        PageHelper.startPage(pageNum, pageSize);
        // 执行查询
        List<Map<String, Object>> list = this.mapper.selectBySalesMode(name, salesModeId, activityId, goodsTypeId, goodsTypeStatus, longitudeMin, longitudeMax, latitudeMin, latitudeMax, sortByPrice, sortByRating, priceMin, priceMax, sortBySale, addressNow, longitudeNow, latitudeNow, synthesize);
        if (salesModeId != null && Integer.parseInt(salesModeId) == SalesModeConstant.SALES_MODE_GROUP) {
            PageInfo<Map<String, Object>> map = new PageInfo<>(list);

            for (int i = 0; i < map.getList().size(); i++) {
                Map<String, Object> avatarMap = new HashMap<>();
                List<Map<String, Object>> avatarList = this.mapper.selectGroupAvatar(map.getList().get(i).get("product_id").toString());
                if (avatarList.size() > 0) {
                    map.getList().get(i).put("avatarList", avatarList);
                }
            }
            return new PageInfo<>(list);
        } else {
            return new PageInfo<>(list);
        }
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
        if (!actionId.equals(ActivityConstant.RECOMMEND_AWARDS_ACTIVITY)) {
            // 执行查询
            List<SlProduct> list = this.mapper.selectByAction(actionId);
            return new PageInfo<>(list);
        } else {
            List<SlProduct> list = this.mapper.selectByAction(actionId);
            return new PageInfo<>(list);
        }


        //return new PageInfo<>(list);
    }

    /**
     * 根据分类查询商品  +  商品筛选  + 根据商品名称
     *
     * @param goodsTypeId 商品分类ID sl_product.product_type_id
     * @param name        商品名称
     * @param screenType  筛选类型
     * @param page        商品当前页
     * @param size        每页容量
     * @return 商品列表
     */
    public BusinessMessage screenGoods(String goodsTypeId, String name, Integer screenType, Integer saleMode, Integer page, Integer size) {
        log.debug("查询 商品分类Id:{},筛选条件:{},页数:{},条数:{},商品名称:{}", goodsTypeId, screenType, page, size, name);
        BusinessMessage<PageInfo> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);

            if (goodsTypeId != null || screenType != null || name != null || saleMode != null) {
                List<Map<String, Object>> list = this.mapper.screenGoods(goodsTypeId, screenType, saleMode, name, ActivityConstant.NO_ACTIVITY);

                if (list.size() > 0) {
                    //如果是拼团商品
                    if (saleMode != null && saleMode == SalesModeConstant.SALES_MODE_GROUP) {
                        List<Object> goodsList = new ArrayList<>();
                        for (Map<String, Object> map : list) {

                            //关联order_detail 表的 product_id
                            Map<String, Object> avatarMap = new HashMap<>();

                            List<Map<String, Object>> avatarList = this.mapper.selectGroupAvatar(map.get("goods_id").toString());
                            map.put("avatarList", avatarList);
                            goodsList.add(avatarMap);
                        }
                        businessMessage.setMsg("查询成功");
                        businessMessage.setSuccess(true);
                        businessMessage.setData(new PageInfo<>(goodsList));
                    } else {
                        businessMessage.setMsg("查询成功");
                        businessMessage.setSuccess(true);
                        businessMessage.setData(new PageInfo<>(list));
                    }

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
     * 根据商品名称查询商品
     *
     * @param goodsName 商品名称
     * @param page      页码
     * @param size      容量
     * @return 商品列表
     */
    public BusinessMessage selectByName(String goodsName, Integer page, Integer size) {
        log.debug("查询商品 商品名称,{}", goodsName);
        BusinessMessage<PageInfo> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
            String name = '%' + goodsName + '%';
            List<Map<String, Object>> list = this.mapper.selectByName(name);
            if (list.size() > 0) {
                businessMessage.setMsg("查询成功");
                businessMessage.setSuccess(true);
                businessMessage.setData(new PageInfo<>(list));
            } else {
                businessMessage.setMsg("查询无数据");
                businessMessage.setSuccess(true);
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
    public BusinessMessage goodsDetail(String goodsId, String activityId, String userId) {
        JSONObject data = new JSONObject();
        log.debug("查询 商品Id:{},活动Id:{}", goodsId, activityId);
        BusinessMessage<Object> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            //查询商品列表信息
            Map<String, Object> goodsBaseInfo = this.mapper.goodsBaseInfo(goodsId, activityId);
            if (goodsBaseInfo != null) {
                Integer salesModeId =  Integer.parseInt(goodsBaseInfo.get("sales_mode_id").toString());
                data.put("goodsBaseInfo", goodsBaseInfo);
                //商品图片
                List<Map<String, Object>> mapImageUrls = this.mapper.goodsImageUrl(goodsId);
                data.put("productImages", mapImageUrls);
                //商品评论
                List<Map<String, Object>> mapComments = this.cmProductCommentMapper.goodsComments(goodsId, 1);
                if (mapComments.size() > 0) {
                    //查询商品评论数量
                    List<Map<String, Object>> commentsNum = this.cmProductCommentMapper.goodsCommentsNum(goodsId);
                    //查询商品评论图片 简版
                    List<Map<String, Object>> commentsimage = this.cmProductCommentMapper.commentImages(goodsId);
                    data.put("commentsImage", commentsimage);
                    data.put("productCommentsNum", commentsNum);
                    data.put("productComments", mapComments);
                }
                //   查询拼团信息
                //step 1 : 判断该商品销售模式 是否为拼团商品
                //step 2 : 如果是拼团商品 则查询该商品的 在该活动下的拼团信息
                if (salesModeId == SalesModeConstant.SALES_MODE_GROUP) {
                    //2018.6.11 为每个拼团商品添加一条虚拟拼团信息
                    //List<Map<String, Object>> virtualGroupOne = new ArrayList<>();
                    Map<String, Object> virtualMap =  new HashMap<String,Object>(16);
                    Map<String, Object> orderMap =  new HashMap<String,Object>(16);
                    orderMap .put("need_people",2);
                    orderMap .put("already_people",1);
                    orderMap .put("group_master", "ed12f9494a2c4627b2fe81a9a14cda5b");
                    orderMap .put("virtual_open",2);
                    orderMap .put("order_num",OrderNumGeneration.getOrderIdByUUId());
                    Map<String, Object> masterMap =  new HashMap<String,Object>(16);
                    VirtualUserConstant vuc = new VirtualUserConstant();
                    String nickName =
                            vuc.RANDOM_NAME[(int)(Math.random()*vuc.RANDOM_NAME.length)]
                                    +vuc.LASTNAME[new Random().nextInt(2)];
                    masterMap.put("nick_name",nickName);
                    String url = vuc.URLAVATAR+(int)(Math.random()*vuc.IMAGENUM)+".png";
                    masterMap.put("avatar",url);
                    virtualMap.put("groupMaster",masterMap);
                    virtualMap.put("order",orderMap);
                   // virtualGroupOne.add(virtualMap);
                    //data.put("virtualGroupOne",virtualGroupOne);
                    //未拼成订单集合
                    List<Map<String, Object>> orderList = this.mapper.selectGroupOrder(activityId, goodsId);

                    if (orderList != null && orderList.size() > 0) {
                        List<Map<String, Object>> removeOrderList = new ArrayList<>();
                        for (Map<String, Object> map : orderList) {
                            int already_people=Integer.valueOf(map.get("already_people").toString());
                            int need_people=(int)map.get("need_people");
                            /** 去除已经拼单完成的数据 **/
                            if (map.get("already_people").equals(map.get("need_people"))) {
                                removeOrderList.add(map);
                            }else if(already_people<need_people){
                                //如果拼团人数还不够,增加虚拟批团
                                List<Map<String, Object>> virtualSpellGroup = this.mapper.selectVirtualSpellGroup(activityId, goodsId);
                                if(virtualSpellGroup!=null && virtualSpellGroup.size()>0){
                                    data.put("virtualSpellGroup",virtualSpellGroup);
                                }
                            }
                        }
                        if (removeOrderList.size() > 0) {
                            orderList.removeAll(removeOrderList);
                        }
                    }
                    if (orderList != null && orderList.size() > 0) {
                        //遍历订单集合 查询首发人
                        List<Object> groupList = new ArrayList<>();
                        groupList.add(virtualMap);
                        for (int i = 0; i < orderList.size(); i++) {
                            Map<String, Object> groupMapper = new HashMap<>(16);
                            String groupId = orderList.get(i).get("group_master").toString();
                            Map<String, Object> groupMaster = this.mapper.findGroupPeople(groupId);
                            groupMapper.put("groupMaster", groupMaster);
                            groupMapper.put("order", orderList.get(i));
                            groupList.add(groupMapper);
                        }
                        data.put("groupList", groupList);
                    } else {
                        List<Object> list = new ArrayList<>();
                        Map<String, String> groupMaster = new HashMap();
                        list.add(groupMaster);
                        Map<String, String> order = new HashMap();
                        list.add(order);
                        list.add(virtualMap);
                        data.put("groupList", list);
                    }
                } else {
                    //添加商品虚拟购买信息
                    List<Map<String, Object>> buyInfoList = new ArrayList<>();
                    VirtualUserConstant vuc = new VirtualUserConstant();
                    int len =new Random().nextInt(10)+25;
                    for(int i = 0 ; i < len ; i++){
                        Map<String, Object> virtualBuyInfo = new HashMap<>(16);
                        String nickName =
                                vuc.RANDOM_NAME[(int)(Math.random()*(vuc.RANDOM_NAME.length))]+vuc.LASTNAME[new Random().nextInt(2)];
                        virtualBuyInfo.put("nick_name",nickName);
                        String url = vuc.URLAVATAR+(int)(Math.random()*vuc.IMAGENUM)+".png";
                        virtualBuyInfo.put("avatar",url);
                        buyInfoList.add(virtualBuyInfo);
                    }
                    data.put("virtualBuyInfoList", buyInfoList);
                    //非拼团商品
                    Map<String, Object> map = new HashMap<>(16);
                    List<Map<String, Object>> alreadyOrderMap = this.mapper.alreadyOrder(activityId, goodsId);
                    if (alreadyOrderMap.size() > 0) {
                        for (int i = 0; i < alreadyOrderMap.size(); i++) {
                            if (alreadyOrderMap.get(i).containsKey("user_id")) {
                                Map<String, Object> userInfo = this.mapper.findGroupPeople(alreadyOrderMap.get(i).get("user_id").toString());
                                if (userInfo != null) {
                                    //放入user信息,头像昵称
                                    alreadyOrderMap.get(i).put("userInfo", userInfo);
                                }
                            } else {
                                Map<String, Object> userInfo = new HashMap<>();

                                alreadyOrderMap.get(i).put("userInfo", userInfo);
                            }
                        }
                    }

                }

                if (userId != null) {
                    //如果用户id 不为空,查询 是否收藏了该商品
                    byte type = 2;
                    List<SlMyCollection> slMyCollections = this.slMyCollectionMapper.select(new SlMyCollection() {{
                        setUserId(userId);
                        setCollectionId(goodsId);
                        setType(type);
                    }});
                    if (slMyCollections.size() > 0) {
                        data.put("isCollection", true);
                    } else {
                        data.put("isCollection", false);
                    }
                }
                //查询商品规格
                //STEP1:商品活动
                List<Map<String, Object>> goodsActivityList = this.mapper.goodsActivityList(goodsId, activityId);
                List<Object> goodsRepositoryList = new ArrayList<>();
                if (goodsActivityList.size() > 0) {
                    for (int i = 0; i < goodsActivityList.size(); i++) {
                        Map<String, Object> goodsRepository = this.mapper.goodsRepository(goodsActivityList.get(i).get("product_repository_id").toString(), activityId);
                        goodsRepository.put("restrict_count", goodsActivityList.get(i).get("restrict_count"));
                        if (salesModeId == SalesModeConstant.SALES_MODE_PRESELL) {
                            goodsRepository.put("silver",0);


                        }
                        goodsRepositoryList.add(goodsRepository);
                    }
                    businessMessage.setData(goodsRepositoryList);
                    data.put("goodsRepositoryList", goodsRepositoryList);
                }

                //查询客服信息(店铺主人的 id username 昵称 头像)
                //根据商铺ID
                Map<String, String> customer = this.mapper.selectCustomerService(goodsBaseInfo.get("shop_id").toString());
                data.put("custmerService", customer);
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
     * 查询预售商品周期
     *
     * @param goodsId 商品ID
     * @return 预售商品周期表
     */
    public BusinessMessage selectGoodsCycle(String goodsId) {
        BusinessMessage<Object> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(true);
        try {
            // 1.预售模式2.消费返利模式
            String type = "1";
            Example example = new Example(SlPresellReturnedRecord.class);
            example.createCriteria().andEqualTo("productId", goodsId).andEqualTo("type", type);
            example.setOrderByClause("number_of_periods asc");
            List<SlPresellReturnedRecord> slPresellReturnedRecordList = this.slPresellReturnedRecordMapper.selectByExample(example);
            if (slPresellReturnedRecordList.size() > 0) {
                double totalMoney = 0;
                int totalDays = 0;
                for (SlPresellReturnedRecord sl : slPresellReturnedRecordList) {
                    totalMoney += sl.getReturnedMoney().doubleValue();
                    totalDays += sl.getReturnedNumber();
                }
                Map<String, Object> map = new HashMap<>();
                map.put("presellReturnedRecordList", slPresellReturnedRecordList);
                map.put("totalMoney", totalMoney);
                map.put("totalDays", totalDays);
                businessMessage.setSuccess(true);
                businessMessage.setData(map);
                businessMessage.setMsg("查询成功");
            }

        } catch (Exception e) {
            log.error("查询异常", e);
            businessMessage.setMsg("查询异常");
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
        int salesMode = 0;
        SlProduct slProduct = this.slProductMapper.selectByPrimaryKey(id);
        if (slProduct == null) {
            businessMessage.setMsg("查询无数据");
        } else {
            salesMode = Integer.parseInt(slProduct.getSalesModeId());
        }
        try {
            //商品活动
            List<Map<String, Object>> goodsActivityList = this.mapper.goodsActivityList(id, activityId);
            List<Object> goodsRepositoryList = new ArrayList<>();
            if (goodsActivityList.size() > 0) {
                for (int i = 0; i < goodsActivityList.size(); i++) {
                    Map<String, Object> goodsRepository = this.mapper.goodsRepository(goodsActivityList.get(i).get("product_repository_id").toString(), activityId);
                    goodsRepository.put("restrict_count", goodsActivityList.get(i).get("restrict_count"));
                    /**
                     * 预售模式由钱+豆购买 改成 纯钱购买模式
                     * 2018年6月14日11:58:51 heming
                     */
                    if (salesMode == SalesModeConstant.SALES_MODE_PRESELL) {
                        goodsRepository.put("silver",0);
                    }
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
     * 查询商品图文详情
     *
     * @param goodsId 商品Id
     * @return 图文详情
     */
    public BusinessMessage<String> selectGoodsText(String goodsId) {
        BusinessMessage<String> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            if (!"".equals(goodsId)) {
                SlProduct slProduct = this.slProductMapper.selectByPrimaryKey(goodsId);
                if (slProduct != null) {
                    businessMessage.setData(slProduct.getDetail());
                    businessMessage.setSuccess(true);
                } else {
                    businessMessage.setMsg("查询未找到");
                }
            } else {
                businessMessage.setMsg("商品Id为空");
            }
        } catch (Exception e) {
            log.error("查询图文详情异常", e);
            businessMessage.setMsg("查询图文详情异常" + e);
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
            //如果商品id 不是空 按照规则查  ,商品id 如果是空的话搜索所有的推荐商品
            if (!"".equals(id)) {
                //查询后台推送推荐商品
                List<Map<String, Object>> backStageGoods = this.mapper.backStageGoods(id, ActivityConstant.NO_ACTIVITY);
                int goodsSize = 4;
                if (backStageGoods.size() < goodsSize) {
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
            } else {
                // 获取推荐商品列表
                List<SlProduct> productList = this.slProductMapper.select(new SlProduct() {{
                    setRecommend(ProductEnum.PRODUCT_RECOMMEND_ENABLE.getValue());
                }});
                businessMessage.setData(productList);
                businessMessage.setSuccess(true);
            }
        } catch (Exception e) {
            log.error("查询热门商品推荐异常", e);
            businessMessage.setMsg("查询热门商品推荐失败");
        }
        return businessMessage;
    }


    /**
     * 处理秒杀商品限时结束事件
     *
     * @param key Redis返回的key
     */
    public void processProductUndercarriage(String key) {
        log.debug("秒杀商品限时结束，标识：{}", key);
        if (StringUtils.isNotBlank(key)) {
            // 商品标识
            String productId = key.substring(key.lastIndexOf(":") + 1);
            if (StringUtils.isNotBlank(productId)) {
                activityProductMapper.updateByPrimaryKeySelective(new SlActivityProduct() {{
                    setId(productId);
                    setEnabled(false);
                }});
                this.slProductMapper.updateByPrimaryKeySelective(new SlProduct(){{
                    setId(productId);
                    setSoldOut(false);
                }});
            }
        }
    }

    public List<Map<String,Object>> simpleActivityProduct(){
        List<Map<String, Object>> mapList =cmProductMapper.simpleActivityProductQuery();
        return mapList;
    }
    /**
     * 查找助力购物商品
     */
    public BusinessMessage selectPowerShopping() {
        log.debug("查询助力购物商品列表");
        BusinessMessage message = new BusinessMessage();
                message.setData(this.mapper.selectPowerShopping());
                message.setMsg("查询成功");
                message.setSuccess(true);
        return message;
    }
}
