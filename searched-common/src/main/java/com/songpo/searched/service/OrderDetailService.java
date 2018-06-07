package com.songpo.searched.service;

import com.songpo.searched.entity.SlOrderDetail;
import com.songpo.searched.mapper.SlOrderDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderDetailService extends BaseService<SlOrderDetail, String> {

    public OrderDetailService(SlOrderDetailMapper mapper) {
        super(mapper);
    }

    @Autowired
    private SlOrderDetailMapper orderDetailMapper;
    /**
     * 虚拟拼团
     * @param activityId
     * @param goodsId
     * @return
     */
    public void virtualSpellGroup(SlOrderDetail virtualOrderDetail){
        virtualOrderDetail.setIsVirtualSpellGroup((byte) 1);
        orderDetailMapper.insert(virtualOrderDetail);
    }
}
