package com.songpo.searched.controller;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlAgentApplication;
import com.songpo.searched.entity.SlBusinessApplication;
import com.songpo.searched.service.ApplicationService;
import com.songpo.searched.typehandler.BusinessApplicationTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 刘松坡
 */
@Api(description = "代理入住")
@RestController
@RequestMapping("/api/common/v1/application")
public class ApplicationController {

    public static final Logger log = LoggerFactory.getLogger(ApplicationController.class);

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * 提交商户入驻申请
     *
     * @param agent       入驻信息
     * @param idCardHand  手持身份证照片
     * @return 业务消息
     */
    @ApiOperation(value = "提交代理商入驻申请", notes = "用于提交代理商入驻申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "province", value = "省份", paramType = "form", required = true),
            @ApiImplicitParam(name = "city", value = "城市", paramType = "form", required = true),
            @ApiImplicitParam(name = "country", value = "区县", paramType = "form", required = true),
            @ApiImplicitParam(name = "town", value = "乡镇", paramType = "form"),
            @ApiImplicitParam(name = "street", value = "村、街道", paramType = "form"),
            @ApiImplicitParam(name = "otherAddress", value = "剩余地址信息", paramType = "form"),
            @ApiImplicitParam(name = "level", value = "申请级别：1 省级代理商，2 市级代理商，3 县级代理商，4 乡级代理商，5 村级代理商", paramType = "form", required = true),
            @ApiImplicitParam(name = "realName", value = "姓名", paramType = "form", required = true),
            @ApiImplicitParam(name = "phone", value = "电话", paramType = "form", required = true),
            @ApiImplicitParam(name = "idCardNumber", value = "身份证号", paramType = "form", required = true),
            @ApiImplicitParam(name = "idCardFront", value = "身份证正面照片", paramType = "form", dataType = "file", required = true),
            @ApiImplicitParam(name = "idCardBack", value = "身份证反面照片", paramType = "form", dataType = "file", required = true),
            @ApiImplicitParam(name = "idCardHand", value = "手持身份证照片", paramType = "form", dataType = "file", required = true)
    })
    @PostMapping("agent")
    public BusinessMessage<JSONObject> agentApplication(SlAgentApplication agent, MultipartFile idCardFront, MultipartFile idCardBack, MultipartFile idCardHand) {
        log.debug("提交代理入驻申请，申请信息：{}", agent);
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        if (StringUtils.isBlank(agent.getProvince())) {
            message.setMsg("省份为空");
        } else if (StringUtils.isBlank(agent.getCity())) {
            message.setMsg("城市为空");
        } else if (StringUtils.isBlank(agent.getCountry())) {
            message.setMsg("区县为空");
        } else if (null == agent.getLevel()) {
            message.setMsg("申请级别为空");
        } else if (StringUtils.isBlank(agent.getRealName())) {
            message.setMsg("姓名为空");
        } else if (StringUtils.isBlank(agent.getPhone())) {
            message.setMsg("电话信息为空");
        } else if (StringUtils.isBlank(agent.getIdCardNumber())) {
            message.setMsg("身份证号为空");
        } else if (null == idCardFront || idCardFront.isEmpty()) {
            message.setMsg("身份证正面照片为空");
        } else if (null == idCardBack || idCardBack.isEmpty()) {
            message.setMsg("身份证反面照片为空");
        } else if (null == idCardHand || idCardHand.isEmpty()) {
            message.setMsg("手持身份证照片为空");
        } else {
            try {
                if (this.applicationService.agentExists(agent.getPhone()) > 0) {
                    message.setMsg("申请信息已存在，请勿重复提交");
                } else {
                    this.applicationService.createAgentApplication(agent, idCardFront, idCardBack, idCardHand);
                    message.setSuccess(true);
                }
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
     * @param idCardHand  手持身份证照片
     * @return 业务消息
     */
    @ApiOperation(value = "提交商户入驻申请", notes = "用于提交商户入驻申请", consumes = "multipart/form-data")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productTypeId", value = "店铺类别", paramType = "form", required = true),
            @ApiImplicitParam(name = "province", value = "省份", paramType = "form", required = true),
            @ApiImplicitParam(name = "city", value = "城市", paramType = "form", required = true),
            @ApiImplicitParam(name = "country", value = "区县", paramType = "form", required = true),
            @ApiImplicitParam(name = "town", value = "乡镇", paramType = "form"),
            @ApiImplicitParam(name = "street", value = "街道", paramType = "form"),
            @ApiImplicitParam(name = "otherAddress", value = "剩余地址信息", paramType = "form"),
            @ApiImplicitParam(name = "realName", value = "姓名，申请类型为个人必填", paramType = "form"),
            @ApiImplicitParam(name = "companyName", value = "企业名称，申请类型为企业时必填", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "电话", paramType = "form", required = true),
            @ApiImplicitParam(name = "type", value = "申请类型：1 个人、2 企业", paramType = "form", required = true),
            @ApiImplicitParam(name = "businessImage", value = "营业执照照片，申请类型为企业时必传", paramType = "form", dataType = "file"),
            @ApiImplicitParam(name = "idCardNumber", value = "身份证号", paramType = "form", required = true),
            @ApiImplicitParam(name = "idCardFront", value = "身份证正面照片，申请类型为个人时必传", paramType = "form", dataType = "file"),
            @ApiImplicitParam(name = "idCardBack", value = "身份证反面照片，申请类型为个人时必传", paramType = "form", dataType = "file"),
            @ApiImplicitParam(name = "idCardHand", value = "手持身份证照片", paramType = "form", dataType = "file", required = true)
    })
    @PostMapping(value = "business")
    public BusinessMessage<JSONObject> businessApplication(SlBusinessApplication business, MultipartFile businessImage, MultipartFile idCardFront, MultipartFile idCardBack, MultipartFile idCardHand) {
        log.debug("提交商户入驻申请，申请信息：{}", business);
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        if (StringUtils.isBlank(business.getProvince())) {
            message.setMsg("省份为空");
        } else if (StringUtils.isBlank(business.getCity())) {
            message.setMsg("城市为空");
        } else if (StringUtils.isBlank(business.getCountry())) {
            message.setMsg("区县为空");
        } else if (null == business.getType()) {
            message.setMsg("申请级别为空");
        } else if (StringUtils.isBlank(business.getPhone())) {
            message.setMsg("电话信息为空");
        } else if (StringUtils.isBlank(business.getIdCardNumber())) {
            message.setMsg("身份证号为空");
        } else if (null == idCardHand || idCardHand.isEmpty()) {
            message.setMsg("手持身份证照片为空");
        } else {
            try {
                if (this.applicationService.businessExists(business.getPhone()) > 0) {
                    message.setMsg("申请信息已存在，请勿重复提交");
                } else {
                    if (business.getType().equals(BusinessApplicationTypeEnum.PERSONAL.getValue())) {
                        if (StringUtils.isBlank(business.getRealName())) {
                            message.setMsg("姓名为空");
                        } else {
                            this.applicationService.createBusinessApplication(business, businessImage, idCardFront, idCardBack, idCardHand);
                            message.setSuccess(true);
                        }
                    }

                    if (business.getType().equals(BusinessApplicationTypeEnum.ENTERPRISE.getValue())) {
                        if (StringUtils.isBlank(business.getCompanyName())) {
                            message.setMsg("公司名称为空");
                        } else if (null == businessImage || businessImage.isEmpty()) {
                            message.setMsg("营业执照为空");
                        } else {
                            this.applicationService.createBusinessApplication(business, businessImage, idCardFront, idCardBack, idCardHand);
                            message.setSuccess(true);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("提交商户入驻申请失败，{}", e);

                message.setMsg("提交商户入驻申请失败，请重试");
            }
        }
        return message;
    }
}
