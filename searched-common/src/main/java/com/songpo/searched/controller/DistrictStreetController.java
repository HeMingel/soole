package com.songpo.searched.controller;

import com.songpo.searched.entity.SlDistrictStreet;
import com.songpo.searched.service.DistrictStreetService;
import com.songpo.searched.validator.DistrictStreetValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuso
 */
@Api(description = "行政区划-乡镇管理")
@CrossOrigin
@RestController
@RequestMapping("/api/base/v1/district-street")
public class DistrictStreetController extends BaseController<SlDistrictStreet, String> {

    private Logger logger = LoggerFactory.getLogger(DistrictStreetController.class);

    public DistrictStreetController(DistrictStreetService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new DistrictStreetValidator(service);
    }
}