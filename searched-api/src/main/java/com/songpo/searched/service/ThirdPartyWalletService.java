package com.songpo.searched.service;

;
import com.alibaba.fastjson.JSONArray;
import com.songpo.searched.constant.BaseConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.entity.SlPhoneZone;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlPhoneZoneMapper;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


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
    @Autowired
    private  LoginUserService loginUserService;
    @Autowired
    private SlPhoneZoneMapper slPhoneZoneMapper;

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
    public String getEncodedNoteStr(String noteStr){
        String encodedNoteStr = RSAUtils.encryptByPublicKey(noteStr, env.getProperty("wallet.publicKey"));
        return encodedNoteStr;
    }
    //公钥加密随机串


    /**
     * 用户注册
     * @param mobile
     * @param pwd
     * @param moblieArea
     * @return 0:成功  小于0：操作不成功
     */
    public String UserRegister (String mobile, String pwd, String moblieArea){
        //截取指定的电话号码
        mobile = subPhone(mobile);
        String returnStr = "";
        //公钥
        String publicKey = env.getProperty("wallet.publicKey");
        //生成加密随机串
        String noteStr =  getNoteStr();
        //加密登录密码
        String loginPwd = pwd;
        loginPwd = AESUtils.encode(loginPwd, noteStr);

        //公钥加密随机串
        String encodedNoteStr = RSAUtils.encryptByPublicKey(noteStr, publicKey);

        //生成签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("mobile", mobile);
        packageParams.put("loginPwd", loginPwd);
        packageParams.put("mobileArea", moblieArea);
        packageParams.put("noteStr", encodedNoteStr);
        String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);

        String url = env.getProperty("wallet.url") + BaseConstant.WALLET_API_USERREGISTER;
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("mobile", mobile);
        params.put("loginPwd", loginPwd);
        params.put("mobileArea", moblieArea);
        params.put("noteStr", encodedNoteStr);
        params.put("sign", sign);
        try {
            String result = HttpUtil.doPost(url, params);
            //返回值处理
            JSONObject jsonObject = JSONObject.parseObject(result);
            String  codeMap =  jsonObject.get("resultCode").toString();
            String  messageMap =  jsonObject.get("message").toString();
            log.debug(messageMap);
            returnStr = codeMap;
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
        log.debug("钱包 {} 开始转入{}slb ",walletAddress,transfAmount);
        String returnStr = "";
       String  noteStr =  getNoteStr();
        //公钥
        String publicKey = env.getProperty("wallet.publicKey");
        String encodedNoteStr = RSAUtils.encryptByPublicKey(noteStr, publicKey);
        String  platTransPwd = env.getProperty("wallet.platTransPwd");
        String endcodePaltTransPwd = AESUtils.encode(platTransPwd, noteStr);
        //生成签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("walletAddress", walletAddress);
        packageParams.put("platTransPwd", endcodePaltTransPwd);
        packageParams.put("lockBeginDate", lockBeginDate);
        packageParams.put("lockEndDate", lockEndDate);
        packageParams.put("releaseNum", releaseNum);
        packageParams.put("releasePercent", releasePercent);
        packageParams.put("transfAmount", transfAmount);
        packageParams.put("noteStr", encodedNoteStr);
        packageParams.put("orderSn", orderSn);
        packageParams.put("batchType", batchType);
        packageParams.put("noteStr", encodedNoteStr);
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
        params.put("noteStr", encodedNoteStr);
        params.put("orderSn", orderSn);
        params.put("batchType", batchType);
        params.put("noteStr", encodedNoteStr);
        params.put("sign", sign);
        String result = HttpUtil.doPost(url, params);
        //返回值处理
        JSONObject jsonObject = JSONObject.parseObject(result);
        String codeMap = jsonObject.get("resultCode").toString();
        String messageMap = jsonObject.get("message").toString();
        log.debug(messageMap);
        returnStr = codeMap;
        return returnStr;
    }
    /**
     * 查询用户是否注册
     * @param mobile 手机号
     */
    public  Boolean checkUserRegister(String mobile){

        //截取指定的电话号码
        mobile = subPhone(mobile);

        Boolean bool = false;
        //获取加密随机串
        String noteStr =  getNoteStr();
        //生成签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("mobile", mobile);
        packageParams.put("noteStr", noteStr);
        String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);
        //第三方钱包接口地址
        String url = env.getProperty("wallet.url")+BaseConstant.WALLET_API_CHECKUSERREGISTER;
        //
        // 接口所需参数
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("mobile", mobile);
        params.put("noteStr", noteStr);
        params.put("sign", sign);
        //获取返回值
        String result = HttpUtil.doPost(url, params);
        //解析返回值 转换成json格式
        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer code =  jsonObject.getInteger("resultCode");
        //操作成功
        if (code >= 0){
            Map map = (Map<String,String>)jsonObject.get("data");
            //用户已注册
            if (Integer.valueOf(map.get("exist").toString()) == 1){
                bool = true;
            }
        }
        return bool;
    }
    /**
     * 获取钱包地址列表
     * @param mobile
     * @return walletAddress
     */
    public String getWalletList(String mobile){

        //截取指定的电话号码
        mobile = subPhone(mobile);

        String walletAddress = "";
        //公钥
        String publicKey =env.getProperty("wallet.publicKey") ;
        //获取加密随机串
        String noteStr =  getNoteStr();
        //加密平台密码
        String platTransPwd = env.getProperty("wallet.platTransPwd");
        platTransPwd = AESUtils.encode(platTransPwd, noteStr);

        //公钥加密随机串
        String encodedNoteStr = RSAUtils.encryptByPublicKey(noteStr, publicKey);

        //生成签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("mobile", mobile);
        packageParams.put("platTransPwd", platTransPwd);
        packageParams.put("noteStr", encodedNoteStr);
        String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);

        String url = env.getProperty("wallet.url") + BaseConstant.WALLET_API_GETWALLETLIST;

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("mobile", mobile);
        params.put("platTransPwd", platTransPwd);
        params.put("noteStr", encodedNoteStr);
        params.put("sign", sign);
        String result = HttpUtil.doPost(url, params);
        //解析返回值 转换成json格式
        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer code =  jsonObject.getInteger("resultCode");
        //操作成功
        if (code >= 0){
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            if(jsonArray.size() > 0) {
                Map map = (Map<String,String>)jsonArray.get(0);
                //用户已注册
                walletAddress = map.get("walletAddress").toString();
            }
        }
        return  walletAddress;
    }
    /**
     *查询钱包总SLB锁仓余额
     * @return
     */
    @Transactional
    public BusinessMessage getSlbScAmount(){
        SlUser user = loginUserService.getCurrentLoginUser();
        BusinessMessage message = new BusinessMessage();
        try {
            if (null != user){
                String mobile = user.getPhone();
                if (checkUserRegister(mobile)){
                    message = retunObject(mobile);
                }else {
                    // 注册 String mobile, String pwd, String moblieArea
                    SlPhoneZone slPhoneZone = slPhoneZoneMapper.selectOne(new SlPhoneZone(){{
                        setZone(user.getZone());
                    }});
                   String res = UserRegister(mobile, BaseConstant.WALLET_DEFAULT_LOGIN_PASSWORD, slPhoneZone.getMobilearea().toString());
                    message = retunObject(mobile);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return  message;
    }
    public BusinessMessage retunObject(String mobile){

        //截取指定的电话号码
        mobile = subPhone(mobile);

        BusinessMessage businessMessage = new BusinessMessage();
        //公钥
        String publicKey = env.getProperty("wallet.publicKey");
        //生成加密随机串
        String noteStr =  getNoteStr();

        String platTransPwd = env.getProperty("wallet.platTransPwd");
        String endcodePaltTransPwd = AESUtils.encode(platTransPwd, noteStr);

        //公钥加密随机串
        String encodedNoteStr = RSAUtils.encryptByPublicKey(noteStr, publicKey);

        //生成签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("mobile", mobile);
        packageParams.put("noteStr", encodedNoteStr);
        packageParams.put("platTransPwd", endcodePaltTransPwd);

        String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);

        String url = env.getProperty("wallet.url") + BaseConstant.WALLET_API_GETSLBSCAMOUNT;

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("mobile", mobile);
        params.put("platTransPwd", endcodePaltTransPwd);
        params.put("noteStr", encodedNoteStr);
        params.put("sign", sign);
        String result = HttpUtil.doPost(url, params);
        //解析返回值 转换成json格式
       JSONObject jsonObject = JSONObject.parseObject(result);
       if (jsonObject.getInteger("resultCode") == 0){
            businessMessage.setSuccess(true);
            businessMessage.setCode(jsonObject.getString("resultCode"));
            businessMessage.setData(jsonObject.get("data"));
            businessMessage.setMsg(jsonObject.getString("message"));
       }else{
           businessMessage.setSuccess(false);
       }
       return  businessMessage;
    }

    //截取指定长度的手机号
    public String subPhone(String mobile){
        if (mobile.length() == 13){
            mobile = mobile.substring(mobile.length()-8, mobile.length());
        }
        return mobile;
    }
}
