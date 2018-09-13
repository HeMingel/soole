package com.songpo.searched.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.songpo.searched.cache.SmsPasswordCache;
import com.songpo.searched.domain.BusinessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;


@Service
public class QcloudSmsService {

    @Autowired
    private SmsPasswordCache smsPasswordCache;
    // 短信应用SDK AppID
    int appid = ; // 1400开头
    // 短信应用SDK AppKey
    String appkey = "a378262450ca156fca3941ee43fb3741";
    // 短信模板ID，需要在短信应用中申请
    // NOTE: 这里的模板ID`7839`只是一个示例，
    // 真实的模板ID需要在短信控制台中申请
    int templateId =;
    //slb注册通知模板
    int slbMsgId = ;
    // 签名
    // 真实的签名需要在短信控制台中申请，另外
    // 签名参数使用的是`签名内容`，而不是`签名ID`
    String smsSign = "";

    /**
     * 指定模板ID单发短信
     * @param mobile 手机列表
     * @param zoneCode 地区编号 如 86
     * @param code 验证码
     * @param
     */
    public BusinessMessage sendQcTemple(String mobile[] ,String zoneCode,String code){
        BusinessMessage message  = new BusinessMessage() ;
        try {
            //模板参数 {1} 验证码 {2} 过期分钟
            String[] params = {code,"5"};
            SmsMultiSender msender = new SmsMultiSender(appid, appkey);
            SmsMultiSenderResult result = msender.sendWithParam(zoneCode, mobile,
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            //System.out.print("腾讯返回结果+++++++++++++++++++" + result);
            JSONObject jsonObject = JSONObject.parseObject(result.toString());
            String resultCode = jsonObject.getString("result");
            String resultMsg = jsonObject.getString("errmsg");
            if ("0".equals(resultCode)){
                message.setSuccess(true);
            }
            //返回短信验证码
            message.setCode(resultCode);
            message.setMsg(resultMsg);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 发送钱包注册密码
     * @param mobile 手机号
     * @param zoneCode 区号
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    public BusinessMessage sendSlbMsgId(String mobile[] ,String zoneCode,String userName,String password){
        BusinessMessage message  = new BusinessMessage() ;
        try {
            //模板参数 {1} SLB用户名 {2} 手机号
            String[] params = {userName, password};
            SmsMultiSender msender = new SmsMultiSender(appid, appkey);
            SmsMultiSenderResult result = msender.sendWithParam(zoneCode, mobile,
                    slbMsgId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.print("腾讯返回结果+++++++++++++++++++" + result);
            JSONObject jsonObject = JSONObject.parseObject(result.toString());
            String resultCode = jsonObject.getString("result");
            String resultMsg = jsonObject.getString("errmsg");
            if ("0".equals(resultCode)){
                message.setSuccess(true);
            }
            //返回短信验证码
            message.setCode(resultCode);
            message.setMsg(resultMsg);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return message;
    }

}
