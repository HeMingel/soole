package com.songpo.searched.service;

import com.songpo.searched.cache.UserCache;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author  l
 */
@Service
public class LoginUserService {

    public static final Logger log = LoggerFactory.getLogger(LoginUserService.class);

    @Autowired
    private UserCache cache;

    @Autowired
    private UserService service;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    /**
     * 获取当前登录用户
     *
     * @return 用户信息
     */
    public SlUser getCurrentLoginUser() {
        SlUser user = null;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth instanceof OAuth2Authentication) {
                String clientId = ((OAuth2Authentication) auth).getOAuth2Request().getClientId();
                if (StringUtils.isNotEmpty(clientId)) {
//                    // 从缓存检测用户信息
//                    user = this.cache.get(clientId);
//
//                    // 从数据库查询用户信息
//                    if (null == user) {
//                        user = this.service.selectOne(new SlUser() {{
//                            setClientId(clientId);
//                        }});
//
//                        if (null != user) {
//                            this.cache.put(clientId, user);
//                        }
//                    }
                    /** redis数据和数据库数据不一致，直接从数据库查询数据 **/
                    user = this.service.selectOne(new SlUser() {{
                        setClientId(clientId);
                    }});

                    if (null != user) {
                        this.cache.put(clientId, user);
                    }
                }
            } else {
                log.error("识别登录用户信息失败");
            }
        } catch (Exception e) {
            log.error("获取登录用户信息失败，{}", e);
        }
        return user;
    }

    /**
     * 检测用户是否在中军创平台存在
     *
     * @param phone    手机号码
     * @param password 密码
     * @return 检测结果 true：存在 false：不存在
     */
    public Boolean checkUserExistByZjcyy(String phone, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(2);
        //  也支持中文
        params.add("phone", phone);
        params.add("password", password);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<BusinessMessage> responseEntity = this.restTemplate.postForEntity(env.getProperty("sp.zjclogin.url"), entity, BusinessMessage.class);
        return responseEntity.getBody().success;
    }
}
