//package com.songpo.searched.service;
//
//import com.github.wxpay.sdk.WXPayUtil;
//import com.songpo.searched.wxpay.util.MD5Util;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.PrintWriter;
//import java.util.*;
//
//@Service
//public class WxXmlVerify {
//
//    public static final Logger log = LoggerFactory.getLogger(WxXmlVerify.class);
//
//    @Value("sp.pay.wxpay.apiKey")
//    private String secret;
//    @Autowired
//    private LoginUserService loginUserService;
//
//    /**
//     * 校验XML的信息
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    public String verify(HttpServletRequest request, HttpServletResponse response) {
//        String result = null;
//        try {
//            log.debug("接收微信异步回调");
//            InputStream inStream = request.getInputStream();
//            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int len = 0;
//            while ((len = inStream.read(buffer)) != -1) {
//                outSteam.write(buffer, 0, len);
//            }
//            outSteam.close();
//            inStream.close();
//            String res = new String(outSteam.toByteArray(), "utf-8");
//            Map<String, String> map = null;
//            map = WXPayUtil.xmlToMap(res);
//
//
//            // 此处调用订单查询接口验证是否交易成功
//            Boolean isSucc = isTenPaySign(map, secret);
//            // 支付成功，商户处理后同步返回给微信参数
//            PrintWriter writer = response.getWriter();
//            if (!isSucc) {
//                // 支付失败， 记录流水失败
//                System.out.println("===============支付失败==============");
//                result = setXML("FAIL", "");
//            } else {
//                System.out.println("===============付款成功，业务处理完毕==============");
//                // 通知微信已经收到消息，不要再给我发消息了，否则微信会8连击调用本接口
//                result = setXML("SUCCESS", "OK");
//            }
//            writer.write(result);
//            writer.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("微信返回信息出错", e);
//        }
//        return result;
//    }
//
//    /**
//     * 验证回调签名
//     *
//     * @return
//     */
//    public static boolean isTenPaySign(Map<String, String> map, String secret) {
//        String characterEncoding = "utf-8";
//        String charset = "utf-8";
//        String signFromAPIResponse = map.get("sign");
//        if (signFromAPIResponse == null || "".equals(signFromAPIResponse)) {
//            System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
//            return false;
//        }
//        System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);
//        //过滤空 设置 TreeMap
//        SortedMap<String, String> packageParams = new TreeMap();
//
//        for (String parameter : map.keySet()) {
//            String parameterValue = map.get(parameter);
//            String v = "";
//            if (null != parameterValue) {
//                v = parameterValue.trim();
//            }
//            packageParams.put(parameter, v);
//        }
//
//        StringBuffer sb = new StringBuffer();
//        Set es = packageParams.entrySet();
//        Iterator it = es.iterator();
//
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            String k = (String) entry.getKey();
//            String v = (String) entry.getValue();
//            if (!"sign".equals(k) && null != v && !"".equals(v)) {
//                sb.append(k + "=" + v + "&");
//            }
//        }
//        sb.append("key=" + secret);
//
//        //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
//        //算出签名
//        String resultSign = "";
//        String tobesign = sb.toString();
//
//        if (null == charset || "".equals(charset)) {
//            resultSign = MD5Util.MD5Encode(tobesign, characterEncoding).toUpperCase();
//        } else {
//            try {
//                resultSign = MD5Util.MD5Encode(tobesign, characterEncoding).toUpperCase();
//            } catch (Exception e) {
//                resultSign = MD5Util.MD5Encode(tobesign, characterEncoding).toUpperCase();
//            }
//        }
//
//        String tenpaySign = packageParams.get("sign").toUpperCase();
//        return tenpaySign.equals(resultSign);
//    }
//
//
//    /**
//     * 返回给微信端的状态
//     *
//     * @param return_code
//     * @param return_msg
//     * @return
//     */
//    public static String setXML(String return_code, String return_msg) {
//        return "<xml>" +
//                "<return_code><![CDATA[" + return_code + "]]></return_code>" +
//                "<return_msg><![CDATA[" + return_msg + "]]></return_msg>" +
//                "</xml>";
//    }
//
//}
