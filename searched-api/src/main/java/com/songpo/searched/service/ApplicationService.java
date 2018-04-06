package com.songpo.searched.service;

import com.songpo.searched.entity.SlAgentApplication;
import com.songpo.searched.entity.SlBusinessApplication;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlAgentApplicationMapper;
import com.songpo.searched.mapper.SlBusinessApplicationMapper;
import com.songpo.searched.typehandler.BusinessApplicationTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

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

    @Autowired
    private CmUserService cmUserService;

    /**
     * 提交代理商入驻申请
     *
     * @param agent      入驻信息
     * @param idCardHand 手持身份证照片
     */
    public void createAgentApplication(SlAgentApplication agent, MultipartFile idCardHand) {
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
     * @param idCardHand    手持身份证照片
     */
    public void createBusinessApplication(SlBusinessApplication business, MultipartFile businessImage, MultipartFile idCardHand) {
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
