package com.songpo.searched.controller;

import com.songpo.searched.entity.SlActivity;
import com.songpo.searched.service.ActivityService;
import com.songpo.searched.validator.ActivityValidator;
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
@RequestMapping("/api/v1/activity")
public class ActivityController extends BaseController<SlActivity, String> {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(SlActivity.class);

    public ActivityController(ActivityService service) {
        //设置业务服务类
        super.service = service;
        //设置实体校验器
        super.validatorHandler = new ActivityValidator(service);
    }
}
