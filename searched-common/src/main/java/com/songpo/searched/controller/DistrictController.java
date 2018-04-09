package com.songpo.searched.controller;

import com.songpo.searched.entity.SlDistrict;
import com.songpo.searched.service.DistrictService;
import com.songpo.searched.validator.DistrictValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuso
 */
@Api(description = "行政区划")
@CrossOrigin
@RestController
@RequestMapping("/api/base/v1/district")
public class DistrictController extends BaseController<SlDistrict, String> {

    private Logger logger = LoggerFactory.getLogger(DistrictController.class);

    public DistrictController(DistrictService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new DistrictValidator(service);
    }
}