package com.songpo.searched.mapper;

import com.songpo.searched.entity.SlActivitySeckill;
import com.songpo.searched.entity.SlActivitySeckillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface SlActivitySeckillMapper extends Mapper<SlActivitySeckill> {
    long countByExample(SlActivitySeckillExample example);

    int deleteByExample(SlActivitySeckillExample example);

    List<SlActivitySeckill> selectByExample(SlActivitySeckillExample example);

    int updateByExampleSelective(@Param("record") SlActivitySeckill record, @Param("example") SlActivitySeckillExample example);

    int updateByExample(@Param("record") SlActivitySeckill record, @Param("example") SlActivitySeckillExample example);
}