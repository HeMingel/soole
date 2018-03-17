package com.songpo.searched.controller;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlActivity;
import com.songpo.searched.service.ActivityService;
import com.songpo.searched.validator.ActivityValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;

/**
 * @author 刘松坡
 */
@Slf4j
@Api(description = "商品活动管理")
@RestController
@RequestMapping("/api/v1/activity")
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
    @ApiOperation("添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "活动名称", paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "活动备注", paramType = "form")
    })
    @PutMapping
    public BusinessMessage<SlActivity> save(SlActivity activity) {
        log.debug("添加商品活动，名称：{}，备注：{}", activity.getName(), activity.getName());
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

            log.error("添加活动信息失败，{}", e);
        }
        return message;
    }

    /**
     * 删除
     *
     * @param id 唯一标识符
     * @return
     */
    @ApiOperation("删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "唯一标识符", paramType = "path")
    })
    @DeleteMapping("{id}")
    public BusinessMessage<SlActivity> delete(@PathVariable String id) {
        BusinessMessage<SlActivity> message = new BusinessMessage<>();
        try {
            this.service.delete(id);

            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg("删除活动信息失败，请重试");

            log.error("删除活动信息失败，{}", e);
        }
        return message;
    }

    /**
     * 添加
     *
     * @param id       唯一标识符
     * @param activity 活动信息
     * @return
     */
    @ApiOperation("修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "唯一标识符", paramType = "path"),
            @ApiImplicitParam(name = "name", value = "活动名称", paramType = "form"),
            @ApiImplicitParam(name = "remark", value = "活动备注", paramType = "form")
    })
    @PostMapping("{id}")
    public BusinessMessage<SlActivity> update(@PathVariable String id, SlActivity activity) {
        BusinessMessage<SlActivity> message = new BusinessMessage<>();
        try {
            activity.setId(id);
            this.service.update(activity);
            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg("修改活动信息失败，请重试");

            log.error("修改活动信息失败，{}", e);
        }
        return message;
    }

    /**
     * 查询
     *
     * @param id 唯一标识符
     * @return
     */
    @ApiOperation("查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "唯一标识符", paramType = "path")
    })
    @GetMapping("{id}")
    public BusinessMessage<SlActivity> findById(@PathVariable String id) {
        BusinessMessage<SlActivity> message = new BusinessMessage<>();
        try {
            message.setData(this.service.findById(id));
            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg("查询活动信息失败，请重试");

            log.error("查询活动信息失败，{}", e);
        }
        return message;
    }

    /**
     * 查询
     *
     * @param name 活动名称
     * @return
     */
    @ApiOperation("查询所有")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "活动名称", paramType = "query")
    })
    @GetMapping("list")
    public BusinessMessage<List<SlActivity>> findAll(String name) {
        BusinessMessage<List<SlActivity>> message = new BusinessMessage<>();
        try {
            message.setData(this.service.findAll(name));
            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg("查询活动信息失败，请重试");

            log.error("查询活动信息失败，{}", e);
        }
        return message;
    }

    /**
     * 查询
     *
     * @param name 活动名称
     * @return
     */
    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "活动名称", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页码，默认为0", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "容量，默认为10", paramType = "query")
    })
    @GetMapping("page")
    public BusinessMessage<PageInfo<SlActivity>> findAll(String name, Integer pageNum, Integer pageSize) {
        BusinessMessage<PageInfo<SlActivity>> message = new BusinessMessage<>();
        try {
            message.setData(this.service.findByPage(name, pageNum, pageSize));
            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg("查询活动信息失败，请重试");

            log.error("查询活动信息失败，{}", e);
        }
        return message;
    }
}
