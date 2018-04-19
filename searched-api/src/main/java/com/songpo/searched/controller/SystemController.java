package com.songpo.searched.controller;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.cache.SmsPasswordCache;
import com.songpo.searched.cache.SmsVerifyCodeCache;
import com.songpo.searched.cache.UserCache;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.service.LoginUserService;
import com.songpo.searched.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

/**
 * @author liuso
 */
@Api(description = "系统接口")
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/system")
public class SystemController {

    public static final Logger log = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserCache userCache;

    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private SmsVerifyCodeCache smsVerifyCodeCache;

    @Autowired
    private SmsPasswordCache smsPasswordCache;

    /**
     * 登录
     *
     * @param phone    账号，可以是用户名、手机号码或邮箱
     * @param password 密码
     * @return 业务消息
     */
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", paramType = "form", required = true),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "form", required = true)
    })
    @PostMapping("login")
    public BusinessMessage<JSONObject> login(String phone, String password) {
        log.debug("用户登录，账号：{}，密码：{}", phone, password);
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        if (StringUtils.isBlank(phone)) {
            message.setMsg("账号为空");
        } else if (StringUtils.isBlank(password)) {
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
                    // 请求中军创接口，检测用户是否存在
                    Boolean exist = null;
                    try {
                        exist = this.loginUserService.checkUserExistByZjcyy(phone, password);
                    } catch (Exception e) {
                        log.error("检测用户在中军创云易平台是否存在错误", e);
                    }

                    if (null != exist && exist) {
                        user = new SlUser();
                        user.setPhone(phone);
                        user.setPassword(passwordEncoder.encode(password));

                        // 定义生成字符串范围
                        char[][] pairs = {{'a', 'z'}, {'A', 'Z'}, {'0', '9'}};
                        // 初始化随机生成器
                        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).filteredBy(LETTERS, DIGITS).build();

                        user.setClientId(generator.generate(16));
                        user.setClientSecret(generator.generate(64));

                        // 添加
                        userService.insertSelective(user);

                        JSONObject data = new JSONObject();
                        data.put("clientId", user.getClientId());
                        data.put("clientSecret", user.getClientSecret());
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
                        // 是否设置支付密码
                        data.put("hasSetSecret", StringUtils.isNotBlank(user.getPayPassword()));

                        message.setData(data);
                        message.setSuccess(true);
                    } else {
                        message.setMsg("用户信息不匹配");
                    }
                } else {
                    if (!passwordEncoder.matches(password, user.getPassword())) {
                        message.setMsg("用户信息不匹配");
                    } else {
                        JSONObject data = new JSONObject();
                        data.put("clientId", user.getClientId());
                        data.put("clientSecret", user.getClientSecret());
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
                        // 是否设置支付密码
                        data.put("hasSetSecret", StringUtils.isNotBlank(user.getPayPassword()));

                        message.setData(data);
                        message.setSuccess(true);
                    }
                }
            } catch (Exception e) {
                log.error("登录失败：{}", e);
                message.setMsg("登录失败：" + e.getMessage());
            }
        }
        return message;
    }

    /**
     * 注册
     *
     * @param phone    账号
     * @param password 密码
     * @param code     短信验证码
     * @return 业务信息
     */
    @ApiOperation(value = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", paramType = "form", required = true),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "form", required = true),
            @ApiImplicitParam(name = "code", value = "短信验证码", paramType = "form", required = true)
    })
    @PostMapping("register")
    public BusinessMessage<JSONObject> register(String phone, String password, String code) {
        log.debug("用户注册，账号：{}，密码：{}，验证码：{}", phone, password, code);
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        if (StringUtils.isBlank(phone)) {
            message.setMsg("账号为空");
        } else if (StringUtils.isBlank(password)) {
            message.setMsg("密码为空");
        } else if (StringUtils.isBlank(code)) {
            message.setMsg("短信验证码为空");
        } else {
            String cacheCode = smsVerifyCodeCache.get(phone);
            if (StringUtils.isBlank(cacheCode) || !code.contentEquals(cacheCode)) {
                message.setMsg("短信验证码已过期，请重试");
            } else {
                try {
                    // 从缓存检测用户信息
                    SlUser user = this.userCache.get(phone);

                    // 从数据库查询用户信息
                    if (null == user) {
                        user = this.userService.selectOne(new SlUser() {{
                            setPhone(phone);
                        }});

                        if (null != user) {
                            this.userCache.put(phone, user);
                        }
                    }

                    if (null != user) {
                        message.setMsg("账号已存在");
                    } else {
                        user = new SlUser();
                        user.setPhone(phone);
                        user.setPassword(passwordEncoder.encode(password));

                        // 定义生成字符串范围
                        char[][] pairs = {{'a', 'z'}, {'A', 'Z'}, {'0', '9'}};
                        // 初始化随机生成器
                        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).filteredBy(LETTERS, DIGITS).build();

                        user.setClientId(generator.generate(16));
                        user.setClientSecret(generator.generate(64));

                        // 添加
                        userService.insertSelective(user);

                        JSONObject data = new JSONObject();
                        data.put("clientId", user.getClientId());
                        data.put("clientSecret", user.getClientSecret());
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
                        // 是否设置支付密码
                        data.put("hasSetSecret", StringUtils.isNotBlank(user.getPayPassword()));

                        message.setData(data);
                        message.setSuccess(true);

                        // 清除短信验证码
                        this.smsVerifyCodeCache.evict(phone);
                    }
                } catch (Exception e) {
                    log.error("注册失败：{}", e);
                    message.setMsg("注册失败：" + e.getMessage());
                }
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
    @GetMapping("user")
    public BusinessMessage<JSONObject> userInfo() {
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        JSONObject data = new JSONObject();
        try {
            OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
            String clientId = authentication.getOAuth2Request().getClientId();
            if (StringUtils.isNotBlank(clientId)) {
                // 从缓存中取出用户信息，如果不存在，则进行数据库查询
                SlUser user = this.userCache.get(clientId);
                if (null == user) {
                    user = this.userService.selectOne(new SlUser() {{
                        setClientId(clientId);
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
                    // 是否设置支付密码
                    data.put("hasSetSecret", StringUtils.isNotBlank(user.getPayPassword()));

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

    /**
     * 重置密码
     *
     * @param oldPassword 原始密码
     * @param newPassword 新密码
     * @return
     */
    @ApiOperation(value = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "原始密码", paramType = "form"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", paramType = "form", required = true)
    })
    @PostMapping("reset-password")
    public BusinessMessage<Void> resetPassword(String oldPassword, String newPassword) {
        log.debug("重置密码，原始密码：{}，新密码：{}", oldPassword, newPassword);
        BusinessMessage<Void> message = new BusinessMessage<>();
        if (StringUtils.isBlank(oldPassword)) {
            message.setMsg("原始密码为空");
        } else if (StringUtils.isBlank(newPassword)) {
            message.setMsg("新密码为空");
        } else {
            try {
                // 从缓存检测用户信息
                SlUser user = this.loginUserService.getCurrentLoginUser();

                if (null == user) {
                    message.setMsg("账号信息不存在");
                } else {
                    // 如果用户密码为空，则允许设置新密码，否则进行比对原密码和新密码
                    if (StringUtils.isBlank(user.getPassword())) {
                        user.setPassword(passwordEncoder.encode(newPassword));
                        this.userService.updateByPrimaryKey(user);

                        // 更新用户缓存
                        this.userCache.put(user.getPhone(), user);
                        this.userCache.put(user.getClientId(), user);

                        message.setSuccess(true);
                    } else {
                        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                            message.setMsg("密码不匹配");
                        } else {
                            user.setPassword(passwordEncoder.encode(newPassword));
                            this.userService.updateByPrimaryKey(user);

                            // 更新用户缓存
                            this.userCache.put(user.getPhone(), user);
                            this.userCache.put(user.getClientId(), user);

                            message.setSuccess(true);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("注册失败：{}", e);
                message.setMsg("注册失败：" + e.getMessage());
            }
        }
        return message;
    }

    /**
     * 忘记密码
     *
     * @param phone    手机号码
     * @param password 原始密码
     * @param code     短信验证码
     * @return
     */
    @ApiOperation(value = "忘记密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", paramType = "form", required = true),
            @ApiImplicitParam(name = "password", value = "新密码", paramType = "form", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", paramType = "form", required = true)
    })
    @PostMapping("forgot-password")
    public BusinessMessage<Void> forgotPassword(String phone, String password, String code) {
        log.debug("忘记密码，手机号码：{}，密码：{}，验证码：{}", phone, password, code);
        BusinessMessage<Void> message = new BusinessMessage<>();
        if (StringUtils.isBlank(phone)) {
            message.setMsg("手机号码为空");
        } else if (StringUtils.isBlank(password)) {
            message.setMsg("新密码为空");
        } else if (StringUtils.isBlank(code)) {
            message.setMsg("验证码为空");
        } else {
            // 校验短信验证码
            String cacheCode = this.smsVerifyCodeCache.get(phone);
            if (StringUtils.isBlank(cacheCode) || !code.contentEquals(cacheCode)) {
                message.setMsg("短信验证码已过期，请重试");
            } else {
                try {
                    // 查询用户信息
                    SlUser user = userService.selectOne(new SlUser() {{
                        setPhone(phone);
                    }});

                    if (null == user) {
                        message.setMsg("账号信息不存在");
                    } else {
                        user.setPassword(passwordEncoder.encode(password));
                        this.userService.updateByPrimaryKey(user);

                        // 更新用户缓存
                        this.userCache.put(user.getPhone(), user);
                        this.userCache.put(user.getClientId(), user);

                        message.setSuccess(true);
                    }
                } catch (Exception e) {
                    log.error("注册失败：{}", e);
                    message.setMsg("注册失败：" + e.getMessage());
                }
            }
        }
        return message;
    }

    /**
     * 微信第三方登录
     *
     * @param openId   手机号码
     * @param nickname 微信昵称
     * @param avatar   头像地址
     * @return 用户信息
     */
    @ApiOperation(value = "第三方登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "开放账号唯一标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "nickname", value = "昵称", paramType = "form", required = true),
            @ApiImplicitParam(name = "avatar", value = "头像地址", paramType = "form", required = true),
            @ApiImplicitParam(name = "type", value = "登录类型 1：微信 2：QQ", paramType = "form", required = true)
    })
    @PostMapping("third-party-login")
    public BusinessMessage<JSONObject> thirdPartyLogin(String openId, String nickname, String avatar, Integer type) {
        log.debug("第三方登录，开放账号唯一标识：{}，昵称：{}，头像地址：{}", openId, nickname, avatar);
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        if (StringUtils.isBlank(openId)) {
            message.setMsg("开放账号唯一标识为空");
        } else if (StringUtils.isBlank(nickname)) {
            message.setMsg("昵称为空");
        } else if (StringUtils.isBlank(avatar)) {
            message.setMsg("头像地址为空");
        } else if (null == type) {
            message.setMsg("登录类型为空");
        } else {
            // 从缓存检测用户信息
            SlUser user = this.userCache.get(openId);
            // 从数据库查询用户信息
            if (null == user) {
                user = this.userService.selectOne(new SlUser() {{
                    setOpenId(openId);
                }});

                if (null != user) {
                    this.userCache.put(openId, user);
                }
            }

            // 从数据库查询用户信息
            if (null == user) {
                user = new SlUser();
                user.setNickName(nickname);
                user.setAvatar(avatar);
                user.setOpenId(openId);
                user.setType(type);

                // 定义生成字符串范围
                char[][] pairs = {{'a', 'z'}, {'A', 'Z'}, {'0', '9'}};
                // 初始化随机生成器
                RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).filteredBy(LETTERS, DIGITS).build();

                user.setClientId(generator.generate(16));
                user.setClientSecret(generator.generate(64));

                // 添加
                userService.insertSelective(user);

                this.userCache.put(openId, user);
            }

            JSONObject data = new JSONObject();
            data.put("clientId", user.getClientId());
            data.put("clientSecret", user.getClientSecret());
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
            // 是否设置支付密码
            data.put("hasSetSecret", StringUtils.isNotBlank(user.getPayPassword()));

            message.setData(data);
            message.setSuccess(true);
        }
        return message;
    }

    /**
     * 登录
     *
     * @param phone    手机号码
     * @param password 密码
     * @return 业务消息
     */
    @ApiOperation(value = "短信登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", paramType = "form", required = true),
            @ApiImplicitParam(name = "password", value = "短信密码", paramType = "form", required = true)
    })
    @PostMapping("sms-login")
    public BusinessMessage<JSONObject> smsLogin(String phone, String password) {
        log.debug("短信登录，账号：{}，密码：{}", phone, password);
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        if (StringUtils.isBlank(phone)) {
            message.setMsg("账号为空");
        } else if (StringUtils.isBlank(password)) {
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

                // 检测用户是否存在
                if (null == user) {
                    message.setMsg("用户信息不匹配");
                } else {
                    String pwd = this.smsPasswordCache.get(phone);
                    if (StringUtils.isBlank(pwd) || !pwd.contentEquals(password)) {
                        message.setMsg("密码已过期，请重试");
                    } else {
                        JSONObject data = new JSONObject();
                        data.put("clientId", user.getClientId());
                        data.put("clientSecret", user.getClientSecret());
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
                        // 是否设置支付密码
                        data.put("hasSetSecret", StringUtils.isNotBlank(user.getPayPassword()));

                        message.setData(data);
                        message.setSuccess(true);
                    }
                }
            } catch (Exception e) {
                log.error("登录失败：{}", e);
                message.setMsg("登录失败：" + e.getMessage());
            }
        }
        return message;
    }

    /**
     * 绑定手机号码
     *
     * @param phone    手机号码
     * @param code     短信验证码
     * @param openId   第三方标识
     * @param password 密码
     * @return 绑定结果
     */
    @ApiOperation(value = "绑定手机号码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", paramType = "form", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", paramType = "form", required = true),
            @ApiImplicitParam(name = "openId", value = "第三方标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "form", required = true)
    })
    @PostMapping("bind-phone")
    public BusinessMessage<JSONObject> bindPhone(String phone, String code, String openId, String password) {
        log.debug("绑定手机号码，手机号码：{}，验证码：{}，第三方标识：{}，密码：******", phone, code, openId);
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        if (StringUtils.isBlank(openId)) {
            message.setMsg("第三方标识为空");
        } else if (StringUtils.isBlank(phone)) {
            message.setMsg("手机号码为空");
        } else if (StringUtils.isBlank(code)) {
            message.setMsg("验证码为空");
        } else if (StringUtils.isBlank(password)) {
            message.setMsg("密码为空");
        } else {
            // 校验短信验证码
            String cacheCode = this.smsVerifyCodeCache.get(phone);
            if (StringUtils.isBlank(cacheCode) || !code.contentEquals(cacheCode)) {
                message.setMsg("短信验证码已过期，请重试");
            } else {
                // 从缓存检测用户信息
                SlUser user = this.userCache.get(openId);
                // 从数据库查询用户信息
                if (null == user) {
                    user = this.userService.selectOne(new SlUser() {{
                        setOpenId(openId);
                    }});

                    if (null != user) {
                        this.userCache.put(openId, user);
                    }
                }

                if (null == user) {
                    message.setMsg("用户信息不存在，请重试");
                } else {
                    if (StringUtils.isNotBlank(user.getPhone())) {
                        message.setMsg("手机号码已被绑定，请更换手机号码再次尝试");
                    } else {
                        // 设置手机号码
                        user.setPhone(phone);
                        // 设置密码
                        user.setPassword(passwordEncoder.encode(password));

                        // 更新
                        userService.updateByPrimaryKeySelective(user);

                        // 更新缓存
                        this.userCache.put(openId, user);

                        // 清除验证码
                        this.smsVerifyCodeCache.evict(phone);

                        message.setSuccess(true);
                    }
                }
            }
        }
        return message;
    }
}
