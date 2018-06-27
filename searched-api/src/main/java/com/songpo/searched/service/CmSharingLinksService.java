package com.songpo.searched.service;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlSharingLinks;
import com.songpo.searched.mapper.SlSharingLinksMapper;
import com.songpo.searched.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author heming
 * @Date 2018年6月25日15:53:07
 */
@Service
public class CmSharingLinksService {

    public static final Logger log = LoggerFactory.getLogger(CmSharingLinksService.class);

    @Autowired
    private SharingLinksService sharingLinksService;
    /**
     * 添加链接记录
     *
     * @param userId
     * @param productId
     * @return
     */
    public BusinessMessage insert(String userId, String productId) {
        BusinessMessage message = new BusinessMessage();
        if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(productId)) {
            message.setMsg("用户ID和产品ID不能为空");
            return message;
        }
        try {
            SlSharingLinks links = sharingLinksService.
                    selectOne(new SlSharingLinks() {{
                        setProductId(productId);
                        setSharePersonId(userId);
                        setIsFailure((byte) 2);
                    }});
            //如果存在有效记录不进行添加
            if (links == null) {
                int reuslt = sharingLinksService.insertSelective(new SlSharingLinks() {{
                    setProductId(productId);
                    setSharePersonId(userId);
                }});
                if (reuslt > 0) {
                    message.setMsg("链接添加成功");
                }
            } else {
                message.setMsg("链接已存在");
            }
            message.setSuccess(true);
        } catch (Exception e) {
            log.debug("用户{}商品id为{}的分享链接添加失败", userId, productId, e);
            message.setMsg("添加失败");
        }
        return message;
    }

    /**
     * 根据用户查询订单列表列表
     * @param userId
     * @return
     */
    public BusinessMessage listByUserId(String userId){
        BusinessMessage message = new BusinessMessage();
        return message;
    }
}
