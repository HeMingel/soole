package com.songpo.searched.service;


import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.util.AESUtils;


import com.songpo.searched.util.HttpUtil;
import com.songpo.searched.util.MD5SignUtils;
import com.songpo.searched.util.RSAUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.songpo.searched.constant.BaseConstant.WALLET_API_CHECKUSERREGISTER;
import static com.songpo.searched.constant.BaseConstant.WALLET_API_GETSLBSCAMOUNT;
import static com.songpo.searched.constant.BaseConstant.WALLET_API_GETWALLETLIST;


/**
 * 第三方钱包接口 （目前主要针对搜了贝）
 * @author  heming
 * @date  2018年7月16日14:32:17
 */
@Service
public class ThirdPartyWalletService {


    @Autowired
    public Environment env;
    @Autowired
    private  LoginUserService loginUserService;

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
     * @return
     */
    public String  UserRegister (String mobile,String pwd){
        String  loginPwd = AESUtils.encode(pwd, getNoteStr());
        return null;
    }

    /**
     * 查询用户是否注册
     * @param mobile 手机号
     */
    public  Boolean checkUserRegister(String mobile){
        Boolean bool = false;
        //获取加密随机串
        String noteStr =  getNoteStr();
        //生成签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("mobile", mobile);
        packageParams.put("noteStr", noteStr);
        String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);
        //第三方钱包接口地址
        String url = env.getProperty("wallet.url")+WALLET_API_CHECKUSERREGISTER;
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

        String url = env.getProperty("wallet.url") + WALLET_API_GETWALLETLIST;

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
            Map map = (Map<String,String>)jsonObject.get("data");
            //用户已注册
            walletAddress = map.get("walletAddress").toString();
        }
        return  walletAddress;
    }
    /**
     *查询钱包总SLB锁仓余额
     * @return
     */
    public BusinessMessage getSlbScAmount(){
        SlUser user = loginUserService.getCurrentLoginUser();
        BusinessMessage message = new BusinessMessage();
        if (null != user){
            String mobile = user.getPhone();
            System.out.println("@@@@@@@@@@@@"+mobile);
            if (checkUserRegister(mobile)){
                System.out.println("#################我已经注册");
                message = retunObject(mobile);
            }else {
                //todo 注册
                //注册完 返回搜了贝
                System.out.println("#################我没有注册");
                message = retunObject(mobile);
            }
        }


        return  message;
    }
    public BusinessMessage retunObject(String mobile){
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

        String url = env.getProperty("wallet.url") + WALLET_API_GETSLBSCAMOUNT;

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
}
