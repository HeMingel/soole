package com.songpo.controller;


import com.github.pagehelper.PageInfo;
import com.songpo.domain.BusinessMessage;
import com.songpo.entity.SlProduct;
import com.songpo.service.ProductService;
import io.swagger.annotations.Api;
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

    @Autowired
    private ProductService productService;

    /**
     * 根据商品名称查询商品
     * @param page
     * @param size
     * @return
     */

    @RequestMapping(value = "/findGoods",method = RequestMethod.POST)
    public BusinessMessage findGoods(String name, Integer page, Integer size) {
        return this.productService.findGoods(name,page,size);

    }

    /**
     * 分类页面 推荐商品
     * 取最新商品的前6个
     * @return
     */
    @RequestMapping(value = "/recommendProduct",method = RequestMethod.POST)
    public BusinessMessage recommendProduct(){
        return this.productService.recommendProduct();
    }

}
