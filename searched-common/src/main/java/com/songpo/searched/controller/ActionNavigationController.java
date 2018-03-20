package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlActionNavigation;
import com.songpo.searched.service.ActionNavigationService;
import com.songpo.searched.validator.ActionNavigationValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "系统动作管理")
@CrossOrigin
@RestController
@RequestMapping("/api/base/v1/action-navigation")
public class ActionNavigationController extends BaseController<SlActionNavigation, String> {

    @Autowired
    private ActionNavigationService actionNavigationService;

    private org.slf4j.Logger logger = LoggerFactory.getLogger(SlActionNavigation.class);

    public ActionNavigationController(ActionNavigationService service) {
        //设置业务服务类
        super.service = service;
        //设置实体校验器
        super.validatorHandler = new ActionNavigationValidator(service);
    }

    /**
     * 商品分类banner图查询
     *
     * @return
     */
    @ApiOperation(value = "商品分类banner")
    @ApiImplicitParams(value = {
    })
    @RequestMapping(value = "goods-category-banner", method = RequestMethod.POST)
    public BusinessMessage goodsCategoryBanner() {
        return this.actionNavigationService.goodsCategoryBanner();

    }
}
