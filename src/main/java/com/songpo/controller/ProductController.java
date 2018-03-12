package com.songpo.controller;


import com.songpo.domain.BusinessMessage;
import com.songpo.entity.SlProduct;
import com.songpo.service.ProductService;
import com.songpo.validator.ProductValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "商品管理")
@CrossOrigin
@RestController
@RequestMapping("/api/v2/product")
public class ProductController extends BaseController<SlProduct,String>{

    private org.slf4j.Logger logger = LoggerFactory.getLogger(SlProduct.class);

    public ProductController(ProductService service) {
        //设置业务服务类
        super.service = service;
        //设置实体校验器
        super.validatorHandler = new ProductValidator(service);
    }

    @Autowired
    private ProductService productService;

    /**
     * 根据商品名称查询商品
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
    @RequestMapping(value = "/find-goods",method = RequestMethod.POST)
    public BusinessMessage findGoods(String name, Integer page, Integer size) {
        return this.productService.findGoods(name,page,size);
    }

    /**
     * 分类页面 推荐商品
     * 取最新商品的前6个
     * @return
     */
    @ApiOperation(value = "分类页面,推荐商品")
    @ApiImplicitParams(value = {
    })
    @RequestMapping(value = "/recommend-product",method = RequestMethod.POST)
    public BusinessMessage recommendProduct(){
        return this.productService.recommendProduct();
    }


    /**
     * 根据商品分类查询商品,商品筛选分类 + 筛选
     * @param goodsType
     * @param screenType
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "根据商品分类查询商品+筛选商品")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "goodsType", value = "分类ID", paramType = "form", required = true),
            @ApiImplicitParam(name = "screenType", value = "筛选条件", paramType = "form", required = true),
            @ApiImplicitParam(name = "page", value = "当前页数", paramType = "form", required = true),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "form", required = true)
    })
    @RequestMapping(value = "/screen-goods",method = RequestMethod.POST)
    public BusinessMessage screenGoods(String goodsType,Integer screenType,Integer page, Integer size){
        return this.productService.screenGoods(goodsType,screenType,page,size);
    }


}
