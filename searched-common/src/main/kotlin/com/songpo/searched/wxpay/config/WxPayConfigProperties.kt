package com.songpo.searched.wxpay.config

import com.github.wxpay.sdk.WXPayConfig
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths


/**
 * 支付宝支付服务配置
 */
@Configuration
@ConfigurationProperties(prefix = "sp.pay.wxpay")
data class WxPayConfigProperties(
        /**
         * 商户号
         */
        var appId: String = "",

        /**
         * 商户号
         */
        var mchId: String = "",

        /**
         * 密钥
         */
        var secret: String = "",
        /**
         * 证书路径
         */
        var certPath: String = "",
        /**
         * 证书字节数组
         */
        var certData: ByteArray = ByteArray(0),
        /**
         * 支付通知地址
         */
        var notifyUrl: String = ""
) : WXPayConfig {

    override fun getAppID(): String {
        return appId
    }

    override fun getMchID(): String {
        return mchId
    }

    override fun getKey(): String {
        return secret
    }

    override fun getCertStream(): InputStream {
        var bytes = ByteArray(0)
        if (!certPath.isNullOrBlank()) {
            bytes = Files.readAllBytes(Paths.get(certPath))
        }

        return ByteArrayInputStream(bytes)
    }

    override fun getHttpConnectTimeoutMs(): Int {
        return 10000
    }

    override fun getHttpReadTimeoutMs(): Int {
        return 10000
    }

}