package com.songpo.searched.service;

import com.github.wxpay.sdk.WXPayUtil;
import com.songpo.searched.wxpay.service.WxPayService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class PaymentService {

    public static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private WxPayService payService;

    /**
     * 返回给微信端的状态
     *
     * @param return_code
     * @param return_msg
     * @return
     */
    public static String setXML(String return_code, String return_msg) {
        return "<xml>" +
                "<return_code><![CDATA[" + return_code + "]]></return_code>" +
                "<return_msg><![CDATA[" + return_msg + "]]></return_msg>" +
                "</xml>";
    }

    public String wxNotify(HttpServletRequest request, HttpServletResponse response) {
        String retStr = "";
        try (InputStream is = request.getInputStream()) {
            // 读取支付回调参数
            byte[] bytes = IOUtils.readFully(is, request.getContentLength());
            if (bytes.length > 0) {
                String result = new String(bytes, StandardCharsets.UTF_8);
                if (StringUtils.isNotBlank(result)) {
                    // 支付回调参数
                    Map<String, String> resParams = WXPayUtil.xmlToMap(result);

                    // 验签
                    if (payService.wxpay.isPayResultNotifySignatureValid(resParams)) {
                        // 签名正确
                        // 进行处理。
                        // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
                    } else {
                        // 签名错误，如果数据里没有sign字段，也认为是签名错误
                    }
                }
            } else {

            }
        } catch (Exception e) {
            log.error("响应微信支付回调信息失败，{}", e);
        }
        return retStr;
    }
}
