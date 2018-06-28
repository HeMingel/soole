package com.songpo.searched.service;

import com.songpo.searched.entity.SlRedPacket;
import tk.mybatis.mapper.common.Mapper;

public class RedPacketService extends  BaseService<SlRedPacket,String> {
    public RedPacketService(Mapper<SlRedPacket> mapper) {
        super(mapper);
    }
}
