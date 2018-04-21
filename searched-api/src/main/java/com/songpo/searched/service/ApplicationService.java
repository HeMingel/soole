package com.songpo.searched.service;

import com.songpo.searched.entity.SlAgentApplication;
import com.songpo.searched.entity.SlBusinessApplication;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlAgentApplicationMapper;
import com.songpo.searched.mapper.SlBusinessApplicationMapper;
import com.songpo.searched.typehandler.BusinessApplicationTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

/**
 * @author 刘松坡
 */
@Service
public class ApplicationService {

    public static final Logger log = LoggerFactory.getLogger(ApplicationService.class);

    private final SlAgentApplicationMapper agentApplicationMapper;

    private final FileService fileService;

    private final SlBusinessApplicationMapper businessApplicationMapper;

    private final CmUserService cmUserService;

    @Autowired
    public ApplicationService(SlAgentApplicationMapper agentApplicationMapper, FileService fileService, SlBusinessApplicationMapper businessApplicationMapper, CmUserService cmUserService) {
        this.agentApplicationMapper = agentApplicationMapper;
        this.fileService = fileService;
        this.businessApplicationMapper = businessApplicationMapper;
        this.cmUserService = cmUserService;
    }

    /**
     * 提交代理商入驻申请
     *
     * @param agent       入驻信息
     * @param idCardFront 身份证正面照片
     * @param idCardBack  身份证反面照片
     * @param idCardHand  手持身份证照片
     */
    public void createAgentApplication(SlAgentApplication agent, MultipartFile idCardFront, MultipartFile idCardBack, MultipartFile idCardHand) {
        log.debug("提交代理商入驻申请， 代理商信息：{}", agent);
        // 上传身份证正面照片
        String idCardFrontImageUrl = this.fileService.upload("agent_application", idCardFront);
        if (StringUtils.isNotBlank(idCardFrontImageUrl)) {
            agent.setIdCardFrontImageUrl(idCardFrontImageUrl);
        }

        // 上传身份证反面照片
        String idCardBackImageUrl = this.fileService.upload("agent_application", idCardBack);
        if (StringUtils.isNotBlank(idCardBackImageUrl)) {
            agent.setIdCardBackImageUrl(idCardBackImageUrl);
        }

        // 上传照片
        String idCardHandImageUrl = this.fileService.upload("agent_application", idCardHand);
        if (StringUtils.isNotBlank(idCardHandImageUrl)) {
            agent.setIdCardHandImageUrl(idCardHandImageUrl);
        }

        // 检测账号是否存在，如果不存在，则创建用户
        SlUser user = this.cmUserService.getByPhone(agent.getPhone());

        if (null == user) {
            // 设置密码为身份证后6位
            String password = StringUtils.substring(agent.getIdCardNumber(), agent.getIdCardNumber().length() - 6, agent.getIdCardNumber().length());

            // 创建用户信息
            user = this.cmUserService.initUser(new SlUser() {{
                setPhone(agent.getPhone());
                setName(agent.getRealName());
                setCardNumber(agent.getIdCardNumber());
                setPassword(password);
            }});
        }

        // 如果用户已经被关联到某个代理商，则无法进行再次关联
        Example example = new Example(SlAgentApplication.class);
        example.createCriteria().andEqualTo("userId", user.getId());
        Integer count = this.agentApplicationMapper.selectCountByExample(example);
        if (count == 0) {
            // 设置为用户标志
            agent.setUserId(user.getId());

            this.agentApplicationMapper.insertSelective(agent);
        }
    }

    /**
     * 提交商户入驻申请
     *
     * @param business      入驻信息
     * @param businessImage 营业执照
     * @param idCardFront   身份证正面照片
     * @param idCardBack    身份证反面照片
     * @param idCardHand    手持身份证照片
     */
    public void createBusinessApplication(SlBusinessApplication business, MultipartFile businessImage, MultipartFile idCardFront, MultipartFile idCardBack, MultipartFile idCardHand) {
        log.debug("提交商户入驻申请， 商户信息：{}", business);
        // 上传照片
        String idCardHandImageUrl = this.fileService.upload("business_application", idCardHand);
        if (StringUtils.isNotBlank(idCardHandImageUrl)) {
            business.setIdCardHandImageUrl(idCardHandImageUrl);
        }

        if (business.getType().equals(BusinessApplicationTypeEnum.ENTERPRISE.getValue())) {
            String businessImageUrl = this.fileService.upload("business_application", businessImage);
            if (StringUtils.isNotBlank(businessImageUrl)) {
                business.setBusinessImageUrl(businessImageUrl);
            }
        } else {
            // 上传身份证正面照片
            String idCardFrontImageUrl = this.fileService.upload("agent_application", idCardFront);
            if (StringUtils.isNotBlank(idCardFrontImageUrl)) {
                business.setIdCardFrontImageUrl(idCardFrontImageUrl);
            }

            // 上传身份证反面照片
            String idCardBackImageUrl = this.fileService.upload("agent_application", idCardBack);
            if (StringUtils.isNotBlank(idCardBackImageUrl)) {
                business.setIdCardBackImageUrl(idCardBackImageUrl);
            }
        }

        // 检测账号是否存在，如果不存在，则创建用户
        SlUser user = this.cmUserService.getByPhone(business.getPhone());

        if (null == user) {
            // 设置密码为身份证后6位
            String password = StringUtils.substring(business.getIdCardNumber(), business.getIdCardNumber().length() - 6, business.getIdCardNumber().length());

            // 创建用户信息
            user = this.cmUserService.initUser(new SlUser() {{
                setPhone(business.getPhone());
                setName(business.getType().equals(BusinessApplicationTypeEnum.ENTERPRISE.getValue()) ? business.getCompanyName() : business.getRealName());
                setCardNumber(business.getIdCardNumber());
                setPassword(password);
            }});
        }

        // 如果用户已经被关联到某个代理商，则无法进行再次关联
        Example example = new Example(SlAgentApplication.class);
        example.createCriteria().andEqualTo("userId", user.getId());
        Integer count = this.businessApplicationMapper.selectCountByExample(example);
        if (count == 0) {
            // 设置为用户标志
            business.setUserId(user.getId());

            this.businessApplicationMapper.insertSelective(business);
        }
    }
}
