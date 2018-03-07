package com.songpo.service;

import com.songpo.entity.SlArticle;
import com.songpo.mapper.SlArticleMapper;
import org.springframework.stereotype.Service;

@Service
public class ArticleService extends BaseService<SlArticle, String> {
    public ArticleService(SlArticleMapper mapper) {
        super(mapper);
    }
}
