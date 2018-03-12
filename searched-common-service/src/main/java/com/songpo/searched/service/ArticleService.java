package com.songpo.searched.service;

import com.songpo.searched.entity.SlArticle;
import com.songpo.searched.mapper.SlArticleMapper;
import org.springframework.stereotype.Service;

@Service
public class ArticleService extends BaseService<SlArticle, String> {

    public ArticleService(SlArticleMapper mapper) {
        super(mapper);
    }

}
