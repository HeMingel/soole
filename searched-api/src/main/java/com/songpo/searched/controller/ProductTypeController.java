package com.songpo.searched.controller;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmProductTypeService;
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

import java.util.List;
import java.util.Map;

/**
 * @author Y.H
 */
@Slf4j
@Api(description = "商品类型管理")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v1/product-type")
public class ProductTypeController {

    @Autowired
    private CmProductTypeService productTypeService;

    /**
     * 搜索一级分类
     *
     * @return 业务消息
     */
    @ApiOperation(value = "查询商品分类")
    @ApiImplicitParams(value = {
    })
    @GetMapping("list")
    public BusinessMessage<List<Map<String, Object>>> findAll() {

        return this.productTypeService.findAll();
    }



}
