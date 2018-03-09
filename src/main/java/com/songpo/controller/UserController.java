package com.songpo.controller;

import com.songpo.entity.SlUser;
import com.songpo.service.UserService;
import com.songpo.validator.UserValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuso
 */
@Api(description = "用户管理")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController<SlUser, String> {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new UserValidator(service);
    }
}