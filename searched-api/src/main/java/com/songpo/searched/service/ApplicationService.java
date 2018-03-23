package com.songpo.searched.service;

import com.songpo.searched.entity.SlAgentApplication;
import com.songpo.searched.entity.SlBusinessApplication;
import com.songpo.searched.mapper.SlAgentApplicationMapper;
import com.songpo.searched.mapper.SlBusinessApplicationMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 刘松坡
 */
@Service
public class ApplicationService {

    @Autowired
    private SlAgentApplicationMapper agentApplicationMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private SlBusinessApplicationMapper businessApplicationMapper;

    /**
     * 提交商户入驻申请
     *
     * @param agent       入驻信息
     * @param idCardFront 身份证正面照片
     * @param idCardBack  身份证反面照片
     * @param idCardHand  手持身份证照片
     * @return 1：提交成功 0：提交失败
     */
    public void createAgentApplication(SlAgentApplication agent, MultipartFile idCardFront, MultipartFile idCardBack, MultipartFile idCardHand) {
        // 上传照片
        String idCardFrontImageUrl = this.fileService.upload("agent_application", idCardFront);
        if (StringUtils.isNotBlank(idCardFrontImageUrl)) {
            agent.setIdCardBackImageUrl(idCardFrontImageUrl);
        }

        // 上传照片
        String idCardBackImageUrl = this.fileService.upload("agent_application", idCardBack);
        if (StringUtils.isNotBlank(idCardBackImageUrl)) {
            agent.setIdCardBackImageUrl(idCardBackImageUrl);
        }

        // 上传照片
        String idCardHandImageUrl = this.fileService.upload("agent_application", idCardHand);
        if (StringUtils.isNotBlank(idCardHandImageUrl)) {
            agent.setIdCardBackImageUrl(idCardHandImageUrl);
        }
        this.agentApplicationMapper.insertSelective(agent);
    }

    /**
     * 提交商户入驻申请
     *
     * @param business    入驻信息
     * @param idCardFront 身份证正面照片
     * @param idCardBack  身份证反面照片
     * @param idCardHand  手持身份证照片
     */
    public void createBusinessApplication(SlBusinessApplication business, MultipartFile idCardFront, MultipartFile idCardBack, MultipartFile idCardHand) {
        // 上传照片
        String idCardFrontImageUrl = this.fileService.upload("business_application", idCardFront);
        if (StringUtils.isNotBlank(idCardFrontImageUrl)) {
            business.setIdCardBackImageUrl(idCardFrontImageUrl);
        }

        // 上传照片
        String idCardBackImageUrl = this.fileService.upload("business_application", idCardBack);
        if (StringUtils.isNotBlank(idCardBackImageUrl)) {
            business.setIdCardBackImageUrl(idCardBackImageUrl);
        }

        // 上传照片
        String idCardHandImageUrl = this.fileService.upload("business_application", idCardHand);
        if (StringUtils.isNotBlank(idCardHandImageUrl)) {
            business.setIdCardBackImageUrl(idCardHandImageUrl);
        }
        this.businessApplicationMapper.insertSelective(business);
    }
}
