package com.songpo.searched.controller;

import com.songpo.searched.entity.SlSpecificationDetail;
import com.songpo.searched.entity.SlSpecificationDetailRepository;
import com.songpo.searched.service.SpecificationDetailRepositoryService;
import com.songpo.searched.service.SpecificationDetailService;
import com.songpo.searched.validator.SpecificationDetailRepositoryValidator;
import com.songpo.searched.validator.SpecificationDetailValidator;
import io.swagger.annotations.Api;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "商品规格信息管理")
@CrossOrigin
@RestController
@RequestMapping("/api/base/v1/spcification-detail-repository")
public class SlSpecificationDetailRepositoryController extends BaseController<SlSpecificationDetailRepository, String> {


    private org.slf4j.Logger logger = LoggerFactory.getLogger(SlSpecificationDetailRepository.class);

    public SlSpecificationDetailRepositoryController(SpecificationDetailRepositoryService service) {
        //设置业务服务类
        super.service = service;
        //设置实体校验器
        super.validatorHandler = new SpecificationDetailRepositoryValidator(service);
    }

}
