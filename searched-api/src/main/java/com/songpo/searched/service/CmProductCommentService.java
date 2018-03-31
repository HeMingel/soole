package com.songpo.searched.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlProductComment;
import com.songpo.searched.mapper.CmProductCommentMapper;
import com.songpo.searched.mapper.CmProductMapper;
import com.songpo.searched.mapper.SlProductCommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CmProductCommentService {

    @Autowired
    private CmProductCommentMapper mapper;

    /**
     * 查询商品评论
     * @param goodsId 商品Id
     * @param status 好中差 有图 状态
     * @param page 页码
     * @param size 容量
     * @return 商品评论
     */
    public BusinessMessage findGoodsCommentsByGoodsId(String goodsId,Integer status,Integer page, Integer size){
        log.debug("查询 商品Id:{},评论好中差:{},页数:{},条数:{}",goodsId,status,page,size);
        BusinessMessage<PageInfo> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
            List<Map<String,Object>> mapComments = this.mapper.goodsComments(goodsId,status);
            if(mapComments.size() >0){
                if (status !=null && status != 4){
                    businessMessage.setData(new PageInfo<>(mapComments));
                }else{
                    List<Object> list = new ArrayList<>();
                    for(int i=0;i<mapComments.size();i++){
                        Map map = mapComments.get(i);
                        if(map.get("status").equals(4)){
                            List<Map<String,Object>> imgMap = this.mapper.commentImages(map.get("id").toString());
                            imgMap.add(map);
                            list.add(imgMap);
                        }else{
                            list.add(map);
                        }
                        businessMessage.setData(new PageInfo<>(list));
                    }
                }
                businessMessage.setMsg("查询成功");
                businessMessage.setSuccess(true);
            }else{
                businessMessage.setMsg("查询无数据!");
            }
        }catch (Exception e){
            businessMessage.setMsg("商品评论查询异常");
            log.error("查询商品评论异常",e);
        }

        return businessMessage;
    }
}
