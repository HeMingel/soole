package com.songpo.searched.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

public class HttpRequest {

    private RestTemplate restTemplate;

    public HttpRequest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static String addUrl(String head, String tail) {
        if (head.endsWith("/")) {
            if (tail.startsWith("/")) {
                return head.substring(0, head.length() - 1) + tail;
            } else {
                return head + tail;
            }
        } else {
            if (tail.startsWith("/")) {
                return head + tail;
            } else {
                return head + "/" + tail;
            }
        }
    }

    public synchronized String postData(String url, Map<String, String> params, String codePage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>(params.size());
        params.forEach((k, v) -> {
            multiValueMap.put(k, Arrays.asList(v));
        });
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(params, headers);

        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(url, entity, String.class);
        return responseEntity.getBody();
    }

    public synchronized String postData(String url, String codePage) {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(url, String.class);
        return responseEntity.getBody();
    }

}
