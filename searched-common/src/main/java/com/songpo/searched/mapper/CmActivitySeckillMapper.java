package com.songpo.searched.mapper;

import com.songpo.searched.entity.SlActivitySeckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface CmActivitySeckillMapper {

   List<SlActivitySeckill> listOutOfDate(@Param("outDate") String outDate);
}
