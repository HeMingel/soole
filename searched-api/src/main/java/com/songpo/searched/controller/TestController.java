package com.songpo.searched.controller;

import com.alipay.api.response.AlipayTradeCustomsQueryResponse;
import com.songpo.searched.alipay.service.AliPayService;
import com.songpo.searched.config.CommonConfig;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlSystemLogs;
import com.songpo.searched.mapper.SlSystemLogsMapper;
import com.songpo.searched.service.CmTotalPoolService;
import com.songpo.searched.util.LocalDateTimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

@Api(description = "测试接口")
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/Test")
public class TestController {
    @Autowired
    private CmTotalPoolService cmTotalPoolService;
    @Autowired
    private SlSystemLogsMapper slSystemLogsMapper;

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

    @GetMapping("test-system-logs")
    public  void  testSystemLogs () {
        Integer   time = (int)(System.currentTimeMillis()/1000);
        slSystemLogsMapper.insertSelective( new SlSystemLogs(){{
             setCreateTime(time);
             setTitle("测试");
             setContent("测试");
             setAction("/test");
             setUsername("6666");
             setUserid(6666);
             setType(true);
        }});
    }
    @Autowired
    private AliPayService aliPayService;
    public static final Logger log = LoggerFactory.getLogger(TestController.class);

    @PostMapping("test-aliPay")
    @ApiOperation("测试支付宝")
    @ApiImplicitParam(name = "orderId", value = "订单", paramType = "form", required = true)
    public void testAliPayTrader(String orderId){
        AlipayTradeCustomsQueryResponse reulst = aliPayService.query(orderId,"");
        log.debug("======================================订单ID{} 的支付支付状态为{}：",orderId,reulst.getSubCode()+"===============================");
    }


}
