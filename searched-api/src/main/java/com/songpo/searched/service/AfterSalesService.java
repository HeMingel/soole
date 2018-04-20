package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.SlAfterSaleServiceVoucherImageMapper;
import com.songpo.searched.mapper.SlAfterSalesServiceMapper;
import com.songpo.searched.mapper.SlOrderDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AfterSalesService {

    public static final Logger log = LoggerFactory.getLogger(AfterSalesService.class);

    @Autowired
    private SlAfterSalesServiceMapper slAfterSalesServiceMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private SlOrderDetailMapper slOrderDetailMapper;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private SlAfterSaleServiceVoucherImageMapper slAfterSaleServiceVoucherImageMapper;
    @Autowired
    private ShopService shopService;


    /**
     * 新增售后服务单
     *
     * @param slAfterSalesService
     * @param files
     */
    public void insertAfterSales(SlAfterSalesService slAfterSalesService, MultipartFile[] files) {
        log.debug("slAfterSalesService = [" + slAfterSalesService + "], files = [" + files + "]");
        SlUser slUser = loginUserService.getCurrentLoginUser();
        SlOrderDetail detail = this.orderDetailService.selectOne(new SlOrderDetail() {{
            setId(slAfterSalesService.getOrderDetailId());
            setCreator(slUser.getId());
        }});
        if (null != detail) {
            slAfterSalesService.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            slAfterSalesService.setUserId(slUser.getId());
            slAfterSalesService.setMoney(detail.getPrice());
            slAfterSalesService.setProductId(detail.getProductId());
            slAfterSalesService.setCreator(slUser.getId());
            slAfterSalesService.setId(UUID.randomUUID().toString());
            slAfterSalesService.setShopId(detail.getShopId());
            this.slAfterSalesServiceMapper.insertSelective(slAfterSalesService);
            orderDetailService.updateByPrimaryKeySelective(new SlOrderDetail() {{
                setId(detail.getId());
                setShippingState(6);
            }});
        }
        if (files.length <= 3 && files.length > 0) {
            for (MultipartFile image : files) {
                if (null != image && !image.isEmpty()) {
                    String fileUrl = fileService.upload(null, image);
                    slAfterSaleServiceVoucherImageMapper.insertSelective(new SlAfterSaleServiceVoucherImage() {{
                        setAfterSalesServiceId(slAfterSalesService.getId());
                        setId(UUID.randomUUID().toString());
                        setImageUrl(fileUrl);
                    }});
                }
            }
        }
    }

    /**
     * 售后单查询
     *
     * @return
     */
    public BusinessMessage selectAfterSales() {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        JSONObject object = new JSONObject();
        List<JSONObject> dataList = new ArrayList<>();
        List<SlAfterSalesService> list = this.slAfterSalesServiceMapper.select(new SlAfterSalesService() {{
            setUserId(user.getId());
        }});
        if (list.size() > 0) {
            for (SlAfterSalesService service : list) {
                SlOrderDetail slOrderDetail = this.slOrderDetailMapper.selectOne(new SlOrderDetail() {{
                    setId(service.getOrderDetailId());
                }});
                //订单编号
                object.put("serial_number", slOrderDetail.getSerialNumber());
                //商品名称
                object.put("product_name", slOrderDetail.getProductName());
                //商品图片
                object.put("product_image_url", slOrderDetail.getProductImageUrl());
                //组合规格名称
                object.put("product_detail_group_name", slOrderDetail.getProductDetailGroupName());
                //商品价格
                object.put("price", slOrderDetail.getPrice());
                //商品数量
                object.put("quantity", slOrderDetail.getQuantity());
                //审核状态
                object.put("status", service.getAuditStatus());
                //邮费
                object.put("post_fee", slOrderDetail.getPostFee());
                //店铺id
                object.put("shop_id", slOrderDetail.getShopId());
                //店铺图片
                SlShop slShop = this.shopService.selectOne(new SlShop() {{
                    setId(slOrderDetail.getShopId());
                }});
                if (null != slShop) {
                    object.put("shop_image_url", slShop.getImageUrl());
                }
                SlAfterSalesService salesService = this.slAfterSalesServiceMapper.selectOne(new SlAfterSalesService() {{
                    setOrderDetailId(slOrderDetail.getId());
                }});
                if (null != salesService) {
                    object.put("status", salesService.getAuditStatus());
                }
                dataList.add(object);
            }
            message.setMsg("查询成功");
            message.setSuccess(true);
            message.setData(dataList);
        } else {
            message.setSuccess(true);
            message.setMsg("当前没有服务单");
        }
        return message;
    }
}
