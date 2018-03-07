package com.songpo.controller;

import com.songpo.entity.SlTag;
import com.songpo.service.TagService;
import com.songpo.validator.TagValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Api(description = "标签管理")
@RestController
@RequestMapping("/api/v1/tag")
public class TagController extends BaseController<SlTag, String> {

    private Logger logger = LoggerFactory.getLogger(TagController.class);

    public TagController(TagService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new TagValidator(service);
    }
}