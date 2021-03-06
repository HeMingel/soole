package com.songpo.searched.mapper;

import com.songpo.searched.entity.SlRedPacket;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface  CmRedPacketMapper {

    List<SlRedPacket> listByOrderExtendId(@Param("orderExtendId") String orderExtendId);

    List<SlRedPacket> findRedByRedType(@Param("redType") String redType);
}
