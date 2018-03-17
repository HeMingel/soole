package com.songpo.searched.mapper;

import com.songpo.searched.entity.SlTag;
import tk.mybatis.mapper.common.Mapper;

public interface SlTagNameMapper extends Mapper<SlTag> {

    String findTagNameByTagId(String tagId, String goodId);
}
