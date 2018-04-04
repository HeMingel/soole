package com.songpo.searched.controller;

import com.songpo.searched.entity.SlMessage;
import com.songpo.searched.service.MessageService;
import com.songpo.searched.validator.MessageValidator;
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
@RequestMapping("/api/base/v1/messages")
public class MessageController extends BaseController<SlMessage, String> {

    private Logger logger = LoggerFactory.getLogger(MessageController.class);

    public MessageController(MessageService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new MessageValidator(service);
    }
}