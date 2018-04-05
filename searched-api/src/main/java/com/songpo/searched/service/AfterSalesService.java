package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlAfterSalesService;
import com.songpo.searched.entity.SlOrderDetail;
import com.songpo.searched.entity.SlProductRepository;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlAfterSalesServiceMapper;
import com.songpo.searched.mapper.SlOrderDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AfterSalesService {

    @Autowired
    private SlAfterSalesServiceMapper slAfterSalesServiceMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private SlOrderDetailMapper slOrderDetailMapper;

    /**
     * 新增售后服务单
     *
     * @param slAfterSalesService
     * @param file
     */
    public void insertAfterSales(SlAfterSalesService slAfterSalesService, MultipartFile file) {
        SlUser slUser = loginUserService.getCurrentLoginUser();
        if (file.getSize() > 0) {
            String fileUrl = fileService.upload(null, file);
            slAfterSalesService.setImageUrl(fileUrl);
        }
        slAfterSalesService.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        slAfterSalesService.setUserId(slUser.getId());
        slAfterSalesService.setCreator(slUser.getId());
        slAfterSalesService.setId(UUID.randomUUID().toString());
        this.slAfterSalesServiceMapper.insertSelective(slAfterSalesService);
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
                    setOrderId(service.getOrderId());
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