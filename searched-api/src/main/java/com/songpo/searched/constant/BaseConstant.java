package com.songpo.searched.constant;

/**
 * 系统常量类
 * @date  2018年6月19日11:56:54
 * @author mingel
 */
public class BaseConstant {
    /**
     * 默认自动确认收货天数
     */
    public static final int DEFAULT_RECEIVING_DATE = 7;

    /**
     * 延迟收货天数
     */
    public static final int DELAY_DATE = 7;

    /**
     * 注册赠送的豆数
     */
    public static final int REGISTER_PEAS = 16;

    /**
     * 资金池主键ID 8C0E7538-8796-E590-20D7-B98EB3AE61B2
     */
    public static final String  TOTAL_POOL_ID = "8C0E7538-8796-E590-20D7-B98EB3AE61B2";

    /**
     * A轮商品主键ID DCD6EA3A-8440-17FC-5CDC-136B272FBE13
     */
    public static final String  PRODUCT_ID_A = "DCD6EA3A-8440-17FC-5CDC-136B272FBE13";
    /**
     * A轮活动主键ID DD5BD623-373D-D163-B293-F757E6FBAD55
     */
    public static final String  ACTIVITY_ID_A = "DD5BD623-373D-D163-B293-F757E6FBAD55";
    /**
     * A轮规格主键ID C731B072-CE81-2D7A-C701-457B2160F17D
     */
    public static final String  REPOSITORY_ID_A = "C731B072-CE81-2D7A-C701-457B2160F17D";
    /**
     * B轮商品主键ID 4CA18693-F2DB-C512-A611-8F219E6B3D0B
     */
    public static final String  PRODUCT_ID_B = "4CA18693-F2DB-C512-A611-8F219E6B3D0B";
    /**
     * C轮商品主键ID 633B4EA8-50D5-8524-A1FE-E0251F331DBF
     */
    public static final String  PRODUCT_ID_C = "633B4EA8-50D5-8524-A1FE-E0251F331DBF";
    /**
     * D轮商品主键ID 6DCBA2D0-D482-CF00-CB19-32B1F7BCB81F
     */
    public static final String  PRODUCT_ID_D = "6DCBA2D0-D482-CF00-CB19-32B1F7BCB81F";
    /**
     * E轮商品主键ID 2286043F-1DC0-229D-6B01-6061DB345CEC
     */
    public static final String  PRODUCT_ID_E = "2286043F-1DC0-229D-6B01-6061DB345CEC";

    /**
     * 钱包平台查询用户是否注册接口
     */
    public static final String WALLET_API_CHECKUSERREGISTER = "wallet/externalWallet/checkUserRegister.htm";

    /**
     * 钱包平台用户注册接口
     */
    public static final String WALLET_API_USERREGISTER = "wallet/externalWallet/userRegister.htm";

    /**
     * 获得钱包列表
     */
    public static final String WALLET_API_GETWALLETLIST = "wallet/externalWallet/getWalletList.htm";

    /**
     * 查询钱包总SLB锁仓余额
     */
    public static final String WALLET_API_GETSLBSCAMOUNT = "wallet/externalWallet/getSlbScAmount.htm";

    /**
     * 查询钱包总SLB+SLB锁仓余额
     */
    public static final String WALLET_API_GETSLBANDSLBSCAMOUNT = "wallet/externalWallet/getSlbAndSlbScAmount.htm";

    /**
     * SLB锁仓资产转入
     */
    public static final String WALLET_API_TRANSFERTOSLBSC = "wallet/externalWallet/transferToSlbSc.htm";
    /**
     * 使用SLB币支付
     */
    public static final String WALLET_API_PAYSLBAMOUNT = "wallet/externalWallet/paySlbAmount.htm";
    /**
     * 从金豆提取到SLB币
     */
    public static final String WALLET_API_TRANSFERTOSLB = "wallet/externalWallet/transferToSLB.htm";
    /**
     * 查询某个钱包SLB余额
     */
    public static final String WALLET_API_GETSLBAMOUNT = "wallet/externalWallet/getSLBAmount.htm";


}
