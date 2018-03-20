package com.songpo.searched.controller;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Api(description = "商品类型管理")
@CrossOrigin
@RestController
@RequestMapping("/api/v2/goods-type")
public class ProductTypeController {

    @Autowired
    private CmProductTypeService productTypeService;


    /**
     * 搜索一级分类
     *
     * @return
     */
    @ApiOperation(value = "搜索分类")
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "parentId", value = "商品名称", paramType = "form", required = true)
    })
    public BusinessMessage findCategory(String parentId) {
        return this.productTypeService.findCategory(parentId);
    }



}
