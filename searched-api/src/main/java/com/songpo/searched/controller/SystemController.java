package com.songpo.searched.controller;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.cache.SmsPasswordCache;
import com.songpo.searched.cache.SmsVerifyCodeCache;
import com.songpo.searched.cache.UserCache;
import com.songpo.searched.constant.BaseConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlMember;
import com.songpo.searched.entity.SlTransactionDetail;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.CmUserMapper;
import com.songpo.searched.mapper.SlTransactionDetailMapper;
import com.songpo.searched.service.LoginUserService;
import com.songpo.searched.service.MemberService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    private MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private SmsVerifyCodeCache smsVerifyCodeCache;

    @Autowired
    private SmsPasswordCache smsPasswordCache;

    @Autowired
    private SlTransactionDetailMapper slTransactionDetailMapper;

    @Autowired
    private CmUserMapper cmUserMapper;

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
//                // 从缓存中获取用户信息
//                SlUser user = this.userCache.get(phone);
//
//                // 如果用户不存在，则从数据库查询
//                if (null == user) {
                SlUser user = userService.selectOne(new SlUser() {{
                    setPhone(phone);
                }});

                // 缓存用户信息
                if (null != user) {
                    this.userCache.put(phone, user);
                }
//                }

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

                        // 天降洪福，100乐豆（银豆）
                        user.setSilver(100);

                        // 定义生成字符串范围
                        char[][] pairs = {{'a', 'z'}, {'A', 'Z'}, {'0', '9'}};
                        // 初始化随机生成器
                        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).filteredBy(LETTERS, DIGITS).build();

                        user.setClientId(generator.generate(16));
                        user.setClientSecret(generator.generate(64));

                        // 添加
//                        userService.insertSelective(user);
                        this.userInsert(user);

                        // 天降洪福，100乐豆（银豆）
                        sendRegisterGiftToNewUser(user.getId());

                        JSONObject data = new JSONObject();
                        data.put("userId", user.getId());
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
                        //搜圈账号动态0正常1禁用（搜圈乱发动态冻结账号的搜圈发布功能）
                        data.put("circleState",user.getCircleState());
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
                        //搜圈账号动态0正常1禁用（搜圈乱发动态冻结账号的搜圈发布功能）
                        data.put("circleState",user.getCircleState());
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
//                    // 从缓存检测用户信息
//                    SlUser user = this.userCache.get(phone);
//
//                    // 从数据库查询用户信息
//                    if (null == user) {
                    SlUser user = this.userService.selectOne(new SlUser() {{
                        setPhone(phone);
                    }});

                    if (null != user) {
                        this.userCache.put(phone, user);
                    }
//                    }

                    if (null != user) {
                        message.setMsg("账号已存在");
                    } else {
                        user = new SlUser();
                        user.setPhone(phone);
                        user.setPassword(passwordEncoder.encode(password));

                        // 天降洪福，赠送乐豆（银豆）
                        user.setSilver(BaseConstant.REGISTER_PEAS);

                        // 定义生成字符串范围
                        char[][] pairs = {{'a', 'z'}, {'A', 'Z'}, {'0', '9'}};
                        // 初始化随机生成器
                        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).filteredBy(LETTERS, DIGITS).build();

                        user.setClientId(generator.generate(16));
                        user.setClientSecret(generator.generate(64));

                        // 添加
//                        userService.insertSelective(user);
                        this.userInsert(user);

                        // 天降洪福，乐豆（银豆）
                        sendRegisterGiftToNewUser(user.getId());

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
            SlUser user = this.loginUserService.getCurrentLoginUser();
            if (null != user) {
                message.setSuccess(true);
                data.put("userId", user.getId());
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
//            // 从缓存检测用户信息
//            SlUser user = this.userCache.get(openId);
//            // 从数据库查询用户信息
//            if (null == user) {
            SlUser user = this.userService.selectOne(new SlUser() {{
                setOpenId(openId);
            }});
//            }

            // 从数据库查询用户信息
            if (null == user) {
                user = new SlUser();
                user.setNickName(nickname);
                user.setAvatar(avatar);
                user.setOpenId(openId);
                user.setType(type);

                // 天降洪福，100乐豆（银豆）
                user.setSilver(BaseConstant.REGISTER_PEAS);

                // 定义生成字符串范围
                char[][] pairs = {{'a', 'z'}, {'A', 'Z'}, {'0', '9'}};
                // 初始化随机生成器
                RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).filteredBy(LETTERS, DIGITS).build();

                user.setClientId(generator.generate(16));
                user.setClientSecret(generator.generate(64));

                // 添加
//                userService.insertSelective(user);
                this.userInsert(user);

                this.sendRegisterGiftToNewUser(user.getId());
            }
            user.setLastLogin(new Date());
            user.setLoginCount((user.getLoginCount() == null ? 0 : user.getLoginCount()) + 1);
            userService.updateByPrimaryKeySelective(user);

            this.userCache.put(openId, user);

            JSONObject data = new JSONObject();
            data.put("userId", user.getId());
            data.put("userName", user.getUsername());
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
            data.put("loginCount", user.getLoginCount());
            //搜圈账号动态0正常1禁用（搜圈乱发动态冻结账号的搜圈发布功能）
            data.put("circleState",user.getCircleState());
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
            @ApiImplicitParam(name = "password", value = "短信密码", paramType = "form", required = true),
            @ApiImplicitParam(name = "zone", value = "地区", paramType = "form", required = true)
    })
    @PostMapping("sms-login")
    public BusinessMessage<JSONObject> smsLogin(String phone, String password ,String zone) {
        log.debug("短信登录，账号：{}，密码：{}", phone, password);
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        if (StringUtils.isBlank(phone)) {
            message.setMsg("账号为空");
        } else if (StringUtils.isBlank(password)) {
            message.setMsg("密码为空");
        } else {
            try {
                String pwd = this.smsPasswordCache.get(phone);
                if (StringUtils.isBlank(pwd) || !pwd.contentEquals(password)) {
                    message.setMsg("密码已过期，请重试");
                } else {
//                    // 从缓存中获取用户信息
//                    SlUser user = this.userCache.get(phone);
//
//                    // 如果用户不存在，则从数据库查询
//                    if (null == user) {
                    SlUser user = userService.selectOne(new SlUser() {{
                        setPhone(phone);
                    }});

                    // 缓存用户信息
                    if (null == user) {
                        user = new SlUser();
                        user.setPhone(phone);
                        user.setZone(zone);
                        // 天降洪福，乐豆（银豆）
                        user.setSilver(BaseConstant.REGISTER_PEAS);

                        // 定义生成字符串范围
                        char[][] pairs = {{'a', 'z'}, {'A', 'Z'}, {'0', '9'}};
                        // 初始化随机生成器
                        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).filteredBy(LETTERS, DIGITS).build();

                        user.setClientId(generator.generate(16));
                        user.setClientSecret(generator.generate(64));

                        // 添加
//                            userService.insertSelective(user);
                        this.userInsert(user);

                        // 天降洪福 乐豆（银豆）
                        sendRegisterGiftToNewUser(user.getId());
                    }
                    user.setLastLogin(new Date());
                    user.setLoginCount((user.getLoginCount() == null ? 0 : user.getLoginCount()) + 1);
                    userService.updateByPrimaryKeySelective(user);

                    this.userCache.put(phone, user);
//                    }

                    JSONObject data = new JSONObject();
                    //用户ID
                    data.put("userId", user.getId());
                    data.put("userName", user.getUsername());
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
                    data.put("loginCount", user.getLoginCount());
                    //搜圈账号动态0正常1禁用（搜圈乱发动态冻结账号的搜圈发布功能）
                    data.put("circleState",user.getCircleState());

                    // 清除验证码
                    this.smsPasswordCache.evict(phone);

                    message.setData(data);
                    message.setSuccess(true);
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
    @Transactional
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
//                // 从缓存检测用户信息
//                SlUser user = this.userCache.get(openId);
//                // 从数据库查询用户信息
//                if (null == user) {
                SlUser user = this.userService.selectOne(new SlUser() {{
                    setOpenId(openId);
                }});

                if (null != user) {
                    this.userCache.put(openId, user);
                }
//                }

                if (null == user) {
                    message.setMsg("用户信息不存在，请重试");
                } else {
                    SlUser phoneUser = this.userService.selectOne(new SlUser() {{
                        setPhone(phone);
                    }});
                    /** 已经存在相同手机号的另一个用户，open合并到该账号，并删除openId账号 **/
                    if (phoneUser != null && !phoneUser.getId().equals(user.getId())) {
                        //删除原来的openId注册账号
                        this.userDelete(user.getId());
                        //数据合并
                        phoneUser.setOpenId(openId);
                        // 设置密码
                        phoneUser.setPassword(passwordEncoder.encode(password));
                        if (StringUtils.isBlank(phoneUser.getNickName())) {
                            phoneUser.setNickName(user.getNickName());
                        }
                        if (StringUtils.isBlank(phoneUser.getAvatar())) {
                            phoneUser.setAvatar(user.getAvatar());
                        }
                        phoneUser.setType(user.getType());
                        //修补错误数据，原来的微信用户中的金豆银豆转移
                        phoneUser.setSilver(phoneUser.getSilver() + ((user.getSilver() - 100) < 0 ? 0 : (user.getSilver() - 100)));
                        phoneUser.setCoin(phoneUser.getCoin() + user.getCoin());
                        // 更新
                        userService.updateByPrimaryKeySelective(phoneUser);
                        // 更新缓存
                        this.userCache.put(openId, phoneUser);
                    } else {
                        /********** 不存在相同手机号用户，更新用户信息 *********/
                        user.setPhone(phone);
                        // 设置密码
                        user.setPassword(passwordEncoder.encode(password));
                        user.setCreatedAt(null);
                        user.setUpdatedAt(null);
                        // 更新
                        userService.updateByPrimaryKeySelective(user);
                        // 更新缓存
                        this.userCache.put(openId, user);
                        this.userCache.put(phone, user);
                    }
                    // 清除验证码
                    this.smsVerifyCodeCache.evict(phone);

                    message.setSuccess(true);

//                    if (phoneUser != null && phoneUser.getId() != user.getId()) {
//                        message.setMsg("手机号码已被绑定，请更换手机号码再次尝试");
//                    } else {
//                        // 设置手机号码
//                        user.setPhone(phone);
//                        // 设置密码
//                        user.setPassword(passwordEncoder.encode(password));
//
//                        // 更新
//                        userService.updateByPrimaryKeySelective(user);
//
//                        // 更新缓存
//                        this.userCache.put(openId, user);
//
//                        // 清除验证码
//                        this.smsVerifyCodeCache.evict(phone);
//
//                        message.setSuccess(true);
//                    }
                }
            }
        }
        return message;
    }

    /**
     * 绑定手机号码
     * <p>
     * 适用于第三方登录，无密码登录流程
     *
     * @param phone  手机号码
     * @param code   短信验证码
     * @param openId 第三方标识
     * @return 绑定结果
     */
    @ApiOperation(value = "绑定手机号码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号码", paramType = "form", required = true),
            @ApiImplicitParam(name = "zone", value = "地区", paramType = "form", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", paramType = "form", required = true),
            @ApiImplicitParam(name = "openId", value = "第三方标识", paramType = "form", required = true)
    })
    @PostMapping("bind-phone-new")
    @Transactional
    public BusinessMessage<JSONObject> bindPhoneNew(String phone, String code, String openId, String zone) {
        log.debug("绑定手机号码，手机号码：{}, 地区() ， 验证码：{}，第三方标识：{}，密码：******", phone, zone, code, openId);
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        if (StringUtils.isBlank(openId)) {
            message.setMsg("第三方标识为空");
        } else if (StringUtils.isBlank(phone)) {
            message.setMsg("手机号码为空");
        } else if (StringUtils.isBlank(code)) {
            message.setMsg("验证码为空");
        } else if (StringUtils.isBlank(zone)){
            message.setMsg("地区为空");
        }else {
            // 校验短信验证码
            String cacheCode = this.smsVerifyCodeCache.get(phone);
            if (StringUtils.isBlank(cacheCode) || !code.contentEquals(cacheCode)) {
                message.setMsg("短信验证码已过期，请重试");
            } else {
                // 从缓存检测用户信息
//                SlUser user = this.userCache.get(openId);
//                // 从数据库查询用户信息
//                if (null == user) {
                SlUser user = this.userService.selectOne(new SlUser() {{
                    setOpenId(openId);
                }});

                if (null != user) {
                    this.userCache.put(openId, user);
                }
//                }

                if (null == user) {
                    message.setMsg("用户信息不存在，请重试");
                } else {
                    SlUser phoneUser = this.userService.selectOne(new SlUser() {{
                        setPhone(phone);
                    }});
                    /** 已经存在相同手机号的另一个用户，open合并到该账号，并删除openId账号 **/
                    if (phoneUser != null && !phoneUser.getId().equals(user.getId())) {
                        //删除原来的openId注册账号
                        this.userDelete(user.getId());
                        //数据合并
                        phoneUser.setOpenId(openId);
                        if (StringUtils.isBlank(phoneUser.getNickName())) {
                            phoneUser.setNickName(user.getNickName());
                        }
                        if (StringUtils.isBlank(phoneUser.getAvatar())) {
                            phoneUser.setAvatar(user.getAvatar());
                        }
                        phoneUser.setZone(zone);
                        phoneUser.setType(user.getType());
                        //修补错误数据，原来的微信用户中的金豆银豆转移
                        phoneUser.setSilver(phoneUser.getSilver() + ((user.getSilver() - 100) < 0 ? 0 : (user.getSilver() - 100)));
                        phoneUser.setCoin(phoneUser.getCoin() + user.getCoin());
                        // 更新
                        userService.updateByPrimaryKeySelective(phoneUser);
                        // 更新缓存
                        this.userCache.put(openId, phoneUser);
                    } else {
                        /********** 不存在相同手机号用户，更新用户信息 *********/
                        user.setPhone(phone);
                        user.setZone(zone);
                        user.setCreatedAt(null);
                        user.setUpdatedAt(null);
                        // 更新
                        userService.updateByPrimaryKeySelective(user);
                        // 更新缓存
                        this.userCache.put(openId, user);
                        this.userCache.put(phone, user);
                    }
                    // 清除验证码
                    this.smsVerifyCodeCache.evict(phone);

                    message.setSuccess(true);
                }
            }
        }
        return message;
    }

    /**
     * 新用户注册福利
     *
     * @param userId 用户标识
     */
    private void sendRegisterGiftToNewUser(String userId) {
        SlTransactionDetail detail = new SlTransactionDetail();
        // 设置hi目标用户
        detail.setTargetId(userId);
        // 消费方式 （1-99：红包、转账业务）1.转账 2. 接收转账 3.发红包 4.抢红包 5.红包过期退回 6.余额提现 （100-199：活动相关） 100：新人礼包 101：签到 102：邀请好友 （200-299：购物相关） 200：购物支付 201：购物赠送 202：评价晒单 （300-400：收益相关）
        detail.setType(100);
        // 赠送100了豆（银豆）
        detail.setSilver(BaseConstant.REGISTER_PEAS);
        // 交易类型 1.支出 2.收入
        detail.setTransactionType(2);
        // 交易货币类型 1.账户余额 2.了豆 3.钱 4.钱+豆
        detail.setDealType(6);
        // 设置创建时间
        detail.setCreateTime(new Date());

        this.slTransactionDetailMapper.insertSelective(detail);
    }

    /**
     * 新增用戶
     *
     * @param user
     */
    private void userInsert(SlUser user) {
        Integer maxUserName = cmUserMapper.selectMaxUserName();
        if (maxUserName == null) {
            maxUserName = 0;
        }
        maxUserName += 6;
        if (String.valueOf(maxUserName).contains("4")) {
            maxUserName = Integer.valueOf(String.valueOf(maxUserName).replaceAll("4", "5"));
        }
        user.setUsername(maxUserName);
        // 添加sl_user
        userService.insertSelective(user);
        //添加sl_member
        SlMember member = new SlMember();
        member.setUserId(user.getId());
        memberService.insertSelective(member);
    }

    /**
     * 删除用户信息
     * 需要删除的表：sl_user、sl_member、sl_transaction_detail
     *
     * @param userId
     */
    private void userDelete(String userId) {
        userService.deleteByPrimaryKey(userId);

        SlMember member = new SlMember();
        member.setUserId(userId);
        memberService.delete(member);

        SlTransactionDetail detail = new SlTransactionDetail();
        detail.setTargetId(userId);
        slTransactionDetailMapper.delete(detail);
    }
}
