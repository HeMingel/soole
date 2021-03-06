package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlAfterSalesService;
import com.songpo.searched.service.AfterSalesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "售后服务")
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/after-sales")
public class AfterSalesController {

    public static final Logger log = LoggerFactory.getLogger(AfterSalesController.class);

    @Autowired
    private AfterSalesService salesService;

    @ApiOperation(value = "新增售后服务单")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "申请售后类型(1:退货退款 2:仅退款)", name = "type", paramType = "form", required = true),
            @ApiImplicitParam(value = "申请原因", name = "reason", paramType = "form", required = true),
            @ApiImplicitParam(value = "退款金额", name = "money", paramType = "form", required = true),
            @ApiImplicitParam(value = "凭证图片", name = "file", paramType = "form"),
            @ApiImplicitParam(value = "商品id", name = "productId", paramType = "form", required = true),
            @ApiImplicitParam(value = "店铺id", name = "shopId", paramType = "form", required = true),
            @ApiImplicitParam(value = "申请说明", name = "remark", paramType = "form"),
            @ApiImplicitParam(value = "订单编号", name = "orderId", paramType = "form", required = true)
    })
    @PostMapping()
    public BusinessMessage insertAfterSales(SlAfterSalesService afterSalesService, MultipartFile[] files) {
        BusinessMessage message = new BusinessMessage();
        try {
            salesService.insertAfterSales(afterSalesService, files);
            message.setMsg("申请成功");
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("申请失败,请重试", e);
        }
        return message;
    }

    @ApiOperation(value = "售后服务单列表")
    @GetMapping()
    public BusinessMessage selectAfterSales() {
        BusinessMessage message = new BusinessMessage();
        try {
            message = this.salesService.selectAfterSales();
            message.setData(message.getData());
            message.setMsg(message.getMsg());
            message.setSuccess(message.getSuccess());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询失败", e);
        }
        return message;
    }


}
