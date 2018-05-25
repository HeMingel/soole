package com.songpo.searched.service;

import com.songpo.searched.alipay.service.AliPayService;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlTransactionDetail;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.typehandler.*;
import com.songpo.searched.util.ClientIPUtil;
import com.songpo.searched.wxpay.service.WxPayService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 账户管理
 */
@Service
public class AccountService {

    public static final Logger log = LoggerFactory.getLogger(AfterSalesService.class);
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private TransactionDetailService transactionDetailService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private AliPayService aliPayService;

    /**
     * 余额充值
     * 1、增加交易流水（未生效）
     * 2、调用第三方支付
     *
     * @param req
     * @param money   充值金额，单位：元
     * @param payType 支付方式
     * @return
     */
    @Transactional
    public BusinessMessage<Map> recharge(HttpServletRequest req, BigDecimal money, int payType) {
        BusinessMessage<Map> message = new BusinessMessage<>();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (user == null || StringUtils.isBlank(user.getId())) {
            message.setMsg("获取当前登录用户信息失败");
            return message;
        }
        PayTypeEnum payTypeEnum = PayTypeEnum.getInstance(payType);
        if (payTypeEnum == null) {
            message.setMsg("请选择支付方式");
            return message;
        }
        /********* 增加交易流水 **********/
        SlTransactionDetail transactionDetail = new SlTransactionDetail();
        transactionDetail.setTargetId(user.getId());
        transactionDetail.setType(ExpenditureTypeEnum.DEPOSIT_RECHARGE.getValue());
        transactionDetail.setMoney(money);
        transactionDetail.setDealType(TransactionCurrencyTypeEnum.MONEY.getValue());
        transactionDetail.setTransactionType(TransactionTypeEnum.INCOME.getValue());
        transactionDetail.setTransactionStatus(TransactionStatusEnum.INEFFECTIVE.getValue().byteValue());
        transactionDetailService.insertSelective(transactionDetail);

        /********** 支付 ***********/
        Map<String, String> map = new HashMap<>();
        switch (payTypeEnum) {
            case WX_APP_PAY:
                double mo = money.multiply(new BigDecimal(100)).doubleValue();
                String total_fee = String.valueOf(Math.round(mo));
                map = wxPayService.unifiedOrderByApp(null, "余额充值 - " + transactionDetail.getId(), null, null, transactionDetail.getId(), "", total_fee, ClientIPUtil.getClientIP(req), "", "", "", "", "", "");
                break;
            case WX_H5_PAY:
                break;
            case ALI_APP_PAY:
                String str = this.aliPayService.appPay("15d", money.toString(), "", "", null, "搜了购物支付 - " + transactionDetail.getId(), transactionDetail.getId(), "", "", "", "", null, null, null, "", "", null, null, null, null, null, "");
                if (StringUtils.isNotBlank(str)) {
                    map.put("alipay", str);
                }
                break;
            case ALI_H5_PAY:
                break;
            case BANK_CARD_PAY:
                break;
            default:
                break;
        }
        if (map.size() > 0) {
            message.setData(null);
            message.setData(map);
            message.setSuccess(true);
        }

        return message;
    }

    /**
     * 余额充值通知
     * 1、调用支付状态查询接口获取支付状态
     * 2、支付成功：1）更新交易账单状态：已生效；2）user余额增加
     *
     * @param transactionDetailId 交易账单ID
     * @param payType             支付方式
     * @return
     */
    @Transactional
    public BusinessMessage rechargeNotify(String transactionDetailId, int payType) {
        BusinessMessage message = new BusinessMessage<>();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (user == null || StringUtils.isBlank(user.getId())) {
            message.setMsg("获取当前登录用户信息失败");
            return message;
        }
        PayTypeEnum payTypeEnum = PayTypeEnum.getInstance(payType);
        if (payTypeEnum == null) {
            message.setMsg("请选择支付方式");
            return message;
        }

        /********** 支付装填查询 ***********/
        Map<String, String> map = new HashMap<>();
        switch (payTypeEnum) {
            case WX_APP_PAY:
                break;
            case WX_H5_PAY:
                break;
            case ALI_APP_PAY:
                break;
            case ALI_H5_PAY:
                break;
            case BANK_CARD_PAY:
                break;
            default:
                break;
        }

        return message;
    }
}
