package com.songpo.searched.service;


import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.constant.BaseConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.util.AESUtils;


import com.songpo.searched.util.HttpUtil;
import com.songpo.searched.util.MD5SignUtils;
import com.songpo.searched.util.RSAUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * 第三方钱包接口 （目前主要针对搜了贝）
 * @author  heming
 * @date  2018年7月16日14:32:17
 */
@Service
public class ThirdPartyWalletService {

    public static final Logger log = LoggerFactory.getLogger(ThirdPartyWalletService.class);

    @Autowired
    public Environment env;

    /**
     * 获取 加密随机串
     * @return
     */
    public String getNoteStr(){
        String noteStr =  String.valueOf(System.currentTimeMillis());
        noteStr =StringUtils.leftPad(noteStr,16);
        return noteStr;
    }

    /**
     * 公钥加密随机串
     * @return
     */
    public String getEncodedNoteStr(){
        String encodedNoteStr = RSAUtils.encryptByPublicKey(getNoteStr(), env.getProperty("wallet.publicKey"));
        return encodedNoteStr;
    }
    //公钥加密随机串

    /**
     * 用户注册
     * @param mobile
     * @param pwd
     * @param moblieArea
     * @return
     */
    public String UserRegister (String mobile, String pwd, String moblieArea){
        String  loginPwd = AESUtils.encode(pwd, getNoteStr());
        String returnStr = "";
        //生成签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("mobile", mobile);
        packageParams.put("loginPwd", loginPwd);
        packageParams.put("mobileArea",  moblieArea);
        packageParams.put("noteStr", getEncodedNoteStr());
        String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);
        //拼接URL
        String url =  env.getProperty("wallet.url")+BaseConstant.WALLET_API_USERREGISTER;
        //设置参数
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("mobile", mobile);
        params.put("loginPwd", loginPwd);
        params.put("mobileArea", moblieArea);
        params.put("noteStr", getEncodedNoteStr());
        params.put("sign", sign);
        try {
            String result = HttpUtil.doPost(url, params);
            //返回值处理
            JSONObject jsonObject = JSONObject.parseObject(result);
            Map codeMap = (Map<String, String>) jsonObject.get("resultCode");
            Map messageMap = (Map<String, String>) jsonObject.get("message");
            log.debug(messageMap.get("message").toString());
            returnStr = codeMap.get("resultCode").toString();
        } catch (Exception e ) {
            log.error("钱包APP注册出错",e);
        }
        return returnStr;
    }


    /**
     * SLB资产装入
     * @param walletAddress 钱包地址
     * @param lockBeginDate 锁仓开始时间
     * @param lockEndDate 锁仓结束时间
     * @param releaseNum 释放批次
     * @param releasePercent 每次释放比例(单位%)
     * @param transfAmount 兑换数量
     * @param orderSn 订单ID,服务端验证订单是否已经同步过
     * @param batchType A ：A轮B ：B轮 C ：C轮 D ：D轮E ：E轮
     * @return  0:成功  小于0：操作不成功
     */
    public String transferToSlbSc (String walletAddress,String lockBeginDate,String lockEndDate,String releaseNum,
                                   String releasePercent,String transfAmount,String orderSn,String batchType){
        String returnStr = "";
        String endcodePaltTransPwd = AESUtils.encode(env.getProperty("wallet.platTransPwd"), getEncodedNoteStr());
        //生成签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("walletAddress", walletAddress);
        packageParams.put("platTransPwd", endcodePaltTransPwd);
        packageParams.put("lockBeginDate", lockBeginDate);
        packageParams.put("lockEndDate", lockEndDate);
        packageParams.put("releaseNum", releaseNum);
        packageParams.put("releasePercent", releasePercent);
        packageParams.put("transfAmount", transfAmount);
        packageParams.put("noteStr", getEncodedNoteStr());
        packageParams.put("orderSn", orderSn);
        packageParams.put("batchType", batchType);
        packageParams.put("noteStr", getEncodedNoteStr());
        String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);
        String url =  env.getProperty("wallet.url")+BaseConstant.WALLET_API_TRANSFERTOSLBSC;
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("walletAddress", walletAddress);
        params.put("platTransPwd", endcodePaltTransPwd);
        params.put("lockBeginDate", lockBeginDate);
        params.put("lockEndDate", lockEndDate);
        params.put("releaseNum", releaseNum);
        params.put("releasePercent", releasePercent);
        params.put("transfAmount", transfAmount);
        params.put("noteStr", getEncodedNoteStr());
        params.put("orderSn", orderSn);
        params.put("batchType", batchType);
        params.put("noteStr", getEncodedNoteStr());
        params.put("sign", sign);
        String result = HttpUtil.doPost(url, params);
        //返回值处理
        JSONObject jsonObject = JSONObject.parseObject(result);
        Map codeMap = (Map<String, String>) jsonObject.get("resultCode");
        Map messageMap = (Map<String, String>) jsonObject.get("message");
        log.debug(messageMap.get("message").toString());
        returnStr = codeMap.get("resultCode").toString();
        return returnStr;
    }

}
