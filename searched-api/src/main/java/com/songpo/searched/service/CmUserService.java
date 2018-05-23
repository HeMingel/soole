package com.songpo.searched.service;

import com.songpo.searched.cache.UserCache;
import com.songpo.searched.entity.SlUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 根据手机号码查询
     *
     * @param phone 手机号码
     * @return 用户信息
     */
    public SlUser getByPhone(String phone) {
        // 检测账号是否存在，如果不存在，则创建用户
        SlUser user = this.userCache.get(phone);
        if (null == user || StringUtils.isBlank(user.getId())) {
            user = this.userService.selectOne(new SlUser() {{
                setPhone(phone);
            }});
        }

        return user;
    }

    /**
     * 根据手机号码查询
     *
     * @param clientId 客户端标识
     * @return 用户信息
     */
    public SlUser getByClientId(String clientId) {
        // 检测账号是否存在，如果不存在，则创建用户
        SlUser user = this.userCache.get(clientId);
        if (null == user) {
            user = this.userService.selectOne(new SlUser() {{
                setClientId(clientId);
            }});
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

        // 定义生成字符串范围
        char[][] pairs = {{'a', 'z'}, {'A', 'Z'}, {'0', '9'}};
        // 初始化随机生成器
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).filteredBy(LETTERS, DIGITS).build();

        user.setClientId(generator.generate(16));
        user.setClientSecret(generator.generate(64));

        // 添加
        userService.insertSelective(user);
        return user;
    }
}
