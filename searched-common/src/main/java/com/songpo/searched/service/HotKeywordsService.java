package com.songpo.searched.service;

import com.songpo.searched.entity.SlHotKeywords;
import com.songpo.searched.mapper.SlHotKeywordsMapper;
import org.springframework.stereotype.Service;

/**
 * @author 刘松坡
 */
@Service
public class HotKeywordsService extends BaseService<SlHotKeywords, String> {

    public HotKeywordsService(SlHotKeywordsMapper mapper) {
        super(mapper);
    }
}

