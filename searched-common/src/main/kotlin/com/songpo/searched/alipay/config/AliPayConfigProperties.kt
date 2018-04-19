package com.songpo.searched.alipay.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * 支付宝支付服务配置
 */
@Configuration
@ConfigurationProperties(prefix = "sp.pay.alipay")
class AliPayConfigProperties(
        /**
         * 商户号
         */
        var appId: String = "",

        /**
         * 商户私钥
         */
        var merchantPrivateKey: String = "",

        /**
         * 支付宝公钥
         */
        var aliPayPublicKey: String = "",

        /**
         * 支付通知地址
         */
        var notifyUrl: String = "",

        /**
         * 支付完成后返回的地址
         */
        var returnUrl: String? = ""
)