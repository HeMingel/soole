package com.songpo.searched.controller;

import com.songpo.searched.entity.SlMyCollection;
import com.songpo.searched.service.CollectionService;
import com.songpo.searched.validator.CollectionValidator;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "我的收藏管理")
@RestController
@CrossOrigin
@RequestMapping("/api/v1/collection")
public class CollectionController extends BaseController<SlMyCollection, String> {

    public CollectionController(CollectionService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new CollectionValidator(service);
    }

}
