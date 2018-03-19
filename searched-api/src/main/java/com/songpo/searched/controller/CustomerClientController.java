package com.songpo.searched.controller;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CustomerClientHomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户的API控制层
 *
 * @author 刘松坡
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/customer-client")
public class CustomerClientController {

    @Autowired
    private CustomerClientHomeService customerClientHomeService;


    /**
     * 获取首页所有数据
     *
     * @return
     */
    @RequestMapping("home")
    public BusinessMessage<JSONObject> home() {
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        try {
            message.setData(this.customerClientHomeService.getHomeData());
            message.setSuccess(true);
        } catch (Exception e) {
            log.error("获取用户端首页数据失败，{}", e);

            message.setMsg("获取数据失败，请重试");
        }
        return message;
    }

}
