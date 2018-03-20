package com.songpo.searched.controller;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmProductCommentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    /**
     *  根据商品id 查询商品所有评论
     *
     */
    public BusinessMessage findGoodsCommentsByGoodsId (String goodsId){
        return this.productCommentService.findGoodsCommentsByGoodsId(goodsId);
    }


}
