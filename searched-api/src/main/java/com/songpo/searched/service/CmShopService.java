package com.songpo.searched.service;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.entity.SlShop;
import com.songpo.searched.entity.SlShopLookNum;
import com.songpo.searched.mapper.SlProductMapper;
import com.songpo.searched.mapper.SlShopLookNumMapper;
import com.songpo.searched.mapper.SlShopMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class CmShopService {

    @Autowired
    private SlShopMapper slShopMapper;
    @Autowired
    private SlProductMapper slProductMapper;
    @Autowired
    private SlShopLookNumMapper slShopLookNumMapper;

    /**
     * 根据店铺Id查询店铺详情和商品
     * @param id
     * @return
     */
    public BusinessMessage shopAndGoods(String id,String userId) {
        log.debug("商户Id:{},用户id:{}",id,userId);

        BusinessMessage<Map<String,Object>> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try{
            Map<String,Object> map = new HashMap<>();
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
            if(slProductList.size()>0){
                map.put("shopGoods",slProductList);
            }else {
                map.put("shopGoods","查询无商品");
            }
            if(userId != null){
                Date date = new Date();
                SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd");

                SlShopLookNum slShopLookNum = this.slShopLookNumMapper.selectOne(new SlShopLookNum() {{
                    setDay(time.format(date));
                    setUserId(userId);
                }});
                if(slShopLookNum == null) {
                    this.slShopLookNumMapper.insert(new SlShopLookNum() {{
                        setUserId(userId);
                        setDay(time.format(date));
                        setShopId(id);
                        setId(UUID.randomUUID().toString());
                    }});
                }
            }

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
