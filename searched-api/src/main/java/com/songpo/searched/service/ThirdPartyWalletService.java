package com.songpo.searched.service;

import com.songpo.searched.constant.BaseConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.*;
import com.songpo.searched.util.*;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    @Autowired
    private SlUserMapper slUserMapper;
    @Autowired
    private SlSystemConnectorMapper slSystemConnectorMapper;
    @Autowired
    private SlTransactionDetailMapper slTransactionDetailMapper;
    @Autowired
    private SmsService smsService;
    @Autowired
    private CmUserSlbMapper cmUserSlbMapper;
    @Autowired
    private SlSlbTransactionMapper slSlbTransactionMapper;
    @Autowired
    private SlUserSlbMapper slUserSlbMapper;
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
        //查询用户
        String finalMobile = mobile;
        SlUser user = slUserMapper.selectOne(new SlUser(){{
            setPhone(finalMobile);
        }});
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
            returnStr =  jsonObject.get("resultCode").toString();
            String  messageMap =  jsonObject.get("message").toString();
            log.debug(messageMap);
            //注册成功 发送密码短信
            if ("0".equals( returnStr)){
                SlPhoneZone slPhoneZone = slPhoneZoneMapper.selectOne(new SlPhoneZone(){{
                    setMobilearea(Integer.parseInt(moblieArea));
                }});
               if (!SLStringUtils.isEmpty(moblieArea)){
                   smsService.sendMess(mobile,slPhoneZone.getZone(),pwd);
               }
            }
        } catch (Exception e ) {
            log.error("钱包APP注册出错",e);
        }
        finally {
            return returnStr;
        }
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
        try {
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
        }catch (Exception e){
            e.printStackTrace();
        }

        return returnStr;
    }
    /**
     * 查询用户是否注册
     * @param mobile 手机号
     */
    public  Boolean checkUserRegister(String mobile, String mobileArea){

        //截取指定的电话号码
        mobile = subPhone(mobile);

        Boolean bool = false;
        //获取加密随机串
        String noteStr =  getNoteStr();
        //生成签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("mobile", mobile);
        packageParams.put("mobileArea", mobileArea);
        packageParams.put("noteStr", noteStr);
        String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);
        //第三方钱包接口地址
        String url = env.getProperty("wallet.url")+BaseConstant.WALLET_API_CHECKUSERREGISTER;
        //
        // 接口所需参数
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("mobile", mobile);
        params.put("mobileArea", mobileArea);
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
    public String getWalletList(String mobile, String mobileArea){

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
        packageParams.put("mobileArea", mobileArea);
        packageParams.put("platTransPwd", platTransPwd);
        packageParams.put("noteStr", encodedNoteStr);
        String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);

        String url = env.getProperty("wallet.url") + BaseConstant.WALLET_API_GETWALLETLIST;

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("mobile", mobile);
        params.put("mobileArea", mobileArea);
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
                walletAddress = map.get("walletAddress").toString();
        }
        return  walletAddress;
    }
    /**
     *查询钱包总SLB锁仓余额
     * @return
     */
    @Transactional
    public BusinessMessage getSlbScAmount(String phone){
        SlUser user = slUserMapper.selectOne(new SlUser(){{
            setPhone(phone);
        }});
        BusinessMessage message = new BusinessMessage();
        try {
            if (null != user){
                String mobile = user.getPhone();
                SlPhoneZone slPhoneZone = slPhoneZoneMapper.selectOne(new SlPhoneZone(){{
                    setZone(user.getZone());
                }});
                if (checkUserRegister(mobile, slPhoneZone.getMobilearea().toString())){
                    message = retunObject(mobile, slPhoneZone.getMobilearea().toString());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return  message;
    }
    public BusinessMessage retunObject(String mobile, String mobileArea){

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
        packageParams.put("mobileArea", mobileArea);
        packageParams.put("noteStr", encodedNoteStr);
        packageParams.put("platTransPwd", endcodePaltTransPwd);

        String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);

        String url = env.getProperty("wallet.url") + BaseConstant.WALLET_API_GETSLBSCAMOUNT;

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("mobile", mobile);
        params.put("mobileArea", mobileArea);
        params.put("platTransPwd", endcodePaltTransPwd);
        params.put("noteStr", encodedNoteStr);
        params.put("sign", sign);
        String result = HttpUtil.doPost(url, params);
        System.out.println("getSlbScAmountgetSlbScAmount="+result);
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
    /**
     * 使用SLB币支付
     * @param walletAddress 钱包地址
     * @param walletPwd  登录密码
     * @param payAmount 支付金额
     * @param orderSn   订单号
     */
    public Integer paySlbAmount(String walletAddress, String walletPwd, BigDecimal payAmount, String orderSn){
        //公钥
        String publicKey = env.getProperty("wallet.publicKey");
        //生成加密随机串
        String noteStr =  String.valueOf(System.currentTimeMillis());
        noteStr = StringUtils.leftPad(noteStr, 16,  "0");

        walletPwd = AESUtils.encode(walletPwd, noteStr);

        //公钥加密随机串
        String encodedNoteStr = RSAUtils.encryptByPublicKey(noteStr, publicKey);

        //生成签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("walletAddress", walletAddress);
        packageParams.put("walletPwd", walletPwd);
        packageParams.put("payAmount", payAmount.toString());
        packageParams.put("noteStr", encodedNoteStr);
        packageParams.put("orderSn", orderSn);

        String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);

        String url = env.getProperty("wallet.url") + BaseConstant.WALLET_API_PAYSLBAMOUNT;

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("walletAddress", walletAddress);
        params.put("walletPwd", walletPwd);
        params.put("payAmount", payAmount.toString());
        params.put("noteStr", encodedNoteStr);
        params.put("orderSn", orderSn);

        params.put("sign", sign);
        String result = HttpUtil.doPost(url, params);
        //解析返回值 转换成json格式
        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer code =  jsonObject.getInteger("resultCode");
        return code;
    }
    /**
     * 从金豆提取到SLB币
     * @param userId         用户ID
     * @param platTransPwd   平台密码
     * @param transfAmount  兑换slb数量
     */
    public BusinessMessage transferToSLB(String userId,String platTransPwd, String transfAmount ){
        log.debug("userId="+userId+",transfAmount="+transfAmount);
        BusinessMessage message = new BusinessMessage();
        //获取用户信息
        SlUser slUser = slUserMapper.selectByPrimaryKey(userId);
        //获取slb汇率
        SlSystemConnector slSystemConnector = slSystemConnectorMapper.selectOne(new SlSystemConnector(){{
            setVar("bean");
        }});
        //兑换的slb数量转换为对应的金币
        Integer coin = Integer.valueOf(transfAmount) * Integer.valueOf(slSystemConnector.getAppId());
        //钱包地址
        String walletAddress = "";
        try {
            if (null != slUser){
                //获取手机区号
                SlPhoneZone slPhoneZone = slPhoneZoneMapper.selectOne(new SlPhoneZone(){{
                    setZone(slUser.getZone()==""?"中国大陆":slUser.getZone());
                }});
                //查询用户是否注册钱包
                if (checkUserRegister(slUser.getPhone(), slPhoneZone.getMobilearea().toString())){
                    //获取钱包地址
                    walletAddress = getWalletList(slUser.getPhone(),slPhoneZone.getMobilearea().toString());
                }else {
                    //生成钱包登录密码
                    String loginPwd = SLStringUtils.getStringRandom(6);
                    //用户钱包注册
                    String res = UserRegister(slUser.getPhone(), loginPwd, slPhoneZone.getMobilearea().toString());
                    if ("0".equals(res)){
                        //获取钱包地址
                        walletAddress = getWalletList(slUser.getPhone(),slPhoneZone.getMobilearea().toString());
                    }else {
                        message.setSuccess(false);
                        message.setMsg("请先注册搜了钱包APP");
                        return message;
                    }
                }
                if (slUser.getCoin()>coin){
                    //公钥
                    String publicKey = env.getProperty("wallet.publicKey");
                    //生成加密随机串
                    String noteStr =  String.valueOf(System.currentTimeMillis());
                    noteStr = StringUtils.leftPad(noteStr, 16,  "0");

                    platTransPwd = AESUtils.encode(platTransPwd, noteStr);

                    //公钥加密随机串
                    String encodedNoteStr = RSAUtils.encryptByPublicKey(noteStr, publicKey);
                    String orderSn = String.valueOf(System.currentTimeMillis());

                    //生成签名
                    SortedMap<String, String> packageParams = new TreeMap<String, String>();
                    packageParams.put("walletAddress", walletAddress);
                    packageParams.put("platTransPwd", platTransPwd);
                    packageParams.put("transfAmount", transfAmount);
                    packageParams.put("noteStr", encodedNoteStr);
                    packageParams.put("orderSn", orderSn);

                    String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);

                    String url = env.getProperty("wallet.url") + BaseConstant.WALLET_API_TRANSFERTOSLB;
                    Map<String,Object> params = new HashMap<String,Object>();
                    params.put("walletAddress", walletAddress);
                    params.put("platTransPwd", platTransPwd);
                    params.put("transfAmount", transfAmount.toString());
                    params.put("noteStr", encodedNoteStr);
                    params.put("orderSn", orderSn);

                    params.put("sign", sign);
                    String result = HttpUtil.doPost(url, params);
                    //解析返回值 转换成json格式
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    Integer code =  jsonObject.getInteger("resultCode");
                    if (0 == code){
                        slUser.setCoin(slUser.getCoin()-coin);
                        //扣除用户金币
                        slUserMapper.updateByPrimaryKey(slUser);
                        //保存交易明细
                        slTransactionDetailMapper.insertSelective(new SlTransactionDetail(){{
                            setTargetId(slUser.getId());
                            setType(105);
                            setCoin(coin);
                            setDealType(5);
                            setCreateTime(new Date());
                        }});
                        message.setMsg("slb兑换成功");
                        message.setSuccess(true);
                    }else {
                        message.setMsg(jsonObject.getString("message"));
                        message.setSuccess(false);
                        return message;
                    }
                }else {
                    message.setMsg("金币不足");
                    message.setSuccess(false);
                    return message;
                }
            }else {
                message.setMsg("用户不存在");
                message.setSuccess(false);
                return message;
            }
        }catch (Exception e){
            log.error("兑换异常",e);
            message.setSuccess(false);
            message.setMsg("兑换异常");
        }

        return message;
    }
    /**
     * 查找搜了贝汇率
     */
    public BusinessMessage selectSlbRate(){
        log.debug("查询搜了贝汇率开始");
        BusinessMessage message = new BusinessMessage();
        try {
            SlSystemConnector slSystemConnector = slSystemConnectorMapper.selectOne(new SlSystemConnector(){{
                setVar("bean");
            }});
            message.setMsg("查询成功");
            message.setData(slSystemConnector);
            message.setSuccess(true);
        }catch (Exception e){
            log.error("查询失败",e);
            message.setSuccess(false);
            message.setMsg("查询失败");
        }
        return message;
    }

    /**
     * 查询某个钱包SLB余额
     * @param phone 手机号
     * @return
     */
    public BusinessMessage getSlbAcount(String phone){
        SlUser user = slUserMapper.selectOne(new SlUser(){{
            setPhone(phone);
        }});
        BusinessMessage message = new BusinessMessage();
        if (null != user){
            String mobile = user.getPhone();
            SlPhoneZone slPhoneZone = slPhoneZoneMapper.selectOne(new SlPhoneZone(){{
                setZone(user.getZone());
            }});
            if (checkUserRegister(mobile, slPhoneZone.getMobilearea().toString())){
                //钱包地址
                String walletAddress = getWalletList(phone,slPhoneZone.getMobilearea()+"");
                //公钥
                String publicKey = env.getProperty("wallet.publicKey");
                //生成加密随机串
                String noteStr =  getNoteStr();
                noteStr = StringUtils.leftPad(noteStr, 16,  "0");

                //公钥加密随机串
                String encodedNoteStr = RSAUtils.encryptByPublicKey(noteStr, publicKey);
                String platTransPwd = env.getProperty("wallet.platTransPwd");
                String endcodePaltTransPwd = AESUtils.encode(platTransPwd, noteStr);

                //生成签名
                SortedMap<String, String> packageParams = new TreeMap<String, String>();
                packageParams.put("walletAddress", walletAddress);
                packageParams.put("noteStr", encodedNoteStr);
                packageParams.put("platTransPwd", endcodePaltTransPwd);


                String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);
                String url = env.getProperty("wallet.url") + BaseConstant.WALLET_API_GETSLBAMOUNT;
                Map<String,Object> params = new HashMap<String,Object>();
                params.put("walletAddress", walletAddress);
                params.put("noteStr", encodedNoteStr);
                params.put("platTransPwd", endcodePaltTransPwd);

                params.put("sign", sign);
                String result = HttpUtil.doPost(url, params);
                System.out.println("getSlbAcount=="+result);
                //解析返回值 转换成json格式
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("resultCode") == 0){
                    message.setSuccess(true);
                    message.setCode(jsonObject.getString("resultCode"));
                    message.setData(jsonObject.get("data"));
                    message.setMsg(jsonObject.getString("message"));
                }else{
                    message.setSuccess(false);
                }
            }
        }
        return message;
    }
    /**
     * 查询搜了贝(最新)
     * @param phone
     */
    public BusinessMessage selectUserSlbNew(String phone){
        BusinessMessage message = new BusinessMessage();
        Map mapScAmount = (Map<String,String>)getSlbScAmount(phone).getData();
        Map mapAmount = (Map<String,String>)getSlbAcount(phone).getData();
        try {
            if (null != mapScAmount && mapScAmount.size()>0){
                if (null != mapAmount && mapAmount.size()>0){
                    mapScAmount.put("amount",Double.parseDouble(mapScAmount.get("allScAmount").toString())+(Double.parseDouble(mapAmount.get("amount")+""))+"");
                    mapScAmount.put("allReleaseAmount",Double.parseDouble(mapAmount.get("amount").toString())+(Double.parseDouble(mapAmount.get("allReleaseAmount")==null?"0.00":mapAmount.get("allReleaseAmount")+""))+"");
                    mapScAmount.put("allScAmount",Double.parseDouble(mapScAmount.get("allScAmount")==null?"0.00":mapScAmount.get("allScAmount").toString())+"");
                    mapScAmount.put("aAmount",Double.parseDouble(mapScAmount.get("aAmount")==null?"0.00":mapScAmount.get("aAmount").toString())+"");
                    mapScAmount.put("eScAmount",Double.parseDouble(mapScAmount.get("eScAmount")==null?"0.00":mapScAmount.get("eScAmount").toString())+"");
                    mapScAmount.put("aReleaseAmount",Double.parseDouble(mapScAmount.get("aReleaseAmount")==null?"0.00":mapScAmount.get("aReleaseAmount").toString())+"");
                    mapScAmount.put("dScAmount",Double.parseDouble(mapScAmount.get("dScAmount")==null?"0.00":mapScAmount.get("dScAmount").toString())+"");
                    mapScAmount.put("eAmount",Double.parseDouble(mapScAmount.get("eAmount")==null?"0.00":mapScAmount.get("eAmount").toString())+"");
                    mapScAmount.put("cScAmount",Double.parseDouble(mapScAmount.get("cScAmount")==null?"0.00":mapScAmount.get("cScAmount").toString())+"");
                    mapScAmount.put("dAmount",Double.parseDouble(mapScAmount.get("dAmount")==null?"0.00":mapScAmount.get("dAmount").toString())+"");
                    mapScAmount.put("cAmount",Double.parseDouble(mapScAmount.get("cAmount")==null?"0.00":mapScAmount.get("cAmount").toString())+"");
                    mapScAmount.put("allAmount",Double.parseDouble(mapScAmount.get("allAmount")==null?"0.00":mapScAmount.get("allAmount").toString())+"");
                    mapScAmount.put("bReleaseBmount",Double.parseDouble(mapScAmount.get("bReleaseBmount")==null?"0.00":mapScAmount.get("bReleaseBmount").toString())+"");
                    mapScAmount.put("bScAmount",Double.parseDouble(mapScAmount.get("bScAmount")==null?"0.00":mapScAmount.get("bScAmount").toString())+"");
                    mapScAmount.put("bAmount",Double.parseDouble(mapScAmount.get("bAmount")==null?"0.00":mapScAmount.get("bAmount").toString())+"");
                    mapScAmount.put("eReleaseBmount",Double.parseDouble(mapScAmount.get("eReleaseBmount")==null?"0.00":mapScAmount.get("eReleaseBmount").toString())+"");
                    mapScAmount.put("cReleaseBmount",Double.parseDouble(mapScAmount.get("cReleaseBmount")==null?"0.00":mapScAmount.get("cReleaseBmount").toString())+"");
                    mapScAmount.put("aScAmount",Double.parseDouble(mapScAmount.get("aScAmount")==null?"0.00":mapScAmount.get("aScAmount").toString())+"");
                    mapScAmount.put("dReleaseBmount",Double.parseDouble(mapScAmount.get("dReleaseBmount")==null?"0.00":mapScAmount.get("dReleaseBmount").toString())+"");
                    message.setData(mapScAmount);
                }else {
                    mapScAmount.put("amount",mapScAmount.get("allScAmount")+"");
                    mapScAmount.put("allScAmount",Double.parseDouble(mapScAmount.get("allScAmount")==null?"0.00":mapScAmount.get("allScAmount").toString())+"");
                    mapScAmount.put("aAmount",Double.parseDouble(mapScAmount.get("aAmount")==null?"0.00":mapScAmount.get("aAmount").toString())+"");
                    mapScAmount.put("eScAmount",Double.parseDouble(mapScAmount.get("eScAmount")==null?"0.00":mapScAmount.get("eScAmount").toString())+"");
                    mapScAmount.put("aReleaseAmount",Double.parseDouble(mapScAmount.get("aReleaseAmount")==null?"0.00":mapScAmount.get("aReleaseAmount").toString())+"");
                    mapScAmount.put("dScAmount",Double.parseDouble(mapScAmount.get("dScAmount")==null?"0.00":mapScAmount.get("dScAmount").toString())+"");
                    mapScAmount.put("eAmount",Double.parseDouble(mapScAmount.get("eAmount")==null?"0.00":mapScAmount.get("eAmount").toString())+"");
                    mapScAmount.put("cScAmount",Double.parseDouble(mapScAmount.get("cScAmount")==null?"0.00":mapScAmount.get("cScAmount").toString())+"");
                    mapScAmount.put("dAmount",Double.parseDouble(mapScAmount.get("dAmount")==null?"0.00":mapScAmount.get("dAmount").toString())+"");
                    mapScAmount.put("cAmount",Double.parseDouble(mapScAmount.get("cAmount")==null?"0.00":mapScAmount.get("cAmount").toString())+"");
                    mapScAmount.put("allAmount",Double.parseDouble(mapScAmount.get("allAmount")==null?"0.00":mapScAmount.get("allAmount").toString())+"");
                    mapScAmount.put("bReleaseBmount",Double.parseDouble(mapScAmount.get("bReleaseBmount")==null?"0.00":mapScAmount.get("bReleaseBmount").toString())+"");
                    mapScAmount.put("bScAmount",Double.parseDouble(mapScAmount.get("bScAmount")==null?"0.00":mapScAmount.get("bScAmount").toString())+"");
                    mapScAmount.put("bAmount",Double.parseDouble(mapScAmount.get("bAmount")==null?"0.00":mapScAmount.get("bAmount").toString())+"");
                    mapScAmount.put("eReleaseBmount",Double.parseDouble(mapScAmount.get("eReleaseBmount")==null?"0.00":mapScAmount.get("eReleaseBmount").toString())+"");
                    mapScAmount.put("cReleaseBmount",Double.parseDouble(mapScAmount.get("cReleaseBmount")==null?"0.00":mapScAmount.get("cReleaseBmount").toString())+"");
                    mapScAmount.put("aScAmount",Double.parseDouble(mapScAmount.get("aScAmount")==null?"0.00":mapScAmount.get("aScAmount").toString())+"");
                    mapScAmount.put("dReleaseBmount",Double.parseDouble(mapScAmount.get("dReleaseBmount")==null?"0.00":mapScAmount.get("dReleaseBmount").toString())+"");
                    mapScAmount.put("allReleaseAmount",Double.parseDouble(mapScAmount.get("allReleaseAmount")==null?"0.00":mapScAmount.get("allReleaseAmount").toString())+"");
                    message.setData(mapScAmount);
                }
            }else {
                mapAmount = new HashMap();
                if (null != mapAmount && mapAmount.size()>0){
                    mapAmount.put("amount",mapAmount.get("amount")+"");
                    mapAmount.put("allScAmount","0.00");
                    mapAmount.put("aAmount","0.00");
                    mapAmount.put("eScAmount","0.00");
                    mapAmount.put("aReleaseAmount","0.00");
                    mapAmount.put("dScAmount","0.00");
                    mapAmount.put("eAmount","0.00");
                    mapAmount.put("allReleaseAmount",mapAmount.get("amount")+"");
                    mapAmount.put("cScAmount","0.00");
                    mapAmount.put("dAmount","0.00");
                    mapAmount.put("cAmount","0.00");
                    mapAmount.put("allAmount","0.00");
                    mapAmount.put("bReleaseBmount","0.00");
                    mapAmount.put("bScAmount","0.00");
                    mapAmount.put("bAmount","0.00");
                    mapAmount.put("eReleaseBmount","0.00");
                    mapAmount.put("cReleaseBmount","0.00");
                    mapAmount.put("aScAmount","0.00");
                    mapAmount.put("dReleaseBmount","0.00");
                    message.setData(mapAmount);
                }else {
                    mapAmount.put("amount","0.00");
                    mapAmount.put("allScAmount","0.00");
                    mapAmount.put("aAmount","0.00");
                    mapAmount.put("eScAmount","0.00");
                    mapAmount.put("aReleaseAmount","0.00");
                    mapAmount.put("dScAmount","0.00");
                    mapAmount.put("eAmount","0.00");
                    mapAmount.put("allReleaseAmount","0.00");
                    mapAmount.put("cScAmount","0.00");
                    mapAmount.put("dAmount","0.00");
                    mapAmount.put("cAmount","0.00");
                    mapAmount.put("allAmount","0.00");
                    mapAmount.put("bReleaseBmount","0.00");
                    mapAmount.put("bScAmount","0.00");
                    mapAmount.put("bAmount","0.00");
                    mapAmount.put("eReleaseBmount","0.00");
                    mapAmount.put("cReleaseBmount","0.00");
                    mapAmount.put("aScAmount","0.00");
                    mapAmount.put("dReleaseBmount","0.00");
                    message.setData(mapAmount);
                }
            }
            message.setMsg("查询成功");
            message.setSuccess(true);
        }catch (Exception e){
            log.error("查询搜了贝(最新)异常",e);
            message.setSuccess(false);
            message.setMsg("查询搜了贝异常");
        }

        return message;
    }

    /**
     * 处理脏数据
     */
    public void urgencySlbTransaction(){
        //查询重复订单
        List<String> orderList = cmUserSlbMapper.listRepeat();
        if (orderList.size() > 0) {
            for (String order : orderList) {
                //查询订单号的重复记录
                List<Map<String, Object>> slbTransation = cmUserSlbMapper.listWrongMsg(order, "2018-08-20 17:50:00");
                for (Map map : slbTransation) {
                    BigDecimal wrongSlb = new BigDecimal(0);
                    String userId = map.get("target_id").toString();
                    String type = map.get("slb_type").toString();
                    SlUserSlb slUserSlb = slUserSlbMapper.selectOne(new SlUserSlb() {{
                        setUserId(userId);
                        setSlbType(Integer.parseInt(type));
                    }});
                    String slb = SLStringUtils.isEmpty(map.get("slb").toString()) ? "0" : map.get("slb").toString();
                    wrongSlb = new BigDecimal(slb).add(wrongSlb);
                    //更新slb用户表信息
                    slUserSlb.setSlb(slUserSlb.getSlb().subtract(wrongSlb));
                    slUserSlbMapper.updateByPrimaryKeySelective(slUserSlb);
                    //删除重复数据
                    slSlbTransactionMapper.deleteByPrimaryKey(map.get("id").toString());
                }
            }
        }
    }
}
