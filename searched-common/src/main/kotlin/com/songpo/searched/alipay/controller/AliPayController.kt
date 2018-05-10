package com.songpo.searched.alipay.controller

import com.alipay.api.domain.*
import com.alipay.api.response.*
import com.songpo.searched.alipay.service.AliPayService
import com.songpo.searched.domain.BusinessMessage
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*

/**
 * 支付宝支付服务集成控制层
 */
@Api(description = "支付宝支付服务")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v1/alipay")
class AlipayController(val alipayService: AliPayService) {

    val log = KotlinLogging.logger {}

    /**
     * 加载支付配置
     *
     * @param serverUrl 支付宝网关
     * @param appId APPID
     * @param privateKey 商户私钥
     * @param format 数据格式
     * @param returnUrl 用户取消支付后跳转的地址
     * @param notifyUrl 支付宝服务器主动通知商户服务器里指定的页面
     * @param charset 编码格式 UTF-8 GBK
     * @param alipayPublicKey 支付宝公钥
     * @param signType 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     * @return 响应信息
     */
    @ApiOperation(value = "加载支付配置", notes = "在线重新加载支付配置信息，可以有效的解决因为配置文件错误而调整配置文件需要重启服务的问题")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "serverUrl", value = "支付宝网关\n - 生产环境：https://openapi.alipay.com/gateway.do\n- 开发环境：https://openapi.alipaydev.com/gateway.do", paramType = "form", required = true),
        ApiImplicitParam(name = "pid", value = "合作伙伴身份标识", paramType = "form", required = true),
        ApiImplicitParam(name = "appId", value = "APPID", paramType = "form", required = true),
        ApiImplicitParam(name = "privateKey", value = "商户私钥", paramType = "form", required = true),
        ApiImplicitParam(name = "format", value = "数据格式，仅支持JSON", paramType = "form", required = true),
        ApiImplicitParam(name = "returnUrl", value = "HTTP/HTTPS开头字符串，用户取消支付后跳转的地址", paramType = "form"),
        ApiImplicitParam(name = "notifyUrl", value = "支付宝服务器主动通知商户服务器里指定的页面http/https路径。", paramType = "form", required = true),
        ApiImplicitParam(name = "charset", value = "编码格式\n - UTF-8\n - GBK", paramType = "form", required = true),
        ApiImplicitParam(name = "alipayPublicKey", value = "支付宝公钥", paramType = "form", required = true),
        ApiImplicitParam(name = "signType", value = "商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2", paramType = "form", required = true)
    ])
    @PostMapping("/load-config")
    fun loadConfig(
            serverUrl: String,
            pid: String,
            appId: String,
            privateKey: String,
            format: String,
            returnUrl: String?,
            notifyUrl: String?,
            charset: String,
            alipayPublicKey: String,
            alipayPublicPayKey: String,
            signType: String
    ): BusinessMessage<Void> {
        log.debug {
            "加载支付配置, 支付宝网关 = [$serverUrl], " +
                    "合作伙伴身份标识 = [$pid], " +
                    "应用标识 = [$appId], " +
                    "商户私钥 = [$privateKey], " +
                    "数据格式 = [$format], " +
                    "用户取消支付后跳转的地址 = [$returnUrl], " +
                    "支付通知地址 = [$notifyUrl], " +
                    "编码格式 = [$charset], " +
                    "支付宝公钥 = [$alipayPublicKey], " +
                    "签名算法 = [$signType]"
        }
        val message = BusinessMessage<Void>()
        try {
            this.alipayService.loadConfig(serverUrl, pid, appId, privateKey, format, returnUrl, notifyUrl, charset, alipayPublicKey, signType)
            message.success = true
        } catch (e: Exception) {
            log.error { "加载支付配置失败，$e" }
            message.msg = "加载支付配置失败，${e.message}"
        }
        return message
    }

    /**
     * 统一收单交易退款查询
     *
     * 商户可使用该接口查询自已通过alipay.trade.refund提交的退款请求是否执行成功。 该接口的返回码10000，仅代表本次查询操作成功，不代表退款成功。如果该接口返回了查询数据，则代表退款成功，如果没有查询到则代表未退款成功，可以调用退款接口进行重试。重试时请务必保证退款请求号一致。
     * 来源 https://docs.open.alipay.com/api_1/alipay.trade.query/
     *
     * @param tradeNo    String	特殊可选	64	支付宝交易号，和商户订单号不能同时为空
     * @param outTradeNo String	特殊可选	64	订单支付时传入的商户订单号,和支付宝交易号不能同时为空。trade_no,out_trade_no如果同时存在优先取trade_no
     * @param outRequestNo    String	必选	64	请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号
     * @return 响应信息
     */
    @ApiOperation(value = "统一收单交易退款查询", notes = "### [alipay.trade.query(统一收单交易退款查询)](https://docs.open.alipay.com/api_1/alipay.trade.query/)\n" +
            "- 商户可使用该接口查询自已通过alipay.trade.refund提交的退款请求是否执行成功。\n" +
            "- 该接口的返回码10000，仅代表本次查询操作成功，不代表退款成功。\n" +
            "- 如果该接口返回了查询数据，则代表退款成功，如果没有查询到则代表未退款成功，可以调用退款接口进行重试。\n" +
            "- 重试时请务必保证退款请求号一致。")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "tradeNo", value = "支付宝交易号，和商户订单号不能同时为空", paramType = "query", required = false),
        ApiImplicitParam(name = "outTradeNo", value = "订单支付时传入的商户订单号,和支付宝交易号不能同时为空。trade_no,out_trade_no如果同时存在优先取trade_no", paramType = "query", required = false),
        ApiImplicitParam(name = "outRequestNo", value = "请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号", paramType = "query", required = true)
    ])
    @GetMapping("/fastpay-refund-query")
    fun fastPayRefundQuery(tradeNo: String?, outTradeNo: String?, outRequestNo: String): BusinessMessage<AlipayTradeFastpayRefundQueryResponse> {
        log.debug { "统一收单交易退款查询, 支付宝交易号 = [$tradeNo]，订单支付时传入的商户订单号= [$outTradeNo], 请求退款接口时，传入的退款请求号 = [$outRequestNo]" }
        val message = BusinessMessage<AlipayTradeFastpayRefundQueryResponse>()
        try {
            message.data = this.alipayService.fastPayRefundQuery(tradeNo, outTradeNo, outRequestNo)
            message.success = true
        } catch (e: Exception) {
            log.error { "统一收单交易退款查询失败，$e" }
            message.msg = "统一收单交易退款查询失败，${e.message}"
        }
        return message
    }

    /**
     * 统一收单交易结算接口
     *
     * 用于在线下场景交易支付后，进行结算
     * 来源 https://docs.open.alipay.com/api_1/alipay.trade.order.settle/
     *
     * @param outRequestNo    String	必选	64	结算请求流水号 开发者自行生成并保证唯一性
     * @param tradeNo    String	必选	64	支付宝订单号
     * @param royaltyParameters    OpenApiRoyaltyDetailInfoPojo[]	必选		分账明细信息
     *        └ trans_out	String	可选	16	分账支出方账户，类型为userId，本参数为要分账的支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字。	2088101126765726
     *        └ trans_in	String	可选	16	分账收入方账户，类型为userId，本参数为要分账的支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字。	2088101126708402
     *        └ amount	Number	可选	9	分账的金额，单位为元	0.1
     *        └ amount_percentage	Number	可选	3	分账信息中分账百分比。取值范围为大于0，少于或等于100的整数。	100
     *        └ desc	String	可选	1000	分账描述	分账给2088101126708402
     * @param operatorId    String	可选	64	操作员id
     * @return 响应信息
     */
    @ApiOperation(value = "统一收单交易结算接口", notes = "### [alipay.trade.order.settle(统一收单交易结算接口)](https://docs.open.alipay.com/api_1/alipay.trade.order.settle/)\n" +
            "用于在线下场景交易支付后，进行结算")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "outRequestNo", value = "结算请求流水号 开发者自行生成并保证唯一性", paramType = "form", required = true),
        ApiImplicitParam(name = "tradeNo", value = "支付宝订单号", paramType = "form", required = true),
        ApiImplicitParam(name = "royaltyParameters", value = "分账明细信息", paramType = "form", required = true),
        ApiImplicitParam(name = "operatorId", value = "操作员id", paramType = "form", required = false)
    ])
    @PostMapping("/order-settle")
    fun orderSettle(outRequestNo: String, tradeNo: String, royaltyParameters: List<OpenApiRoyaltyDetailInfoPojo>, operatorId: String?): BusinessMessage<AlipayTradeOrderSettleResponse> {
        log.debug { "统一收单交易结算接口, 结算请求流水号 = [$outRequestNo]，支付宝订单号= [$tradeNo], 分账明细信息 = [$royaltyParameters], 操作员id = [$operatorId]" }
        val message = BusinessMessage<AlipayTradeOrderSettleResponse>()
        try {
            message.data = this.alipayService.orderSettle(outRequestNo, tradeNo, royaltyParameters, operatorId)
            message.success = true
        } catch (e: Exception) {
            log.error { "统一收单交易结算接口失败，$e" }
            message.msg = "统一收单交易结算接口失败，${e.message}"
        }
        return message
    }

    /**
     * 统一收单交易关闭接口
     *
     * 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭。
     * 来源 https://docs.open.alipay.com/api_1/alipay.trade.close/
     *
     * @param tradeNo    String	特殊可选	64	该交易在支付宝系统中的交易流水号。最短 16 位，最长 64 位。和out_trade_no不能同时为空，如果同时传了 out_trade_no和 trade_no，则以 trade_no为准。
     * @param outTradeNo    String	特殊可选	64	订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no
     * @param operatorId    String	可选	28	卖家端自定义的的操作员ID
     * @return 响应信息
     */
    @ApiOperation(value = "统一收单交易关闭接口", notes = "### [alipay.trade.close(统一收单交易关闭接口)](https://docs.open.alipay.com/api_1/alipay.trade.close/)\n" +
            "- 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭。")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "tradeNo", value = "该交易在支付宝系统中的交易流水号", paramType = "form", required = false),
        ApiImplicitParam(name = "outTradeNo", value = "订单支付时传入的商户订单号", paramType = "form", required = false),
        ApiImplicitParam(name = "operatorId", value = "卖家端自定义的的操作员ID", paramType = "form", required = false)
    ])
    @PostMapping("/close")
    fun close(tradeNo: String?, outTradeNo: String?, operatorId: String?): BusinessMessage<AlipayTradeCloseResponse> {
        log.debug { "统一收单交易关闭接口，支付宝订单号= [$tradeNo], 该交易在支付宝系统中的交易流水号 = [$outTradeNo], 卖家端自定义的的操作员ID = [$operatorId]" }
        val message = BusinessMessage<AlipayTradeCloseResponse>()
        try {
            message.data = this.alipayService.close(tradeNo, outTradeNo, operatorId)
            message.success = true
        } catch (e: Exception) {
            log.error { "统一收单交易关闭接口失败，$e" }
            message.msg = "统一收单交易关闭接口失败，${e.message}"
        }
        return message
    }

    /**
     * alipay.trade.cancel(统一收单交易撤销接口)
     *
     * 支付交易返回失败或支付系统超时，调用该接口撤销交易。如果此订单用户支付失败，支付宝系统会将此订单关闭；如果用户支付成功，支付宝系统会将此订单资金退还给用户。 注意：只有发生支付系统超时或者支付结果未知时可调用撤销，其他正常支付的单如需实现相同功能请调用申请退款API。提交支付交易后调用【查询订单API】，没有明确的支付结果再调用【撤销订单API】。
     * 来源 https://docs.open.alipay.com/api_1/alipay.trade.cancel/
     *
     * @param outTradeNo    String	特殊可选	64	原支付请求的商户订单号,和支付宝交易号不能同时为空
     * @param tradeNo    String	特殊可选	64	支付宝交易号，和商户订单号不能同时为空
     * @return 响应信息
     */
    @ApiOperation(value = "统一收单交易撤销接口", notes = "### [alipay.trade.cancel(统一收单交易撤销接口)](https://docs.open.alipay.com/api_1/alipay.trade.cancel/)\n" +
            "- 支付交易返回失败或支付系统超时，调用该接口撤销交易。\n" +
            "- 如果此订单用户支付失败，支付宝系统会将此订单关闭；如果用户支付成功，支付宝系统会将此订单资金退还给用户。\n" +
            "- 注意：只有发生支付系统超时或者支付结果未知时可调用撤销，其他正常支付的单如需实现相同功能请调用申请退款API。\n" +
            "- 提交支付交易后调用【查询订单API】，没有明确的支付结果再调用【撤销订单API】。\n")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "outTradeNo", value = "原支付请求的商户订单号,和支付宝交易号不能同时为空", paramType = "form", required = false),
        ApiImplicitParam(name = "tradeNo", value = "支付宝交易号，和商户订单号不能同时为空", paramType = "form", required = false)
    ])
    @PostMapping("/cancel")
    fun cancel(outTradeNo: String?, tradeNo: String?): BusinessMessage<AlipayTradeCancelResponse> {
        log.debug { "统一收单交易撤销接口, 原支付请求的商户订单号 = [$outTradeNo]，支付宝交易号= [$tradeNo]" }
        val message = BusinessMessage<AlipayTradeCancelResponse>()
        try {
            message.data = this.alipayService.cancel(outTradeNo, tradeNo)
            message.success = true
        } catch (e: Exception) {
            log.error { "统一收单交易撤销接口失败，$e" }
            message.msg = "统一收单交易撤销接口失败，${e.message}"
        }
        return message
    }

    /**
     * alipay.trade.refund(统一收单交易退款接口)
     *
     * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，支付宝将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。 交易超过约定时间（签约时设置的可退款时间）的订单无法进行退款 支付宝退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。一笔退款失败后重新提交，要采用原来的退款单号。总退款金额不能超过用户实际支付金额
     * 来源 https://docs.open.alipay.com/api_1/alipay.trade.refund/
     *
     * @param outTradeNo    String	特殊可选	64	原支付请求的商户订单号,和支付宝交易号不能同时为空
     * @param tradeNo    String	特殊可选	64	支付宝交易号，和商户订单号不能同时为空
     * @param refundAmount    Price	必选	9	需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
     * @param refundReason    String	可选	256	退款的原因说明
     * @param outRequestNo    String	可选	64	标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
     * @param operatorId    String	可选	30	商户的操作员编号
     * @param storeId    String	可选	32	商户的门店编号
     * @param terminalId    String	可选	32	商户的终端编号
     * @return 响应信息
     */
    @ApiOperation(value = "统一收单交易退款接口", notes = "### [alipay.trade.refund(统一收单交易退款接口)](https://docs.open.alipay.com/api_1/alipay.trade.refund/)\n" +
            "- 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，支付宝将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。\n" +
            "- 交易超过约定时间（签约时设置的可退款时间）的订单无法进行退款\n" +
            "- 支付宝退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。\n" +
            "- 一笔退款失败后重新提交，要采用原来的退款单号。\n" +
            "- 总退款金额不能超过用户实际支付金额")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "outTradeNo", value = "原支付请求的商户订单号,和支付宝交易号不能同时为空", paramType = "form", required = false),
        ApiImplicitParam(name = "tradeNo", value = "支付宝交易号，和商户订单号不能同时为空", paramType = "form", required = false),
        ApiImplicitParam(name = "refundAmount", value = "需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数", paramType = "form", required = true),
        ApiImplicitParam(name = "refundReason", value = "退款的原因说明", paramType = "form", required = false),
        ApiImplicitParam(name = "outRequestNo", value = "标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。", paramType = "form", required = false),
        ApiImplicitParam(name = "operatorId", value = "商户的操作员编号", paramType = "form", required = false),
        ApiImplicitParam(name = "storeId", value = "商户的门店编号", paramType = "form", required = false),
        ApiImplicitParam(name = "terminalId", value = "商户的终端编号", paramType = "form", required = false)
    ])
    @PostMapping("/refund")
    fun refund(outTradeNo: String?, tradeNo: String?, refundAmount: String, refundReason: String?, outRequestNo: String?, operatorId: String?, storeId: String?, terminalId: String?): BusinessMessage<AlipayTradeRefundResponse> {
        log.debug { "统一收单交易退款接口, 原支付请求的商户订单号 = [$outTradeNo]，支付宝交易号= [$tradeNo]，需要退款的金额= [$refundAmount]，退款的原因说明= [$refundReason]，标识一次退款请求= [$outRequestNo]，商户的操作员编号= [$operatorId]，商户的门店编号= [$storeId]，商户的终端编号= [$terminalId]" }
        val message = BusinessMessage<AlipayTradeRefundResponse>()
        try {
            message.data = this.alipayService.refund(outTradeNo, tradeNo, refundAmount, refundReason, outRequestNo, operatorId, storeId, terminalId)
            message.success = true
        } catch (e: Exception) {
            log.error { "统一收单交易退款接口失败，$e" }
            message.msg = "统一收单交易退款接口失败，${e.message}"
        }
        return message
    }

    /**
     * 统一收单线下交易预创建
     *
     * 收银员通过收银台或商户后台调用支付宝接口，生成二维码后，展示给用户，由用户扫描二维码完成订单支付。
     * 来源：https://docs.open.alipay.com/api_1/alipay.trade.precreate/
     *
     * @param outTradeNo   String	必选	64	商户订单号,64个字符以内、只能包含字母、数字、下划线；需保证在商户端不重复
     * @param sellerId    String	可选	28	卖家支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
     * @param totalAmount    Price	必选	11	订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 如果同时传入了【打折金额】，【不可打折金额】，【订单总金额】三者，则必须满足如下条件：【订单总金额】=【打折金额】+【不可打折金额】
     * @param discountableAmount    Price	可选	11	可打折金额. 参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 如果该值未传入，但传入了【订单总金额】，【不可打折金额】则该值默认为【订单总金额】-【不可打折金额】
     * @param subject String	必选	256	订单标题
     * @param goodsDetail    GoodsDetail[]	可选		订单包含的商品列表信息，Json格式，其它说明详见商品明细说明
     *        └ goods_id	String	必填	32	商品的编号	apple-01
     *        └ goods_name	String	必填	256	商品名称	ipad
     *        └ quantity	Number	必填	10	商品数量	1
     *        └ price	Price	必填	9	商品单价，单位为元	2000
     *        └ goods_category	String	可选	24	商品类目	34543238
     *        └ body	String	可选	1000	商品描述信息	特价手机
     *        └ show_url	String	可选	400	商品的展示地址	http://www.alipay.com/xxx.jpg
     * @param body    String	可选	128	对交易或商品的描述	Iphone6 16G
     * @param operatorId    String	可选	28	商户操作员编号
     * @param storeId    String	可选	32	商户门店编号
     * @param disablePayChannels    String	可选	128	禁用渠道，用户不可用指定渠道支付，当有多个渠道时用“,”分隔注，与enable_pay_channels互斥
     * @param enablePayChannels    String	可选	128	可用渠道，用户只能在指定渠道范围内支付，当有多个渠道时用“,”分隔注，与disable_pay_channels互斥
     * @param terminalId    String	可选	32	商户机具终端编号
     * @param extendParams    ExtendParams	可选		业务扩展参数
     *        └ sys_service_provider_id	String	可选	64	系统商编号 该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
     * @param timeoutExpress    String	可选	6	该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
     * @param businessParams    String	可选	512	商户传入业务信息，具体值要和支付宝约定，应用于安全，营销等参数直传场景，格式为json格式
     * @return 响应信息
     */
    @ApiOperation(value = "统一收单线下交易预创建", notes = "### [alipay.trade.precreate(统一收单线下交易预创建)](https://docs.open.alipay.com/api_1/alipay.trade.precreate/)\n" +
            "- 收银员通过收银台或商户后台调用支付宝接口，生成二维码后，展示给用户，由用户扫描二维码完成订单支付。")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "outTradeNo", value = "商户订单号,64个字符以内、只能包含字母、数字、下划线；需保证在商户端不重复", paramType = "form", required = true),
        ApiImplicitParam(name = "sellerId", value = "卖家支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID", paramType = "form", required = false),
        ApiImplicitParam(name = "totalAmount", value = "订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 如果同时传入了【打折金额】，【不可打折金额】，【订单总金额】三者，则必须满足如下条件：【订单总金额】=【打折金额】+【不可打折金额】", paramType = "form", required = true),
        ApiImplicitParam(name = "discountableAmount", value = "可打折金额. 参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 如果该值未传入，但传入了【订单总金额】，【不可打折金额】则该值默认为【订单总金额】-【不可打折金额】", paramType = "form", required = false),
        ApiImplicitParam(name = "subject", value = "订单标题", paramType = "form", required = true),
        ApiImplicitParam(name = "goodsDetail", value = "订单包含的商品列表信息，Json格式，其它说明详见商品明细说明", paramType = "form", required = false),
        ApiImplicitParam(name = "body", value = "对交易或商品的描述", paramType = "form", required = false),
        ApiImplicitParam(name = "operatorId", value = "商户操作员编号", paramType = "form", required = false),
        ApiImplicitParam(name = "storeId", value = "商户门店编号", paramType = "form", required = false),
        ApiImplicitParam(name = "disablePayChannels", value = "禁用渠道，用户不可用指定渠道支付，当有多个渠道时用“,”分隔注，与enable_pay_channels互斥", paramType = "form", required = false),
        ApiImplicitParam(name = "enablePayChannels", value = "可用渠道，用户只能在指定渠道范围内支付，当有多个渠道时用“,”分隔注，与disable_pay_channels互斥", paramType = "form", required = false),
        ApiImplicitParam(name = "terminalId", value = "商户机具终端编号", paramType = "form", required = false),
        ApiImplicitParam(name = "extendParams", value = "业务扩展参数", paramType = "form", required = false),
        ApiImplicitParam(name = "timeoutExpress", value = "该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。", paramType = "form", required = false),
        ApiImplicitParam(name = "businessParams", value = "商户传入业务信息，具体值要和支付宝约定，应用于安全，营销等参数直传场景，格式为json格式", paramType = "form", required = false)
    ])
    @PostMapping("/pre-create")
    fun preCreate(outTradeNo: String, sellerId: String?, totalAmount: String, discountableAmount: String?,
                  subject: String, goodsDetail: List<GoodsDetail>?, body: String?, operatorId: String?, storeId: String?,
                  disablePayChannels: String?, enablePayChannels: String?, terminalId: String?, extendParams: ExtendParams?,
                  timeoutExpress: String?, businessParams: String?): BusinessMessage<AlipayTradePrecreateResponse> {
        log.debug {
            "统一收单线下交易预创建，商户订单号= [$outTradeNo], 卖家支付宝用户ID = [$sellerId], 总金额 = [$totalAmount], " +
                    "可打折金额 = [$discountableAmount], 订单标题 = [$subject], 订单包含的商品列表信息 = [$goodsDetail], " +
                    "对交易或商品的描述 = [$body], 商户操作员编号 = [$operatorId], 商户门店编号 = [$storeId], " +
                    "禁用渠道 = [$disablePayChannels], 可用渠道 = [$enablePayChannels], terminalId = [$terminalId], " +
                    "业务扩展参数 = [$extendParams], 该笔订单允许的最晚付款时间 = [$timeoutExpress], 商户传入业务信息 = [$businessParams]"
        }
        val message = BusinessMessage<AlipayTradePrecreateResponse>()
        try {
            message.data = this.alipayService.preCreate(outTradeNo, sellerId, totalAmount, discountableAmount, subject, goodsDetail, body, operatorId, storeId, disablePayChannels, enablePayChannels, terminalId, extendParams, timeoutExpress, businessParams)
            message.success = true
        } catch (e: Exception) {
            log.error { "统一收单线下交易预创建失败，$e" }
            message.msg = "统一收单线下交易预创建失败，${e.message}"
        }
        return message
    }

    /**
     * 统一收单交易创建接口
     *
     * 商户通过该接口进行交易的创建下单
     * 来源：https://docs.open.alipay.com/api_1/alipay.trade.create/
     *
     * @param outTradeNo   String	必选	64	商户订单号,64个字符以内、只能包含字母、数字、下划线；需保证在商户端不重复
     * @param sellerId    String	可选	28	卖家支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
     * @param totalAmount    Price	必选	11	订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 如果同时传入了【打折金额】，【不可打折金额】，【订单总金额】三者，则必须满足如下条件：【订单总金额】=【打折金额】+【不可打折金额】
     * @param discountableAmount    Price	可选	11	可打折金额. 参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 如果该值未传入，但传入了【订单总金额】，【不可打折金额】则该值默认为【订单总金额】-【不可打折金额】
     * @param subject String	必选	256	订单标题
     * @param body    String	可选	128	对交易或商品的描述	Iphone6 16G
     * @param buyerId    String	特殊可选	28	买家的支付宝唯一用户号（2088开头的16位纯数字）,和buyer_logon_id不能同时为空
     * @param goodsDetail    GoodsDetail[]	可选		订单包含的商品列表信息，Json格式，其它说明详见商品明细说明
     *        └ goods_id	String	必填	32	商品的编号	apple-01
     *        └ goods_name	String	必填	256	商品名称	ipad
     *        └ quantity	Number	必填	10	商品数量	1
     *        └ price	Price	必填	9	商品单价，单位为元	2000
     *        └ goods_category	String	可选	24	商品类目	34543238
     *        └ body	String	可选	1000	商品描述信息	特价手机
     *        └ show_url	String	可选	400	商品的展示地址	http://www.alipay.com/xxx.jpg
     * @param operatorId    String	可选	28	商户操作员编号
     * @param storeId    String	可选	32	商户门店编号
     * @param terminalId    String	可选	32	商户机具终端编号
     * @param extendParams    ExtendParams	可选		业务扩展参数
     *        └ sys_service_provider_id	String	可选	64	系统商编号 该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
     * @param timeoutExpress    String	可选	6	该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
     * @param businessParams    String	可选	512	商户传入业务信息，具体值要和支付宝约定，应用于安全，营销等参数直传场景，格式为json格式
     * @return 响应信息
     */
    @ApiOperation(value = "统一收单交易创建接口", notes = "### [alipay.trade.create(统一收单交易创建接口)](https://docs.open.alipay.com/api_1/alipay.trade.create/)\n" +
            "- 商户通过该接口进行交易的创建下单")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "outTradeNo", value = "商户订单号,64个字符以内、只能包含字母、数字、下划线；需保证在商户端不重复", paramType = "form", required = true),
        ApiImplicitParam(name = "sellerId", value = "卖家支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID", paramType = "form", required = false),
        ApiImplicitParam(name = "totalAmount", value = "订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 如果同时传入了【打折金额】，【不可打折金额】，【订单总金额】三者，则必须满足如下条件：【订单总金额】=【打折金额】+【不可打折金额】", paramType = "form", required = true),
        ApiImplicitParam(name = "discountableAmount", value = "可打折金额. 参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 如果该值未传入，但传入了【订单总金额】，【不可打折金额】则该值默认为【订单总金额】-【不可打折金额】", paramType = "form", required = false),
        ApiImplicitParam(name = "subject", value = "订单标题", paramType = "form", required = true),
        ApiImplicitParam(name = "body", value = "对交易或商品的描述", paramType = "form", required = false),
        ApiImplicitParam(name = "buyerId", value = "买家的支付宝唯一用户号（2088开头的16位纯数字）,和buyer_logon_id不能同时为空", paramType = "form", required = false),
        ApiImplicitParam(name = "goodsDetail", value = "订单包含的商品列表信息，Json格式，其它说明详见商品明细说明", paramType = "form", required = false),
        ApiImplicitParam(name = "operatorId", value = "商户操作员编号", paramType = "form", required = false),
        ApiImplicitParam(name = "storeId", value = "商户门店编号", paramType = "form", required = false),
        ApiImplicitParam(name = "terminalId", value = "商户机具终端编号", paramType = "form", required = false),
        ApiImplicitParam(name = "extendParams", value = "业务扩展参数", paramType = "form", required = false),
        ApiImplicitParam(name = "timeoutExpress", value = "该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。", paramType = "form", required = false),
        ApiImplicitParam(name = "businessParams", value = "商户传入业务信息，具体值要和支付宝约定，应用于安全，营销等参数直传场景，格式为json格式", paramType = "form", required = false)
    ])
    @PostMapping("/create")
    fun create(outTradeNo: String, sellerId: String?, totalAmount: String, discountableAmount: String?,
               subject: String, body: String?, buyerId: String?, goodsDetail: List<GoodsDetail>?, operatorId: String?, storeId: String?,
               terminalId: String?, extendParams: ExtendParams?, timeoutExpress: String?, businessParams: String?): BusinessMessage<AlipayTradeCreateResponse> {
        log.debug {
            "统一收单交易创建接口，商户订单号= [$outTradeNo], 卖家支付宝用户ID = [$sellerId], 总金额 = [$totalAmount], " +
                    "可打折金额 = [$discountableAmount], 订单标题 = [$subject] " +
                    "对交易或商品的描述 = [$body], 买家的支付宝唯一用户号 = [$buyerId]，订单包含的商品列表信息 = [$goodsDetail], 商户操作员编号 = [$operatorId], 商户门店编号 = [$storeId], , terminalId = [$terminalId], " +
                    "业务扩展参数 = [$extendParams], 该笔订单允许的最晚付款时间 = [$timeoutExpress], 商户传入业务信息 = [$businessParams]"
        }
        val message = BusinessMessage<AlipayTradeCreateResponse>()
        try {
            message.data = this.alipayService.create(outTradeNo, sellerId, totalAmount, discountableAmount, subject, body, buyerId, goodsDetail, operatorId, storeId, terminalId, extendParams, timeoutExpress, businessParams)
            message.success = true
        } catch (e: Exception) {
            log.error { "统一收单交易创建接口失败，$e" }
            message.msg = "统一收单交易创建接口失败，${e.message}"
        }
        return message
    }

    /**
     * 统一收单交易支付接口
     *
     * 收银员使用扫码设备读取用户手机支付宝“付款码”/声波获取设备（如麦克风）读取用户手机支付宝的声波信息后，将二维码或条码信息/声波信息通过本接口上送至支付宝发起支付。
     * 来源 https://docs.open.alipay.com/api_1/alipay.trade.pay/
     *
     * @param outTradeNo String	必选	64 商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
     * @param scene    String	必选	32 支付场景 条码支付，取值：bar_code 声波支付，取值：wave_code	bar_code
     * @param authCode    String	必选	32 支付授权码，25~30开头的长度为16~24位的数字，实际字符串长度以开发者获取的付款码长度为准
     * @param productCode    String	可选	32	销售产品码
     * @param subject    String	必选	256	订单标题
     * @param buyerId    String	可选	28	买家的支付宝用户id，如果为空，会从传入了码值信息中获取买家ID
     * @param sellerId    String	可选	28	如果该值为空，则默认为商户签约账号对应的支付宝用户ID
     * @param totalAmount    Price	可选	11	订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 如果同时传入【可打折金额】和【不可打折金额】，该参数可以不用传入；如果同时传入了【可打折金额】，【不可打折金额】，【订单总金额】三者，则必须满足如下条件：【订单总金额】=【可打折金额】+【不可打折金额】
     * @param discountableAmount    Price	可选	11	参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。如果该值未传入，但传入了【订单总金额】和【不可打折金额】，则该值默认为【订单总金额】-【不可打折金额】
     * @param body    String	可选	128	订单描述
     * @param goodsDetail    GoodsDetail[]	可选		订单包含的商品列表信息，Json格式，其它说明详见商品明细说明
     * @param operatorId    String	可选	28	商户操作员编号
     * @param storeId    String	可选	32	商户门店编号
     * @param terminalId    String	可选	32	商户机具终端编号
     * @param extendParams    ExtendParams	可选		业务扩展参数
     * @param timeoutExpress    String	可选	6	该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m
     * @return 响应信息
     */
    @ApiOperation(value = "统一收单交易支付接口", notes = "### [alipay.trade.pay(统一收单交易支付接口)](https://docs.open.alipay.com/api_1/alipay.trade.pay/)\n" +
            "- 收银员使用扫码设备读取用户手机支付宝“付款码”/声波获取设备（如麦克风）读取用户手机支付宝的声波信息后，将二维码或条码信息/声波信息通过本接口上送至支付宝发起支付。")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "outTradeNo", value = "商户订单号,64个字符以内、只能包含字母、数字、下划线；需保证在商户端不重复", paramType = "form", required = true),
        ApiImplicitParam(name = "scene", value = "支付场景 条码支付，取值：bar_code 声波支付，取值：wave_code bar_code", paramType = "form", required = true),
        ApiImplicitParam(name = "authCode", value = "支付授权码，25~30开头的长度为16~24位的数字，实际字符串长度以开发者获取的付款码长度为准", paramType = "form", required = true),
        ApiImplicitParam(name = "productCode", value = "销售产品码", paramType = "form", required = true),
        ApiImplicitParam(name = "subject", value = "订单标题", paramType = "form", required = true),
        ApiImplicitParam(name = "buyerId", value = "买家的支付宝用户id，如果为空，会从传入了码值信息中获取买家ID", paramType = "form", required = false),
        ApiImplicitParam(name = "sellerId", value = "卖家支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID", paramType = "form", required = false),
        ApiImplicitParam(name = "totalAmount", value = "订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 如果同时传入了【打折金额】，【不可打折金额】，【订单总金额】三者，则必须满足如下条件：【订单总金额】=【打折金额】+【不可打折金额】", paramType = "form", required = false),
        ApiImplicitParam(name = "discountableAmount", value = "可打折金额. 参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 如果该值未传入，但传入了【订单总金额】，【不可打折金额】则该值默认为【订单总金额】-【不可打折金额】", paramType = "form", required = false),
        ApiImplicitParam(name = "body", value = "对交易或商品的描述", paramType = "form", required = false),
        ApiImplicitParam(name = "goodsDetail", value = "订单包含的商品列表信息，Json格式，其它说明详见商品明细说明", paramType = "form", required = false),
        ApiImplicitParam(name = "operatorId", value = "商户操作员编号", paramType = "form", required = false),
        ApiImplicitParam(name = "storeId", value = "商户门店编号", paramType = "form", required = false),
        ApiImplicitParam(name = "terminalId", value = "商户机具终端编号", paramType = "form", required = false),
        ApiImplicitParam(name = "extendParams", value = "业务扩展参数", paramType = "form", required = false),
        ApiImplicitParam(name = "timeoutExpress", value = "该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。", paramType = "form", required = false),
        ApiImplicitParam(name = "businessParams", value = "商户传入业务信息，具体值要和支付宝约定，应用于安全，营销等参数直传场景，格式为json格式", paramType = "form", required = false)
    ])
    @PostMapping("/pay")
    fun pay(outTradeNo: String, scene: String, authCode: String, productCode: String, subject: String, buyerId: String?, sellerId: String?, totalAmount: String?, discountableAmount: String?,
            body: String?, goodsDetail: List<GoodsDetail>?, operatorId: String?, storeId: String?,
            terminalId: String?, extendParams: ExtendParams?, timeoutExpress: String?, businessParams: String?): BusinessMessage<AlipayTradePayResponse> {
        log.debug {
            "统一收单交易支付接口，商户订单号= [$outTradeNo], 支付场景 = [$scene], 支付授权码 = [$authCode], 销售产品码 = [$productCode], 订单标题 = [$subject], 买家的支付宝用户id = [$buyerId], 卖家支付宝用户ID = [$sellerId], 总金额 = [$totalAmount], " +
                    "可打折金额 = [$discountableAmount]，对交易或商品的描述 = [$body], 订单包含的商品列表信息 = [$goodsDetail], 商户操作员编号 = [$operatorId], 商户门店编号 = [$storeId], , terminalId = [$terminalId], " +
                    "业务扩展参数 = [$extendParams], 该笔订单允许的最晚付款时间 = [$timeoutExpress], 商户传入业务信息 = [$businessParams]"
        }
        val message = BusinessMessage<AlipayTradePayResponse>()
        try {
            message.data = this.alipayService.pay(outTradeNo, scene, authCode, productCode, subject, buyerId, sellerId, totalAmount, discountableAmount, body, goodsDetail, operatorId, storeId, terminalId, extendParams, timeoutExpress, businessParams)
            message.success = true
        } catch (e: Exception) {
            log.error { "统一收单交易支付接口失败，$e" }
            message.msg = "统一收单交易支付接口失败，${e.message}"
        }
        return message
    }

    /**
     * alipay.trade.app.pay(app支付接口2.0)
     *
     * 外部商户APP唤起快捷SDK创建订单并支付
     * 参考 https://docs.open.alipay.com/api_1/alipay.trade.app.pay/
     *
     * @param timeoutExpress    String	可选	6	该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m
     * @param totalAmount    Price	可选	11	订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 如果同时传入【可打折金额】和【不可打折金额】，该参数可以不用传入；如果同时传入了【可打折金额】，【不可打折金额】，【订单总金额】三者，则必须满足如下条件：【订单总金额】=【可打折金额】+【不可打折金额】
     * @param sellerId    String	可选	28	如果该值为空，则默认为商户签约账号对应的支付宝用户ID
     * @param productCode    String	必选	64	销售产品码，商家和支付宝签约的产品码	QUICK_WAP_WAY
     * @param body    String	可选	128	订单描述
     * @param subject    String	必选	256	订单标题
     * @param outTradeNo String	必选	64 商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复
     * @param timeExpire    String	可选	32	绝对超时时间，格式为yyyy-MM-dd HH:mm。	2016-12-31 10:05
     * @param goodsType    String	可选	2	商品主类型 :0-虚拟类商品,1-实物类商品	0
     * @param promoParams    String	可选	512	优惠参数 注：仅与支付宝协商后可用	{"storeIdType":"1"}
     * @param passbackParams    String	可选	512	公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在同步返回（包括跳转回商户网站）和异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。	merchantBizType%3d3C%26merchantBizNo%3d2016010101111
     * @param royaltyInfo    RoyaltyInfo	可选		描述分账信息，json格式，详见分账参数说明
    └ royalty_type	String	可选	150	分账类型 卖家的分账类型，目前只支持传入ROYALTY（普通分账类型）。	ROYALTY
    royalty_detail_infos	RoyaltyDetailInfos[]	必填	2500	分账明细的信息，可以描述多条分账指令，json数组。
    └ serial_no	Number	可选	9	分账序列号，表示分账执行的顺序，必须为正整数	1
    └ trans_in_type	String	可选	24	接受分账金额的账户类型： 	userId：支付宝账 号对应的支付宝唯一用户号。  bankIndex：分账到银行账户的银行编号。目前暂时只支持分账到一个银行编号。  storeId：分账到门店对应的银行卡编号。 默认值为userId。	userId
    └ batch_no	String	必填	32	分账批次号 分账批次号。 目前需要和转入账号类型为bankIndex配合使用。	123
    └ out_relation_id	String	可选	64	商户分账的外部关联号，用于关联到每一笔分账信息，商户需保证其唯一性。 如果为空，该值则默认为“商户网站唯一订单号+分账序列号”	20131124001
    └ trans_out_type	String	必填	24	要分账的账户类型。 目前只支持userId：支付宝账号对应的支付宝唯一用户号。 默认值为userId。	userId
    └ trans_out	String	必填	16	如果转出账号类型为userId，本参数为要分账的支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字。	2088101126765726
    └ trans_in	String	必填	28	如果转入账号类型为userId，本参数为接受分账金额的支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字。 	如果转入账号类型为bankIndex，本参数为28位的银行编号（商户和支付宝签约时确定）。 如果转入账号类型为storeId，本参数为商户的门店ID。	2088101126708402
    └ amount	Number	必填	9	分账的金额，单位为元	0.1
    └ desc	String	可选	1000	分账描述信息	分账测试1
    └ amount_percentage	String	可选	3	分账的比例，值为20代表按20%的比例分账	100
     * @param extendParams    ExtendParams	可选		业务扩展参数
    └ sys_service_provider_id	String	可选	64	系统商编号 该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID	2088511833207846
    └ hb_fq_num	String	可选	5	使用花呗分期要进行的分期数	3
    └ hb_fq_seller_percent	String	可选	3	使用花呗分期需要卖家承担的手续费比例的百分值，传入100代表100%	100
    └ industry_reflux_info	String	可选	512	行业数据回流信息, 详见：地铁支付接口参数补充说明	{\"scene_code\":\"metro_tradeorder\",\"channel\":\"xxxx\",\"scene_data\":{\"asset_name\":\"ALIPAY\"}}
    └ card_type	String	可选	32	卡类型	S0JP0000
     * @param subMerchant    SubMerchant	可选		间连受理商户信息体，当前只对特殊银行机构特定场景下使用此字段
    └ merchant_id	String	必填	11	间连受理商户的支付宝商户编号，通过间连商户入驻后得到。间连业务下必传，并且需要按规范传递受理商户编号。	19023454
    └ merchant_type	String	可选	32	商户id类型，	alipay: 支付宝分配的间连商户编号, merchant: 商户端的间连商户编号
     * @param enablePayChannels    String	可选	128	可用渠道，用户只能在指定渠道范围内支付 当有多个渠道时用“,”分隔 注，与disable_pay_channels互斥	pcredit,moneyFund,debitCardExpress
     * @param storeId    String	可选	32	商户门店编号	NJ_001
     * @param specifiedChannel    String	可选	128	指定渠道，目前仅支持传入pcredit  若由于用户原因渠道不可用，用户可选择是否用其他渠道支付。 注：该参数不可与花呗分期参数同时传入	pcredit
     * @param disablePayChannels    String	可选	128	禁用渠道，用户不可用指定渠道支付  当有多个渠道时用“,”分隔 注，与enable_pay_channels互斥	pcredit,moneyFund,debitCardExpress
     * @param settleInfo    SettleInfo	可选		描述结算信息，json格式，详见结算参数说明
    settle_detail_infos	SettleDetailInfo[]	必填	10	结算详细信息，json数组，目前只支持一条。
    └ trans_in_type	String	必填	64	结算收款方的账户类型。
    cardSerialNo：结算收款方的银行卡编号。
    目前只支持cardSerialNo账户类型	cardSerialNo
    └ trans_in	String	必填	64	结算收款方。当结算收款方类型是cardSerialNo时，本参数为用户在支付宝绑定的卡编号	A0001
    └ summary_dimension	String	可选	64	结算汇总维度，按照这个维度汇总成批次结算，由商户指定。
    目前需要和结算收款方账户类型为cardSerialNo配合使用	A0001
    └ amount	Number	必填	9	结算的金额，单位为元。目前必须和交易金额相同	0.1
     * @param  invoiceInfo    InvoiceInfo	可选		开票信息
    key_info	InvoiceKeyInfo	必填	200	开票关键信息
    └ is_support_invoice	Boolean	必填	5	该交易是否支持开票	true
    └ invoice_merchant_name	String	必填	80	开票商户名称：商户品牌简称|商户门店简称	ABC|003
    └ tax_num	String	必填	30	税号	1464888883494
    └ details	String	必填	400	开票内容 注：json数组格式	[{"code":"100294400","name":"服饰","num":"2","sumPrice":"200.00","taxRate":"6%"}]

     * @param extUserInfo    ExtUserInfo	可选		外部指定买家
    └ name	String	可选	16	姓名 注： need_check_info=T时该参数才有效	李明
    └ mobile	String	可选	20	手机号 注：该参数暂不校验	16587658765
    └ cert_type	String	可选	32	身份证：IDENTITY_CARD、护照：PASSPORT、军官证：OFFICER_CARD、士兵证：SOLDIER_CARD、户口本：HOKOU等。如有其它类型需要支持，请与蚂蚁金服工作人员联系。 注： need_check_info=T时该参数才有效	IDENTITY_CARD
    └ cert_no	String	可选	64	证件号 注：need_check_info=T时该参数才有效	362334768769238881
    └ min_age	String	可选	3	允许的最小买家年龄，买家年龄必须大于等于所传数值 注： 1. need_check_info=T时该参数才有效 2. min_age为整数，必须大于等于0	18
    └ fix_buyer	String	可选	8	是否强制校验付款人身份信息 T:强制校验，F：不强制	F
    └ need_check_info	String	可选	1	是否强制校验身份信息 T:强制校验，F：不强制	F
     * @param businessParams    String	可选	512	商户传入业务信息，具体值要和支付宝约定，应用于安全，营销等参数直传场景，格式为json格式	{"data":"123"}
     * @return 响应信息
     */
    @ApiOperation(value = "app支付接口2.0", notes = "### [alipay.trade.app.pay(app支付接口2.0)](https://docs.open.alipay.com/api_1/alipay.trade.app.pay/)\n" +
            "- 外部商户APP唤起快捷SDK创建订单并支付")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "timeoutExpress", value = "String\t可选\t6\t该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。\t90m", paramType = "form", required = false),
        ApiImplicitParam(name = "totalAmount", value = "String\t可选\t9\t订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]\t9.00", paramType = "form", required = false),
        ApiImplicitParam(name = "sellerId", value = "String\t可选\t16\t收款支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID\t2088102147948060", paramType = "form", required = false),
        ApiImplicitParam(name = "productCode", value = "String\t可选\t64\t销售产品码，商家和支付宝签约的产品码\tQUICK_MSECURITY_PAY", paramType = "form", required = false),
        ApiImplicitParam(name = "body", value = "String\t可选\t128\t对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。\tIphone6 16G", paramType = "form", required = false),
        ApiImplicitParam(name = "subject", value = "String\t可选\t256\t商品的标题/交易标题/订单标题/订单关键字等。\t大乐透", paramType = "form", required = false),
        ApiImplicitParam(name = "outTradeNo", value = "String\t可选\t64\t商户网站唯一订单号\t70501111111S001111119", paramType = "form", required = false),
        ApiImplicitParam(name = "timeExpire", value = "String\t可选\t32\t绝对超时时间，格式为yyyy-MM-dd HH:mm。\t2016-12-31 10:05", paramType = "form", required = false),
        ApiImplicitParam(name = "goodsType", value = "String\t可选\t2\t商品主类型 :0-虚拟类商品,1-实物类商品\t0", paramType = "form", required = false),
        ApiImplicitParam(name = "promoParams", value = "String\t可选\t512\t优惠参数 \n 注：仅与支付宝协商后可用\t{\"storeIdType\":\"1\"}", paramType = "form", required = false),
        ApiImplicitParam(name = "passbackParams", value = "String\t可选\t512\t公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在同步返回（包括跳转回商户网站）和异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。\tmerchantBizType%3d3C%26merchantBizNo%3d2016010101111", paramType = "form", required = false),
        ApiImplicitParam(name = "royaltyInfo", value = "RoyaltyInfo\t可选\t\t描述分账信息，json格式，详见分账参数说明", paramType = "form", required = false),
        ApiImplicitParam(name = "extendParams", value = "ExtendParams\t可选\t\t业务扩展参数", paramType = "form", required = false),
        ApiImplicitParam(name = "subMerchant", value = "SubMerchant\t可选\t\t间连受理商户信息体，当前只对特殊银行机构特定场景下使用此字段", paramType = "form", required = false),
        ApiImplicitParam(name = "enablePayChannels", value = "String\t可选\t128\t可用渠道，用户只能在指定渠道范围内支付 \n 当有多个渠道时用“,”分隔 \n 注，与disable_pay_channels互斥\tpcredit,moneyFund,debitCardExpress", paramType = "form", required = false),
        ApiImplicitParam(name = "storeId", value = "String\t可选\t32\t商户门店编号\tNJ_001", paramType = "form", required = false),
        ApiImplicitParam(name = "specifiedChannel", value = "String\t可选\t128\t指定渠道，目前仅支持传入pcredit \n 若由于用户原因渠道不可用，用户可选择是否用其他渠道支付。 \n 注：该参数不可与花呗分期参数同时传入\tpcredit", paramType = "form", required = false),
        ApiImplicitParam(name = "disablePayChannels", value = "String\t可选\t128\t禁用渠道，用户不可用指定渠道支付 \n 当有多个渠道时用“,”分隔 \n 注，与enable_pay_channels互斥\tpcredit,moneyFund,debitCardExpress", paramType = "form", required = false),
        ApiImplicitParam(name = "settleInfo", value = "SettleInfo\t可选\t\t描述结算信息，json格式，详见结算参数说明\t", paramType = "form", required = false),
        ApiImplicitParam(name = "invoiceInfo", value = "InvoiceInfo，可选，开票信息", paramType = "form", required = false),
        ApiImplicitParam(name = "extUserInfo", value = "ExtUserInfo，可选，外部指定买家", paramType = "form", required = false),
        ApiImplicitParam(name = "businessParams", value = "String\t可选\t512\t商户传入业务信息，具体值要和支付宝约定，应用于安全，营销等参数直传场景，格式为json格式", paramType = "form", required = false)
    ])
    @PostMapping("/app-pay")
    fun appPay(
            timeoutExpress: String?,
            totalAmount: String?,
            sellerId: String?,
            productCode: String?,
            body: String?,
            subject: String?,
            outTradeNo: String?,
            timeExpire: String?,
            goodsType: String?,
            promoParams: String?,
            passbackParams: String?,
            royaltyInfo: RoyaltyInfo?,
            extendParams: ExtendParams?,
            subMerchant: SubMerchant?,
            enablePayChannels: String?,
            storeId: String?,
            specifiedChannel: String?,
            disablePayChannels: String?,
            settleInfo: SettleInfo?,
            invoiceInfo: InvoiceInfo?,
            extUserInfo: ExtUserInfo?,
            businessParams: String?
    ): BusinessMessage<String> {
        log.debug {
            "app支付接口2.0，" +
                    "该笔订单允许的最晚付款时间= [$timeoutExpress], " +
                    "订单总金额 = [$totalAmount], " +
                    "收款支付宝用户ID = [$sellerId], " +
                    "销售产品码 = [$productCode], " +
                    "对一笔交易的具体描述信息 = [$body], " +
                    "商品的标题 = [$subject], " +
                    "商户网站唯一订单号 = [$outTradeNo], " +
                    "绝对超时时间 = [$timeExpire], " +
                    "商品主类型 = [$goodsType]，" +
                    "优惠参数  = [$promoParams], " +
                    "公用回传参数 = [$passbackParams], " +
                    "描述分账信息 = [$royaltyInfo], " +
                    "业务扩展参数 = [$extendParams], " +
                    "间连受理商户信息体 = [$subMerchant], " +
                    "可用渠道 = [$enablePayChannels], " +
                    "商户门店编号 = [$storeId], " +
                    "指定渠道 = [$specifiedChannel], " +
                    "禁用渠道 = [$disablePayChannels], " +
                    "描述结算信息 = [$settleInfo], " +
                    "开票信息 = [$invoiceInfo], " +
                    "外部指定买家 = [$extUserInfo], " +
                    "商户传入业务信息 = [$businessParams]"
        }
        val message = BusinessMessage<String>()
        try {
            message.data = this.alipayService.appPay(timeoutExpress,
                    totalAmount,
                    sellerId,
                    productCode,
                    body,
                    subject,
                    outTradeNo,
                    timeExpire,
                    goodsType,
                    promoParams,
                    passbackParams,
                    royaltyInfo,
                    extendParams,
                    subMerchant,
                    enablePayChannels,
                    storeId,
                    specifiedChannel,
                    disablePayChannels,
                    settleInfo,
                    invoiceInfo,
                    extUserInfo,
                    businessParams)
            message.success = true
        } catch (e: Exception) {
            e.printStackTrace()
            log.error { "app支付接口2.0接口失败，$e" }
            message.msg = "app支付接口2.0接口失败，${e.message}"
        }
        return message
    }

    @ApiOperation(value = "手机网站支付接口2.0", notes = "### [alipay.trade.wap.pay(手机网站支付接口2.0)](https://docs.open.alipay.com/api_1/alipay.trade.wap.pay/)\n" +
            "- 外部商户创建订单并支付")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "body", value = "String\t可选\t128\t对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。\tIphone6 16G", paramType = "form", required = false),
        ApiImplicitParam(name = "subject", value = "String\t必选\t256\t商品的标题/交易标题/订单标题/订单关键字等。\t大乐透", paramType = "form", required = true),
        ApiImplicitParam(name = "outTradeNo", value = "String\t必选\t64\t商户网站唯一订单号\t70501111111S001111119", paramType = "form", required = true),
        ApiImplicitParam(name = "timeoutExpress", value = "String\t可选\t6\t该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。\t90m", paramType = "form", required = false),
        ApiImplicitParam(name = "timeExpire", value = "String\t可选\t32\t绝对超时时间，格式为yyyy-MM-dd HH:mm。\t2016-12-31 10:05", paramType = "form", required = false),
        ApiImplicitParam(name = "totalAmount", value = "String\t可选\t9\t订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]\t9.00", paramType = "form", required = true),
        ApiImplicitParam(name = "sellerId", value = "String\t可选\t16\t收款支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID\t2088102147948060", paramType = "form", required = true),
        ApiImplicitParam(name = "authToken", value = "String\\t可选\\t40\\t针对用户授权接口，获取用户相关数据时，用于标识用户授权关系\\tappopenBb64d181d0146481ab6a762c00714cC27", paramType = "form", required = false),
        ApiImplicitParam(name = "goodsType", value = "String\t可选\t2\t商品主类型 :0-虚拟类商品,1-实物类商品\t0", paramType = "form", required = false),
        ApiImplicitParam(name = "passbackParams", value = "String\t可选\t512\t公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在同步返回（包括跳转回商户网站）和异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。\tmerchantBizType%3d3C%26merchantBizNo%3d2016010101111", paramType = "form", required = false),
        ApiImplicitParam(name = "quitUrl", value = "String\t必选\t400\t用户付款中途退出返回商户网站的地址\thttp://www.taobao.com/product/113714.html", paramType = "form", required = true),
        ApiImplicitParam(name = "productCode", value = "String\t可选\t64\t销售产品码，商家和支付宝签约的产品码\tQUICK_MSECURITY_PAY", paramType = "form", required = true),
        ApiImplicitParam(name = "promoParams", value = "String\t可选\t512\t优惠参数 \n 注：仅与支付宝协商后可用\t{\"storeIdType\":\"1\"}", paramType = "form", required = false),
        ApiImplicitParam(name = "royaltyInfo", value = "RoyaltyInfo\t可选\t\t描述分账信息，json格式，详见分账参数说明", paramType = "form", required = false),
        ApiImplicitParam(name = "extendParams", value = "ExtendParams\t可选\t\t业务扩展参数", paramType = "form", required = false),
        ApiImplicitParam(name = "subMerchant", value = "SubMerchant\t可选\t\t间连受理商户信息体，当前只对特殊银行机构特定场景下使用此字段", paramType = "form", required = false),
        ApiImplicitParam(name = "enablePayChannels", value = "String\t可选\t128\t可用渠道，用户只能在指定渠道范围内支付 \n 当有多个渠道时用“,”分隔 \n 注，与disable_pay_channels互斥\tpcredit,moneyFund,debitCardExpress", paramType = "form", required = false),
        ApiImplicitParam(name = "disablePayChannels", value = "String\t可选\t128\t禁用渠道，用户不可用指定渠道支付 \n 当有多个渠道时用“,”分隔 \n 注，与enable_pay_channels互斥\tpcredit,moneyFund,debitCardExpress", paramType = "form", required = false),
        ApiImplicitParam(name = "storeId", value = "String\t可选\t32\t商户门店编号\tNJ_001", paramType = "form", required = false),
        ApiImplicitParam(name = "settleInfo", value = "SettleInfo\t可选\t\t描述结算信息，json格式，详见结算参数说明\t", paramType = "form", required = false),
        ApiImplicitParam(name = "invoiceInfo", value = "InvoiceInfo，可选，开票信息", paramType = "form", required = false),
        ApiImplicitParam(name = "specifiedChannel", value = "String\t可选\t128\t指定渠道，目前仅支持传入pcredit \n 若由于用户原因渠道不可用，用户可选择是否用其他渠道支付。 \n 注：该参数不可与花呗分期参数同时传入\tpcredit", paramType = "form", required = false),
        ApiImplicitParam(name = "businessParams", value = "String\t可选\t512\t商户传入业务信息，具体值要和支付宝约定，应用于安全，营销等参数直传场景，格式为json格式", paramType = "form", required = false),
        ApiImplicitParam(name = "extUserInfo", value = "ExtUserInfo，可选，外部指定买家", paramType = "form", required = false)
    ])
    @PostMapping("/wap-pay")
    fun wapPay(
            body: String?,
            subject: String,
            outTradeNo: String,
            timeoutExpress: String?,
            timeExpire: String?,
            totalAmount: String,
            sellerId: String?,
            authToken: String?,
            goodsType: String?,
            passbackParams: String?,
            quitUrl: String,
            productCode: String,
            promoParams: String?,
            royaltyInfo: RoyaltyInfo?,
            extendParams: ExtendParams?,
            subMerchant: SubMerchant?,
            enablePayChannels: String?,
            disablePayChannels: String?,
            storeId: String?,
            settleInfo: SettleInfo?,
            invoiceInfo: InvoiceInfo?,
            specifiedChannel: String?,
            businessParams: String?,
            extUserInfo: ExtUserInfo?
    ): BusinessMessage<AlipayTradeWapPayResponse> {
        log.debug {
            "手机网站支付接口2.0，" +
                    "对一笔交易的具体描述信息 = [$body], " +
                    "商品的标题 = [$subject], " +
                    "商户网站唯一订单号 = [$outTradeNo], " +
                    "该笔订单允许的最晚付款时间= [$timeoutExpress], " +
                    "绝对超时时间 = [$timeExpire], " +
                    "订单总金额 = [$totalAmount], " +
                    "收款支付宝用户ID = [$sellerId], " +
                    "用户授权关系 = [$authToken]，" +
                    "商品主类型 = [$goodsType]，" +
                    "公用回传参数 = [$passbackParams], " +
                    "用户付款中途退出返回商户网站的地址 = [$quitUrl], " +
                    "销售产品码 = [$productCode], " +
                    "优惠参数  = [$promoParams], " +
                    "描述分账信息 = [$royaltyInfo], " +
                    "业务扩展参数 = [$extendParams], " +
                    "间连受理商户信息体 = [$subMerchant], " +
                    "可用渠道 = [$enablePayChannels], " +
                    "禁用渠道 = [$disablePayChannels], " +
                    "商户门店编号 = [$storeId], " +
                    "描述结算信息 = [$settleInfo], " +
                    "开票信息 = [$invoiceInfo], " +
                    "指定渠道 = [$specifiedChannel], " +
                    "商户传入业务信息 = [$businessParams], " +
                    "外部指定买家 = [$extUserInfo]"
        }
        val message = BusinessMessage<AlipayTradeWapPayResponse>()
        try {
            message.data = this.alipayService.wapPay(
                    body,
                    subject,
                    outTradeNo,
                    timeoutExpress,
                    timeExpire,
                    totalAmount,
                    sellerId,
                    authToken,
                    goodsType,
                    passbackParams,
                    quitUrl,
                    productCode,
                    promoParams,
                    royaltyInfo,
                    extendParams,
                    subMerchant,
                    enablePayChannels,
                    disablePayChannels,
                    storeId,
                    settleInfo,
                    invoiceInfo,
                    specifiedChannel,
                    businessParams,
                    extUserInfo)
            message.success = true
        } catch (e: Exception) {
            e.printStackTrace()
            log.error { "手机网站支付接口2.0接口失败，$e" }
            message.msg = "手机网站支付接口2.0接口失败，${e.message}"
        }
        return message
    }

    /**
     * 统一收单线下交易查询
     *
     * 该接口提供所有支付宝支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑。 需要调用查询接口的情况： 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知； 调用支付接口后，返回系统错误或未知交易状态情况； 调用alipay.trade.pay，返回INPROCESS的状态； 调用alipay.trade.cancel之前，需确认支付状态；
     * 来源 https://docs.open.alipay.com/api_1/alipay.trade.query/
     *
     * @param outTradeNo String	特殊可选	64	订单支付时传入的商户订单号,和支付宝交易号不能同时为空。trade_no,out_trade_no如果同时存在优先取trade_no
     * @param tradeNo    String	特殊可选	64	支付宝交易号，和商户订单号不能同时为空
     * @return 响应信息
     */
    @ApiOperation(value = "统一收单线下交易查询", notes = "### [alipay.trade.query(统一收单线下交易查询)](https://docs.open.alipay.com/api_1/alipay.trade.query/)\n" +
            "- 该接口提供所有支付宝支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑。\n" +
            "- 需要调用查询接口的情况： \n" +
            "   - 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；\n" +
            "   - 调用支付接口后，返回系统错误或未知交易状态情况；\n" +
            "   - 调用alipay.trade.pay，返回INPROCESS的状态;\n" +
            "   - 调用alipay.trade.cancel之前，需确认支付状态")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "outTradeNo", value = "订单支付时传入的商户订单号,和支付宝交易号不能同时为空。trade_no,out_trade_no如果同时存在优先取trade_no", paramType = "query", required = false),
        ApiImplicitParam(name = "tradeNo", value = "支付宝交易号，和商户订单号不能同时为空", paramType = "query", required = false)
    ])
    @GetMapping("/query")
    fun query(outTradeNo: String?, tradeNo: String?): BusinessMessage<AlipayTradeCustomsQueryResponse> {
        log.debug { "统一收单线下交易查询，商户订单号= [$outTradeNo], 支付宝交易号 = [$tradeNo]" }
        val message = BusinessMessage<AlipayTradeCustomsQueryResponse>()
        try {
            message.data = this.alipayService.query(outTradeNo, tradeNo)
            message.success = true
        } catch (e: Exception) {
            log.error { "统一收单线下交易查询失败，$e" }
            message.msg = "统一收单线下交易查询失败，${e.message}"
        }
        return message
    }

}
