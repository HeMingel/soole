package com.songpo.searched.service;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.mapper.CmProductCommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CmProductCommentService {

    @Autowired
    private CmProductCommentMapper productCommentMapper;


    public BusinessMessage findGoodsCommentsByGoodsId(String goodsId){

        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try {
            this.productCommentMapper.findGoodsCommentsByGoodsId(goodsId);
        }catch (Exception e){
            businessMessage.setMsg("商品评论查询异常");
            log.error("查询商品评论异常",e);
        }

        return businessMessage;
    }
}
