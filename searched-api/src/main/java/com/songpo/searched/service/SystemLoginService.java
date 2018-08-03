package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.cache.SmsPasswordCache;
import com.songpo.searched.cache.SmsVerifyCodeCache;
import com.songpo.searched.constant.BaseConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.CmUserMapper;
import com.songpo.searched.mapper.SlSlbTransactionMapper;
import com.songpo.searched.mapper.SlTransactionDetailMapper;
import com.songpo.searched.util.LocalDateTimeUtils;
import com.songpo.searched.util.SLStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.songpo.searched.cache.BaseCache.log;
import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

/**
 * 系统登录注册
 *
 * @author heming
 */
@Service
public class SystemLoginService {
    @Autowired
    private UserService userService;
    @Autowired
    private SmsPasswordCache smsPasswordCache;
    @Autowired
    private CmTotalPoolService cmTotalPoolService;
    @Autowired
    private SlTransactionDetailMapper slTransactionDetailMapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CmUserMapper cmUserMapper;
    @Autowired
    private UserSlbService userSlbService;
    @Autowired
    private SlSlbTransactionMapper slSlbTransactionMapper;
    @Autowired
    private SmsVerifyCodeCache smsVerifyCodeCache;

    /**
     * 微信网页第三方注册
     *
     * @param fromUser
     * @param nickname
     * @param avatar
     * @param phone
     * @param city
     * @param province
     * @param sex
     * @param verificationCode
     * @param zone
     * @return
     */
    @Transactional
    public BusinessMessage<JSONObject> wxWebRegister(String fromUser,String unionId, String  nickname, String avatar, String phone,
                                                     String city, String province, Integer sex, String verificationCode, String zone) {
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        //验证短信验证码
        String code = this.smsPasswordCache.get(phone);
        if (StringUtils.isBlank(code) || !code.contentEquals(verificationCode)) {
            message.setMsg("验证码已过期，请重试");
            return message;
        }
        SlUser userTemp = userService.selectOne(new SlUser() {{
            setFromUser(fromUser);
        }});
        if (userTemp != null) {
            message.setMsg("用户标识已存在");
            return message;
        }
        //根据手机号查询
        SlUser user = userService.selectOne(new SlUser() {{
            setPhone(phone);
        }});
        //如果数据库存在这个手机号用户
        if (null != user) {
            if (!SLStringUtils.isEmpty(user.getFromUser())) {
                message.setMsg("用户已注册");
            } else {
                user.setFromUser(fromUser);
                user.setUnionid(unionId);
                userService.updateByPrimaryKeySelective(user);
                message.setMsg("用户账号合并成功");
                JSONObject data = new JSONObject();
                data.put("userId", user.getId());
                data.put("clientId", user.getClientId());
                data.put("clientSecret", user.getClientSecret());
                message.setData(data);
                message.setSuccess(true);
            }
        } else {
            user = new SlUser();
            String address = "";
            if (!SLStringUtils.isEmpty(province) && !SLStringUtils.isEmpty(city)) {
                address = province + " " + city;
            }
            user.setFromUser(fromUser);
            user.setType(3);
            user.setPhone(phone);
            user.setNickName(nickname);
            user.setUnionid(unionId);
            user.setZone(zone);
            user.setAvatar(avatar);
            user.setAddress(address);
            user.setSex(sex.byteValue());
            user.setCoin(BaseConstant.REGISTER_PEAS);
            // 定义生成字符串范围
            char[][] pairs = {{'a', 'z'}, {'A', 'Z'}, {'0', '9'}};
            // 初始化随机生成器
            RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).filteredBy(LETTERS, DIGITS).build();
            user.setClientId(generator.generate(16));
            user.setClientSecret(generator.generate(64));
            //添加用户
            this.userInsert(user);
            // 天降洪福 乐豆（银豆）
            this.sendRegisterGiftToNewUser(user.getId());
            // 清除验证码
            this.smsPasswordCache.evict(phone);
            //特定时间内 微信网页注册用户赠送B轮的1枚SLB
            Date now = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
            Date beginTime1 = null;
            Date endTime1 = null;
            Date beginTime2 = null;
            Date endTime2 = null;
            try {
                now = df.parse(df.format(now));
                beginTime1 = df.parse("2018-08-03 13:59:59");
                endTime1 = df.parse("2018-08-03 21:01:00");
                beginTime2 = df.parse("2018-08-05 13:59:59");
                endTime2 = df.parse("2018-08-05 21:01:00");
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                Boolean flag1 = LocalDateTimeUtils.belongCalendar(now, beginTime1, endTime1);
                Boolean flag2 = LocalDateTimeUtils.belongCalendar(now, beginTime2, endTime2);
                if (flag1 || flag2) {
                    this.signUpBonusForSlb(user, 2, new BigDecimal(1));
                }
                JSONObject data = new JSONObject();
                data.put("userId", user.getId());
                data.put("clientId", user.getClientId());
                data.put("clientSecret", user.getClientSecret());
                message.setData(data);
                message.setMsg("用户注册成功");
                message.setSuccess(true);
            }

        }
        return message;

    }

    /**
     * 微信网页第三方登录
     *
     * @param fromUser
     * @return
     */
    @Transactional
    public BusinessMessage wxWeblogin(String fromUser) {
        BusinessMessage message = new BusinessMessage<>();
        //根据微信网页唯一标识查询
        SlUser user = userService.selectOne(new SlUser() {{
            setFromUser(fromUser);
        }});
        if (null == user) {
            message.setMsg("用户不存在");
        } else {
            message.setMsg("登录成功");
            message.setSuccess(true);
            message.setData(user);
            //更新登录信息
            user.setLastLogin(new Date());
            Integer loginCount = user.getLoginCount() == null ? 0 : user.getLoginCount();
            user.setLoginCount(loginCount + 1);
            userService.updateByPrimaryKeySelective(user);
        }
        return message;
    }

    /**
     * 微信第三方登录
     *
     * @param openId
     * @param unionId unionId 针对以前IOS端 openId 实际传入的为 unionId 的错误
     * @return code : 1 openId 异常 2 用户未注册 3 用户未绑定手机号 4 登录成功
     */
    @Transactional
    public BusinessMessage wxlogin(String openId, String unionId) {
        BusinessMessage message = new BusinessMessage<>();
        //根据微信openId唯一标识查询
        SlUser user = userService.selectOne(new SlUser() {{
            setOpenId(openId);
        }});
        if (user == null) {
            //根据unionId查询
            SlUser userTemp = userService.selectOne(new SlUser() {{
                setOpenId(unionId);
            }});
            //如果是以前的错误录入数据,更新
            if (userTemp != null) {
                userTemp.setOpenId(openId);
                userTemp.setUnionid(unionId);
                 userService.updateByPrimaryKey(userTemp);
                //userService.updateByPrimaryKeySelective(userTemp);
                user = userTemp;
            }else {
                user = userService.selectOne(new SlUser() {{
                    setUnionid(unionId);
                }});
            }
        }
        //用户未注册
        if (null == user) {
            message.setMsg("用户未注册");
            message.setCode("2");
            log.debug("微信用户openId:{} 登录失败，该用户未注册 ", openId);
        } else {
            //用户没有手机号
            if (SLStringUtils.isEmpty(user.getPhone())) {
                message.setMsg("用户未绑定手机号");
                message.setCode("3");
                log.debug("微信用户openId:{} 登录失败，该用户未绑定手机号 ", openId);
            } else {
                message.setMsg("用户登录成功");
                if (SLStringUtils.isEmpty(user.getPayPassword())){
                    user.setPayPassword("");
                }
                message.setData(user);
                message.setCode("4");
                message.setSuccess(true);
                //如果用户没有unionId
                if (SLStringUtils.isEmpty(user.getUnionid())) {
                    user.setUnionid(unionId);
                }
                //用户登录信息
                user.setLastLogin(new Date());
                Integer loginCount = user.getLoginCount() == null ? 0 : user.getLoginCount();
                user.setLoginCount(loginCount + 1);
                userService.updateByPrimaryKeySelective(user);
                log.debug("微信用户openId:{} 登录成功", openId);
            }
        }
        return message;
    }

    /**
     * 微信第三方注册
     *
     * @param openId
     * @return
     */
    @Transactional
    public BusinessMessage wxRegister(String openId, String unionId, String nickname, String avatar, Integer type,
                                      String phone, String zone, String code) {
        BusinessMessage message = new BusinessMessage<>();
        //根据微信openId唯一标识查询
        SlUser user = userService.selectOne(new SlUser() {{
            setOpenId(openId);
        }});
        //用户已经注册
        if (user != null) {
            message.setMsg("用户已注册,openId已存在");
            message.setSuccess(true);
            return message;
        }
        //根据微信unionId唯一标识查询
        user = userService.selectOne(new SlUser() {{
            setUnionid(unionId);
        }});
        //用户已经注册
        if (user != null) {
            message.setMsg("用户已注册,union已存在");
            return message;
        }
        //验证短信验证码
        String verificationCode = this.smsVerifyCodeCache.get(phone);
        if (SLStringUtils.isEmpty(verificationCode) || !verificationCode.contentEquals(code)) {
            message.setMsg("验证码错误，请重试");
            return message;
        }
        //验证手机号是否被注册
        user = userService.selectOne(new SlUser() {{
            setPhone(phone);
        }});
        if (user != null) {
            //如果微信APP端未注册，通过手机和微信网页注册过  更新openId字段
            if (SLStringUtils.isEmpty(user.getOpenId())) {
                user.setOpenId(openId);
                user.setAvatar(avatar);
                user.setNickName(nickname);
                user.setUnionid(unionId);
                userService.updateByPrimaryKeySelective(user);
                message.setSuccess(true);
                message.setMsg("手机号存在，合并成功");
                // 清除验证码
                this.smsVerifyCodeCache.evict(phone);
            } else {
                message.setMsg("手机号已注册");
            }
            return message;
        } else {
            //用户第一次注册
            user = new SlUser();
            user.setOpenId(openId);
            user.setUnionid(unionId);
            user.setType(type);
            user.setPhone(phone);
            user.setNickName(nickname);
            user.setZone(zone);
            user.setAvatar(avatar);
            user.setCoin(BaseConstant.REGISTER_PEAS);
            // 定义生成字符串范围
            char[][] pairs = {{'a', 'z'}, {'A', 'Z'}, {'0', '9'}};
            // 初始化随机生成器
            RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).filteredBy(LETTERS, DIGITS).build();
            user.setClientId(generator.generate(16));
            user.setClientSecret(generator.generate(64));
            //添加用户
            this.userInsert(user);
            // 天降洪福 乐豆（银豆）
            this.sendRegisterGiftToNewUser(user.getId());
            // 清除验证码
            this.smsVerifyCodeCache.evict(phone);
            message.setMsg("用户注册成功");
            message.setSuccess(true);
            log.debug("微信用户openId:{} 注册账号成功", openId);
        }
        return message;
    }

    /**
     * 微信登录-绑定手机号接口（只针对以前通过微信注册没有绑定手机号的用户）
     *
     * @param openId
     * @param phone
     * @param code
     * @return
     */
    public BusinessMessage bindPhoneForWxLogin(String openId, String phone, String code, String zone) {
        BusinessMessage message = new BusinessMessage();
        //验证短信验证码
        String verificationCode = this.smsVerifyCodeCache.get(phone);
        if (SLStringUtils.isEmpty(verificationCode) || !verificationCode.contentEquals(code)) {
            message.setMsg("验证码错误，请重试");
            return message;
        }
        SlUser userTemp = userService.selectOne(new SlUser() {{
            setPhone(phone);
        }});
        if (userTemp != null) {
            message.setMsg("手机号已经被注册");
            return message;
        }
        SlUser user = userService.selectOne(new SlUser() {{
            setOpenId(openId);
        }});
        if (SLStringUtils.isEmpty(user.getPhone())) {
            user.setPhone(phone);
            user.setZone(zone);
            userService.updateByPrimaryKeySelective(user);
            message.setSuccess(true);
            message.setMsg("绑定手机号码成功，请重新登录");
        } else {
            message.setMsg("绑定失败，该用户已经绑定手机号");
        }
        // 清除验证码
        this.smsVerifyCodeCache.evict(phone);
        return message;
    }

    /**
     * 新用户注册福利
     *
     * @param userId 用户标识
     */
    public void sendRegisterGiftToNewUser(String userId) {
        SlTransactionDetail detail = new SlTransactionDetail();
        // 设置hi目标用户
        detail.setTargetId(userId);
        /** 消费方式 （1-99：红包、转账业务）1.转账 2. 接收转账 3.发红包 4.抢红包 5.红包过期退回
         * 6.余额提现 （100-199：活动相关） 100：新人礼包 101：签到 102：邀请好友 （200-299：购物相关）
         * 200：购物支付 201：购物赠送 202：评价晒单 （300-400：收益相关）
         */
        detail.setType(100);
        /**
         * 2018年7月2日
         * 改为赠送送金豆
         */
        detail.setCoin(BaseConstant.REGISTER_PEAS);
        // 交易类型 1.支出 2.收入
        detail.setTransactionType(2);
        // 交易货币类型 1.账户余额 2.了豆 3.钱 4.钱+豆
        detail.setDealType(5);
        // 设置创建时间
        detail.setCreateTime(new Date());
        //资金池扣除银豆
        cmTotalPoolService.updatePool(BaseConstant.REGISTER_PEAS, null, null, 2, null, userId, 1);
        this.slTransactionDetailMapper.insertSelective(detail);
    }

    /**
     * 新增用戶
     *
     * @param user
     */
    public void userInsert(SlUser user) {
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
     * 注册赠送SLB
     *
     * @param user 用户
     * @param type 轮数 1 A轮 2 B轮 3 C轮 4 D轮 5 E轮
     * @param slb  贝数
     */
    public boolean signUpBonusForSlb(SlUser user, Integer type, BigDecimal slb) {
        boolean flag = false;
        if (user == null) {
            return flag;
        }
        try {
            //添加用户SLB基本信息
            userSlbService.insertSelective(new SlUserSlb() {{
                log.debug("微信注册赠送SLB开始");
                setSlbType(type);
                setUserId(user.getId());
                setSlb(slb);
            }});
            //添加SLB 交易信息
            slSlbTransactionMapper.insertSelective(new SlSlbTransaction() {{
                setSlb(slb);
                setSlbType(type);
                setTargetId(user.getId());
                setType(4);
                setTransactionType(2);
            }});
            //更新资金池信息
            cmTotalPoolService.updatePool(null, null, slb, 2, null, user.getId(), 9);
            flag = true;
        } catch (Exception e) {
            log.error("用户注册赠送SLB失败", e);
        }
        return flag;
    }
}
