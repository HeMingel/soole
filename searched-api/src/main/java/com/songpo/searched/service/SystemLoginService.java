package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.cache.SmsPasswordCache;
import com.songpo.searched.cache.UserCache;
import com.songpo.searched.constant.BaseConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlMember;
import com.songpo.searched.entity.SlTransactionDetail;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.CmUserMapper;
import com.songpo.searched.mapper.SlTransactionDetailMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

/**
 * 系统登录注册
 * @author  heming
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
    private UserCache userCache;

    /**
     * 注册
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
    public BusinessMessage<JSONObject> wxWebRegister(String fromUser, String nickname, String avatar,String phone,
                                                     String city, String province, Integer sex,String verificationCode,String zone) {
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        //验证短信验证码
        String code = this.smsPasswordCache.get(phone);
        if (StringUtils.isBlank(code) || !code.contentEquals(verificationCode)) {
            message.setMsg("验证码已过期，请重试");
            return message;
        }
        //根据手机号查询
        SlUser user =  userService.selectOne( new SlUser(){{
            setPhone(phone);
        }});
        //如果数据库存在这个手机号用户
        if (null != user) {
            if (null != user.getFromUser()) {
                message.setMsg("用户已注册");
            } else {
                user.setFromUser(fromUser);
                userService.updateByPrimaryKeySelective(user);
                this.userCache.put(fromUser, user,86400L);
                message.setMsg("用户账号合并成功");
                JSONObject data = new JSONObject();
                data.put("userId", user.getId());
                data.put("clientId", user.getClientId());
                data.put("clientSecret", user.getClientSecret());
                message.setData(data);
                message.setSuccess(true);
            }
        }else{
            String address = province+ " " +city;
            user.setFromUser(fromUser);
            user.setType(3);
            user.setPhone(phone);
            user.setNickName(nickname);
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
            //添加缓存(一天)
            this.userCache.put(fromUser, user,86400L);
            JSONObject data = new JSONObject();
            data.put("userId", user.getId());
            data.put("clientId", user.getClientId());
            data.put("clientSecret", user.getClientSecret());
            message.setData(data);
            message.setSuccess(true);
        }
        return message;

    }

    /**
     * 登录
     * @param fromUser
     * @return
     */
    @Transactional
    public BusinessMessage wxWeblogin(String fromUser) {
        BusinessMessage message = new BusinessMessage<>();
        //根据微信网页唯一标识查询
        SlUser user = userCache.get(fromUser);
        if (null == user) {
            user = userService.selectOne(new SlUser() {{
            setFromUser(fromUser);
        }});
        }
        if (null == user) {
            message.setMsg("用户不存在");
        } else {
            message.setMsg("登录成功");
            message.setSuccess(true);
            message.setData(user);
            //更新登录信息
            user.setLastLogin(new Date());
            user.setLoginCount(user.getLoginCount()+1);
            userService.updateByPrimaryKeySelective(user);
        }
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
        // 消费方式 （1-99：红包、转账业务）1.转账 2. 接收转账 3.发红包 4.抢红包 5.红包过期退回 6.余额提现 （100-199：活动相关） 100：新人礼包 101：签到 102：邀请好友 （200-299：购物相关） 200：购物支付 201：购物赠送 202：评价晒单 （300-400：收益相关）
        detail.setType(100);
        // 赠送100了豆（银豆）
        //detail.setSilver(BaseConstant.REGISTER_PEAS);
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
        cmTotalPoolService.updatePool(BaseConstant.REGISTER_PEAS,null,null,2,null,userId,1);
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
}
