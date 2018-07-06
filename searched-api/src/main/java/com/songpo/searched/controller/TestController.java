package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmTotalPoolService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Api(description = "测试接口")
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/showTest")
public class TestController {
    @Autowired
    private CmTotalPoolService cmTotalPoolService;

    @GetMapping("test-total-pool")
    public BusinessMessage TestTotalPool(String userId,String orderId,Integer coin ,Integer silver,BigDecimal slb ,Integer addAndSubtract,
                                         Integer type) {
       /* String userId ="4d84a8cd6d6411e886c57cd30abeb1d8";
        String order = "0fb09e6e758a4e2095cbeb8fe31c8074";*/
       return  cmTotalPoolService.updatePool(coin,silver,slb,addAndSubtract,orderId,userId,type);
    }
}
