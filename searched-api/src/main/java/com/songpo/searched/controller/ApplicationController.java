package com.songpo.searched.controller;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlAgentApplication;
import com.songpo.searched.entity.SlBusinessApplication;
import com.songpo.searched.service.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 刘松坡
 */
@Slf4j
@Api(description = "代理入住")
@RestController
@RequestMapping("/api/common/v1/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    /**
     * 提交商户入驻申请
     *
     * @param agent       入驻信息
     * @param idCardFront 身份证正面照片
     * @param idCardBack  身份证反面照片
     * @param idCardHand  手持身份证照片
     * @return 业务消息
     */
    @ApiOperation(value = "提交代理商入驻申请", notes = "用于提交代理商入驻申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province", value = "省份", paramType = "form"),
            @ApiImplicitParam(name = "city", value = "城市", paramType = "form"),
            @ApiImplicitParam(name = "county", value = "区县", paramType = "form"),
            @ApiImplicitParam(name = "ownACompany", value = "是否有公司或团队，0：没有 1：有", paramType = "form"),
            @ApiImplicitParam(name = "real_name", value = "姓名", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "电话", paramType = "form"),
            @ApiImplicitParam(name = "idCardFront", value = "身份证正面照片", paramType = "body", dataType = "file", required = true),
            @ApiImplicitParam(name = "idCardBack", value = "身份证反面照片", paramType = "body", dataType = "file", required = true),
            @ApiImplicitParam(name = "idCardHand", value = "手持身份证照片", paramType = "body", dataType = "file", required = true)
    })
    @GetMapping("agent")
    public BusinessMessage<JSONObject> agentApplication(SlAgentApplication agent, MultipartFile idCardFront, MultipartFile idCardBack, MultipartFile idCardHand) {
        log.debug("提交代理入驻申请，申请信息：{}", agent);
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        if (null == idCardFront || idCardFront.isEmpty()) {
            message.setMsg("身份证正面照片为空");
        } else if (null == idCardBack || idCardBack.isEmpty()) {
            message.setMsg("身份证反面照片为空");
        } else if (null == idCardHand || idCardHand.isEmpty()) {
            message.setMsg("手持身份证照片为空");
        } else {
            try {
                this.applicationService.createAgentApplication(agent, idCardFront, idCardBack, idCardHand);
                message.setSuccess(true);
            } catch (Exception e) {
                log.error("提交代理入驻申请失败，{}", e);

                message.setMsg("提交代理入驻申请失败，请重试");
            }
        }
        return message;
    }

    /**
     * 提交商户入驻申请
     *
     * @param business    入驻信息
     * @param idCardFront 身份证正面照片
     * @param idCardBack  身份证反面照片
     * @param idCardHand  手持身份证照片
     * @return 业务消息
     */
    @ApiOperation(value = "提交商户入驻申请", notes = "用于提交商户入驻申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productTypeId", value = "经营类目", paramType = "form"),
            @ApiImplicitParam(name = "companyAddress", value = "公司地址", paramType = "form"),
            @ApiImplicitParam(name = "companyName", value = "公司名称", paramType = "form"),
            @ApiImplicitParam(name = "employeeCount", value = "员工数量，例如：0-50、50-100", paramType = "form"),
            @ApiImplicitParam(name = "reasonsForClearance", value = "企业需要清仓的原因", paramType = "form"),
            @ApiImplicitParam(name = "productsForClearance", value = "企业需要清仓的产品及数量，例如：苹果 100吨", paramType = "form"),
            @ApiImplicitParam(name = "realName", value = "姓名", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "电话", paramType = "form"),
            @ApiImplicitParam(name = "idCardFront", value = "身份证正面照片", paramType = "body", dataType = "file", required = true),
            @ApiImplicitParam(name = "idCardBack", value = "身份证反面照片", paramType = "body", dataType = "file", required = true),
            @ApiImplicitParam(name = "idCardHand", value = "手持身份证照片", paramType = "body", dataType = "file", required = true)
    })
    @GetMapping("business")
    public BusinessMessage<JSONObject> businessApplication(SlBusinessApplication business, MultipartFile idCardFront, MultipartFile idCardBack, MultipartFile idCardHand) {
        log.debug("提交商户入驻申请，申请信息：{}", business);
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        try {
            this.applicationService.createBusinessApplication(business, idCardFront, idCardBack, idCardHand);
            message.setSuccess(true);
        } catch (Exception e) {
            log.error("提交商户入驻申请失败，{}", e);

            message.setMsg("提交商户入驻申请失败，请重试");
        }
        return message;
    }
}
