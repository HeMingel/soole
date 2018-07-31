package com.songpo.searched.controller;


import com.songpo.searched.mapper.*;
import com.songpo.searched.service.ProcessOrders;
import com.songpo.searched.service.UserService;
import com.songpo.searched.util.*;
import io.swagger.annotations.Api;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Api(description = "测试")
@RestController
@CrossOrigin
@RequestMapping("/api/common/v1/test")
public class TestController {
    @Autowired
    private CmActivitySeckillMapper cmActivitySeckillMapper;
    @Autowired
    private SlActivitySeckillMapper slActivitySeckillMapper;
    @Autowired
    private SlProductMapper slProductMapper;
    @Autowired
    private SlSeckillRemindMapper slSeckillRemindMapper;
    @Autowired
    private ProcessOrders processOrders;
    @Autowired
    private UserService userService;
    @Autowired
    private CmSeckillRemindMapper cmSeckillRemindMapper;
    @Autowired
    private HttpRequest httpRequest;
    @Autowired
    private Environment env;
    @GetMapping("test1")
    public void Test1() throws ParseException {
            //手机号
            String mobile = "17611230000";
            //公钥
            String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCx4DP4sSde3yncPdJlPHLGNisl5PRpcvjjeet88Jd5vj1uMmAqPWSHwzn+k0TWXxQclL0h/GhWNQ49dWV1ooc+NlF91T9ChRNDr0VMvRwYYmx/5fT/BzWhFWD1g6WvgDKbCezE6DH+gckszVjNXmhZeeJVSTqT8uK0JZU7MYbYZwIDAQAB";
            //生成加密随机串
            String noteStr =  String.valueOf(System.currentTimeMillis());
            noteStr = StringUtils.leftPad(noteStr, 16,  "0");
            //加密登录密码
            String loginPwd = "111111";
            loginPwd = AESUtils.encode(loginPwd, noteStr);

            //公钥加密随机串
            String encodedNoteStr = RSAUtils.encryptByPublicKey(noteStr, publicKey);

            //生成签名
            SortedMap<String, String> packageParams = new TreeMap<String, String>();
            packageParams.put("mobile", mobile);
            packageParams.put("loginPwd", loginPwd);
            packageParams.put("mobileArea", "86");
            packageParams.put("noteStr", encodedNoteStr);
            String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);

            String url = "http://47.96.235.100:8080/digital-wallet-interface/wallet/externalWallet/userRegister.htm";
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("mobile", mobile);
            params.put("loginPwd", loginPwd);
            params.put("mobileArea", "86");
            params.put("noteStr", encodedNoteStr);
            params.put("sign", sign);
            String result = HttpUtil.doPost(url, params);
            System.out.println(result);
        }

    @GetMapping("test2")
    public void Test2() throws ParseException {
        //手机号
        String mobile = "17611230000";
        //公钥
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCx4DP4sSde3yncPdJlPHLGNisl5PRpcvjjeet88Jd5vj1uMmAqPWSHwzn+k0TWXxQclL0h/GhWNQ49dWV1ooc+NlF91T9ChRNDr0VMvRwYYmx/5fT/BzWhFWD1g6WvgDKbCezE6DH+gckszVjNXmhZeeJVSTqT8uK0JZU7MYbYZwIDAQAB";
        //生成加密随机串
        String noteStr =  String.valueOf(System.currentTimeMillis());
        noteStr = StringUtils.leftPad(noteStr, 16,  "0");
        //加密登录密码
        String loginPwd = "111111";
        loginPwd = AESUtils.encode(loginPwd, noteStr);

        //公钥加密随机串
        String encodedNoteStr = RSAUtils.encryptByPublicKey(noteStr, publicKey);

        //生成签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("mobile", mobile);
        packageParams.put("loginPwd", loginPwd);
        packageParams.put("noteStr", encodedNoteStr);
        String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);

        String url = "http://47.96.235.100:8080/digital-wallet-interface/wallet/externalWallet/getWalletList.htm";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("mobile", mobile);
        params.put("loginPwd", loginPwd);
        params.put("noteStr", encodedNoteStr);
        params.put("sign", sign);
        String result = HttpUtil.doPost(url, params);
        System.out.println("================================================");
        System.out.println(result);

    }

    @GetMapping("test3")
    public void Test3() throws ParseException {
        //钱包地址
        String mobile = "17611230000";
        //公钥
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCx4DP4sSde3yncPdJlPHLGNisl5PRpcvjjeet88Jd5vj1uMmAqPWSHwzn+k0TWXxQclL0h/GhWNQ49dWV1ooc+NlF91T9ChRNDr0VMvRwYYmx/5fT/BzWhFWD1g6WvgDKbCezE6DH+gckszVjNXmhZeeJVSTqT8uK0JZU7MYbYZwIDAQAB";
        //生成加密随机串
        String noteStr =  String.valueOf(System.currentTimeMillis());
        noteStr = StringUtils.leftPad(noteStr, 16,  "0");

        String platTransPwd = "1234567890";
        String endcodePaltTransPwd = AESUtils.encode(platTransPwd, noteStr);

        //公钥加密随机串
        String encodedNoteStr = RSAUtils.encryptByPublicKey(noteStr, publicKey);

        //生成签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("mobile", mobile);
        packageParams.put("noteStr", encodedNoteStr);
        packageParams.put("platTransPwd", endcodePaltTransPwd);

        String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);

        //String url = "http://127.0.0.1:8080/digital-wallet-interface/wallet/externalWallet/getSlbScAmount.htm";
        String url = "http://47.96.235.100:8080/digital-wallet-interface/wallet/externalWallet/getSlbScAmount.htm";

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("mobile", mobile);
        params.put("platTransPwd", endcodePaltTransPwd);
        params.put("noteStr", encodedNoteStr);
        params.put("sign", sign);
        String result = HttpUtil.doPost(url, params);
        System.out.println(result);
    }

    @GetMapping("test4")
    public void Test4() throws ParseException {
        //钱包地址
        //String walletAddress = "1C4RU377vWRcAFKuH88tiyTZqYrnjkFqQu";
        String walletAddress = "1Au5bQqw36wrD4YDKUDy1BQ491y8F6F5DR";
        //公钥
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCx4DP4sSde3yncPdJlPHLGNisl5PRpcvjjeet88Jd5vj1uMmAqPWSHwzn+k0TWXxQclL0h/GhWNQ49dWV1ooc+NlF91T9ChRNDr0VMvRwYYmx/5fT/BzWhFWD1g6WvgDKbCezE6DH+gckszVjNXmhZeeJVSTqT8uK0JZU7MYbYZwIDAQAB";
        //生成加密随机串
        String noteStr =  String.valueOf(System.currentTimeMillis());
        noteStr = StringUtils.leftPad(noteStr, 16,  "0");

        String platTransPwd = "1234567890";
        String endcodePaltTransPwd = AESUtils.encode(platTransPwd, noteStr);
        String lockBeginDate = "20180101";
        String lockEndDate = "20190101";
        String releaseNum = "10";
        String releasePercent = "1";
        String transfAmount = "7777777777777";
        String orderSn = "2018073000000362";
        String batchType = "A";

        //公钥加密随机串
        String encodedNoteStr = RSAUtils.encryptByPublicKey(noteStr, publicKey);

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

        //String url = "http://127.0.0.1:8080/digital-wallet-interface/wallet/externalWallet/transferToSlbSc.htm";
        String url = "http://47.96.235.100:8080/digital-wallet-interface/wallet/externalWallet/transferToSlbSc.htm";


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
        System.out.println(result);
    }

    @GetMapping("test5")
    public void Test5() throws ParseException {
        //手机号
        String mobile = "17611230000";
        //生成加密随机串
        String noteStr =  String.valueOf(System.currentTimeMillis());
        noteStr = StringUtils.leftPad(noteStr, 16,  "0");
        //生成签名
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("mobile", mobile);
        packageParams.put("noteStr", noteStr);
        String sign = MD5SignUtils.createMD5Sign(packageParams, MD5SignUtils.CHARSET_NAME_DEFAULT);

        //String url = "http://127.0.0.1:8080/digital-wallet-interface/wallet/externalWallet/checkUserRegister.htm";
        String url = "http://47.96.235.100:8080/digital-wallet-interface/wallet/externalWallet/checkUserRegister.htm";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("mobile", mobile);
        params.put("noteStr", noteStr);
        params.put("sign", sign);
        String result = HttpUtil.doPost(url, params);
        System.out.println(result);  JSONObject jsonObject = JSONObject.parseObject(result);
        System.out.println("json:============"+jsonObject.toJSONString());
        Map map = (Map<String,String>)jsonObject.get("data");
        System.out.println(map.get("exist"));


    }

}
