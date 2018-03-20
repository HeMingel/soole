package com.songpo.searched.controller;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.cache.UserCache;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author liuso
 */
@Api(description = "系统接口")
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/system")
public class SystemController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCache userCache;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(SystemController.class);

    /**
     * 登录
     *
     * @param phone 账号，可以是用户名、手机号码或邮箱
     * @param password 密码
     * @return 业务消息
     */
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", paramType = "form", required = true),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "form", required = true)
    })
    @PostMapping("login")
    public BusinessMessage<Map<String, String>> login(String phone, String password) {
        logger.debug("用户登录，账号：{}，密码：{}", phone, password);
        BusinessMessage<Map<String, String>> message = new BusinessMessage<>();
        if (StringUtils.isEmpty(phone)) {
            message.setMsg("账号为空");
        } else if (StringUtils.isEmpty(password)) {
            message.setMsg("密码为空");
        } else {
            try {
                // 从缓存中获取用户信息
                SlUser user = this.userCache.get(phone);

                // 如果用户不存在，则从数据库查询
                if (null == user) {
                    user = userService.selectOne(new SlUser() {{
                        setPhone(phone);
                    }});

                    // 缓存用户信息
                    if (null != user) {
                        this.userCache.put(phone, user);
                    }
                }

                if (null == user) {
                    message.setMsg("用户信息不匹配");
                } else if (!passwordEncoder.matches(password, user.getPassword())) {
                    message.setMsg("用户信息不匹配");
                } else {
                    SlUser finalUser = user;
                    message.setData(new HashMap<String, String>() {{
                        put("clientId", finalUser.getPhone());
                        put("clientSecret", finalUser.getSecret());
                    }});
                    message.setSuccess(true);
                }
            } catch (Exception e) {
                logger.error("登录失败：{}", e);
                message.setMsg("登录失败：" + e.getMessage());
            }
        }
        return message;
    }

    /**
     * 注册
     *
     * @param phone 账号
     * @param password 密码
     * @param code 短信验证码
     * @return 业务信息
     */
    @ApiOperation(value = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", paramType = "form", required = true),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "form", required = true),
            @ApiImplicitParam(name = "code", value = "短信验证码", paramType = "form", required = true)
    })
    @PostMapping("register")
    public BusinessMessage<SlUser> register(String phone, String password, String code) {
        logger.debug("用户注册，账号：{}，密码：{}，验证码：{}", phone, password, code);
        BusinessMessage<SlUser> message = new BusinessMessage<>();
        if (StringUtils.isEmpty(phone)) {
            message.setMsg("账号为空");
        } else if (StringUtils.isEmpty(password)) {
            message.setMsg("密码为空");
        } else {
            try {
                SlUser user = this.userService.selectOne(new SlUser() {{
                    setPhone(phone);
                }});

                if (null != user) {
                    message.setMsg("账号已存在");
                } else {
                    user = new SlUser();
                    user.setPhone(phone);
                    user.setPassword(passwordEncoder.encode(password));
                    user.setSecret(UUID.randomUUID().toString());
                    user.setCreator("admin");
                    user.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                    // 添加
                    userService.insertSelective(user);

                    message.setData(user);
                    message.setSuccess(true);
                }
            } catch (Exception e) {
                logger.error("注册失败：{}", e);
                message.setMsg("注册失败：" + e.getMessage());
            }
        }
        return message;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @ApiOperation(value = "用户信息")
    @GetMapping("uaa")
    public BusinessMessage<JSONObject> userInfo() {
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        JSONObject data = new JSONObject();
        try {
            OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
            String clientId = authentication.getOAuth2Request().getClientId();
            if (!StringUtils.isEmpty(clientId)) {
                // 从缓存中取出用户信息，如果不存在，则进行数据库查询
                SlUser user = this.userCache.get(clientId);
                if (null == user) {
                    user = this.userService.selectOne(new SlUser() {{
                        setPhone(clientId);
                    }});
                }

                if (null != user) {
                    message.setSuccess(true);
                    // 用户真实姓名
                    data.put("realname", user.getName());
                    // 用户昵称
                    data.put("nickname", user.getNickName());
                    // 用户头像
                    data.put("avatar", user.getAvatar());
                    // 手机号码
                    data.put("phone", user.getPhone());
                    // 电子邮箱
                    data.put("email", user.getEmail());

                    message.setData(data);
                } else {
                    message.setMsg("用户信息不存在");
                }
            }
        } catch (Exception e) {
            log.error("获取用户信息失败，{}", e);
        }
        return message;
    }

}
