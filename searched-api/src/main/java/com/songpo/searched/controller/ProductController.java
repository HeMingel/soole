package com.songpo.searched.controller;

import com.github.pagehelper.PageInfo;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.service.CmProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(description = "商品管理")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v1/product")
public class ProductController {

    public static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private CmProductService productService;

    /**
     * 根据活动唯一标识符分页查询商品列表
     *
     * @param name         商品名称
     * @param salesModeId  销售模式唯一标识符
     * @param activityId   活动Id
     * @param goodsTypeId  商品分类
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
     * @param sortBySale   根据销售数量排序规则，取值 desc、asc、空，默认为空则不进行排序
     * @param addressNow   当前地址
     * @param longitudeNow 当前经度
     * @param latitudeNow  当前纬度
     * @param synthesize 综合排序 (销量+评论数量)
     * @return 商品分页列表
     */
    @ApiOperation(value = "根据销售模式,活动id查询商品列表,经纬度排序,商品价格,评分,销量排序")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name", value = "商品名称", paramType = "form"),
            @ApiImplicitParam(name = "salesModeId", value = "销售模式唯一标识符", paramType = "form"),
            @ApiImplicitParam(name = "activityId", value = "活动唯一标识符", paramType = "form"),
            @ApiImplicitParam(name = "goodsTypeId", value = "商品分类", paramType = "form"),
            @ApiImplicitParam(name = "longitudeMin", value = "最小经度，经维度一起使用，缺一不可", paramType = "form"),
            @ApiImplicitParam(name = "longitudeMax", value = "最大经度，经维度一起使用，缺一不可", paramType = "form"),
            @ApiImplicitParam(name = "latitudeMin", value = "最小维度，经维度一起使用，缺一不可", paramType = "form"),
            @ApiImplicitParam(name = "latitudeMax", value = "最大维度，经维度一起使用，缺一不可", paramType = "form"),
            @ApiImplicitParam(name = "sortByPrice", value = "按商品价格排序规则，取值 desc、asc、空，默认为空则不进行排序", paramType = "form"),
            @ApiImplicitParam(name = "sortByRating", value = "按店铺评分排序规则，取值 desc、asc、空，默认为空则不进行排序", paramType = "form"),
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
    @GetMapping("by-sales-mode")
    public BusinessMessage<PageInfo<Map<String, Object>>> selectBySalesMode(String name,
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
        log.debug("分页查询商品，名称：{}，销售模式唯一标识符：{}，商品分类唯一标识符：{}，商品分类标识(一级二级):{},最小经度：{}，最大经度：{}，最小维度：{}，" +
                        "最大维度：{}，按商品价格排序规则：{}，按店铺评分排序规则：{}，价格区间最小值：{}，价格区间最大值：{}，页码：{}，" +
                        "容量：{},销售数量排序:{},用户当前位置:{},当前经度:{},当前纬度:{},综合排序:{}", name, salesModeId, activityId, goodsTypeId, longitudeMin, longitudeMax, latitudeMin,
                latitudeMax, sortByPrice, sortByRating, priceMin, priceMax, pageNum, pageSize, sortBySale, addressNow, longitudeNow, latitudeNow,synthesize);
        BusinessMessage<PageInfo<Map<String, Object>>> message = new BusinessMessage<>();
        message.setSuccess(false);
        try {
            PageInfo data = this.productService.selectBySalesMode(name, salesModeId, activityId, goodsTypeId, goodsTypeStatus, longitudeMin, longitudeMax, latitudeMin,
                    latitudeMax, sortByPrice, sortByRating, priceMin, priceMax, pageNum, pageSize, sortBySale, addressNow, longitudeNow, latitudeNow,synthesize);

            message.setData(data);
            message.setSuccess(true);
        } catch (Exception e) {
            log.error("分页查询商品失败，{}", e);

            message.setMsg("分页查询商品失败，请重试");
        }
        return message;
    }

    /**
     * 商品分类只查询普通商品 和名字
     * 商品分类查询商品,商品筛选分类 + 筛选
     *
     * @param goodsTypeId 商品分类ID
     * @param screenType  筛选类型
     * @param page        页码
     * @param size        数量
     * @return 商品列表
     */
    @ApiOperation(value = "查询普通类型商品 根据商品分类查询商品+筛选商品+商品名称")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "goodsTypeId", value = "分类ID", paramType = "form"),
            @ApiImplicitParam(name = "name", value = "商品名称", paramType = "form"),
            @ApiImplicitParam(name = "screenType", value = "筛选条件1销量倒序2销量正序3价格倒序4价格正序", paramType = "form", required = true),
            @ApiImplicitParam(name = "saleMode", value = "销售模式", paramType = "form"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "form"),
            @ApiImplicitParam(name = "size", value = "条数", paramType = "form")
    })
    @GetMapping("screen-goods")
    public BusinessMessage screenGoods(String goodsTypeId, String name, Integer screenType, Integer saleMode, Integer page, Integer size) {
        log.debug("分页查询商品，分类Id:{},筛选Id:{},销售模式:{},页码：{}，数量：{},商品名称:{},", goodsTypeId, screenType, saleMode, page, size, name);
        return this.productService.screenGoods(goodsTypeId, name, screenType, saleMode, page, size);
    }


    @ApiOperation(value = "查询商品详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "商品ID", paramType = "form", required = true),
            @ApiImplicitParam(name = "activityId", value = "活动ID", paramType = "form", required = true),
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "form")
    })
    @GetMapping("/goods-detail")
    public BusinessMessage goodsDetail(String id, String activityId, String userId) {
        log.debug("根据商品Id查询普通商品，商品Id：{},活动Id: {},用户id:{}", id, activityId, userId);
        if (id != null) {
            return this.productService.goodsDetail(id, activityId, userId);
        } else {
            BusinessMessage businessMessage = new BusinessMessage();
            businessMessage.setMsg("商品ID为空");
            businessMessage.setSuccess(false);
            return businessMessage;
        }
    }

    @ApiOperation(value = "查询预售商品周期")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "goodsId", value = "商品ID", paramType = "form", required = true)
    })
    @GetMapping("/goods-cycle")
    public BusinessMessage goodsCycle(String goodsId) {
        log.debug("根据商品Id查询普通商品，商品Id：{}", goodsId);
        if (goodsId != null) {
            return this.productService.selectGoodsCycle(goodsId);
        } else {
            BusinessMessage businessMessage = new BusinessMessage();
            businessMessage.setMsg("商品ID为空");
            businessMessage.setSuccess(false);
            return businessMessage;
        }
    }

    @ApiOperation(value = "根据商品Id,活动Id查询商品规格")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "商品ID", paramType = "form", required = true),
            @ApiImplicitParam(name = "activityId", value = "活动ID", paramType = "form", required = true)
    })
    @GetMapping("goods-specification")
    public BusinessMessage goodsSpecification(String id, String activityId) {
        log.debug("商品规格,商品Id:{},活动Id", id, activityId);
        return this.productService.goodsNormalSpecification(id, activityId);
    }


    @ApiOperation(value = "根据商品Id查询图文详情")
    @ApiImplicitParam(name = "goodsId",value = "商品Id",paramType = "form",required = true)
    @GetMapping("goods-text")
    public BusinessMessage<String> goodsText(String goodsId){
        log.debug("商品图文详情,商品id:{}",goodsId);
        return this.productService.selectGoodsText(goodsId);
    }

    @ApiOperation(value = "热品推荐")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "商品ID", paramType = "form", required = true)
    })
    @GetMapping("/hotGoods")
    public BusinessMessage hotGoods(String id) {
        return this.productService.hotGoods(id);
    }

    /**
     * 没用到
     * 根据活动唯一标识符分页查询商品列表
     *
     * @param actionId 销售模式
     * @param pageNum  页码
     * @param pageSize 容量
     * @return 商品分页列表
     */
    @ApiOperation(value = "根据活动分页查询商品列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "actionId", value = "活动唯一标识符", paramType = "form", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页码，从1开始", paramType = "form"),
            @ApiImplicitParam(name = "pageSize", value = "数量，必须大于0", paramType = "form")
    })
    @GetMapping("by-activity")
    public BusinessMessage<PageInfo<SlProduct>> selectByActivity(String actionId, Integer pageNum, Integer pageSize) {
        log.debug("分页查询商品，页码：{}，数量：{}", pageNum, pageSize);
        BusinessMessage<PageInfo<SlProduct>> message = new BusinessMessage<>();
        try {
            PageInfo<SlProduct> data = this.productService.selectByAction(actionId, pageNum, pageSize);

            message.setData(data);
            message.setSuccess(true);
        } catch (Exception e) {
            log.error("分页查询商品失败，{}", e);

            message.setMsg("分页查询商品失败，请重试");
        }
        return message;
    }
    /**
     * 查询助力购物商品
     * @return 商品列表
     */
    @ApiOperation(value = "查询助力购物商品")
    @GetMapping("power_shopping")
    public BusinessMessage selectPowerShopping() {
        log.debug("查询助力购物商品");
        return this.productService.selectPowerShopping();
    }
}









