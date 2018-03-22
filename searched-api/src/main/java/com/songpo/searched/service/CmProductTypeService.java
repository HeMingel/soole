package com.songpo.searched.service;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.domain.ProductCategoryDto;
import com.songpo.searched.entity.SlProductType;
import com.songpo.searched.mapper.CmProductTypeMapper;
import com.songpo.searched.mapper.SlProductTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CmProductTypeService extends BaseService<SlProductType, String> {

    @Autowired
    private SlProductTypeMapper slProductTypeMapper;

    @Autowired
    private CmProductTypeService productTypeService;

    @Autowired
    private CmProductTypeMapper productTypeMapper;

    public CmProductTypeService(SlProductTypeMapper mapper) {
        super(mapper);
    }

    /**
     * 查询商品一级分类
     *
     * @return
     */
    public BusinessMessage findCategory(String parentId) {
        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try {
            if (parentId == null) {
                List<SlProductType> slProductTypeList = this.productTypeService.select(new SlProductType() {{
                    setParentId(null);
                }});
                businessMessage.setData(slProductTypeList);
                businessMessage.setSuccess(true);
                businessMessage.setMsg("查询成功");
            } else {
                List<Map<String, Object>> map = this.productTypeMapper.findCategoryByParentId(parentId);
                if (map.size() > 0) {
                    businessMessage.setData(map);
                    businessMessage.setSuccess(true);
                    businessMessage.setMsg("查询成功");
                } else {
                    businessMessage.setSuccess(true);
                    businessMessage.setMsg("查询无数据!");
                }
            }
        } catch (Exception e) {
            businessMessage.setMsg("查询分类失败");
        }
        return businessMessage;
    }


}
