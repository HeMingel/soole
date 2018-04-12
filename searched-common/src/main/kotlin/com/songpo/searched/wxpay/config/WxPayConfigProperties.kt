package com.songpo.searched.wxpay.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "sp.pay.wxpay")
data class WxPayConfigProperties(
        var appId: String = "",
        var mchId: String = "",
        var secret: String = "",
        var certPath: String = "",
        var certData: ByteArray? = null,
        var notifyUrl: String = ""
)
