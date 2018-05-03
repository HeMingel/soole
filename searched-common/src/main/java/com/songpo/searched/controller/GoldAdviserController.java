package com.songpo.searched.controller;

import com.songpo.searched.entity.SlGoldAdviser;
import com.songpo.searched.service.GoldAdviserService;
import com.songpo.searched.validator.GoldAdviserValidator;
import io.swagger.annotations.Api;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘松坡
 */
@Api(description = "金牌顾问管理")
@RestController
@RequestMapping("/api/base/v1/gold-adviser")
public class GoldAdviserController extends BaseController<SlGoldAdviser, String> {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(SlGoldAdviser.class);

    public GoldAdviserController(GoldAdviserService service) {
        //设置业务服务类
        super.service = service;
        //设置实体校验器
        super.validatorHandler = new GoldAdviserValidator(service);
    }
}
