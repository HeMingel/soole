package com.songpo.searched.controller;

import com.songpo.searched.entity.SlSystemConfig;
import com.songpo.searched.service.SystemConfigService;
import com.songpo.searched.validator.SystemConfigValidator;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘松坡
 */
@Slf4j
@Api(description = "系统配置管理")
@RestController
@RequestMapping("/api/base/v1/system-com.songpo.searched.alipay.config")
public class SystemConfigController extends BaseController<SlSystemConfig, String> {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(SlSystemConfig.class);

    public SystemConfigController(SystemConfigService service) {
        //设置业务服务类
        super.service = service;
        //设置实体校验器
        super.validatorHandler = new SystemConfigValidator(service);
    }
}
