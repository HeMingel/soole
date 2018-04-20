package com.songpo.searched.service;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlActivityProduct;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.entity.SlShop;
import com.songpo.searched.entity.SlShopLookNum;
import com.songpo.searched.mapper.SlActivityProductMapper;
import com.songpo.searched.mapper.SlProductMapper;
import com.songpo.searched.mapper.SlShopLookNumMapper;
import com.songpo.searched.mapper.SlShopMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CmShopService {

    public static final Logger log = LoggerFactory.getLogger(CmShopService.class);

    @Autowired
    private SlShopMapper slShopMapper;
    @Autowired
    private SlProductMapper slProductMapper;
    @Autowired
    private SlShopLookNumMapper slShopLookNumMapper;
    @Autowired
    private SlActivityProductMapper activityProductMapper;

    /**
     * 根据店铺Id查询店铺详情和商品
     * @param id
     * @return
     */

    public BusinessMessage shopAndGoods(String id,String userId) {
        log.debug("商户Id:{},用户id:{}",id,userId);

        BusinessMessage<Object> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try{
            //查询商铺
            SlShop shop = this.slShopMapper.selectByPrimaryKey(new SlShop () {{
                setId(id);
            }});
            if(null == shop){
                businessMessage.setMsg("未找到该商铺");
                return businessMessage;
            }
            Example example = new Example(SlProduct.class);
            example.createCriteria().andEqualTo("soldOut",1).andEqualTo("shopId",id);
            List<SlProduct> productList = this.slProductMapper.selectByExample(example);
            List<Object> goodsList = new ArrayList<>();
            for (int i=0;i<productList.size();i++){
                Map<String,Object> activityProduct = new HashMap<>();
                Example apExample = new Example(SlActivityProduct.class);
                apExample.createCriteria().andEqualTo("productId",productList.get(i).getId()).andEqualTo("enabled",1);
                List<SlActivityProduct> activityProductList = this.activityProductMapper.selectByExample(apExample);
                activityProduct.put("activityProduct",activityProductList);
                activityProduct.put("goodsBaseInfo",productList);
                goodsList.add(activityProduct);
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

            businessMessage.setData(goodsList);
            businessMessage.setSuccess(true);
            businessMessage.setMsg("查询成功");
        }catch (Exception e){
            businessMessage.setMsg("查询失败");
            log.error("商铺查询失败:{}",e);
        }
        return businessMessage;
    }
}
