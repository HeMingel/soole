package com.songpo.controller;

import com.songpo.entity.SlActionNavigation;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "系统动作管理")
@RestController
@RequestMapping("/api/v1/actionNavigation")
public class ActionNavigationController extends BaseController<SlActionNavigation, String>{


}
