package com.songpo.searched.service;

import com.songpo.searched.entity.SlSharingLinks;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
@Service
public class SharingLinksService  extends BaseService<SlSharingLinks, String>{
    public SharingLinksService(Mapper<SlSharingLinks> mapper) {
        super(mapper);
    }
}
