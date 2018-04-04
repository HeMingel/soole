package com.songpo.searched.controller;

import com.songpo.searched.entity.SlHotKeywords;
import com.songpo.searched.service.HotKeywordsService;
import com.songpo.searched.validator.HotKeywordsValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuso
 */
@Api(description = "热词管理")
@CrossOrigin
@RestController
@RequestMapping("/api/base/v1/hot-keywords")
public class HotKeywordsController extends BaseController<SlHotKeywords, String> {

    private Logger logger = LoggerFactory.getLogger(HotKeywordsController.class);

    public HotKeywordsController(HotKeywordsService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new HotKeywordsValidator(service);
    }
}