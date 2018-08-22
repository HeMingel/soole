package com.songpo.searched.controller;

import com.github.pagehelper.PageInfo;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmSharingLinksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 分享链接
 * @author  heming
 * @Date 2018年6月25日11:51:11
 */
@Api(description = "分享链接管理")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v2/sharing-links")
public class SharingLinksController {

    private static final Logger log = LoggerFactory.getLogger(SharingLinksController.class);

    @Autowired
    private CmSharingLinksService cmSharingLinksService;

    /**
     * /add 添加分享链接记录
     * @param userId
     * @param productId
     * @return
     */
    @ApiOperation(value = "添加分享链接记录")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "String", required = true),
            @ApiImplicitParam(name = "productId", value = "产品ID", paramType = "String", required = true)
    })
    @PostMapping("insert")
    public BusinessMessage insert(String userId,String productId) {
        BusinessMessage message = cmSharingLinksService.insert(userId,productId);
        return message;
    }


    @ApiOperation("根据用户ID查询分享链接记录")
    @ApiImplicitParams(value ={
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "String", required = true),
            @ApiImplicitParam(name = "condition", value = "条件", paramType = "Integer", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页数", paramType = "Integer", required = true),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", paramType = "Integer", required = true)
    } )
    @PostMapping("list")
    public BusinessMessage listByuserId(String userId,Integer condition,Integer pageNum,Integer pageSize) {
        BusinessMessage message = cmSharingLinksService.listByUserId(userId,condition,pageNum,pageSize);
        return message;
    }

    /**
     * 分页查询商品列表
     *
     * @param salesModeId  销售模式唯一标识符
     * @param activityId   活动Id
     * @param goodsTypeId  商品分类
     * @param longitudeMin 最小经度
     * @param longitudeMax 最大经度
     * @param latitudeMin  最小维度
     * @param latitudeMax  最大维度
     * @param sortByPrice  按商品价格排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param sortByCount  按库存排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param sortByAward  按佣金排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param priceMin     价格区间最小值，默认为空。如果只有最小值，则选择大于等于此价格
     * @param priceMax     价格区间最大值，默认为空。如果只有最大值，则选择小于等于此价格
     * @param pageNum      页码
     * @param pageSize     容量
     * @param sortBySale   根据销售数量排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param addressNow   当前地址
     * @param longitudeNow 当前经度
     * @param latitudeNow  当前纬度
     * @param synthesize 综合排序 (销量+评论数量)
     * @return 商品分页列表
     */
    @ApiOperation(value = "根据销售模式,活动id查询商品列表,经纬度排序,商品价格,库存,佣金,评分,销量排序")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "salesModeId", value = "销售模式唯一标识符", paramType = "form"),
            @ApiImplicitParam(name = "activityId", value = "活动唯一标识符", paramType = "form"),
            @ApiImplicitParam(name = "goodsTypeId", value = "商品分类", paramType = "form"),
            @ApiImplicitParam(name = "longitudeMin", value = "最小经度，经维度一起使用，缺一不可", paramType = "form"),
            @ApiImplicitParam(name = "longitudeMax", value = "最大经度，经维度一起使用，缺一不可", paramType = "form"),
            @ApiImplicitParam(name = "latitudeMin", value = "最小维度，经维度一起使用，缺一不可", paramType = "form"),
            @ApiImplicitParam(name = "latitudeMax", value = "最大维度，经维度一起使用，缺一不可", paramType = "form"),
            @ApiImplicitParam(name = "sortByPrice", value = "按商品价格排序规则，取值 desc、asc、空，默认为空则不进行排序", paramType = "form"),
            @ApiImplicitParam(name = "sortByCount", value = "按库存排序规则，取值 desc、asc、空，默认为空则不进行排序", paramType = "form"),
            @ApiImplicitParam(name = "sortByAward", value = "按佣金排序规则，取值 desc、asc、空，默认为空则不进行排序", paramType = "form"),
            @ApiImplicitParam(name = "priceMin", value = "价格区间最小值，默认为空。如果只有最小值，则选择大于等于此价格", paramType = "form"),
            @ApiImplicitParam(name = "priceMax", value = "价格区间最大值，默认为空。如果只有最大值，则选择小于等于此价格", paramType = "form"),
            @ApiImplicitParam(name = "pageNum", value = "页码，从1开始", paramType = "form"),
            @ApiImplicitParam(name = "pageSize", value = "数量，必须大于0", paramType = "form"),
            @ApiImplicitParam(name = "sortBySale", value = "根据销售数量排序规则，取值 desc、asc、空，默认为空则不进行排序", paramType = "form"),
            @ApiImplicitParam(name = "addressNow", value = "当前地址", paramType = "form"),
            @ApiImplicitParam(name = "longitudeNow", value = "当前经度", paramType = "form"),
            @ApiImplicitParam(name = "latitudeNow", value = "当前纬度", paramType = "form"),
            @ApiImplicitParam(name = " synthesize",value = "综合排序参数值 1 按照销量+评论数量排序 ",paramType = "form")
    })
    @GetMapping("by-sharing-links")
    public BusinessMessage<PageInfo<Map<String, Object>>> selectBySharingLinks(
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
        log.debug("分页查询商品，销售模式唯一标识符：{}，商品分类唯一标识符：{}，商品分类标识(一级二级):{},最小经度：{}，最大经度：{}，最小维度：{}，" +
                        "最大维度：{}，按商品价格排序规则：{}，按库存排序规则：{}，按佣金排序规则：{}，价格区间最小值：{}，" +
                        "价格区间最大值：{}，页码：{}，容量：{},销售数量排序:{},用户当前位置:{},当前经度:{},当前纬度:{},综合排序:{}",
                salesModeId, activityId, goodsTypeId, longitudeMin, longitudeMax, latitudeMin,
                latitudeMax, sortByPrice, sortByCount, sortByAward, priceMin,
                priceMax, pageNum, pageSize, sortBySale, addressNow, longitudeNow, latitudeNow,synthesize);
        BusinessMessage<PageInfo<Map<String, Object>>> message = new BusinessMessage<>();
        message.setSuccess(false);
        try {
            PageInfo data = this.cmSharingLinksService.selectBySharingLinks(salesModeId, activityId, goodsTypeId, goodsTypeStatus, longitudeMin, longitudeMax,
                    latitudeMin,latitudeMax, sortByPrice, sortByCount, sortByAward, priceMin, priceMax, pageNum, pageSize, sortBySale, addressNow,
                    longitudeNow, latitudeNow,synthesize);

            message.setData(data);
            message.setSuccess(true);
        } catch (Exception e) {
            log.error("分页查询商品失败，{}", e);
            message.setSuccess(false);
            message.setMsg("分页查询商品失败，请重试");
        }
        return message;
    }

    /**
     * 获取红包信息  根据result
     * @param  result 红包结果
     * @return
     */
    @ApiOperation(value = "根据红包结果查询红包信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "result",value = "红包结果1.有效  2.已抢完  3.过期  4.待领取(分享奖励商品) 5.立即领取(分享奖励商品) " +
                    "6.已领取(分享奖励商品) ",paramType = "form")
    })
    @PostMapping("by-result")
    public BusinessMessage selectRedPacketByResult(String result){
        log.debug("红包结果状态: {}", result);
        return  this.cmSharingLinksService.selectRedPacketByResult(result);
    }
    /**
     * 获取红包数 已领红包金额
     * @param  userId 用户ID
     * @return
     */
    @ApiOperation(value = "获取红包数 已领红包金额")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "userId",value = "用户ID",paramType = "form")
    })
    @PostMapping("red_by_userId")
    public BusinessMessage selectRedByUserId(String userId){
        log.debug("用户ID: {}", userId);
        return  this.cmSharingLinksService.selectRedByUserId(userId);
    }
    /**
     * 分享红包详情
     * @return
     */
    @ApiOperation(value = "分享红包详情")
    @GetMapping("red_packet_detail")
    public BusinessMessage selectRedPacketDetail(){
        log.debug("查询 分享红包详情");
        return this.cmSharingLinksService.selectRedPacketDetail();
    }

    /**
     * 支付成功后 添加订单-链接关联信息
     *
     * @param linksId
     * @param orderId
     * @return
     */
    @ApiOperation(value = "添加订单-链接关联信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "linksId", value = "用户ID", paramType = "form", required = true),
            @ApiImplicitParam(name = "orderId", value = "订单ID", paramType = "form", required = true)
    })
    @PostMapping("save-order-extend")
    public BusinessMessage saveOrderExtend(String linksId, String orderId) {
        BusinessMessage message = cmSharingLinksService.saveOrderExtend(linksId, orderId);
        return message;
    }

    /**
     * 我的分享以及详情
     * @param  userId 用户ID
     * @param  shareId 分享ID
     * @return
     */
    @ApiOperation(value = "我的分享以及详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "userId",value = "用户ID",paramType = "form"),
            @ApiImplicitParam(name = "shareId",value = "分享ID",paramType = "form")
    })
    @PostMapping("share_by_userId")
    public BusinessMessage selectShareList(String userId, String shareId) {
        log.debug("用户ID: {}, 分享ID: {}", userId, shareId);
        return this.cmSharingLinksService.selectShareList(userId, shareId);
    }

    /**
     * 获取分享奖励的红包
     * @param redPacketId
     * @return
     */
    @ApiOperation(value="获取分享奖励的红包")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "redPacketId",value = "红包ID",paramType = "form")
    })
    @PostMapping("get-red-packet")
    public BusinessMessage getRedPacket(String redPacketId) {
        return cmSharingLinksService.getRedPacket(redPacketId);
    }

    /**
     * H5 领取红包以及详情
     * @return
     */
    @ApiOperation(value = "H5 领取红包以及详情")
    @GetMapping("red_list")
    public BusinessMessage selectRedList() {
        log.debug("H5 领取红包以及详情" );
        return this.cmSharingLinksService.selectRedList();
    }

}
