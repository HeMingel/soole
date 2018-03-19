package com.songpo.searched.mapper;

import com.songpo.searched.entity.SlTag;
import tk.mybatis.mapper.common.Mapper;

public interface SpecificationNameMapper extends Mapper<SlTag> {

    String findSpecificationContentBySpecificationId(String tagId, String goodId);
}
