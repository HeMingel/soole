package com.songpo.searched.service;


import com.songpo.searched.util.AESUtils;



import com.songpo.searched.util.RSAUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;




/**
 * 第三方钱包接口 （目前主要针对搜了贝）
 * @author  heming
 * @date  2018年7月16日14:32:17
 */
@Service
public class ThirdPartyWalletService {


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
     * @return
     */
    public String  UserRegister (String mobile,String pwd){
        String  loginPwd = AESUtils.encode(pwd, getNoteStr());
        return null;
    }


}
