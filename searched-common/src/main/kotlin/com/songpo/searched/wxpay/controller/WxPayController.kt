package com.songpo.searched.wxpay.controller

import com.songpo.searched.domain.BusinessMessage
import com.songpo.searched.wxpay.service.WxPayService
import com.songpo.searched.wxpay.util.ClientIPUtil
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import mu.KotlinLogging
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

/**
 * 微信支付服务集成控制层
 */
@Api(description = "微信支付服务")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v1/wxpay")
class WxPayController(val wxPayService: WxPayService) {

    val log = KotlinLogging.logger {}

    /**
     * 加载支付配置
     *
     * @param appId    String	应用ID
     * @param mchId    String	商户号
     * @param secret    String	支付密钥
     * @param certFile    String	支付证书文件
     * @param notifyUrl    String	支付通知地址
     * @return 响应信息
     */
    @ApiOperation(value = "加载支付配置", notes = "在线重新加载支付配置信息，可以有效的解决因为配置文件错误而调整配置文件需要重启服务的问题")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "appId", value = "应用ID或公众号ID", paramType = "form", required = true),
        ApiImplicitParam(name = "mchId", value = "商户号", paramType = "form", required = true),
        ApiImplicitParam(name = "secret", value = "APP密钥", paramType = "form", required = true),
        ApiImplicitParam(name = "apiKey", value = "API密钥", paramType = "form", required = true),
        ApiImplicitParam(name = "certFile", value = "支付证书文件", paramType = "form", dataType = "file", required = true),
        ApiImplicitParam(name = "notifyUrl", value = "支付通知地址", paramType = "form", required = true)
    ])
    @PostMapping("/load-com.songpo.searched.alipay.configProperties")
    fun loadConfig(
            appId: String,
            mchId: String,
            secret: String,
            apiKey: String,
            certFile: MultipartFile,
            notifyUrl: String
    ): BusinessMessage<Void> {
        log.debug { "加载支付配置, 应用ID = [$appId], 商户号 = [$mchId], AppSecret = [$secret], API密钥 = [$apiKey], 支付完成后返回的地址 = [$notifyUrl]" }
        val message = BusinessMessage<Void>()
        try {
            this.wxPayService.loadConfig(appId, mchId, secret, apiKey, certFile, notifyUrl)
            message.success = true
        } catch (e: Exception) {
            log.error { "加载支付配置失败，$e" }
            message.msg = "加载支付配置失败，${e.message}"
        }
        return message
    }

    /**
     * 查询退款
     *
     * 提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，银行卡支付的退款3个工作日后重新查询退款状态。
     * 注意：如果单个支付订单部分退款次数超过20次请使用退款单号查询
     * 分页查询
     * 当一个订单部分退款超过10笔后，商户用微信订单号或商户订单号调退款查询API查询退款时，默认返回前10笔和total_refund_count（订单总退款次数）。商户需要查询同一订单下超过10笔的退款单时，可传入订单号及offset来查询，微信支付会返回offset及后面的10笔，以此类推。当商户传入的offset超过total_refund_count，则系统会返回报错PARAM_ERROR。
     * 举例：
     * 一笔订单下的退款单有36笔，当商户想查询第25笔时，可传入订单号及offset=24，微信支付平台会返回第25笔到第35笔的退款单信息，或商户可直接传入退款单号查询退款
     * 来源 https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=9_4&index=4
     *
     * @param transaction_id    微信订单号	四选一	String(32)	1009660380201506130728806387	微信的订单号，建议优先使用
     * @param out_trade_no    商户订单号	四选一 String(32)	20150806125346	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。 详见商户订单号
     * @param out_refund_no    商户退款单号	四选一	String(64)	1217752501201407033233368018	商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
     * @param refund_id    微信退款单号	四选一 String(32)	1217752501201407033233368018 微信生成的退款单号，在申请退款接口有返回
     * @param offset    偏移量	否	Int	15 偏移量，当部分退款次数超过10次时可使用，表示返回的查询结果从这个偏移量开始取记录
     * @return 响应信息
     */
    @ApiOperation(value = "查询退款", notes = "### [查询退款](https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=9_4&index=4)\n" +
            "- 提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，银行卡支付的退款3个工作日后重新查询退款状态。\n" +
            "- 注意：如果单个支付订单部分退款次数超过20次请使用退款单号查询\n" +
            "- 分页查询\n" +
            "   - 当一个订单部分退款超过10笔后，商户用微信订单号或商户订单号调退款查询API查询退款时，默认返回前10笔和total_refund_count（订单总退款次数）。商户需要查询同一订单下超过10笔的退款单时，可传入订单号及offset来查询，微信支付会返回offset及后面的10笔，以此类推。当商户传入的offset超过total_refund_count，则系统会返回报错PARAM_ERROR。\n" +
            "- 举例：\n" +
            "   - 一笔订单下的退款单有36笔，当商户想查询第25笔时，可传入订单号及offset=24，微信支付平台会返回第25笔到第35笔的退款单信息，或商户可直接传入退款单号查询退款")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "transaction_id", value = "微信订单号\t四选一\tString(32)\t1009660380201506130728806387\t微信的订单号，建议优先使用", paramType = "query", required = false),
        ApiImplicitParam(name = "out_trade_no", value = "商户订单号\t四选一 String(32)\t20150806125346\t商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。 详见商户订单号", paramType = "query", required = false),
        ApiImplicitParam(name = "out_refund_no", value = "商户退款单号\t四选一\tString(64)\t1217752501201407033233368018\t商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。", paramType = "query", required = false),
        ApiImplicitParam(name = "refund_id", value = "微信退款单号\t四选一 String(32)\t1217752501201407033233368018 微信生成的退款单号，在申请退款接口有返回", paramType = "query", required = false),
        ApiImplicitParam(name = "offset", value = "偏移量\t否\tInt\t15 偏移量，当部分退款次数超过10次时可使用，表示返回的查询结果从这个偏移量开始取记录", paramType = "query", required = false)
    ])
    @GetMapping("/refund-query")
    fun refundQuery(transaction_id: String?, out_trade_no: String?, out_refund_no: String?, refund_id: String?, offset: String?): BusinessMessage<Map<String, String>> {
        log.debug { "查询退款, 微信交易号 = [$transaction_id]，商户订单号= [$out_trade_no], 商户退款单号 = [$out_refund_no]，微信退款单号= [$refund_id], 偏移量 = [$offset]" }
        val message = BusinessMessage<Map<String, String>>()
        try {
            message.data = this.wxPayService.refundQuery(transaction_id, out_trade_no, out_refund_no, refund_id, offset)
            message.success = true
        } catch (e: Exception) {
            log.error { "查询退款失败，$e" }
            message.msg = "查询退款失败，${e.message}"
        }
        return message
    }

    /**
     * 申请退款
     *
     * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，微信支付将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。
     * 注意：
     * 1、交易时间超过一年的订单无法提交退款
     * 2、微信支付退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。申请退款总金额不能超过订单金额。 一笔退款失败后重新提交，请不要更换退款单号，请使用原商户退款单号
     * 3、请求频率限制：150qps，即每秒钟正常的申请退款请求次数不超过150次 错误或无效请求频率限制：6qps，即每秒钟异常或错误的退款申请请求不超过6次
     * 4、每个支付订单的部分退款次数不能超过50次
     * 来源 https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=9_4&index=4
     *
     * @param transaction_id    微信订单号	二选一	String(32)	1009660380201506130728806387	微信的订单号，建议优先使用
     * @param out_trade_no    商户订单号	String(32)	20150806125346	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。 详见商户订单号
     * @param out_refund_no    商户退款单号	是	String(64)	1217752501201407033233368018	商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
     * @param total_fee    订单金额	是	Int	100	订单总金额，单位为分，只能为整数，详见支付金额
     * @param refund_fee    退款金额	是	Int	100	退款总金额，订单总金额，单位为分，只能为整数，详见支付金额
     * @param refund_fee_type    货币种类 	否	String(8)	CNY	货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * @param refund_desc    退款原因 	否	String(80)	商品已售完	若商户传入，会在下发给用户的退款消息中体现退款原因
     * @param refund_account    退款资金来源	否	String(30) REFUND_SOURCE_RECHARGE_FUNDS 仅针对老资金流商户使用 REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款）REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款
     * @return 响应信息
     */
    @ApiOperation(value = "申请退款", notes = "### [申请退款](https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=9_4&index=4)\n" +
            "- 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，微信支付将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家帐号上。" +
            "- 注意：" +
            "   - 1、交易时间超过一年的订单无法提交退款" +
            "   - 2、微信支付退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。申请退款总金额不能超过订单金额。 一笔退款失败后重新提交，请不要更换退款单号，请使用原商户退款单号" +
            "   - 3、请求频率限制：150qps，即每秒钟正常的申请退款请求次数不超过150次 错误或无效请求频率限制：6qps，即每秒钟异常或错误的退款申请请求不超过6次" +
            "   - 4、每个支付订单的部分退款次数不能超过50次")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "transaction_id", value = "微信订单号\t二选一\tString(32)\t1009660380201506130728806387\t微信的订单号，建议优先使用", paramType = "form", required = true),
        ApiImplicitParam(name = "out_trade_no", value = "商户订单号\tString(32)\t20150806125346\t商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。 详见商户订单号", paramType = "form", required = true),
        ApiImplicitParam(name = "out_refund_no", value = "商户退款单号\t是\tString(64)\t1217752501201407033233368018\t商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。", paramType = "form", required = true),
        ApiImplicitParam(name = "total_fee", value = "订单金额\t是\tInt\t100\t订单总金额，单位为分，只能为整数，详见支付金额", paramType = "form", required = false),
        ApiImplicitParam(name = "refund_fee", value = "退款金额\t是\tInt\t100\t退款总金额，订单总金额，单位为分，只能为整数，详见支付金额", paramType = "form", required = true),
        ApiImplicitParam(name = "refund_fee_type", value = "货币种类 \t否\tString(8)\tCNY\t货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型", paramType = "form", required = true),
        ApiImplicitParam(name = "refund_desc", value = "退款原因 \t否\tString(80)\t商品已售完\t若商户传入，会在下发给用户的退款消息中体现退款原因", paramType = "form", required = false),
        ApiImplicitParam(name = "refund_account", value = "退款资金来源\t否\tString(30) REFUND_SOURCE_RECHARGE_FUNDS 仅针对老资金流商户使用 REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款）REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款", paramType = "form", required = false)
    ])
    @PostMapping("/refund")
    fun refund(transaction_id: String?, out_trade_no: String?, out_refund_no: String, total_fee: String, refund_fee: String, refund_fee_type: String?, refund_desc: String?, refund_account: String?): BusinessMessage<Map<String, String>> {
        log.debug { "申请退款, 微信订单号 = [$transaction_id]，商户订单号= [$out_trade_no], 商户退款单号 = [$out_refund_no], 订单金额 = [$total_fee], 退款金额 = [$refund_fee]，货币种类= [$refund_fee_type], 退款原因 = [$refund_desc], 退款资金来源 = [$refund_account]" }
        val message = BusinessMessage<Map<String, String>>()
        try {
            message.data = this.wxPayService.refund(transaction_id, out_trade_no, out_refund_no, total_fee, refund_fee, refund_fee_type, refund_desc, refund_account)
            message.success = true
        } catch (e: Exception) {
            log.error { "申请退款失败，$e" }
            message.msg = "申请退款失败，${e.message}"
        }
        return message
    }

    /**
     * 关闭订单
     *
     * 以下情况需要调用关单接口：商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
     * 注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。
     * 来源 https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=9_3&index=3
     *
     * @param out_trade_no    商户订单号	是	String(32)	1217752501201407033233368018	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     * @return 响应信息
     */
    @ApiOperation(value = "关闭订单", notes = "### [关闭订单](https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=9_3&index=3)\n" +
            "- 以下情况需要调用关单接口：商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。" +
            "- 注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "out_trade_no", value = "商户订单号\t是\tString(32)\t1217752501201407033233368018\t商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。", paramType = "form", required = false)
    ])
    @PostMapping("/close")
    fun closeOrder(out_trade_no: String): BusinessMessage<Map<String, String>> {
        log.debug { "关闭订单，商户订单号= [$out_trade_no]" }
        val message = BusinessMessage<Map<String, String>>()
        try {
            message.data = this.wxPayService.closeOrder(out_trade_no)
            message.success = true
        } catch (e: Exception) {
            log.error { "关闭订单失败，$e" }
            message.msg = "关闭订单失败，${e.message}"
        }
        return message
    }

    /**
     * 查询订单
     *
     * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。
     * 需要调用查询接口的情况：
     * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
     * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
     * ◆ 调用刷卡支付API，返回USERPAYING的状态；
     * ◆ 调用关单或撤销接口API之前，需确认支付状态；
     * 来源 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
     *
     * @param transaction_id    微信订单号	二选一	String(32)	1009660380201506130728806387	微信的订单号，建议优先使用
     * @param out_trade_no    商户订单号	String(32)	20150806125346	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。 详见商户订单号
     * @return 响应信息
     */
    @ApiOperation(value = "查询订单", notes = "### [查询订单](https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2)\n" +
            "该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。\n" +
            "需要调用查询接口的情况：\n" +
            "- 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；\n" +
            "- 调用支付接口后，返回系统错误或未知交易状态情况；\n" +
            "- 调用刷卡支付API，返回USERPAYING的状态；\n" +
            "- 调用关单或撤销接口API之前，需确认支付状态；")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "transaction_id", value = "微信订单号\t二选一\tString(32)\t1009660380201506130728806387\t微信的订单号，建议优先使用", paramType = "form", required = false),
        ApiImplicitParam(name = "out_trade_no", value = "商户订单号\tString(32)\t20150806125346\t商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。 详见商户订单号", paramType = "form", required = false)
    ])
    @PostMapping("/query")
    fun orderQuery(transaction_id: String?, out_trade_no: String?): BusinessMessage<Map<String, String>> {
        log.debug { "查询订单, 微信订单号 = [$transaction_id]，商户订单号 = [$out_trade_no]" }
        val message = BusinessMessage<Map<String, String>>()
        try {
            message.data = this.wxPayService.orderQuery(transaction_id, out_trade_no)
            message.success = true
        } catch (e: Exception) {
            log.error { "查询订单失败，$e" }
            message.msg = "查询订单失败，${e.message}"
        }
        return message
    }

    /**
     * 统一下单-JSAPI(公众号)
     *
     * 除被扫支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再按扫码、JSAPI、APP等不同场景生成交易串调起支付。
     * 来源 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
     *
     * @param device_info    设备号 否 	String(32) 	013467007045764 	终端设备号(门店号或收银设备ID)，默认请传"WEB"
     * @param body 商品描述 是 	String(128) 	商品描述交易字段格式根据不同的应用场景按照以下格式：APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
     * @param detail 商品详情 	否 	String(8192) 	  	商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”
     * @param attach 附加数据	    否 	String(127) 	深圳分店 	附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     * @param out_trade_no 商户订单号	是 	String(32) 	20150806125346 	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。详见商户订单号
     * @param fee_type    货币类型 	否 	String(16) 	CNY 	符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * @param total_fee    总金额 	是 	Int 	888 	订单总金额，单位为分，详见支付金额
     * @param time_start    交易起始时间 	否 	String(14) 	20091225091010 	订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     * @param time_expire    交易结束时间 	否 	String(14) 	20091227091010 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则 注意：最短失效时间间隔必须大于5分钟
     * @param goods_tag    订单优惠标记 	否 	String(32) 	WXG 	订单优惠标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
     * @param product_id    商品ID	否	String(32)	12235413214070356458058	trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
     * @param limit_pay    指定支付方式 	否 	String(32) 	no_credit 	no_credit--指定不能使用信用卡支付
     * @param openid    用户标识	否	String(128)	oUpF8uMuAJO_M2pxb1Q9zNjWeS6o	trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
     * @param scene_info    场景信息 	否 	String(256) 该字段用于上报场景信息，目前支持上报实际门店信息。该字段为JSON对象数据，对象格式为{"store_info":{"id": "门店ID","name": "名称","area_code": "编码","address": "地址" }} ，字段详细说明请点击行前的+展开
     *                       {
     *                         "store_info": {
     *                           "id": "SZTX001", // 门店唯一标识
     *                           "name": "腾大餐厅", // 门店名称
     *                           "area_code": "440305", // 门店所在地行政区划码，详细见《最新县及县以上行政区划代码》
     *                           "address": "科技园中一路腾讯大厦" //门店详细地址
     *                         }
     *                       }
     * @return 响应信息
     */
    @ApiOperation(value = "统一收单线下交易预创建", notes = "### [统一下单-JSAPI(公众号)](https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1)\n" +
            "- 除被扫支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再按扫码、JSAPI、APP等不同场景生成交易串调起支付。")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "device_info", value = "设备号 否 \tString(32) \t013467007045764 \t终端设备号(门店号或收银设备ID)，默认请传\"WEB\"", paramType = "form", required = true),
        ApiImplicitParam(name = "body", value = "商品描述 是 \tString(128) \t商品描述交易字段格式根据不同的应用场景按照以下格式：APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。", paramType = "form", required = false),
        ApiImplicitParam(name = "detail", value = "商品详情 \t否 \tString(8192) \t  \t商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”", paramType = "form", required = true),
        ApiImplicitParam(name = "attach", value = "附加数据\t    否 \tString(127) \t深圳分店 \t附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据", paramType = "form", required = false),
        ApiImplicitParam(name = "out_trade_no", value = "商户订单号\t是 \tString(32) \t20150806125346 \t商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。详见商户订单号", paramType = "form", required = true),
        ApiImplicitParam(name = "fee_type", value = "货币类型 \t否 \tString(16) \tCNY \t符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型", paramType = "form", required = false),
        ApiImplicitParam(name = "total_fee", value = "总金额 \t是 \tInt \t888 \t订单总金额，单位为分，详见支付金额", paramType = "form", required = false),
        ApiImplicitParam(name = "time_start", value = "交易起始时间 \t否 \tString(14) \t20091225091010 \t订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则", paramType = "form", required = false),
        ApiImplicitParam(name = "time_expire", value = "交易结束时间 \t否 \tString(14) \t20091227091010 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则 注意：最短失效时间间隔必须大于5分钟", paramType = "form", required = false),
        ApiImplicitParam(name = "goods_tag", value = "订单优惠标记 \t否 \tString(32) \tWXG \t订单优惠标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠", paramType = "form", required = false),
        ApiImplicitParam(name = "notify_url", value = "通知地址 \t是 \tString(256) \thttp://www.weixin.qq.com/wxpay/pay.php \t接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。", paramType = "form", required = false),
        ApiImplicitParam(name = "product_id", value = "商品ID\t否\tString(32)\t12235413214070356458058\ttrade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。", paramType = "form", required = false),
        ApiImplicitParam(name = "limit_pay", value = "指定支付方式 \t否 \tString(32) \tno_credit \tno_credit--指定不能使用信用卡支付", paramType = "form", required = false),
        ApiImplicitParam(name = "openid", value = "用户标识\t否\tString(128)\toUpF8uMuAJO_M2pxb1Q9zNjWeS6o\ttrade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换", paramType = "form", required = true),
        ApiImplicitParam(name = "scene_info", value = "场景信息 \t否 \tString(256) 该字段用于上报场景信息，目前支持上报实际门店信息。该字段为JSON对象数据，对象格式为{\"store_info\":{\"id\": \"门店ID\",\"name\": \"名称\",\"area_code\": \"编码\",\"address\": \"地址\" }}", paramType = "form", required = false)
    ])
    @PostMapping("/unified-order-wechat-public")
    fun unifiedOrderByWeChatPublic(request: HttpServletRequest, device_info: String?, body: String, detail: String?,
                                   attach: String?, out_trade_no: String, fee_type: String?, total_fee: String,
                                   time_start: String?, time_expire: String?, goods_tag: String?, notify_url: String?, product_id: String?,
                                   limit_pay: String?, openid: String, scene_info: String?): BusinessMessage<Map<String, String>> {
        log.debug {
            "统一下单-JSAPI(公众号)，设备号= [$device_info], 商品描述 = [$body], 商品详情 = [$detail], " +
                    "附加数据 = [$attach], 商户订单号 = [$out_trade_no], 货币类型 = [$fee_type], " +
                    "总金额 = [$total_fee], 交易起始时间 = [$time_start], 交易结束时间 = [$time_expire], " +
                    "订单优惠标记 = [$goods_tag], 通知地址 = [$notify_url], 商品ID = [$product_id], 指定支付方式 = [$limit_pay], " +
                    "用户标识 = [$openid], 场景信息 = [$scene_info]"
        }
        val message = BusinessMessage<Map<String, String>>()
        try {
            val clientIp = ClientIPUtil.getClientIP(request)
            message.data = this.wxPayService.unifiedOrderByWeChatPublic(device_info, body, detail, attach, out_trade_no, fee_type, total_fee, clientIp, time_start, time_expire, goods_tag, notify_url, product_id, limit_pay, openid, scene_info)
            message.success = true
        } catch (e: Exception) {
            log.error { "统一下单-JSAPI(公众号)失败，$e" }
            message.msg = "统一下单-JSAPI(公众号)失败，${e.message}"
        }
        return message
    }

    /**
     * 统一下单-Native(扫码支付)
     *
     * 商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再在APP里面调起支付。
     * 来源 https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1
     *
     * @param device_info    设备号 否 	String(32) 	013467007045764 	终端设备号(门店号或收银设备ID)，默认请传"WEB"
     * @param body 商品描述 是 	String(128) 	商品描述交易字段格式根据不同的应用场景按照以下格式：APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
     * @param detail 商品详情 	否 	String(8192) 	  	商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”
     * @param attach 附加数据	    否 	String(127) 	深圳分店 	附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     * @param out_trade_no 商户订单号	是 	String(32) 	20150806125346 	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。详见商户订单号
     * @param fee_type    货币类型 	否 	String(16) 	CNY 	符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * @param total_fee    总金额 	是 	Int 	888 	订单总金额，单位为分，详见支付金额
     * @param time_start    交易起始时间 	否 	String(14) 	20091225091010 	订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     * @param time_expire    交易结束时间 	否 	String(14) 	20091227091010 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则 注意：最短失效时间间隔必须大于5分钟
     * @param goods_tag    订单优惠标记 	否 	String(32) 	WXG 	订单优惠标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
     * @param notify_url    通知地址 	是 	String(256) 	http://www.weixin.qq.com/wxpay/pay.php 	接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
     * @param product_id    商品ID	否	String(32)	12235413214070356458058	trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
     * @param limit_pay    指定支付方式 	否 	String(32) 	no_credit 	no_credit--指定不能使用信用卡支付
     * @param openid    用户标识	否	String(128)	oUpF8uMuAJO_M2pxb1Q9zNjWeS6o	trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
     * @param scene_info    场景信息 	否 	String(256) 该字段用于上报场景信息，目前支持上报实际门店信息。该字段为JSON对象数据，对象格式为{"store_info":{"id": "门店ID","name": "名称","area_code": "编码","address": "地址" }} ，字段详细说明请点击行前的+展开
     *                       {
     *                         "store_info": {
     *                           "id": "SZTX001", // 门店唯一标识
     *                           "name": "腾大餐厅", // 门店名称
     *                           "area_code": "440305", // 门店所在地行政区划码，详细见《最新县及县以上行政区划代码》
     *                           "address": "科技园中一路腾讯大厦" //门店详细地址
     *                         }
     *                       }
     * @return 响应信息
     */
    @ApiOperation(value = "统一下单-Native(扫码支付)", notes = "### [统一下单-Native(扫码支付)](https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1)\n" +
            "- 商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再在APP里面调起支付。")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "device_info", value = "设备号 否 \tString(32) \t013467007045764 \t终端设备号(门店号或收银设备ID)，默认请传\"WEB\"", paramType = "form", required = true),
        ApiImplicitParam(name = "body", value = "商品描述 是 \tString(128) \t商品描述交易字段格式根据不同的应用场景按照以下格式：APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。", paramType = "form", required = false),
        ApiImplicitParam(name = "detail", value = "商品详情 \t否 \tString(8192) \t  \t商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”", paramType = "form", required = true),
        ApiImplicitParam(name = "attach", value = "附加数据\t    否 \tString(127) \t深圳分店 \t附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据", paramType = "form", required = false),
        ApiImplicitParam(name = "out_trade_no", value = "商户订单号\t是 \tString(32) \t20150806125346 \t商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。详见商户订单号", paramType = "form", required = true),
        ApiImplicitParam(name = "fee_type", value = "货币类型 \t否 \tString(16) \tCNY \t符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型", paramType = "form", required = false),
        ApiImplicitParam(name = "total_fee", value = "总金额 \t是 \tInt \t888 \t订单总金额，单位为分，详见支付金额", paramType = "form", required = false),
        ApiImplicitParam(name = "time_start", value = "交易起始时间 \t否 \tString(14) \t20091225091010 \t订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则", paramType = "form", required = false),
        ApiImplicitParam(name = "time_expire", value = "交易结束时间 \t否 \tString(14) \t20091227091010 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则 注意：最短失效时间间隔必须大于5分钟", paramType = "form", required = false),
        ApiImplicitParam(name = "goods_tag", value = "订单优惠标记 \t否 \tString(32) \tWXG \t订单优惠标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠", paramType = "form", required = false),
        ApiImplicitParam(name = "notify_url", value = "通知地址 \t是 \tString(256) \thttp://www.weixin.qq.com/wxpay/pay.php \t接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。", paramType = "form", required = false),
        ApiImplicitParam(name = "product_id", value = "商品ID\t否\tString(32)\t12235413214070356458058\ttrade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。", paramType = "form", required = false),
        ApiImplicitParam(name = "limit_pay", value = "指定支付方式 \t否 \tString(32) \tno_credit \tno_credit--指定不能使用信用卡支付", paramType = "form", required = false),
        ApiImplicitParam(name = "openid", value = "用户标识\t否\tString(128)\toUpF8uMuAJO_M2pxb1Q9zNjWeS6o\ttrade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换", paramType = "form", required = true),
        ApiImplicitParam(name = "scene_info", value = "场景信息 \t否 \tString(256) 该字段用于上报场景信息，目前支持上报实际门店信息。该字段为JSON对象数据，对象格式为{\"store_info\":{\"id\": \"门店ID\",\"name\": \"名称\",\"area_code\": \"编码\",\"address\": \"地址\" }}", paramType = "form", required = false)
    ])
    @PostMapping("/unified-order-native")
    fun unifiedOrderByNative(request: HttpServletRequest, device_info: String?, body: String, detail: String?,
                             attach: String?, out_trade_no: String, fee_type: String?, total_fee: String,
                             time_start: String?, time_expire: String?, goods_tag: String?, notify_url: String?, product_id: String,
                             limit_pay: String?, openid: String?, scene_info: String?): BusinessMessage<Map<String, String>> {
        log.debug {
            "统一下单-JSAPI(公众号)，设备号= [$device_info], 商品描述 = [$body], 商品详情 = [$detail], " +
                    "附加数据 = [$attach], 商户订单号 = [$out_trade_no], 货币类型 = [$fee_type], " +
                    "总金额 = [$total_fee], 交易起始时间 = [$time_start], 交易结束时间 = [$time_expire], " +
                    "订单优惠标记 = [$goods_tag], 通知地址 = [$notify_url], 商品ID = [$product_id], 指定支付方式 = [$limit_pay], " +
                    "用户标识 = [$openid], 场景信息 = [$scene_info]"
        }
        val message = BusinessMessage<Map<String, String>>()
        try {
            val clientIp = ClientIPUtil.getClientIP(request)
            message.data = this.wxPayService.unifiedOrderByNative(device_info, body, detail, attach, out_trade_no, fee_type, total_fee, clientIp, time_start, time_expire, goods_tag, notify_url, product_id, limit_pay, openid, scene_info)
            message.success = true
        } catch (e: Exception) {
            log.error { "统一下单-JSAPI(公众号)失败，$e" }
            message.msg = "统一下单-JSAPI(公众号)失败，${e.message}"
        }
        return message
    }

    /**
     * 统一下单-APP
     *
     * 商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再在APP里面调起支付。
     * 来源 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
     *
     * @param device_info    设备号 否 	String(32) 	013467007045764 	终端设备号(门店号或收银设备ID)，默认请传"WEB"
     * @param body 商品描述 是 	String(128) 	腾讯充值中心-QQ会员充值 商品描述交易字段格式根据不同的应用场景按照以下格式： APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
     * @param detail 商品详情 	否 	String(8192) 	  	商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”
     * @param attach 附加数据	    否 	String(127) 	深圳分店 	附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     * @param out_trade_no 商户订单号	是 	String(32) 	20150806125346 	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。详见商户订单号
     * @param fee_type    货币类型 	否 	String(16) 	CNY 	符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * @param total_fee    总金额 	是 	Int 	888 	订单总金额，单位为分，详见支付金额
     * @param time_start    交易起始时间 	否 	String(14) 	20091225091010 	订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     * @param time_expire    交易结束时间 	否 	String(14) 	20091227091010 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则 注意：最短失效时间间隔必须大于5分钟
     * @param goods_tag    订单优惠标记 	否 	String(32) 	WXG 	订单优惠标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
     * @param notify_url    通知地址 	是 	String(256) 	http://www.weixin.qq.com/wxpay/pay.php 	接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
     * @param limit_pay    指定支付方式 	否 	String(32) 	no_credit 	no_credit--指定不能使用信用卡支付
     * @param scene_info    场景信息 	否 	String(256) 该字段用于统一下单时上报场景信息，目前支持上报实际门店信息。
     *                       {
     *                           "store_id": "SZT10000", //门店唯一标识，选填，String(32)
     *                           "store_name":"腾讯大厦腾大餐厅”//门店名称，选填，String(64)
     *                       }
     * @return 响应信息
     */
    @ApiOperation(value = "统一下单-APP", notes = "### [统一下单-APP](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1)\n" +
            "- 商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再在APP里面调起支付。")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "device_info", value = "设备号 否 \tString(32) \t013467007045764 \t终端设备号(门店号或收银设备ID)，默认请传\"WEB\"", paramType = "form", required = true),
        ApiImplicitParam(name = "body", value = "商品描述 是 \tString(128) \t商品描述交易字段格式根据不同的应用场景按照以下格式：APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。", paramType = "form", required = false),
        ApiImplicitParam(name = "detail", value = "商品详情 \t否 \tString(8192) \t  \t商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”", paramType = "form", required = true),
        ApiImplicitParam(name = "attach", value = "附加数据\t    否 \tString(127) \t深圳分店 \t附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据", paramType = "form", required = false),
        ApiImplicitParam(name = "out_trade_no", value = "商户订单号\t是 \tString(32) \t20150806125346 \t商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。详见商户订单号", paramType = "form", required = true),
        ApiImplicitParam(name = "fee_type", value = "货币类型 \t否 \tString(16) \tCNY \t符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型", paramType = "form", required = false),
        ApiImplicitParam(name = "total_fee", value = "总金额 \t是 \tInt \t888 \t订单总金额，单位为分，详见支付金额", paramType = "form", required = false),
        ApiImplicitParam(name = "time_start", value = "交易起始时间 \t否 \tString(14) \t20091225091010 \t订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则", paramType = "form", required = false),
        ApiImplicitParam(name = "time_expire", value = "交易结束时间 \t否 \tString(14) \t20091227091010 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则 注意：最短失效时间间隔必须大于5分钟", paramType = "form", required = false),
        ApiImplicitParam(name = "goods_tag", value = "订单优惠标记 \t否 \tString(32) \tWXG \t订单优惠标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠", paramType = "form", required = false),
        ApiImplicitParam(name = "notify_url", value = "通知地址 \t是 \tString(256) \thttp://www.weixin.qq.com/wxpay/pay.php \t接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。", paramType = "form", required = false),
        ApiImplicitParam(name = "limit_pay", value = "指定支付方式 \t否 \tString(32) \tno_credit \tno_credit--指定不能使用信用卡支付", paramType = "form", required = false),
        ApiImplicitParam(name = "scene_info", value = "场景信息 \t否 \tString(256) 该字段用于统一下单时上报场景信息，目前支持上报实际门店信息。{\"store_id\": \"SZT10000\", //门店唯一标识，选填，String(32)\"store_name\":\"腾讯大厦腾大餐厅”//门店名称，选填，String(64)\"}", paramType = "form", required = false)
    ])
    @PostMapping("/unified-order-app")
    fun unifiedOrderByApp(request: HttpServletRequest, device_info: String?, body: String, detail: String?, attach: String?,
                          out_trade_no: String, fee_type: String?, total_fee: String, time_start: String?, time_expire: String?, goods_tag: String?,
                          notify_url: String?, limit_pay: String?, scene_info: String?): BusinessMessage<Map<String, String>> {
        log.debug {
            "统一下单-APP，设备号= [$device_info], 商品描述 = [$body], 商品详情 = [$detail], " +
                    "附加数据 = [$attach], 商户订单号 = [$out_trade_no], 货币类型 = [$fee_type], " +
                    "总金额 = [$total_fee], 交易起始时间 = [$time_start], 交易结束时间 = [$time_expire], " +
                    "订单优惠标记 = [$goods_tag], 通知地址 = [$notify_url], 指定支付方式 = [$limit_pay], 场景信息 = [$scene_info]"
        }
        val message = BusinessMessage<Map<String, String>>()
        try {
            val clientIp = ClientIPUtil.getClientIP(request)
            message.data = this.wxPayService.unifiedOrderByApp(device_info, body, detail, attach, out_trade_no, fee_type, total_fee, clientIp, time_start, time_expire, goods_tag, notify_url, limit_pay, scene_info)
            message.success = true
        } catch (e: Exception) {
            log.error { "统一下单-APP失败，$e" }
            message.msg = "统一下单-APP失败，${e.message}"
        }
        return message
    }

    /**
     * 统一下单-H5
     *
     * H5支付是指商户在微信客户端外的移动端网页展示商品或服务，用户在前述页面确认使用微信支付时，商户发起本服务呼起微信客户端进行支付。 主要用于触屏版的手机浏览器请求微信支付的场景。可以方便的从外部浏览器唤起微信支付。
     * 来源 https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=9_20&index=1
     *
     * @param device_info    设备号 否 	String(32) 	013467007045764 	终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
     * @param body 商品描述 是 	String(128) 	腾讯充值中心-QQ会员充值 商品描述交易字段格式根据不同的应用场景按照以下格式： APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
     * @param detail 商品详情 	否 	String(8192) 	  	商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”
     * @param attach 附加数据	    否 	String(127) 	深圳分店 	附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     * @param out_trade_no 商户订单号	是 	String(32) 	20150806125346 	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。详见商户订单号
     * @param fee_type    货币类型 	否 	String(16) 	CNY 	符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * @param total_fee    总金额 	是 	Int 	888 	订单总金额，单位为分，详见支付金额
     * @param time_start    交易起始时间 	否 	String(14) 	20091225091010 	订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     * @param time_expire    交易结束时间 	否 	String(14) 	20091227091010 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则 注意：最短失效时间间隔必须大于5分钟
     * @param goods_tag    订单优惠标记 	否 	String(32) 	WXG 	订单优惠标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
     * @param notify_url    通知地址 	是 	String(256) 	http://www.weixin.qq.com/wxpay/pay.php 	接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
     * @param limit_pay    指定支付方式 	否 	String(32) 	no_credit 	no_credit--指定不能使用信用卡支付
     * @param openid    用户标识	否	String(128)	oUpF8uMuAJO_M2pxb1Q9zNjWeS6o	trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
     * @param scene_info    场景信息 	否 	String(256) 该字段用于上报支付的场景信息,针对H5支付有以下三种场景,请根据对应场景上报,H5支付不建议在APP端使用，针对场景1，2请接入APP支付，不然可能会出现兼容性问题
     *                      1，IOS移动应用
     *                       {"h5_info": //h5支付固定传"h5_info"
     *                           {"type": "",  //场景类型
     *                              "app_name": "",  //应用名
     *                              "bundle_id": ""  //bundle_id
     *                           }
     *                       }
     *                       2，安卓移动应用
     *                       {"h5_info": //h5支付固定传"h5_info"
     *                          {"type": "",  //场景类型
     *                              "app_name": "",  //应用名
     *                              "package_name": ""  //包名
     *                          }
     *                       }
     *                       3，WAP网站应用
     *                       {"h5_info": //h5支付固定传"h5_info"
     *                          {"type": "",  //场景类型
     *                              "wap_url": "",//WAP网站URL地址
     *                              "wap_name": ""  //WAP 网站名
     *                          }
     *                       }
     * @return 响应信息
     */
    @ApiOperation(value = "统一下单-H5", notes = "### [统一下单-H5](https://pay.weixin.qq.com/wiki/doc/api/H5.php?chapter=9_20&index=1)\n" +
            "- 商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再在APP里面调起支付。")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "device_info", value = "设备号 否 \tString(32) \t013467007045764 \t终端设备号(门店号或收银设备ID)，默认请传\"WEB\"", paramType = "form", required = true),
        ApiImplicitParam(name = "body", value = "商品描述 是 \tString(128) \t商品描述交易字段格式根据不同的应用场景按照以下格式：APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。", paramType = "form", required = false),
        ApiImplicitParam(name = "detail", value = "商品详情 \t否 \tString(8192) \t  \t商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”", paramType = "form", required = true),
        ApiImplicitParam(name = "attach", value = "附加数据\t    否 \tString(127) \t深圳分店 \t附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据", paramType = "form", required = false),
        ApiImplicitParam(name = "out_trade_no", value = "商户订单号\t是 \tString(32) \t20150806125346 \t商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。详见商户订单号", paramType = "form", required = true),
        ApiImplicitParam(name = "fee_type", value = "货币类型 \t否 \tString(16) \tCNY \t符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型", paramType = "form", required = false),
        ApiImplicitParam(name = "total_fee", value = "总金额 \t是 \tInt \t888 \t订单总金额，单位为分，详见支付金额", paramType = "form", required = false),
        ApiImplicitParam(name = "time_start", value = "交易起始时间 \t否 \tString(14) \t20091225091010 \t订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则", paramType = "form", required = false),
        ApiImplicitParam(name = "time_expire", value = "交易结束时间 \t否 \tString(14) \t20091227091010 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则 注意：最短失效时间间隔必须大于5分钟", paramType = "form", required = false),
        ApiImplicitParam(name = "goods_tag", value = "订单优惠标记 \t否 \tString(32) \tWXG \t订单优惠标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠", paramType = "form", required = false),
        ApiImplicitParam(name = "notify_url", value = "通知地址 \t是 \tString(256) \thttp://www.weixin.qq.com/wxpay/pay.php \t接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。", paramType = "form", required = false),
        ApiImplicitParam(name = "limit_pay", value = "指定支付方式 \t否 \tString(32) \tno_credit \tno_credit--指定不能使用信用卡支付", paramType = "form", required = false),
        ApiImplicitParam(name = "openid", value = "用户标识\t否\tString(128)\toUpF8uMuAJO_M2pxb1Q9zNjWeS6o\ttrade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换", paramType = "form", required = false),
        ApiImplicitParam(name = "scene_info", value = "场景信息 \t否 \tString(256) 该字段用于统一下单时上报场景信息，目前支持上报实际门店信息。{\"store_id\": \"SZT10000\", //门店唯一标识，选填，String(32)\"store_name\":\"腾讯大厦腾大餐厅”//门店名称，选填，String(64)\"}", paramType = "form", required = false)
    ])
    @PostMapping("/unified-order-h5")
    fun unifiedOrderByH5(request: HttpServletRequest, device_info: String?, body: String, detail: String?, attach: String?,
                         out_trade_no: String, fee_type: String?, total_fee: String, time_start: String?, time_expire: String?, goods_tag: String?,
                         notify_url: String?, limit_pay: String?, openid: String?, scene_info: String): BusinessMessage<Map<String, String>> {
        log.debug {
            "统一下单-H5，设备号= [$device_info], 商品描述 = [$body], 商品详情 = [$detail], " +
                    "附加数据 = [$attach], 商户订单号 = [$out_trade_no], 货币类型 = [$fee_type], " +
                    "总金额 = [$total_fee], 交易起始时间 = [$time_start], 交易结束时间 = [$time_expire], " +
                    "订单优惠标记 = [$goods_tag], 通知地址 = [$notify_url], 指定支付方式 = [$limit_pay], 场景信息 = [$scene_info]"
        }
        val message = BusinessMessage<Map<String, String>>()
        try {
            val clientIp = ClientIPUtil.getClientIP(request)
            message.data = this.wxPayService.unifiedOrderByH5(device_info, body, detail, attach, out_trade_no, fee_type, total_fee, clientIp, time_start, time_expire, goods_tag, notify_url, limit_pay, openid, scene_info)
            message.success = true
        } catch (e: Exception) {
            log.error { "统一下单-H5失败，$e" }
            message.msg = "统一下单-H5失败，${e.message}"
        }
        return message
    }


}
