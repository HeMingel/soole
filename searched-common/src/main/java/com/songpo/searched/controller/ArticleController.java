package com.songpo.searched.controller;


import com.songpo.searched.entity.SlArticle;
import com.songpo.searched.service.ArticleService;
import com.songpo.searched.validator.ArticleValidator;
import io.swagger.annotations.Api;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章管理
 * @author 刘松坡
 */
@Api(description = "文章管理")
@RestController
@CrossOrigin
@RequestMapping("/api/base/v1/article")
public class ArticleController extends BaseController<SlArticle, String> {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(SlArticle.class);

    public ArticleController(ArticleService service) {
        //设置业务服务类
        super.service = service;
        //设置实体校验器
        super.validatorHandler = new ArticleValidator(service);
    }
}
