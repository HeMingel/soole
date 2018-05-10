package com.songpo.searched.controller;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmProductCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Y.H
 */
@Api(description = "商品评论管理")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v1/goods-comment")
public class ProductCommentController {

    @Autowired
    private CmProductCommentService productCommentService;


    @ApiOperation(value = "商品评论查询+筛选")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "goodsId", value = "商品Id", paramType = "form", required = true),
            @ApiImplicitParam(name = "status", value = "评论状态1好2中3差4有图", paramType = "form", required = true),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "form", required = true),
            @ApiImplicitParam(name = "size", value = "每页数量", paramType = "form")
    })
    @GetMapping("goods-comments")
    public BusinessMessage goodsComments(String goodsId, Integer status, Integer page, Integer size) {
        return this.productCommentService.findGoodsCommentsByGoodsId(goodsId, status, page, size);
    }

    @ApiOperation(value = "新增商品评论")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "productId", value = "商品ID", paramType = "form", required = true),
            @ApiImplicitParam(name = "content", value = "评论内容", paramType = "form", required = true),
            @ApiImplicitParam(name = "status", value = "评论状态:1好2中3差", paramType = "form", required = true),
            @ApiImplicitParam(name = "imageList", value = "评论图片", paramType = "form"),
    })
    @PostMapping("add")
    public BusinessMessage insertGoodsComments(String productId, String content, Integer status, List<MultipartFile> imageList) {
        return productCommentService.insertGoodsComments(productId, content, status, imageList);
    }


}
