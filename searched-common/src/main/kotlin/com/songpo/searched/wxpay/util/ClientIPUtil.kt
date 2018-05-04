package com.songpo.searched.wxpay.util

import javax.servlet.http.HttpServletRequest

/**
 * Created by SongpoLiu on 2017/6/5.
 */
object ClientIPUtil {

    /***
     * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP,
     * @param request
     * @return
     */
    fun getClientIP(request: HttpServletRequest): String? {
        var fromSource = "X-Real-IP"
        var ip: String? = request.getHeader("X-Real-IP")
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("X-Forwarded-For")
            fromSource = "X-Forwarded-For"
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
            fromSource = "Proxy-Client-IP"
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
            fromSource = "WL-Proxy-Client-IP"
        }
        if (ip == null || ip.length == 0 || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.remoteAddr
            fromSource = "request.getRemoteAddr"
        }
        return ip
    }
}
