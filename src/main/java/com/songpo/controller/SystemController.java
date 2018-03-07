package com.songpo.controller;

import com.songpo.domain.BusinessMessage;
import com.songpo.entity.SlUser;
import com.songpo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuso
 */
@RestController
@RequestMapping("/api/v1/system")
public class SystemController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(SystemController.class);

    /**
     * 登录
     *
     * @param username 账号，可以是用户名、手机号码或邮箱
     * @param password 密码
     * @return 业务消息
     */
    @PostMapping("login")
    public BusinessMessage<Map<String, String>> login(String username, String password) {
        logger.debug("用户登录，账号：{}，密码：{}", username, password);
        BusinessMessage<Map<String, String>> message = new BusinessMessage<>();
        try {
            SlUser user = userService.selectOne(new SlUser() {{
                setUsername(username);
            }});
            if (null == user) {
                message.setMsg("用户信息不匹配");
            } else if (!passwordEncoder.matches(password, user.getPassword())) {
                message.setMsg("用户信息不匹配");
            } else {
                message.setData(new HashMap<String, String>() {{
                    put("clientId", user.getId());
                    put("clientSecret", user.getSecret());
                }});
                message.setSuccess(true);
            }
        } catch (Exception e) {
            logger.error("登录失败：{}", e);
            message.setMsg("登录失败：" + e.getMessage());
        }
        return message;
    }

    /**
     * 注册
     *
     * @param username 账号
     * @param password 密码
     * @return 业务信息
     */
    @PostMapping("register")
    public BusinessMessage<SlUser> register(String username, String password) {
        logger.debug("用户注册，账号：{}，密码：{}", username, password);
        BusinessMessage<SlUser> message = new BusinessMessage<>();
        if (StringUtils.isEmpty(username)) {
            message.setMsg("账号为空");
        } else if (StringUtils.isEmpty(password)) {
            message.setMsg("密码为空");
        } else {
            try {
                SlUser user = this.userService.selectOne(new SlUser() {{
                    setUsername(username);
                }});

                if (null != user) {
                    message.setMsg("账号已存在");
                } else {
                    user = new SlUser();
                    user.setUsername(username);
                    user.setPassword(password);
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

}
