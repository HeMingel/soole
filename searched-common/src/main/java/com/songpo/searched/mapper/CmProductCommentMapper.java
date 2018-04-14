package com.songpo.searched.mapper;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CmProductCommentMapper {



     //获取商品评论
     List<Map<String,Object>> goodsComments(@Param("goodsId")String goodsId, @Param("status")Integer status);
     //获取商品评论图片
     List<Map<String,Object>> commentImages(String commentId);
     //获取商品评论数量
     List<Map<String,Object>> goodsCommentsNum(String goodsId);
}
