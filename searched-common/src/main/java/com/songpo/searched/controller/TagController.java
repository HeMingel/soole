package com.songpo.searched.controller;

import com.songpo.searched.entity.SlTag;
import com.songpo.searched.service.TagService;
import com.songpo.searched.validator.TagValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Api(description = "标签管理")
@RestController
@CrossOrigin
@RequestMapping("/api/base/v1/tag")
public class TagController extends BaseController<SlTag, String> {

    private Logger logger = LoggerFactory.getLogger(TagController.class);

    public TagController(TagService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new TagValidator(service);
    }
}