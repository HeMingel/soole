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
     * @param idCardHand  手持身份证照片
     */
    public void createAgentApplication(SlAgentApplication agent, MultipartFile idCardHand) {
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
     * @param idCardHand  手持身份证照片
     */
    public void createBusinessApplication(SlBusinessApplication business, MultipartFile idCardHand) {
        // 上传照片
        String idCardHandImageUrl = this.fileService.upload("business_application", idCardHand);
        if (StringUtils.isNotBlank(idCardHandImageUrl)) {
            business.setIdCardBackImageUrl(idCardHandImageUrl);
        }
        this.businessApplicationMapper.insertSelective(business);
    }
}
