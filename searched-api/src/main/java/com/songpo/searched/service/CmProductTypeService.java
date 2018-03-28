package com.songpo.searched.service;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlProductType;
import com.songpo.searched.mapper.CmProductTypeMapper;
import com.songpo.searched.mapper.SlProductTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘松坡
 */
@Service
@Slf4j
public class CmProductTypeService {

    @Autowired
    private CmProductTypeMapper mapper;
    @Autowired
    private SlProductTypeMapper slProductTypeMapper;

    /**
     * 查询商品分类
     *
     * @return 商品分类集合
     */
    public BusinessMessage findAll() {
        BusinessMessage message = new BusinessMessage<>();
        try {
            List<Map<String,Object>> parentMenu = this.mapper.selectAll();
            List list = new ArrayList();
            for(int i=0;i<parentMenu.size();i++){
                Map map = new HashMap();

                List<Map<String,Object>> twoMenu = this.mapper.selectTwoMenu(parentMenu.get(i).get("id").toString());
                map.put("parentMenu",parentMenu.get(i));
                map.put("twoMenu",twoMenu);
                list.add(map);
            }
            message.setData(list);
            message.setSuccess(true);
        } catch (Exception e) {
            log.error("查询商品分类失败，{}", e);

            message.setMsg("查询商品分类失败，请重试");
        }
        return message;
    }

}
