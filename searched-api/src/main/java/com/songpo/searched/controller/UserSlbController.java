package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmOrderService;
import com.songpo.searched.service.CmUserSlbService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("transfer-slb")
    public BusinessMessage transferUserSlb(Integer id, BigDecimal slb, Integer slbType) {
        return this.cmUserSlbService.transferUserSlb(id,slb,slbType);
    }

}
