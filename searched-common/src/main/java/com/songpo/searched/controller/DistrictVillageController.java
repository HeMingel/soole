package com.songpo.searched.controller;

import com.songpo.searched.entity.SlDistrictVillage;
import com.songpo.searched.service.DistrictVillageService;
import com.songpo.searched.validator.DistrictVillageValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuso
 */
@Api(description = "行政区划-村委会管理")
@CrossOrigin
@RestController
@RequestMapping("/api/base/v1/district-village")
public class DistrictVillageController extends BaseController<SlDistrictVillage, String> {

    private Logger logger = LoggerFactory.getLogger(DistrictVillageController.class);

    public DistrictVillageController(DistrictVillageService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new DistrictVillageValidator(service);
    }
}