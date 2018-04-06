package com.songpo.searched.controller;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlAfterSalesService;
import com.songpo.searched.service.AfterSalesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "售后服务")
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/afterSales")
public class AfterSalesController {

    @Autowired
    private AfterSalesService salesService;

    @ApiOperation(value = "新增售后服务单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "申请售后类型(1:退货退款 2:仅退款)", value = "type", paramType = "form", required = true),
            @ApiImplicitParam(name = "申请原因", value = "reason", paramType = "form", required = true),
            @ApiImplicitParam(name = "退款金额", value = "money", paramType = "form", required = true),
            @ApiImplicitParam(name = "凭证图片", value = "file", paramType = "form"),
            @ApiImplicitParam(name = "商品id", value = "productId", paramType = "form", required = true),
            @ApiImplicitParam(name = "店铺id", value = "shopId", paramType = "form", required = true),
            @ApiImplicitParam(name = "申请说明", value = "remark", paramType = "form")
    })
    @PostMapping("insertAfterSales")
    public BusinessMessage insertAfterSales(SlAfterSalesService afterSalesService, MultipartFile file) {
        BusinessMessage message = new BusinessMessage();
        try {
            salesService.insertAfterSales(afterSalesService, file);
            message.setMsg("申请成功");
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    @ApiOperation(value = "售后服务单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "售后id", value = "afterSalesServiceId", paramType = "form", required = true)
    })
    @GetMapping("selectAfterSales")
    public BusinessMessage selectAfterSales() {
        BusinessMessage message = new BusinessMessage();
        try {
            this.salesService.selectAfterSales();
            message.setMsg("查询成功");
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }


}
