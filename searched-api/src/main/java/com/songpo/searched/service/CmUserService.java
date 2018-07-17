package com.songpo.searched.service;

import com.songpo.searched.cache.UserCache;
import com.songpo.searched.constant.BaseConstant;
import com.songpo.searched.entity.SlMember;
import com.songpo.searched.entity.SlTransactionDetail;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.CmUserMapper;
import com.songpo.searched.mapper.SlTransactionDetailMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

/**
 * @author 刘松坡
 */
@Service
public class CmUserService {

    @Autowired
    private UserCache userCache;

    @Autowired
    private UserService userService;
    @Autowired
    private MemberService memberService;

    @Autowired
    private SlTransactionDetailMapper slTransactionDetailMapper;

    @Autowired
    private CmUserMapper cmUserMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private  CmTotalPoolService  cmTotalPoolService;

    /**
     * 根据手机号码查询
     *
     * @param phone 手机号码
     * @return 用户信息
     */
    public SlUser getByPhone(String phone) {
        // 检测账号是否存在，如果不存在，则创建用户
//        SlUser user = this.userCache.get(phone);
//        if (null == user || SLStringUtils.isBlank(user.getId())) {
//            user = this.userService.selectOne(new SlUser() {{
//                setPhone(phone);
//            }});
//            if (null == user || SLStringUtils.isBlank(user.getId())) {
//                this.userCache.put(phone, user);
//            }
//        }
        /** redis数据和数据库数据不一致，直接从数据库查询数据 **/
        SlUser user = this.userService.selectOne(new SlUser() {{
            setPhone(phone);
        }});
        if (null == user || StringUtils.isBlank(user.getId())) {
            this.userCache.put(phone, user);
        }

        return user;
    }

    /**
     * 根据clientId查询
     *
     * @param clientId 客户端标识
     * @return 用户信息
     */
    public SlUser getByClientId(String clientId) {
        // 检测账号是否存在，如果不存在，则创建用户
//        SlUser user = this.userCache.get(clientId);
//        if (null == user || SLStringUtils.isBlank(user.getId())) {
//            user = this.userService.selectOne(new SlUser() {{
//                setClientId(clientId);
//            }});
//            if (null == user || SLStringUtils.isBlank(user.getId())) {
//                this.userCache.put(clientId, user);
//            }
//        }
        /** redis数据和数据库数据不一致，直接从数据库查询数据 **/
        SlUser user = this.userService.selectOne(new SlUser() {{
            setClientId(clientId);
        }});
        if (null == user || StringUtils.isBlank(user.getId())) {
            this.userCache.put(clientId, user);
        }

        return user;
    }

    /**
     * 初始化用户信息
     *
     * @param params 用户参数
     * @return 用户信息
     */
    public SlUser initUser(SlUser params) {
        SlUser user = new SlUser();
        user.setPhone(params.getPhone());
        user.setPassword(passwordEncoder.encode(params.getPassword()));

       /* // 天降洪福，100乐豆（银豆）
        user.setSilver(100);*/
       //改成16金豆
        user.setCoin(BaseConstant.REGISTER_PEAS);



        // 定义生成字符串范围
        char[][] pairs = {{'a', 'z'}, {'A', 'Z'}, {'0', '9'}};
        // 初始化随机生成器
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).filteredBy(LETTERS, DIGITS).build();

        user.setClientId(generator.generate(16));
        user.setClientSecret(generator.generate(64));

        // 添加
//        userService.insertSelective(user);
        this.userInsert(user);

        this.sendRegisterGiftToNewUser(user.getId());

        return user;
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
        detail.setCoin(BaseConstant.REGISTER_PEAS);
        // 交易类型 1.支出 2.收入
        detail.setTransactionType(2);
        // 交易货币类型 1.账户余额 2.了豆 3.钱 4.钱+豆
        detail.setDealType(5);
        // 设置创建时间
        detail.setCreateTime(new Date());

        this.slTransactionDetailMapper.insertSelective(detail);
        cmTotalPoolService.updatePool(BaseConstant.REGISTER_PEAS,null,null,2,null,userId,1);

    }

    /**
     * 新增用戶
     *
     * @param user
     */
    private void userInsert(SlUser user) {
        int maxUserName = cmUserMapper.selectMaxUserName();
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
