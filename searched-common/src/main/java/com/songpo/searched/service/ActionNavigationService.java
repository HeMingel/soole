package com.songpo.searched.service;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlActionNavigation;
import com.songpo.searched.mapper.SlActionNavigationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Slf4j
@Service
public class ActionNavigationService extends BaseService<SlActionNavigation, String> {

    public ActionNavigationService(SlActionNavigationMapper mapper) {
        super(mapper);
    }

    /**
     * 查询商品分类banner图 一张
     *
     * @return
     */
    public BusinessMessage goodsCategoryBanner() {
        BusinessMessage<List<SlActionNavigation>> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            Example example = new Example(SlActionNavigation.class);
            example.createCriteria().andEqualTo("typeId", 1);
            List<SlActionNavigation> slActionNavigations = selectByExample(example);
            if (null != slActionNavigations && slActionNavigations.size() > 0) {
                businessMessage.setSuccess(true);
                businessMessage.setMsg("查询成功");
                businessMessage.setData(slActionNavigations);
            } else {
                businessMessage.setSuccess(true);
                businessMessage.setMsg("查询无数据");
            }
        } catch (Exception e) {
            businessMessage.setMsg("查询异常");
            log.error("商品banner图异常", e);
        }
        return businessMessage;
    }
}
