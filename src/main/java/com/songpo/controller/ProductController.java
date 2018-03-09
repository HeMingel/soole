package com.songpo.controller;


import com.github.pagehelper.PageInfo;
import com.songpo.domain.BusinessMessage;
import com.songpo.entity.SlProduct;
import com.songpo.service.ProductService;
import com.songpo.validator.ProductValidator;
import io.swagger.annotations.Api;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/find-goods",method = RequestMethod.POST)
    public BusinessMessage findGoods(String name, Integer page, Integer size) {
        return this.productService.findGoods(name,page,size);

    }

    /**
     * 分类页面 推荐商品
     * 取最新商品的前6个
     * @return
     */
    @RequestMapping(value = "/recommend-product",method = RequestMethod.POST)
    public BusinessMessage recommendProduct(){
        return this.productService.recommendProduct();
    }

    /**
     * 通过商品分类查询商品
     */
    @RequestMapping(value = "/find-goods-by-type",method = RequestMethod.POST)
    public BusinessMessage findGoodsByCategory(String goodsType){
        return this.productService.findGoodsByCategory(goodsType);
    }
}
