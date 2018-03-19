package com.songpo.searched.controller;

import com.songpo.searched.entity.SlActionNavigationType;
import com.songpo.searched.service.ActionNavigationTypeService;
import com.songpo.searched.validator.ActionNavigationTypeValidator;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘松坡
 */
@Slf4j
@Api(description = "商品活动管理")
@RestController
@RequestMapping("/api/v1/action-navigation-type")
public class ActionNavigationTypeController extends BaseController<SlActionNavigationType, String> {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(SlActionNavigationType.class);

    public ActionNavigationTypeController(ActionNavigationTypeService service) {
        //设置业务服务类
        super.service = service;
        //设置实体校验器
        super.validatorHandler = new ActionNavigationTypeValidator(service);
    }
}
