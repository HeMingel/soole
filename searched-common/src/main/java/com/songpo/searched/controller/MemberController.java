package com.songpo.searched.controller;

import com.songpo.searched.entity.SlMember;
import com.songpo.searched.service.MemberService;
import com.songpo.searched.validator.MemberValidator;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "会员管理")
@RestController
@RequestMapping("/api/base/v1/member")
public class MemberController extends BaseController<SlMember, String> {
    public MemberController(MemberService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new MemberValidator(service);
    }
}
