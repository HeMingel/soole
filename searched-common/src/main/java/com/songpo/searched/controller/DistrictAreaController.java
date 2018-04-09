package com.songpo.searched.controller;

import com.songpo.searched.entity.SlDistrictArea;
import com.songpo.searched.service.DistrictAreaService;
import com.songpo.searched.validator.DistrictAreaValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuso
 */
@Api(description = "行政区划-区县管理")
@CrossOrigin
@RestController
@RequestMapping("/api/base/v1/district-area")
public class DistrictAreaController extends BaseController<SlDistrictArea, String> {

    private Logger logger = LoggerFactory.getLogger(DistrictAreaController.class);

    public DistrictAreaController(DistrictAreaService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new DistrictAreaValidator(service);
    }
}