package com.songpo.searched.service;

import com.songpo.searched.entity.SlRedPacket;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
@Service
public class RedPacketService extends  BaseService<SlRedPacket,String> {
    public RedPacketService(Mapper<SlRedPacket> mapper) {
        super(mapper);
    }
}
