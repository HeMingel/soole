package com.songpo.searched.service;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.constant.SalesModeConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    @Autowired
    private CmProductMapper mapper;
    @Autowired
    private SlMyCollectionMapper slMyCollectionMapper;
    @Autowired
    private  SlActivitySeckillMapper slActivitySeckillMapper;
    @Autowired
    private  CmActivitySeckillMapper cmActivitySeckillMapper;

    /**
     * 根据店铺Id查询店铺详情和商品
     * @param shopId 店铺Id
     * @param userId 用户id
     * @param goodsName 商品名称
     * @param sortBySale 根据销量排序
     * @param sortByPrice 根据价格排序
     * @param pageNum
     * @param pageSize
     * @return 店铺商品
     */

    public BusinessMessage shopAndGoods(String shopId,String userId,String goodsName,String sortBySale,String sortByPrice,Integer pageNum,Integer pageSize) {
        log.debug("商户Id:{},用户id:{},商品名称:{},销售排序:{},价格排序:{}",shopId,userId,goodsName,sortBySale,sortByPrice);

        BusinessMessage<Object> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        JSONObject data = new JSONObject();
        try{

            //查询商铺 判断是否有这家店
            SlShop shop = this.slShopMapper.selectByPrimaryKey(new SlShop () {{
                setId(shopId);
            }});
            if(null == shop){
                businessMessage.setMsg("未找到该商铺");
                return businessMessage;
            }
            //查询收藏的数量
            //1：收藏的为店铺
            //2：收藏的为商品
            Byte type =1;
             int count = this.slMyCollectionMapper.selectCount(new SlMyCollection(){{
                 setType(type);
                 setCollectionId(shopId);
             }});


            if (null == pageNum || pageNum <= 1) {
                pageNum = 1;
            }

            if (null == pageSize || pageSize <= 1) {
                pageSize = 10;
            }

            // 排序规则字符串
            String[] orderStrArray = new String[]{"DESC", "desc", "ASC", "asc"};

            // 过滤价格排序规则中的非法字符
            if (!StringUtils.containsAny(sortByPrice, orderStrArray)) {
                sortByPrice = StringUtils.trimToEmpty(sortByPrice);
            }

            //过滤销售数量排序中的非法字符
            if(!StringUtils.containsAny(sortBySale, orderStrArray)){
                sortBySale = StringUtils.trimToEmpty(sortBySale);
            }

            // 设置分页参数
            PageHelper.startPage(pageNum, pageSize);

            //执行查询 查询该商铺商品
            List<Map<String,Object>> goodsList = this.mapper.selectShopGoods(shopId,goodsName,sortBySale,sortByPrice);
            //如果是拼团产品 需要查询拼团产品的头像
            for (Map<String,Object> map : goodsList){
                //判断
                if (Integer.parseInt(map.get("sales_mode_id").toString()) == SalesModeConstant.SALES_MODE_GROUP ){
                    //查询拼团商品拼主的头像 在订单表有记录
                    List<Map<String,Object>> avatarList = this.mapper.selectGroupAvatar(map.get("goods_id").toString());
                    if (avatarList.size() >0){
                        map.put("avatarList",avatarList);
                    }
                }
                //如果是显示秒杀商品
                if (Integer.parseInt(map.get("sales_mode_id").toString()) == SalesModeConstant.SALES_MODE_SECKILL) {
                    List<SlActivitySeckill> slActivitySeckills = this.cmActivitySeckillMapper.selectActivitySeckill(map.get("goods_id").toString());
                    if (slActivitySeckills.size()>0){
                        map.put("slActivitySeckills",slActivitySeckills);
                    }
                }
            }
            //涉及到访问量
            if(userId != null) {
                Date date = new Date();
                SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");

                SlShopLookNum slShopLookNum = this.slShopLookNumMapper.selectOne(new SlShopLookNum() {{
                    setDay(time.format(date));
                    setUserId(userId);
                }});
                if (slShopLookNum == null) {
                    this.slShopLookNumMapper.insert(new SlShopLookNum() {{
                        setUserId(userId);
                        setDay(time.format(date));
                        setShopId(shopId);
                        setId(UUID.randomUUID().toString());
                    }});
                }
            }
            //查询访问量 包含同一个人(需求不明)
            int lookNum = this.slShopLookNumMapper.selectCount(new SlShopLookNum(){{
                setShopId(shopId);
            }});

                data.put("shopDetail",shop);
                data.put("collectionNum",count);
                data.put("goodsInfo",new PageInfo<>(goodsList));
                data.put("lookNum",lookNum);

                businessMessage.setData(data);
                businessMessage.setSuccess(true);
                businessMessage.setMsg("查询成功");

        }catch (Exception e){
            businessMessage.setMsg("查询失败");
            log.error("商铺查询失败:{}",e);
        }
        return businessMessage;
    }

    /**
     *
     * @param userId 用户Id
     * @param shopId 收藏信息
     * @return
     */
    public boolean isCollection(String userId,String shopId) {

        List<SlMyCollection> slMyCollections = this.slMyCollectionMapper.select(new SlMyCollection(){{
            setUserId(userId);
            setCollectionId(shopId);
        }});
        if(slMyCollections.size()>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 查询优质店铺
     * @return 店铺商品
     */

    public BusinessMessage selectGroomShop() {
        log.debug("查询优质店铺");

        BusinessMessage<Object> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try{
            businessMessage.setData(this.mapper.selectGroomShop());
            businessMessage.setSuccess(true);
            businessMessage.setMsg("查询成功");

        }catch (Exception e){
            businessMessage.setMsg("查询失败");
            log.error("商铺查询失败:{}",e);
        }
        return businessMessage;
    }
}
