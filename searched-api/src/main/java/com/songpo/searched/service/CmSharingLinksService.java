package com.songpo.searched.service;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlSharingLinks;
import com.songpo.searched.mapper.CmSharingLinksMapper;
import com.songpo.searched.mapper.SlSharingLinksMapper;
import com.songpo.searched.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author heming
 * @Date 2018年6月25日15:53:07
 */
@Service
public class CmSharingLinksService {

    @Autowired
    private SharingLinksService sharingLinksService;
    public static final Logger log = LoggerFactory.getLogger(CmSharingLinksService.class);

    @Autowired
    private CmSharingLinksMapper cmSharingLinksMapper;
    /**
     * 添加链接记录
     *
     * @param userId
     * @param productId
     * @return
     */
    @Transactional
    public BusinessMessage insert(String userId, String productId) {
        BusinessMessage message = new BusinessMessage();
        if (StringUtils.isEmpty(userId)|| StringUtils.isEmpty(productId)) {
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
     * 根据用户查询分享链接列表
     * @param userId
     * @return
     */
    public BusinessMessage listByUserId(String userId){
        BusinessMessage message = new BusinessMessage();
        try {
            if  (!StringUtils.isEmpty(userId)){
                List<Map<String,Object>> sharingLilnksList =  cmSharingLinksMapper.listByUserId(userId);
                if (sharingLilnksList.size() > 0 ) {
                    message.setData(sharingLilnksList);
                    message.setMsg("查询成功");
                    message.setSuccess(true);
                }else{
                    message.setMsg("当前用户没有分享链接数据");
                }
            } else {
                message.setMsg("查询失败");
            }
        }catch ( Exception e) {
            log.debug(" 根据用户ID{}查询分享链接列表失败",userId,e);
            message.setMsg("查询异常");
        }
        return message;
    }
}
