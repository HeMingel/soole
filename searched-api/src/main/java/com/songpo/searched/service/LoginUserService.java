package com.songpo.searched.service;

import com.songpo.searched.cache.UserCache;
import com.songpo.searched.entity.SlUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginUserService {

    @Autowired
    private UserCache cache;

    @Autowired
    private UserService service;

    /**
     * 获取当前登录用户
     *
     * @return 用户信息
     */
    public SlUser getCurrentLoginUser() {
        SlUser user = null;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (null != auth) {
                String clientId = ((OAuth2Authentication) auth).getOAuth2Request().getClientId();
                if (StringUtils.isNotEmpty(clientId)) {
                    // 从缓存检测用户信息
                    user = this.cache.get(clientId);

                    // 从数据库查询用户信息
                    if (null == user) {
                        user = this.service.selectOne(new SlUser() {{
                            setClientId(clientId);
                        }});

                        if (null != user) {
                            this.cache.put(clientId, user);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取登录用户信息失败，{}", e);
        }
        return user;
    }

}
