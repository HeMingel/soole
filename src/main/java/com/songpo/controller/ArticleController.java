package com.songpo.controller;


import com.songpo.entity.SlArticle;
import com.songpo.service.ArticleService;
import com.songpo.validator.ArticleValidator;
import io.swagger.annotations.Api;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 *
 */
@Api(description = "文章管理")
@RestController
@RequestMapping("/api/v1/article")
public class ArticleController extends BaseController<SlArticle, String> {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(SlArticle.class);

    public ArticleController(ArticleService service) {
        //设置业务服务类
        super.service = service;
        //设置实体校验器
        super.validatorHandler = new ArticleValidator(service);
    }
}
