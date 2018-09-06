package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.PutForwardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Api(description = "提现")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v1/putforward")
public class PutForwardController {

    public static final Logger log = LoggerFactory.getLogger(PutForwardController.class);

    @Autowired
    private PutForwardService putForwardService;

    /**
     * 提现
     *
     * @param pfUserId
     * @param bankId
     * @param pfMoney
     * @return
     */
    @ApiOperation(value = "提现")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pfUserId", value = "提现用户ID", paramType = "form", required = true),
            @ApiImplicitParam(name = "bankId", value = "用户银行卡ID", paramType = "form", required = true),
            @ApiImplicitParam(name = "pfMoney", value = "提现金额", paramType = "form", required = true)
    })
    @PostMapping("put-forward")
    public BusinessMessage add(String pfUserId, String bankId, String pfMoney) {
        log.debug("提现用户ID，pfUserId = {}, 用户银行卡ID bankId = {}, 提现金额 pfMoney = {}", pfUserId, bankId, pfMoney);
        BusinessMessage message = new BusinessMessage();
        try {
            BigDecimal isMoney = BigDecimal.valueOf(Double.parseDouble(pfMoney));
            if (isMoney.compareTo(new BigDecimal(100)) < 0) {

                message.setMsg("提现失败，需要大于100元!!!");
                return message;
            }

            this.putForwardService.add(pfUserId, bankId, pfMoney);


            message.setSuccess(true);
            message.setMsg("提现成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("提现失败 {}", e);
        }
        return message;
    }

    public void Send() {

    }
}
