package com.songpo.controller;


import com.songpo.entity.SlRepository;
import com.songpo.service.RepositoryService;
import com.songpo.validator.RepositoryValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Api(description = "商店库存管理")
@RestController
@CrossOrigin
@RequestMapping("/api/v1/repository")
public class RepositoryController extends BaseController<SlRepository, String>{

    private Logger logger = LoggerFactory.getLogger(RepositoryController.class);

    public RepositoryController(RepositoryService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new RepositoryValidator(service);
    }

}
