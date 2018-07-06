package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.CmRedPacketMapper;
import com.songpo.searched.entity.SlOrder;
import com.songpo.searched.entity.SlOrderExtend;
import com.songpo.searched.entity.SlSharingLinks;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.CmSharingLinksMapper;
import com.songpo.searched.mapper.SlOrderExtendMapper;
import com.songpo.searched.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author heming
 * @Date 2018年6月25日15:53:07
 */
@Service
public class CmSharingLinksService {

    public static final Logger log = LoggerFactory.getLogger(CmSharingLinksService.class);

    @Autowired
    private SharingLinksService sharingLinksService;

    @Autowired
    private CmSharingLinksMapper cmSharingLinksMapper;

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SlOrderExtendMapper slOrderExtendMapper;

    @Autowired
    private CmRedPacketMapper cmRedPacketMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private  OrderExtendService orderExtendService;
    @Autowired
    private ProductService productService;

    /**
     * 添加链接记录
     *
     * @param userId
     * @param productId
     * @return
     */
    @Transactional
    public BusinessMessage insert(String userId, String productId) {
        BusinessMessage message = new BusinessMessage();
        if (StringUtils.isEmpty(userId)|| StringUtils.isEmpty(productId)) {
            message.setMsg("用户ID和产品ID不能为空");
            return message;
        }
        SlSharingLinks links = new SlSharingLinks();
        try {
                     links = sharingLinksService.
                    selectOne(new SlSharingLinks() {{
                        setProductId(productId);
                        setSharePersonId(userId);
                        setIsFailure((byte) 2);
                    }});
            //如果存在有效记录不进行添加
            if (links == null) {
                int reuslt = sharingLinksService.insertSelective(new SlSharingLinks() {{
                    setProductId(productId);
                    setSharePersonId(userId);
                }});
                if (reuslt > 0) {
                             links = sharingLinksService.
                            selectOne(new SlSharingLinks() {{
                                setProductId(productId);
                                setSharePersonId(userId);
                                setIsFailure((byte) 2);
                            }});
                    message.setMsg("链接添加成功");
                }
            } else {
                message.setMsg("链接已存在");
            }
            message.setSuccess(true);
        } catch (Exception e) {
            log.debug("用户{}商品id为{}的分享链接添加失败", userId, productId, e);
            message.setMsg("添加失败");
        }
        message.setData(links);
        return message;
    }

    /**
     * 根据用户查询分享链接列表
     * @param userId 用户ID
     * @param condition 条件查询 1未成交 2 已成交 3 已失效
     * @param pageNum
     * @param pageSize
     * @return
     */
    public BusinessMessage listByUserId(String userId ,Integer condition,Integer pageNum,Integer pageSize){
        BusinessMessage message = new BusinessMessage();
        try {
            if  (!StringUtils.isEmpty(userId)){
                PageHelper.startPage(pageNum, pageSize);
                List<Map<String,Object>> sharingLilnksList =  cmSharingLinksMapper.listByUserId(userId,condition);
                if (sharingLilnksList.size() > 0 ) {
                    message.setData(sharingLilnksList);
                    message.setMsg("查询成功");
                    message.setSuccess(true);
                }else{
                    message.setMsg("当前用户没有分享链接数据");
                }
            } else {
                message.setMsg("查询失败");
            }
        }catch ( Exception e) {
            log.debug(" 根据用户ID{}查询分享链接列表失败",userId,e);
            message.setMsg("查询异常");
        }
        return message;
    }

    /**
     * 分页查询商品列表
     *
     * @param salesModeId     销售模式唯一标识符
     * @param activityId      活动Id
     * @param goodsTypeId     商品分类Id
     * @param goodsTypeStatus 商品分类标识 1:一级分类 2:二级分类
     * @param longitudeMin    最小经度
     * @param longitudeMax    最大经度
     * @param latitudeMin     最小维度
     * @param latitudeMax     最大维度
     * @param sortByPrice     按商品价格排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param sortByCount     按库存排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param sortByAward     按佣金排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param priceMin        价格区间最小值，默认为空。如果只有最小值，则选择大于等于此价格
     * @param priceMax        价格区间最大值，默认为空。如果只有最大值，则选择小于等于此价格
     * @param pageNum         页码
     * @param pageSize        容量
     * @param sortBySale      根据销售数量排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param synthesize      综合排序 (销量+评论数量)
     * @return 商品分页列表
     */
    public PageInfo selectBySharingLinks(
            String salesModeId,
            String activityId,
            String goodsTypeId,
            Integer goodsTypeStatus,
            Double longitudeMin,
            Double longitudeMax,
            Double latitudeMin,
            Double latitudeMax,
            String sortByPrice,
            String sortByCount,
            String sortByAward,
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
        if (!org.apache.commons.lang3.StringUtils.containsAny(sortByPrice, orderStrArray)) {
            sortByPrice = org.apache.commons.lang3.StringUtils.trimToEmpty(sortByPrice);
        }

        // 过滤库存排序规则中的非法字符
        if (!org.apache.commons.lang3.StringUtils.containsAny(sortByCount, orderStrArray)) {
            sortByCount = org.apache.commons.lang3.StringUtils.trimToEmpty(sortByCount);
        }

        // 过滤佣金排序规则中的非法字符
        if (!org.apache.commons.lang3.StringUtils.containsAny(sortByAward, orderStrArray)) {
            sortByAward = org.apache.commons.lang3.StringUtils.trimToEmpty(sortByAward);
        }

        //过滤销售数量排序中的非法字符
        if (!org.apache.commons.lang3.StringUtils.containsAny(sortBySale, orderStrArray)) {
            sortBySale = org.apache.commons.lang3.StringUtils.trimToEmpty(sortBySale);
        }

        // 设置分页参数
        PageHelper.startPage(pageNum, pageSize);
        // 执行查询
        List<Map<String, Object>> list = this.cmSharingLinksMapper.selectBySharingLinks(salesModeId, activityId, goodsTypeId, goodsTypeStatus, longitudeMin,
                longitudeMax, latitudeMin, latitudeMax, sortByPrice, sortByCount, sortByAward, priceMin, priceMax, sortBySale,
                addressNow, longitudeNow, latitudeNow, synthesize);
        return new PageInfo<>(list);
    }
    /**
     * 获取红包信息  根据result
     * @param  result 红包结果
     * @return
     */
    public BusinessMessage selectRedPacketByResult(String result) {
        log.debug("根据红包结果状态查询红包信息, 红包状态: {}", result);
        BusinessMessage message = new BusinessMessage();
        try {
            SlUser user = loginUserService.getCurrentLoginUser();
            if (null != user){
                message.setData(this.cmSharingLinksMapper.selectRedPacketByResult(result, user.getId()));
                message.setMsg("查询成功");
                message.setSuccess(true);
            }else {
                message.setMsg("查询成功");
                message.setSuccess(true);
            }
        }catch (Exception e){
            log.error("查询红包信息异常", e);
            message.setSuccess(false);
            message.setMsg("查询红包信息失败, 请重试");
        }

        return message;
    }
    /**
     * 获取红包数 已领红包金额
     * @param  userId 用户ID
     * @return
     */
    public BusinessMessage selectRedByUserId(String userId) {
        log.debug("根据用户ID获取红包数 已领红包金额, 用户ID: {}", userId);
        BusinessMessage message = new BusinessMessage();
        try {
            if (null != userId){
                List list = cmSharingLinksMapper.selectRedByUserId(userId);
                message.setData(list);
                message.setMsg("获取红包数 已领红包金额成功");
                message.setSuccess(true);
            }else {
                message.setMsg("用户信息不存在");
                message.setSuccess(false);
            }
        }catch (Exception e){
            log.error("获取红包数 已领红包金额异常", e);
            message.setSuccess(false);
            message.setMsg("获取红包数 已领红包金额失败, 请重试");
        }

        return message;
    }

    /**
     * 支付成功后 添加订单-链接关联表信息
     * @param orderId
     * @param linksId
     * @return
     */
    @Transactional
    public BusinessMessage saveOrderExtend(String linksId, String orderId) {
        BusinessMessage message = new BusinessMessage();
        try {
            SlOrder order = orderService.selectByPrimaryKey(orderId);
            if (order == null) {
                message.setMsg("订单不存在");
            } else {
                if (order.getPaymentState() == 1) {
                    int result = slOrderExtendMapper.insertSelective(new SlOrderExtend() {
                        {
                            setServiceId(linksId);
                            setServiceType((byte) 1);
                            setOrderId(orderId);
                        }
                    });
                    if (result > 0) {
                        message.setMsg("添加成功");
                        message.setSuccess(true);
                    } else {
                        message.setMsg("添加失败");
                    }
                } else {
                    message.setMsg("订单未支付");
                }
            }
        } catch (Exception e) {
            log.error("添加分享链接-订单到关联表失败,订单id:{}", orderId, e);
            message.setMsg("添加失败");
        } finally {
            return message;
        }
    }

    /**
     * 获取分享红包详情
     *
     * @return
     */
    public BusinessMessage selectRedPacketDetail(){
        BusinessMessage message = new BusinessMessage();
        JSONObject data = new JSONObject();
        List list  = new ArrayList();
        try {
            //获取分享红包
            List<SlRedPacket> redPacketList =  cmRedPacketMapper.findRedByRedType("4");
            for(SlRedPacket slRedPacket : redPacketList){
                data.put("slRedPacket", slRedPacket);
                //购买人信息
                SlUser slUserBuy = userService.selectByPrimaryKey(slRedPacket.getUserId());
                data.put("slUserBuy", slUserBuy);
                //订单关联表
                SlOrderExtend slOrderExtend = orderExtendService.selectByPrimaryKey(slRedPacket.getOrderExtendId());

                //分享链接表详情
                SlSharingLinks slSharingLinks = sharingLinksService.selectByPrimaryKey(slOrderExtend.getServiceId());
                //订单详情
                SlOrder slOrder = orderService.selectByPrimaryKey(slOrderExtend.getOrderId());
                data.put("slOrder", slOrder);
                //分享人信息
                SlUser slUserShare = userService.selectByPrimaryKey(slSharingLinks.getSharePersonId());
                data.put("slUserShare", slUserShare);
                //商品信息
                SlProduct slProduct = productService.selectByPrimaryKey(slSharingLinks.getProductId());
                data.put("slProduct",slProduct);
                list.add(data);
            }
            message.setData(list);
            message.setSuccess(true);
            message.setMsg("获取分享红包详情成功");
        }catch (Exception e){
            log.error("获取分享红包详情失败", e);
            message.setSuccess(false);
            message.setMsg("获取分享红包详情失败");
        }

        return  message;
    }

    /**
     * 我的分享以及详情
     * @param userId  用户ID
     * @param shareId 分享ID
     * @return
     */
    public BusinessMessage selectShareList(String userId, String shareId){
        BusinessMessage message = new BusinessMessage();
        try {
            message.setData(this.cmSharingLinksMapper.selectShareList(userId, shareId));
            message.setMsg("获取我的分享以及详情成功");
            message.setSuccess(true);
        }catch (Exception e){
            log.error("获取我的分享以及详情失败", e);
            message.setSuccess(false);
            message.setMsg("获取我的分享以及详情失败");
        }

        return  message;
    }
}
