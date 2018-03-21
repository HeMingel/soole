package com.songpo.searched.service;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.entity.SlShop;
import com.songpo.searched.mapper.SlProductMapper;
import com.songpo.searched.mapper.SlShopMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CmShopService {

    @Autowired
    private SlShopMapper slShopMapper;
    @Autowired
    private SlProductMapper slProductMapper;

    /**
     * 根据店铺Id查询店铺详情和商品
     * @param id
     * @return
     */
    public BusinessMessage shopAndGoods(String id) {
        log.debug("商户Id:{}",id);

        BusinessMessage businessMessage = new BusinessMessage();
        businessMessage.setSuccess(false);
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            //查询商铺
            SlShop shop = this.slShopMapper.selectByPrimaryKey(new SlShop () {{
                setId(id);
            }});
            if(null == shop){
                businessMessage.setMsg("未找到该商铺");
                return businessMessage;
            }
            map.put("shopDetail",shop);
            //查询店铺商品
            Example example = new Example(SlProduct.class);
            example.createCriteria().andEqualTo("shopId",id);
            List<SlProduct> slProductList = this.slProductMapper.selectByExample(example);
            map.put("shopGoods",slProductList);

            businessMessage.setData(map);
            businessMessage.setSuccess(true);
            businessMessage.setMsg("查询成功");
        }catch (Exception e){
            businessMessage.setMsg("查询失败");
            log.error("商铺查询失败:{}",e);
        }
        return businessMessage;
    }
}
