package com.songpo.controller;

import com.songpo.entity.SlMember;
import com.songpo.entity.SlMember;
import com.songpo.service.MemberService;
import com.songpo.service.UserService;
import com.songpo.validator.MemberValidator;
import com.songpo.validator.UserValidator;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "会员管理")
@RestController
@CrossOrigin
@RequestMapping("/api/v1/member")
public class MemberController extends BaseController<SlMember, String> {
    public MemberController(MemberService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new MemberValidator(service);
    }
}
