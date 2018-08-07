package com.songpo.searched.mapper;

import com.songpo.searched.entity.SlActivitySeckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface CmActivitySeckillMapper {

   List<SlActivitySeckill> listOutOfDate(@Param("outDate") String outDate);


   /**
    * 查询限时秒杀抢购中商品
    * @return 商品集合
    */
   List<SlActivitySeckill> limitTimeProductsDay();

   /**
    * 查询限时秒杀明日预告商品
    * @return 商品集合
    */
   List<SlActivitySeckill> limitTimeProductsTomo();

   /**
    * 查询限时秒杀今日商品
    * @param productId
    * @return 商品集合
    */
   List<SlActivitySeckill> selectActivitySeckill(String productId);
}
