package com.songpo.searched.controller;

import com.songpo.searched.entity.SlDistrictCity;
import com.songpo.searched.service.DistrictCityService;
import com.songpo.searched.validator.DistrictCityValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuso
 */
@Api(description = "行政区划-城市管理")
@CrossOrigin
@RestController
@RequestMapping("/api/base/v1/district-city")
public class DistrictCityController extends BaseController<SlDistrictCity, String> {

    private Logger logger = LoggerFactory.getLogger(DistrictCityController.class);

    public DistrictCityController(DistrictCityService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new DistrictCityValidator(service);
    }
}