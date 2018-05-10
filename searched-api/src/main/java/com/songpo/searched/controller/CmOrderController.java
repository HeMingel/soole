package com.songpo.searched.controller;


import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.CmOrderService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Api(description = "订单管理")
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/order")
public class CmOrderController {

    public static final Logger log = LoggerFactory.getLogger(CmOrderController.class);

    @Autowired
    private CmOrderService cmOrderService;

    /**
     * 多商品订单
     *
     * @param shippingAddressId
     * @param detail
     * @return
     */
    @ApiOperation(value = "多商品下单", authorizations = {
            @Authorization(value = "application", scopes = {
                    @AuthorizationScope(scope = "read", description = "allows reading resources"),
                    @AuthorizationScope(scope = "write", description = "allows modifying resources")
            })
    }, tags = {"cm-order-controller",})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "form", required = true),
            @ApiImplicitParam(name = "shippingAddressId", value = "地址Id", paramType = "form", required = true),
            @ApiImplicitParam(name = "repositoryId", value = "店铺仓库唯一标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "quantity", value = "订单商品数量", paramType = "form", required = true),
            @ApiImplicitParam(name = "paymentState", value = "支付状态(0：待支付1：支付成功 2：支付失败)", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "订单类型(1：普通订单 2：拼团订单3:预售订单 4:一元购 5:消费奖励 6:豆赚)", paramType = "form", required = true),
            @ApiImplicitParam(name = "paymentChannel", value = "支付类型(1：微信支付 2：支付宝支付 3：厦门银行支付)", paramType = "form"),
            @ApiImplicitParam(name = "payTime", value = "支付时间", paramType = "form"),
            @ApiImplicitParam(name = "buyerMessage", value = "买家留言", paramType = "form"),
            @ApiImplicitParam(name = "price", value = "单个商品的价格", paramType = "form"),
            @ApiImplicitParam(name = "deductPulse", value = "单个商品需扣除的金豆", paramType = "form"),
            @ApiImplicitParam(name = "postFee", value = "邮费", paramType = "form"),
            @ApiImplicitParam(name = "productId", value = "商品id", paramType = "form", required = true)
    })
    @PostMapping("add")
    public BusinessMessage addOrder(HttpServletRequest request, String[] detail, String shippingAddressId) {
        BusinessMessage message = new BusinessMessage();
        try {
            message = this.cmOrderService.addOrder(request, detail, shippingAddressId);
            message.setData(message.getData());
            message.setMsg(message.getMsg());
            message.setSuccess(message.getSuccess());
        } catch (Exception e) {
            message.setMsg("添加订单失败");
            log.error("新增订单失败", e);
        }
        return message;
    }


    /**
     * 单商品下单
     *
     * @param repositoryId
     * @param quantity
     * @return
     */
    @ApiOperation(value = "单商品下单", authorizations = {
            @Authorization(value = "application", scopes = {
                    @AuthorizationScope(scope = "read", description = "allows reading resources"),
                    @AuthorizationScope(scope = "write", description = "allows modifying resources")
            })
    }, tags = {"cm-order-controller",})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "repositoryId", value = "店铺仓库唯一标识", paramType = "form", required = true),
            @ApiImplicitParam(name = "quantity", value = "订单商品数量", paramType = "form", required = true),
            @ApiImplicitParam(name = "shareOfPeopleId", value = "分享人id", paramType = "form"),
            @ApiImplicitParam(name = "serialNumber", value = "订单编号", paramType = "form"),
            @ApiImplicitParam(name = "groupMaster", value = "开团团长", paramType = "form"),
            @ApiImplicitParam(name = "shippingAddressId", value = "加入订单的地址id", paramType = "form"),
            @ApiImplicitParam(name = "buyerMessage", value = "用户留言", paramType = "form")
    })
    @PostMapping("purchase-immediately")
    public BusinessMessage purchaseAddOrder(HttpServletRequest request, HttpServletResponse response, String repositoryId, Integer quantity, String shareOfPeopleId, String serialNumber, String groupMaster, String shippingAddressId, String buyerMessage, String activityProductId) {
        BusinessMessage message = new BusinessMessage();
        try {
            message = this.cmOrderService.purchaseAddOrder(request, response, repositoryId, quantity, shareOfPeopleId, serialNumber, groupMaster, shippingAddressId, buyerMessage, activityProductId);
            message.setData(message.getData());
            message.setMsg(message.getMsg());
            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg("添加订单失败");
            log.error("新增订单失败", e);
        }
        return message;
    }


    /**
     * 我的订单列表
     *
     * @param
     * @return
     */
    @ApiOperation(value = "我的订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oAuth2Authentication", value = "token", paramType = "form", required = true),
            @ApiImplicitParam(name = "订单状态", value = "status", paramType = "form", required = true)
    })
    @GetMapping("list")
    public BusinessMessage list(Integer status, Integer pageNum, Integer pageSize) {
        return this.cmOrderService.findList(status, pageNum, pageSize);
    }

    /**
     * 我的订单详情
     *
     * @return
     */
    @ApiOperation(value = "我的订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单Id", paramType = "form", required = true)
    })
    @GetMapping("order-info")
    public BusinessMessage orderInfo(String id) {
        return this.cmOrderService.orderInfo(id);
    }

    /**
     * 取消订单/确定收货
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单Id", paramType = "form", required = true),
            @ApiImplicitParam(name = "state", value = "操作状态", paramType = "form", required = true)
    })
    @PostMapping("order-status")
    public BusinessMessage cancelAnOrder(String id, String state) {
        log.debug("orderId = [" + id + "]");
        BusinessMessage message = new BusinessMessage();
        try {
            this.cmOrderService.cancelAnOrder(id, state);
            message.setSuccess(true);
            message.setMsg("取消成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("取消失败", e);
        }
        return message;
    }

    /**
     * 删除订单
     *
     * @param orderId
     * @param orderDetailId
     * @return
     */
    @ApiOperation(value = "删除订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderDetailId", value = "订单Id", paramType = "form", required = true),
            @ApiImplicitParam(name = "orderId", value = "订单Id", paramType = "form", required = true)
    })
    @PostMapping("delete-order")
    public BusinessMessage deleteOrder(String orderId, String orderDetailId) {
        BusinessMessage message = new BusinessMessage();
        try {
            this.cmOrderService.deleteOrder(orderId, orderDetailId);
            message.setSuccess(true);
            message.setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("删除失败", e);
        }
        return message;
    }

    /**
     * 预售订单查询
     *
     * @param status
     * @return
     */
    @ApiOperation(value = "预售订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "搜索条件", paramType = "form")
    })
    @GetMapping("pre-sale-order")
    public BusinessMessage preSaleOrderList(Integer status, Integer pageNum, Integer pageSize) {
        BusinessMessage message = new BusinessMessage();
        try {
            message = this.cmOrderService.preSaleOrderList(status, pageNum, pageSize);
            message.setData(message.getData());
            message.setSuccess(true);
            message.setMsg("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("查询失败", e);
        }
        return message;
    }

    /**
     * 预售提醒发货
     *
     * @param orderId
     * @return
     */
    @ApiOperation(value = "提醒发货")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单Id", paramType = "form", required = true)
    })
    @PostMapping("reminding-the-shipments")
    public BusinessMessage remindingTheShipments(String orderId) {
        BusinessMessage message = new BusinessMessage();
        try {
            message = this.cmOrderService.remindingTheShipments(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("提醒失败", e);
        }
        return message;
    }

    /**
     * 预售确认收货
     *
     * @param orderId
     * @return
     */

    @ApiOperation(value = "预售确认收货")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单Id", paramType = "form", required = true),
            @ApiImplicitParam(name = "returnsDetailId", value = "预售返现记录表", paramType = "form", required = true)
    })
    @PostMapping("presell-premises")
    public BusinessMessage presellPremises(String returnsDetailId, String orderId) {
        BusinessMessage message = new BusinessMessage();
        try {
            message = this.cmOrderService.presellPremises(returnsDetailId, orderId);
            message.setMsg(message.getMsg());
            message.setSuccess(message.getSuccess());
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("确认失败", e);
        }
        return message;
    }

    /**
     * 快递100 接口
     *
     * @param emsId
     * @param expressCode
     * @return
     */
    @RequestMapping("/searchExpress")
    public BusinessMessage SearchExpress(Integer emsId, String expressCode) {
        return this.cmOrderService.searchExpress(emsId, expressCode);
    }

    /**
     * 支付宝App预下单
     *
     * @param orderId 订单id
     * @return 预下单信息
     */
    @GetMapping("alipay-app-pay")
    public BusinessMessage<String> alipayAppPayGet(String orderId) {
        return alipayAppPayTestPost(orderId);
    }

    @PostMapping("alipay-app-pay")
    public BusinessMessage<String> alipayAppPayPost(String orderId) {
        log.debug("支付宝App下单，productName = {}", orderId);
        BusinessMessage<String> message = new BusinessMessage<>();
        try {
            message = this.cmOrderService.alipayAppPay(orderId);
            message.setData(message.getData());
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("支付宝App下单失败，{}", e);
        }
        return message;
    }

    /**
     * 微信App预下单
     *
     * @param orderId 订单编号
     * @return 预下单信息
     */
    @GetMapping("wechat-app-pay")
    public BusinessMessage<Map> wechatAppPayGet(HttpServletRequest req, String orderId) {
        return wechatAppPayPost(req, orderId);
    }

    @PostMapping("wechat-app-pay")
    public BusinessMessage<Map> wechatAppPayPost(HttpServletRequest req, String orderId) {
        log.debug("微信App下单，productName = {}", orderId);
        BusinessMessage<Map> message = new BusinessMessage<>();
        try {
            message = this.cmOrderService.wechatAppPay(req, orderId);
            message.setData(message.getData());
            message.setSuccess(message.getSuccess());
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("微信App下单失败，{}", e);
        }
        return message;
    }


    ///////////////////////////////////////////////// 支付测试开始 /////////////////////////////////////////////////////


    /**
     * 微信App预下单
     *
     * @param productName 商品名称
     * @return 预下单信息
     */
    @GetMapping("wechat-app-pay-test")
    public BusinessMessage<Map<String, String>> wechatAppPayTestGet(HttpServletRequest req, String productName) {
        return wechatAppPayTestPost(req, productName);
    }

    @PostMapping("wechat-app-pay-test")
    public BusinessMessage<Map<String, String>> wechatAppPayTestPost(HttpServletRequest req, String productName) {
        log.debug("微信App下单，productName = {}", productName);
        BusinessMessage<Map<String, String>> message = new BusinessMessage<>();
        try {
            message.setData(this.cmOrderService.wechatAppPayTest(req, productName));
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("微信App下单失败，{}", e);
        }
        return message;
    }

    /**
     * 支付宝App预下单
     *
     * @param productName 商品名称
     * @return 预下单信息
     */
    @GetMapping("alipay-app-pay-test")
    public BusinessMessage<String> alipayAppPayTestGet(String productName) {
        return alipayAppPayTestPost(productName);
    }

    @PostMapping("alipay-app-pay-test")
    public BusinessMessage<String> alipayAppPayTestPost(String productName) {
        log.debug("支付宝App下单，productName = {}", productName);
        BusinessMessage<String> message = new BusinessMessage<>();
        try {
            message.setData(this.cmOrderService.alipayAppPayTest(productName));
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("支付宝App下单失败，{}", e);
        }
        return message;
    }

    /**
     * 支付宝H5预下单
     *
     * @param productName 商品名称
     * @return 预下单信息
     */
    @GetMapping("alipay-h5-pay-test")
    public BusinessMessage<AlipayTradeWapPayResponse> alipayH5PayTestGet(String productName) {
        return alipayH5PayTestPost(productName);
    }

    @PostMapping("alipay-h5-pay-test")
    public BusinessMessage<AlipayTradeWapPayResponse> alipayH5PayTestPost(String productName) {
        log.debug("支付宝H5下单，productName = {}", productName);
        BusinessMessage<AlipayTradeWapPayResponse> message = new BusinessMessage<>();
        try {
            message.setData(this.cmOrderService.alipayH5PayTest(productName));
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("支付宝H5下单失败，{}", e);
        }
        return message;
    }
    ///////////////////////////////////////////////// 支付测试结束 /////////////////////////////////////////////////////

}
