package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmTotalPoolService;
import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

//@Api(description = "测试接口")
@Controller
@CrossOrigin
@RequestMapping("/showTest")
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

    @ResponseBody
    @GetMapping("testHtml")
    public String testHtml(HttpServletRequest request){
        //request.setAttribute("key", "hello world");
        return "/test";
    }


}
