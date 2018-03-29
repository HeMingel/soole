package com.songpo.searched.controller;

import com.github.pagehelper.PageInfo;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.service.CmProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@Api(description = "商品管理")
@CrossOrigin
@RestController
@RequestMapping("/api/v2/product")
public class ProductController {


    @Autowired
    private CmProductService productService;

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
    @ApiOperation(value = "根据销售模式查询商品列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name", value = "商品名称", paramType = "form"),
            @ApiImplicitParam(name = "salesModeId", value = "销售模式唯一标识符", paramType = "form"),
            @ApiImplicitParam(name = "longitudeMin", value = "最小经度，经维度一起使用，缺一不可", paramType = "form"),
            @ApiImplicitParam(name = "longitudeMax", value = "最大经度，经维度一起使用，缺一不可", paramType = "form"),
            @ApiImplicitParam(name = "latitudeMin", value = "最小维度，经维度一起使用，缺一不可", paramType = "form"),
            @ApiImplicitParam(name = "latitudeMax", value = "最大维度，经维度一起使用，缺一不可", paramType = "form"),
            @ApiImplicitParam(name = "sortByPrice", value = "按商品价格排序规则，取值 desc、asc、空，默认为空则不进行排序", paramType = "form"),
            @ApiImplicitParam(name = "sortByRating", value = "按店铺评分排序规则，取值 desc、asc、空，默认为空则不进行排序", paramType = "form"),
            @ApiImplicitParam(name = "priceMin", value = "价格区间最小值，默认为空。如果只有最小值，则选择大于等于此价格", paramType = "form"),
            @ApiImplicitParam(name = "priceMax", value = "价格区间最大值，默认为空。如果只有最大值，则选择小于等于此价格", paramType = "form"),
            @ApiImplicitParam(name = "pageNum", value = "页码，从1开始", paramType = "form"),
            @ApiImplicitParam(name = "pageSize", value = "数量，必须大于0", paramType = "form")
    })
    @GetMapping("by-sales-mode")
    public BusinessMessage<PageInfo<Map<String, Object>>> selectBySalesMode(String name,
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
        log.debug("分页查询商品，名称：{}，销售模式唯一标识符：{}，最小经度：{}，最大经度：{}，最小维度：{}，最大维度：{}，按商品价格排序规则：{}，按店铺评分排序规则：{}，价格区间最小值：{}，价格区间最大值：{}，页码：{}，容量：{}", name, salesModeId, longitudeMin, longitudeMax, latitudeMin, latitudeMax, sortByPrice, sortByRating, priceMin, priceMax, pageNum, pageSize);
        BusinessMessage<PageInfo<Map<String, Object>>> message = new BusinessMessage<>();
        try {
            PageInfo<Map<String, Object>> data = this.productService.selectBySalesMode(name, salesModeId, longitudeMin, longitudeMax, latitudeMin, latitudeMax, sortByPrice, sortByRating, priceMin, priceMax, pageNum, pageSize);

            message.setData(data);
            message.setSuccess(true);
        } catch (Exception e) {
            log.error("分页查询商品失败，{}", e);

            message.setMsg("分页查询商品失败，请重试");
        }
        return message;
    }

    /**
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
     * 分类页面 推荐商品
     * 取最新商品的前6个
     *
     * @return
     */
    @ApiOperation(value = "分类页面,推荐商品")
    @ApiImplicitParams(value = {
    })
    @GetMapping("recommend-product")
    public BusinessMessage recommendProduct() {
        return this.productService.recommendProduct();
    }


    /**
     * 商品分类只查询普通商品 和名字
     *  商品分类查询商品,商品筛选分类 + 筛选
     *
     * @param goodsTypeId  商品分类ID
     * @param screenType 筛选类型
     * @param page       页码
     * @param size       数量
     * @return
     */
    @ApiOperation(value = "查询普通类型商品 根据商品分类查询商品+筛选商品+商品名称")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "goodsTypeId", value = "分类ID", paramType = "form"),
            @ApiImplicitParam(name = "screenType", value = "筛选条件1销量倒序2销量正序3价格倒序4价格正序", paramType = "form",required = true),
            @ApiImplicitParam(name = "saleMode", value = "销售模式", paramType = "form"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "form"),
            @ApiImplicitParam(name = "size", value = "条数", paramType = "form")
    })
    @GetMapping("screen-goods")
    public BusinessMessage screenGoods(String goodsTypeId, String name, Integer screenType,String saleMode,Integer page, Integer size) {
        log.debug("分页查询商品，分类Id:{},筛选Id:{},销售模式:{},页码：{}，数量：{},商品名称:{},", goodsTypeId, screenType,saleMode,page, size, name);
        return this.productService.screenGoods(goodsTypeId, name, screenType,saleMode, page, size);
    }


    @ApiOperation(value = "查询普通商品详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "商品ID", paramType = "form", required = true)
            //@ApiImplicitParam(name = "saleModeType", value = "商品销售类型,1拼团 2预售 3豆赚 4消费返利 5一元购 6普通", paramType = "form", required = true)
    })
    @GetMapping("/general_detail")
    public BusinessMessage goodsDetail(String id) {
        log.debug("根据商品Id查询商品，商品Id：{}", id);
        if (id != null) {
            return this.productService.goodsDetail(id);
        } else {
            BusinessMessage businessMessage = new BusinessMessage();
            businessMessage.setMsg("商品ID为空");
            businessMessage.setSuccess(false);
            return businessMessage;
        }
    }

    /**
     * 商品规格详情
     */
    @ApiOperation(value = "根据商品Id查询商品规格")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "商品ID", paramType = "form", required = true)
    })
    @GetMapping("goods-specification")
    public BusinessMessage goodsSpecification(String id){
        log.debug("商品规格,商品Id:{}",id);
        return this.productService.goodsSpecification(id);

    }

    @ApiOperation(value = "热品推荐")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "商品ID", paramType = "form", required = true)
    })
    @GetMapping("/hotGoods")
    public BusinessMessage hotGoods(String id){
        return this.productService.hotGoods(id);
    }

}






