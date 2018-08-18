package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmOrderService;
import com.songpo.searched.service.CmUserSlbService;
import com.songpo.searched.service.ThirdPartyWalletService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;

@Api(description = "用户搜了贝管理")
@RestController
@CrossOrigin
@RequestMapping("/api/common/v2/userSlb")
public class UserSlbController {

    public static final Logger log = LoggerFactory.getLogger(UserSlbController.class);

    @Autowired
    private CmUserSlbService cmUserSlbService;
    @Autowired
    private ThirdPartyWalletService thirdPartyWalletService;
    @Autowired
    public Environment env;

    /**
     * 搜了贝转让
     *
     * @return
     */
    @ApiOperation(value = "搜了贝转让")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "搜了ID", paramType = "form", required = true),
            @ApiImplicitParam(name = "slb", value = "搜了贝", paramType = "form", required = true),
            @ApiImplicitParam(name = "slbType", value = "搜了贝类型", paramType = "form", required = true)
    })
    @PostMapping("transfer-slb")
    public BusinessMessage transferUserSlb(Integer id, BigDecimal slb, Integer slbType) {
        return this.cmUserSlbService.transferUserSlb(id,slb,slbType);
    }

    /**
     * 查询搜了贝
     *
     * @return
     */
    @ApiOperation(value = "查询搜了贝 ")
    @GetMapping("select-slb")
    public BusinessMessage selectUserSlb() {
        return this.cmUserSlbService.selectUserSlb();
//        return  thirdPartyWalletService.getSlbScAmount();
    }

    /**
     * 查询搜了贝详情
     *
     * @return
     */
    @ApiOperation(value = "查询搜了贝详情 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "form", required = true),
            @ApiImplicitParam(name = "slbType", value = "搜了贝类型", paramType = "form", required = true)
    })
    @PostMapping("select-slb-detail")
    public BusinessMessage selectUserSlbDetail(String userId, Integer slbType) {
        return this.cmUserSlbService.selectUserSlbDetail(userId, slbType);
    }

    /**
     * 金币兑换搜了贝
     * @param userId         用户ID
     * @param transfAmount  兑换slb数量
     */
    @ApiOperation(value = "金币兑换搜了贝 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "form", required = true),
            @ApiImplicitParam(name = "transfAmount", value = "兑换slb数量", paramType = "form", required = true)
    })
    @PostMapping("coin-to-slb")
    public BusinessMessage transferToSLB(String userId, String transfAmount) {
        String platTransPwd = env.getProperty("wallet.platTransPwd");
        return this.thirdPartyWalletService.transferToSLB(userId, platTransPwd, transfAmount);
    }
    /**
     * 查找搜了贝汇率
     */
    @ApiOperation(value = "查找搜了贝汇率 ")
    @GetMapping("select-slb-rate")
    public BusinessMessage selectSlbRate() {
        return this.thirdPartyWalletService.selectSlbRate();
    }
}
