package com.songpo.searched.controller;

import com.songpo.searched.entity.SlSpecification;
import com.songpo.searched.service.SpecificationService;
import com.songpo.searched.validator.SpecificationValidator;
import io.swagger.annotations.Api;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "商品规格信息管理")
@CrossOrigin
@RestController
@RequestMapping("/api/base/v1/spcification")
public class SlSpecificationController extends BaseController<SlSpecification, String> {


    private org.slf4j.Logger logger = LoggerFactory.getLogger(SlSpecification.class);

    public SlSpecificationController(SpecificationService service) {
        //设置业务服务类
        super.service = service;
        //设置实体校验器
        super.validatorHandler = new SpecificationValidator(service);
    }

}
