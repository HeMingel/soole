package com.songpo.searched.controller;

import com.songpo.searched.entity.SlDistrictProvince;
import com.songpo.searched.service.DistrictProvinceService;
import com.songpo.searched.validator.DistrictProvinceValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuso
 */
@Api(description = "行政区划-省份管理")
@CrossOrigin
@RestController
@RequestMapping("/api/base/v1/district-province")
public class DistrictProvinceController extends BaseController<SlDistrictProvince, String> {

    private Logger logger = LoggerFactory.getLogger(DistrictProvinceController.class);

    public DistrictProvinceController(DistrictProvinceService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new DistrictProvinceValidator(service);
    }
}