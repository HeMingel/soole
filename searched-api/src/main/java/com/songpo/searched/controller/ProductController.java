package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "商品管理")
@CrossOrigin
@RestController
@RequestMapping("/api/v2/product")
public class ProductController {

    @Autowired
    private CmProductService productService;


    /**
     * 根据商品名称查询商品
     *
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "根据商品名称查询商品")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name", value = "商品名称", paramType = "form", required = true),
            @ApiImplicitParam(name = "page", value = "当前页数", paramType = "form", required = true),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "form", required = true)
    })
    @GetMapping("name")
    public BusinessMessage findGoods(String name, Integer page, Integer size) {
        return this.productService.findGoods(name, page, size);
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
     * 根据商品分类查询商品,商品筛选分类 + 筛选
     *
     * @param goodsType    商品分类ID
     * @param screenType   筛选类型
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "根据商品分类查询商品+筛选商品")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "goodsType", value = "分类ID", paramType = "form", required = true),
            @ApiImplicitParam(name = "screenType", value = "筛选条件1销量倒序2销量正序3价格倒序4价格正序567商品类型", paramType = "form", required = true),
            @ApiImplicitParam(name = "page", value = "当前页数", paramType = "form", required = true),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "form", required = true)
    })
    @GetMapping("screen-goods")
    public BusinessMessage screenGoods(String goodsType, Integer screenType, Integer page, Integer size,String name) {
        return this.productService.screenGoods(goodsType, screenType, page, size,name);
    }



    @ApiOperation(value = "根据商品Id查询商品详情 未完成")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "Id", value = "分类ID", paramType = "form", required = true)
    })
    @GetMapping("Id")
    public BusinessMessage goodsDetail(String Id) {
        if (Id != null) {
            return this.productService.goodsDetail(Id);
        } else {
            BusinessMessage businessMessage = new BusinessMessage();
            businessMessage.setMsg("商品ID为空");
            businessMessage.setSuccess(false);
            return businessMessage;
        }
    }





}






