package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlAction;
import com.songpo.searched.entity.SlActionNavigation;
import com.songpo.searched.service.ActionNavigationService;
import com.songpo.searched.service.ActionService;
import com.songpo.searched.validator.ActionNavigationValidator;
import com.songpo.searched.validator.ActionValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "活动管理")
@CrossOrigin
@RestController
@RequestMapping("/api/base/v1/action")
public class ActionController extends BaseController<SlAction, String> {

    @Autowired
    private ActionService actionService;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(SlAction.class);

    public ActionController(ActionService service) {
        //设置业务服务类
        super.service = service;
        //设置实体校验器
        super.validatorHandler = new ActionValidator(service);
    }

}
