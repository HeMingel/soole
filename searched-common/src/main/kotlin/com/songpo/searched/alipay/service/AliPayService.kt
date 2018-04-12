package com.songpo.searched.alipay.service

import com.alipay.api.DefaultAlipayClient
import com.alipay.api.domain.*
import com.alipay.api.request.*
import com.alipay.api.response.*
import com.songpo.searched.alipay.config.AliPayConfigProperties
import org.springframework.stereotype.Service

/**
 * 支付宝支付服务集成实现
 */
@Service
class AliPayService(val configProperties: AliPayConfigProperties) {

    lateinit var client: DefaultAlipayClient

    init {
        initClient()
    }

    /**
     * 加载支付配置
     *
     * @param appId    String	支付配置唯一标识
     * @param merchantPrivateKey    String	支付配置唯一标识
     * @param alipayPublicKey    String	支付配置唯一标识
     * @param notifyUrl    String	支付配置唯一标识
     * @param returnUrl    String	支付配置唯一标识
     * @return 响应信息
     */
    fun loadConfig(appId: String, merchantPrivateKey: String, alipayPublicKey: String, notifyUrl: String, returnUrl: String?) {
        configProperties.appId = appId
        configProperties.merchantPrivateKey = merchantPrivateKey
        configProperties.alipayPublicKey = alipayPublicKey
        configProperties.notifyUrl = notifyUrl
        configProperties.returnUrl = returnUrl

        initClient()
    }

    /**
     * 初始化客户端
     */
    private final fun initClient() {
        client = DefaultAlipayClient(
                "https://openapi.alipay.com/gateway.do",
                configProperties.appId,
                configProperties.merchantPrivateKey,
                "json",
                "UTF-8",
                configProperties.alipayPublicKey,
                "RSA2")
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
    fun fastPayRefundQuery(tradeNo: String?, outTradeNo: String?, outRequestNo: String): AlipayTradeFastpayRefundQueryResponse? {
        val request = AlipayTradeFastpayRefundQueryRequest()
        val model = AlipayTradeFastpayRefundQueryModel()
        request.bizModel = model

        model.outRequestNo = outRequestNo

        if (!outTradeNo.isNullOrBlank()) {
            model.outTradeNo = outTradeNo
        }

        if (!tradeNo.isNullOrBlank()) {
            model.tradeNo = tradeNo
        }

        return client.execute(request)
    }

    /**
     * alipay.trade.order.settle(统一收单交易结算接口)
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
    fun orderSettle(outRequestNo: String, tradeNo: String, royaltyParameters: List<OpenApiRoyaltyDetailInfoPojo>, operatorId: String?): AlipayTradeOrderSettleResponse? {
        val request = AlipayTradeOrderSettleRequest()
        val model = AlipayTradeOrderSettleModel()
        request.bizModel = model

        model.outRequestNo = outRequestNo
        model.tradeNo = tradeNo
        model.royaltyParameters = royaltyParameters

        if (!operatorId.isNullOrBlank()) {
            model.operatorId = operatorId
        }

        return client.execute(request)
    }

    /**
     * alipay.trade.close(统一收单交易关闭接口)
     *
     * 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭。
     * 来源 https://docs.open.alipay.com/api_1/alipay.trade.close/
     *
     * @param tradeNo    String	特殊可选	64	该交易在支付宝系统中的交易流水号。最短 16 位，最长 64 位。和out_trade_no不能同时为空，如果同时传了 out_trade_no和 trade_no，则以 trade_no为准。
     * @param outTradeNo    String	特殊可选	64	订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no
     * @param operatorId    String	可选	28	卖家端自定义的的操作员ID
     * @return 响应信息
     */
    fun close(tradeNo: String?, outTradeNo: String?, operatorId: String?): AlipayTradeCloseResponse? {
        val request = AlipayTradeCloseRequest()
        val model = AlipayTradeCloseModel()
        request.bizModel = model

        if (!tradeNo.isNullOrBlank()) {
            model.tradeNo = tradeNo
        }

        if (!outTradeNo.isNullOrBlank()) {
            model.outTradeNo = outTradeNo
        }

        if (!operatorId.isNullOrBlank()) {
            model.operatorId = operatorId
        }

        return client.execute(request)
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
    fun cancel(outTradeNo: String?, tradeNo: String?): AlipayTradeCancelResponse? {
        val request = AlipayTradeCancelRequest()
        val model = AlipayTradeCancelModel()
        request.bizModel = model

        if (!outTradeNo.isNullOrBlank()) {
            model.outTradeNo = outTradeNo
        }

        if (!tradeNo.isNullOrBlank()) {
            model.tradeNo = tradeNo
        }

        return client.execute(request)
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
    fun refund(outTradeNo: String?, tradeNo: String?, refundAmount: String, refundReason: String?, outRequestNo: String?, operatorId: String?, storeId: String?, terminalId: String?): AlipayTradeRefundResponse? {
        val request = AlipayTradeRefundRequest()
        val model = AlipayTradeRefundModel()
        request.bizModel = model

        if (!outTradeNo.isNullOrBlank()) {
            model.outTradeNo = outTradeNo
        }

        if (!tradeNo.isNullOrBlank()) {
            model.tradeNo = tradeNo
        }

        model.refundAmount = refundAmount

        if (!refundReason.isNullOrBlank()) {
            model.refundReason = refundReason
        }

        if (!outRequestNo.isNullOrBlank()) {
            model.outRequestNo = outRequestNo
        }

        if (!operatorId.isNullOrBlank()) {
            model.operatorId = operatorId
        }

        if (!storeId.isNullOrBlank()) {
            model.storeId = storeId
        }

        if (!terminalId.isNullOrBlank()) {
            model.terminalId = terminalId
        }

        return client.execute(request)
    }

    /**
     * alipay.trade.precreate(统一收单线下交易预创建)
     *
     * 收银员通过收银台或商户后台调用支付宝接口，生成二维码后，展示给用户，由用户扫描二维码完成订单支付。
     * https://docs.open.alipay.com/api_1/alipay.trade.precreate/
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
    fun preCreate(outTradeNo: String, sellerId: String?, totalAmount: String, discountableAmount: String?,
                  subject: String, goodsDetail: List<GoodsDetail>?, body: String?, operatorId: String?, storeId: String?,
                  disablePayChannels: String?, enablePayChannels: String?, terminalId: String?, extendParams: ExtendParams?,
                  timeoutExpress: String?, businessParams: String?): AlipayTradePrecreateResponse? {
        // 初始化预下单请求
        val request = AlipayTradePrecreateRequest()

        // 设置通知地址
        request.notifyUrl = configProperties.notifyUrl

        // 如果返回地址不为空，则设置返回地址
        if (!configProperties.returnUrl.isNullOrBlank()) {
            request.returnUrl = configProperties.returnUrl
        }

        // 初始化预下单模型
        val model = AlipayTradePrecreateModel()
        request.bizModel = model

        model.outTradeNo = outTradeNo

        if (!sellerId.isNullOrBlank()) {
            model.sellerId = sellerId
        }

        model.totalAmount = totalAmount

        if (!discountableAmount.isNullOrBlank()) {
            model.discountableAmount = discountableAmount
        }

        model.subject = subject

        if (null != goodsDetail) {
            model.goodsDetail = goodsDetail
        }

        if (!body.isNullOrBlank()) {
            model.body = body
        }

        if (!operatorId.isNullOrBlank()) {
            model.operatorId = operatorId
        }

        if (!storeId.isNullOrBlank()) {
            model.storeId = storeId
        }

        if (!disablePayChannels.isNullOrBlank()) {
            model.disablePayChannels = disablePayChannels
        }

        if (!enablePayChannels.isNullOrBlank()) {
            model.enablePayChannels = enablePayChannels
        }

        if (!terminalId.isNullOrBlank()) {
            model.terminalId = terminalId
        }

        if (null != extendParams) {
            model.extendParams = extendParams
        }

        if (!timeoutExpress.isNullOrBlank()) {
            model.timeoutExpress = timeoutExpress
        }

        if (!businessParams.isNullOrBlank()) {
            model.businessParams = businessParams
        }

        return client.execute(request)
    }

    /**
     * alipay.trade.create(统一收单交易创建接口)
     *
     * 收银员通过收银台或商户后台调用支付宝接口，生成二维码后，展示给用户，由用户扫描二维码完成订单支付。
     * https://docs.open.alipay.com/api_1/alipay.trade.precreate/
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
    fun create(outTradeNo: String, sellerId: String?, totalAmount: String, discountableAmount: String?,
               subject: String, body: String?, buyerId: String?, goodsDetail: List<GoodsDetail>?, operatorId: String?, storeId: String?,
               terminalId: String?, extendParams: ExtendParams?, timeoutExpress: String?, businessParams: String?): AlipayTradeCreateResponse? {
        // 初始化预下单请求
        val request = AlipayTradeCreateRequest()

        // 设置通知地址
        request.notifyUrl = configProperties.notifyUrl

        // 如果返回地址不为空，则设置返回地址
        if (!configProperties.returnUrl.isNullOrBlank()) {
            request.returnUrl = configProperties.returnUrl
        }

        val model = AlipayTradeCreateModel()
        request.bizModel = model

        model.outTradeNo = outTradeNo

        if (!sellerId.isNullOrBlank()) {
            model.sellerId = sellerId
        }

        model.totalAmount = totalAmount

        if (!discountableAmount.isNullOrBlank()) {
            model.discountableAmount = discountableAmount
        }

        model.subject = subject

        if (!body.isNullOrBlank()) {
            model.body = body
        }

        if (!buyerId.isNullOrBlank()) {
            model.buyerId = buyerId
        }

        if (null != goodsDetail) {
            model.goodsDetail = goodsDetail
        }

        if (!operatorId.isNullOrBlank()) {
            model.operatorId = operatorId
        }

        if (!storeId.isNullOrBlank()) {
            model.storeId = storeId
        }

        if (!terminalId.isNullOrBlank()) {
            model.terminalId = terminalId
        }

        if (null != extendParams) {
            model.extendParams = extendParams
        }

        if (!timeoutExpress.isNullOrBlank()) {
            model.timeoutExpress = timeoutExpress
        }

        if (!businessParams.isNullOrBlank()) {
            model.businessParams = businessParams
        }

        return client.execute(request)
    }

    /**
     * alipay.trade.pay(统一收单交易支付接口)
     *
     * 收银员使用扫码设备读取用户手机支付宝“付款码”/声波获取设备（如麦克风）读取用户手机支付宝的声波信息后，将二维码或条码信息/声波信息通过本接口上送至支付宝发起支付。
     * 参考 https://docs.open.alipay.com/api_1/alipay.trade.pay/
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
    fun pay(outTradeNo: String, scene: String, authCode: String, productCode: String, subject: String, buyerId: String?, sellerId: String?, totalAmount: String?, discountableAmount: String?,
            body: String?, goodsDetail: List<GoodsDetail>?, operatorId: String?, storeId: String?,
            terminalId: String?, extendParams: ExtendParams?, timeoutExpress: String?, businessParams: String?): AlipayTradePayResponse? {
        // 初始化预下单请求
        val request = AlipayTradePayRequest()

        // 设置通知地址
        request.notifyUrl = configProperties.notifyUrl

        // 如果返回地址不为空，则设置返回地址
        if (!configProperties.returnUrl.isNullOrBlank()) {
            request.returnUrl = configProperties.returnUrl
        }

        val model = AlipayTradePayModel()
        request.bizModel = model

        model.outTradeNo = outTradeNo
        model.scene = scene
        model.authCode = authCode

        model.productCode = productCode

        model.subject = subject

        if (!buyerId.isNullOrBlank()) {
            model.buyerId = buyerId
        }

        if (!sellerId.isNullOrBlank()) {
            model.buyerId = sellerId
        }

        if (!totalAmount.isNullOrBlank()) {
            model.totalAmount = totalAmount
        }

        if (!discountableAmount.isNullOrBlank()) {
            model.discountableAmount = discountableAmount
        }

        if (!body.isNullOrBlank()) {
            model.body = body
        }

        if (null != goodsDetail) {
            model.goodsDetail = goodsDetail
        }

        if (!operatorId.isNullOrBlank()) {
            model.operatorId = operatorId
        }

        if (!storeId.isNullOrBlank()) {
            model.storeId = storeId
        }

        if (!terminalId.isNullOrBlank()) {
            model.terminalId = terminalId
        }

        if (null != extendParams) {
            model.extendParams = extendParams
        }

        if (!timeoutExpress.isNullOrBlank()) {
            model.timeoutExpress = timeoutExpress
        }

        return client.execute(request)
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
    fun query(outTradeNo: String?, tradeNo: String?): AlipayTradeCustomsQueryResponse? {
        val request = AlipayTradeCustomsQueryRequest()
        val model = AlipayTradeQueryModel()
        request.bizModel = model

        if (!outTradeNo.isNullOrBlank()) {
            model.outTradeNo = outTradeNo
        }

        if (!tradeNo.isNullOrBlank()) {
            model.tradeNo = tradeNo
        }

        return client.execute(request)
    }

}
