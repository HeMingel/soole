package com.songpo.searched.oauth2;

import com.songpo.searched.cache.UserCache;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author SongpoLiu
 */
@Slf4j
@Service
public class MyClientDetailsService implements ClientDetailsService {

    @Autowired
    private SlUserMapper mapper;

    @Autowired
    private UserCache cache;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.debug("客户端登录，标识：{}", clientId);
        BaseClientDetails details = null;
        if (StringUtils.isNotBlank(clientId)) {
            SlUser user = this.cache.get(clientId);
            if (null == user) {
                user = this.mapper.selectOne(new SlUser() {{
                    setClientId(clientId);
                }});

                // 加入缓存
                if (null != user) {
                    this.cache.put(clientId, user);
                }
            }

            if (null != user) {
                details = new BaseClientDetails();
                details.setClientId(user.getClientId());
                details.setClientSecret(passwordEncoder.encode(user.getClientSecret()));
                // 默认保存15天
                details.setAccessTokenValiditySeconds(60 * 60 * 24 * 15);
                details.setAuthorizedGrantTypes(Arrays.asList("client_credentials", "password", "implicit", "authorization_code"));
                details.setScope(Arrays.asList("read", "write"));
            }
        }
        return details;
    }
}