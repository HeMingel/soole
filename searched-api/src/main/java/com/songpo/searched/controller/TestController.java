package com.songpo.searched.controller;

import com.alipay.api.response.AlipayTradeCustomsQueryResponse;
import com.songpo.searched.alipay.service.AliPayService;
import com.songpo.searched.config.CommonConfig;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlOrder;
import com.songpo.searched.entity.SlSystemLogs;
import com.songpo.searched.mapper.SlSystemLogsMapper;
import com.songpo.searched.service.CmTotalPoolService;
import com.songpo.searched.service.OrderService;
import com.songpo.searched.service.ProcessOrders;
import com.songpo.searched.util.LocalDateTimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    @Autowired
    private ProcessOrders processOrders;
    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private Environment env;
    public static final Logger log = LoggerFactory.getLogger(TestController.class);


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

    @PostMapping("test-aliPay")
    @ApiOperation("测试支付宝")
    @ApiImplicitParam(name = "orderId", value = "订单", paramType = "form", required = true)
    public void testAliPayTrader(String orderId){
        AlipayTradeCustomsQueryResponse reulst = aliPayService.query(orderId,"");
        log.debug("======================================订单ID{} 的支付支付状态为{}：",orderId,reulst.getSubCode()+"===============================");
    }

    @PostMapping("process-order")
    @ApiOperation("手动处理订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单", paramType = "form", required = true),
            @ApiImplicitParam(name = "key", value = "秘钥", paramType = "form", required = true)
    })
    public BusinessMessage changePaySatus(String orderId,String key) {
        BusinessMessage message = new BusinessMessage();
        if (!env.getProperty("sp.pay.wxpay.apiKey").equals(key)){
            message.setMsg("支付秘钥不对");
            return message;
        }
        SlOrder order = orderService.selectByPrimaryKey(orderId);
        if (order == null) {
            message.setMsg("订单不存在");
            return message;
        }
        if (order.getPaymentState() == 2) {
            log.debug("======================================订单ID{} 的支付宝手动处理订单开始}：",orderId+"===============================");
           processOrders.processOrders(orderId, 2);
           message.setMsg("处理成功");
           message.setSuccess(true);
        }else {
            log.debug("订单 {} 不处于未支付状态",order.getId());
            message.setMsg("订单"+order.getId()+" 不处于未支付状态");
        }
        return message;

    }

}
