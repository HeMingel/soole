package com.songpo.controller;

import com.github.pagehelper.PageInfo;
import com.songpo.domain.BusinessMessage;
import com.songpo.entity.Demo;
import com.songpo.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "消息服务")
@Slf4j
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    /**
     * 保存
     *
     * @param title   标题
     * @param content 内容
     * @return 业务消息
     */
    @ApiOperation(value = "添加")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title", value = "标题", paramType = "form", required = true),
            @ApiImplicitParam(name = "content", value = "内容", paramType = "form", required = true)
    })
    @PutMapping
    public BusinessMessage<Void> create(String title, String content) {
        // 请在这里加上日期，程序执行时，能更好的从终端看到请求的参数
        log.debug("创建信息：标题[{}]，内容[{}]", title, content);

        BusinessMessage<Void> message = new BusinessMessage<>();

        // 参数校验
        if (StringUtils.isBlank(title)) {
            message.setMsg("参数为空：请检查 title");
            return message;
        }

        if (StringUtils.isBlank(content)) {
            message.setMsg("参数为空：请检查 content");
            return message;
        }

        // 在这里捕获异常，是为了确保服务层出了问题的时候，事务可以回滚
        try {
            this.demoService.save(title, content);

            message.setSuccess(true);
        } catch (Exception e) {
            log.error("保存失败，{}", e);

            message.setMsg("保存失败，请重试");
        }
        return message;
    }

    /**
     * 删除
     *
     * @param id 唯一标识
     * @return 业务消息
     */
    @ApiOperation(value = "删除")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "唯一标识", paramType = "path", required = true)
    })
    @DeleteMapping("{id}")
    public BusinessMessage<Void> delete(@PathVariable String id) {
        // 请在这里加上日期，程序执行时，能更好的从终端看到请求的参数
        log.debug("删除信息：唯一标识[{}]", id);

        BusinessMessage<Void> message = new BusinessMessage<>();

        // 参数校验
        if (StringUtils.isBlank(id)) {
            message.setMsg("参数为空：请检查 id");
            return message;
        }

        // 在这里捕获异常，是为了确保服务层出了问题的时候，事务可以回滚
        try {
            this.demoService.delete(id);

            message.setSuccess(true);
        } catch (Exception e) {
            log.error("删除失败，{}", e);

            message.setMsg("删除失败，请重试");
        }
        return message;
    }

    /**
     * 更新
     *
     * @param id 唯一标识
     * @return 业务消息
     */
    @ApiOperation(value = "更新")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "唯一标识", paramType = "path", required = true),
            @ApiImplicitParam(name = "title", value = "标题", paramType = "form"),
            @ApiImplicitParam(name = "content", value = "内容", paramType = "form")
    })
    @PatchMapping("{id}")
    public BusinessMessage<Void> delete(@PathVariable String id, String title, String content) {
        // 请在这里加上日期，程序执行时，能更好的从终端看到请求的参数
        log.debug("更新信息：唯一标识[{}]，标题[{}]，内容[{}]", id, title, content);

        BusinessMessage<Void> message = new BusinessMessage<>();

        // 参数校验
        if (StringUtils.isBlank(id)) {
            message.setMsg("参数为空：请检查 id");
            return message;
        }

        // 在这里捕获异常，是为了确保服务层出了问题的时候，事务可以回滚
        try {
            this.demoService.update(id, title, content);

            message.setSuccess(true);
        } catch (Exception e) {
            log.error("更新失败，{}", e);

            message.setMsg("更新失败，请重试");
        }
        return message;
    }

    /**
     * 查询
     *
     * @param id 唯一标识
     * @return 消息
     */
    @ApiOperation(value = "查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "唯一标识", paramType = "path", required = true)
    })
    @GetMapping("{id}")
    public BusinessMessage<Demo> findOne(@PathVariable String id) {
        // 请在这里加上日期，程序执行时，能更好的从终端看到请求的参数
        log.debug("查询信息：唯一标识[{}]", id);

        BusinessMessage<Demo> message = new BusinessMessage<>();

        // 参数校验
        if (StringUtils.isBlank(id)) {
            message.setMsg("参数为空：请检查 id");
            return message;
        }

        // 在这里捕获异常，是为了确保服务层出了问题的时候，事务可以回滚
        try {
            Demo demo = this.demoService.findOne(id);

            message.setData(demo);
            message.setSuccess(true);
        } catch (Exception e) {
            log.error("查询失败，{}", e);

            message.setMsg("查询失败，请重试");
        }
        return message;
    }

    /**
     * 分页查询
     *
     * @param title 标题
     * @param page  页码
     * @param size  容量
     * @param sort  排序
     * @return 业务消息
     */
    @ApiOperation(value = "分页查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title", value = "标题", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "当前页码，从1开始", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页数据量", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序字段，单个字段排序 createTime,desc，多个字段排序 createTime,desc,title,asc", paramType = "query")
    })
    @GetMapping()
    public BusinessMessage<PageInfo<Demo>> findPage(String title, Integer page, Integer size, String sort) {
        // 请在这里加上日期，程序执行时，能更好的从终端看到请求的参数
        log.debug("分页查询信息：标题[{}]", title);

        BusinessMessage<PageInfo<Demo>> message = new BusinessMessage<>();

        // 在这里捕获异常，是为了确保服务层出了问题的时候，事务可以回滚
        try {
            PageInfo<Demo> pageInfo = this.demoService.findPage(title, page, size, sort);

            message.setData(pageInfo);
            message.setSuccess(true);
        } catch (Exception e) {
            log.error("分页查询失败，{}", e);

            message.setMsg("分页查询失败，请重试");
        }
        return message;
    }
}
