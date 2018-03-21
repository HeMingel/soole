package com.songpo.searched.service;

import com.songpo.searched.cache.UserCache;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @author SongpoLiu
 */
@Service
public class MyClientDetailsService implements ClientDetailsService {

    @Autowired
    private SlUserMapper mapper;

    @Autowired
    private UserCache cache;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BaseClientDetails details = null;

        SlUser user = this.cache.get(clientId);
        if (null == user) {
            user = this.mapper.selectOne(new SlUser() {{
                setPhone(clientId);
            }});

            // 加入缓存
            if (null != user) {
                this.cache.put(clientId, user);
            }
        }

        if (null != user) {
            details = new BaseClientDetails();
            details.setClientId(user.getClientId());
            details.setClientSecret(user.getClientSecret());
            // 默认保存15天
            details.setAccessTokenValiditySeconds(60 * 60 * 24 * 15);
            details.setAuthorizedGrantTypes(Arrays.asList("client_credentials", "password", "implicit", "authorization_code"));
            details.setScope(StringUtils.commaDelimitedListToSet("read"));
        }
        return details;
    }
}