package com.songpo.controller;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.github.pagehelper.PageInfo;
import com.songpo.domain.BusinessMessage;
import com.songpo.service.BaseService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;

/**
 * @author liuso
 */
@Transactional
public class BaseController<T, PK> {

    private final Logger logger = LoggerFactory.getLogger(BaseController.class);
    public BaseService<T, PK> service;
    public ValidatorHandler<T> validatorHandler;

    /**
     * 添加
     *
     * @param entity 实体信息
     * @return 业务信息
     */
    @ApiOperation(value = "添加")
    @PutMapping
    public BusinessMessage<T> save(T entity) {
        logger.debug("添加：实体信息[{}]", entity);
        BusinessMessage<T> message = new BusinessMessage<>();
        if (null == entity) {
            message.setMsg("实体信息为空");
        } else {
            Result result = FluentValidator.checkAll().on(entity, validatorHandler).doValidate().result(toSimple());

            if (result.isSuccess()) {
                this.service.insertSelective(entity);
                message.setData(entity);
            }

            // 设置业务处理信息
            if (null != result.getErrors() && result.getErrors().size() > 0) {
                message.setMsg(String.join(";", result.getErrors()));
            }

            message.setSuccess(result.isSuccess());
        }
        return message;
    }

    /**
     * 根据条件查询是否存在
     *
     * @param entity 查询条件
     * @return 业务信息
     */
    @ApiOperation(value = "是否存在")
    @GetMapping("exist")
    public BusinessMessage<Map<String, Boolean>> exist(T entity) {
        logger.debug("根据条件查询是否存在，查询条件：{}", entity);
        BusinessMessage<Map<String, Boolean>> message = new BusinessMessage<>();
        if (null == entity) {
            message.setMsg("查询条件为空");
        } else {
            message.setData(new HashMap<String, Boolean>(1) {{
                put("exist", service.selectCount(entity) > 0);
            }});
            message.setSuccess(true);
        }
        return message;
    }

    /**
     * 根据唯一标识删除
     *
     * @param id 唯一标识
     * @return 业务信息
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public BusinessMessage<T> delete(@PathVariable PK id) {
        logger.debug("根据唯一标识删除：唯一标识[{}]", id);
        BusinessMessage<T> message = new BusinessMessage<>();
        if (StringUtils.isEmpty(id)) {
            message.setMsg("唯一标识为空");
        } else {
            this.service.deleteByPrimaryKey(id);
            // 设置业务处理信息
            message.setSuccess(true);
        }
        return message;
    }

    /**
     * 根据唯一标识更新
     *
     * @param id 唯一标识
     * @return 业务信息
     */
    @ApiOperation(value = "更新")
    @PatchMapping("{id}")
    public BusinessMessage<T> update(@PathVariable PK id, T entity) {
        logger.debug("根据唯一标识删除：唯一标识[{}]", id);
        BusinessMessage<T> message = new BusinessMessage<>();
        if (StringUtils.isEmpty(id)) {
            message.setMsg("唯一标识为空");
        } else {
            if (this.service.selectByPrimaryKey(id) != null) {
                this.service.updateByPrimaryKeySelective(entity);
                // 设置业务处理信息
                message.setSuccess(true);
            } else {
                message.setMsg("实体不存在，无法进行更新");
            }
        }
        return message;
    }

    /**
     * 根据唯一标识查询
     *
     * @param id 唯一标识
     * @return 业务信息
     */
    @ApiOperation(value = "单个查询")
    @GetMapping("{id}")
    public BusinessMessage<T> findById(@PathVariable PK id) {
        logger.debug("根据唯一标识查询：唯一标识[{}]", id);
        BusinessMessage<T> message = new BusinessMessage<>();
        if (StringUtils.isEmpty(id)) {
            message.setMsg("唯一标识为空");
        } else {
            // 设置业务处理信息
            message.setData(this.service.selectByPrimaryKey(id));
            message.setSuccess(true);
        }
        return message;
    }

    /**
     * 根据条件查询
     *
     * @param entity 查询条件
     * @return 业务信息
     */
    @ApiOperation(value = "列表查询")
    @GetMapping("list")
    public BusinessMessage<List<T>> findAll(T entity) {
        logger.debug("根据条件查询：查询条件[{}]", entity);
        BusinessMessage<List<T>> message = new BusinessMessage<>();
        try {
            // 设置业务处理信息
            message.setData(this.service.select(entity));
            message.setSuccess(true);
        } catch (Exception e) {
            logger.error("根据条件查询失败，{}", e);

            message.setMsg("根据条件查询失败，" + e.getMessage());
        }
        return message;
    }

    /**
     * 根据条件分页查询
     *
     * @param entity 查询条件
     * @return 业务信息
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("page")
    public BusinessMessage<PageInfo<T>> findAll(T entity, Integer page, Integer size, String sortBy) {
        logger.debug("根据条件分页查询：查询条件[{}]", entity);
        BusinessMessage<PageInfo<T>> message = new BusinessMessage<>();
        if (page == null) {
            page = 1;
        }

        if (null == size) {
            size = 10;
        }

        if (StringUtils.isEmpty(sortBy)) {
            sortBy = "";
        }

        // 设置业务处理信息
        message.setData(this.service.selectPage(entity, page, size, sortBy));
        message.setSuccess(true);
        return message;
    }
}