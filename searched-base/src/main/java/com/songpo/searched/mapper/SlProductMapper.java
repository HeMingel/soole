package com.songpo.searched.mapper;

import com.songpo.searched.entity.SlProduct;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SlProductMapper extends Mapper<SlProduct> {
    List<Map<String,Object>> simpleActivityProductQuery();
}