package com.songpo.searched.controller;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlActivity;
import com.songpo.searched.service.ActivityService;
import com.songpo.searched.validator.ActivityValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;

/**
 * @author 刘松坡
 */
@Api(description = "商品活动管理")
@RestController
@RequestMapping("activity")
public class ActivityController {

    @Autowired
    private ActivityValidator validator;

    @Autowired
    private ActivityService service;

    /**
     * 添加
     *
     * @param activity 活动信息
     * @return
     */
    @ApiOperation("添加活动")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "名称", paramType = "form", example = "拼团", type = "字符串")
    )
    @PutMapping
    public BusinessMessage<SlActivity> save(SlActivity activity) {
        BusinessMessage<SlActivity> message = new BusinessMessage<>();
        try {
            // 执行实体校验
            Result result = FluentValidator.checkAll().on(activity, validator).doValidate().result(toSimple());

            if (result.isSuccess()) {
                this.service.save(activity);
                message.setData(activity);
            }

            // 设置业务处理信息
            if (null != result.getErrors() && result.getErrors().size() > 0) {
                message.setMsg(String.join(";", result.getErrors()));
            }
            message.setSuccess(result.isSuccess());
        } catch (Exception e) {
            message.setMsg("添加活动信息失败，请重试");
        }
        return message;
    }
}
