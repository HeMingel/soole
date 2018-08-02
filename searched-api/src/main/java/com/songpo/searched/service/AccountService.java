package com.songpo.searched.service;

import com.songpo.searched.alipay.service.AliPayService;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlTransactionDetail;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlTransactionDetailMapper;
import com.songpo.searched.typehandler.*;
import com.songpo.searched.util.Arith;
import com.songpo.searched.util.ClientIPUtil;
import com.songpo.searched.util.HttpRequest;
import com.songpo.searched.wxpay.service.WxPayService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
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
    @Autowired
    private UserService userService;
    @Autowired
    private CmTotalPoolService cmTotalPoolService;
    @Autowired
    private HttpRequest httpRequest;
    @Autowired
    private Environment env;

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

        /********** 支付状态查询 ***********/
        //第三方返回的支付状态
        boolean payStatus = false;
        //第三方返回的支付金额
        BigDecimal payAmount;
        if (payTypeEnum.equals(PayTypeEnum.WX_APP_PAY) || payTypeEnum.equals(PayTypeEnum.WX_H5_PAY)) {
            Map<String, String> result = wxPayService.orderQuery("", transactionDetailId);
            if (result != null && result.get("return_code").equals("SUCCESS") && result.get("result_code").equals("SUCCESS") && result.get("trade_state").equals("SUCCESS")) {
                payStatus = true;
                double total_fee = Arith.div(Double.valueOf(result.get("total_fee")), 100, 2);
                payAmount = new BigDecimal(total_fee);
            }
        } else if (payTypeEnum.equals(PayTypeEnum.ALI_APP_PAY) || payTypeEnum.equals(PayTypeEnum.ALI_H5_PAY)) {

        } else if (payTypeEnum.equals(PayTypeEnum.BANK_CARD_PAY)) {

        }
        if (payStatus == true) {

        }

        return message;
    }

    /**
     *根据ID 获取用户信息
     * @param userId
     * @return
     */
    public BusinessMessage getUserInfo (String userId) {
        BusinessMessage message = new BusinessMessage<>();
        SlUser user = userService.selectByPrimaryKey(userId);
        if (user == null || StringUtils.isBlank(user.getId())) {
            message.setMsg("获取当前登录用户信息失败");
            return message;
        }
        message.setData(user);
        return message;
    }

    /**
     *根据ID操作用户的豆子
     * @param userId 用户id
     * @param number 豆子数量
     * @param type 0: 增加 1:扣除
     * @return
     */
    public BusinessMessage operateCoinById (String userId,Integer number,Integer type) {
        BusinessMessage message = new BusinessMessage<>();
        if (number <= 0) {
            message.setMsg("非法金豆数量");
            return message;
        }
        /**
         * 获益超过100W返回异常
         */
        if (number > 1000000 && type == 0) {
            message.setMsg("数据异常");
            return message;
        }
       if (type == null ) {
            message.setMsg("非法操作");
           return message;
       }
        if (userId != null && userId != "") {
            SlUser user = userService.selectByPrimaryKey(userId);
            Integer coin = user.getCoin();

            if ( 0 == type) {
                coin = user.getCoin() + number;
            }else if (1 == type) {
                coin = user.getCoin() - number;
                if (coin  < 0 ) {
                    message.setMsg("对不起，您的账户金豆数不足");
                    return message;
                }
            }else {

            }
            Integer finalCoin = coin;
           int result =  userService.updateByPrimaryKeySelective(new SlUser(){{
                setId(user.getId());
                setCoin(finalCoin);
            }});
           if (result > 0) {
               //资金池相应的豆数
               Integer addAndSubtract = type == 0 ? 2 :1;
               cmTotalPoolService.updatePool(number,null,null,addAndSubtract,null,userId,6);
               //添加交易记录
               SlTransactionDetail transactionDetail = new SlTransactionDetail();
               transactionDetail.setTargetId(user.getId());
               if (type == 0 ) {
                   transactionDetail.setType(501);
                   transactionDetail.setTransactionType(TransactionTypeEnum.INCOME.getValue());
               }
               if (type == 1) {
                   transactionDetail.setType(502);
                   transactionDetail.setTransactionType(TransactionTypeEnum.EXPENDITURE.getValue());
               }
               transactionDetail.setMoney(new BigDecimal(0));
               transactionDetail.setCoin(number);
               transactionDetail.setDealType(TransactionCurrencyTypeEnum.COIN.getValue());
               transactionDetail.setTransactionStatus(TransactionStatusEnum.EFFICIENT.getValue().byteValue());
               transactionDetail.setCreateTime(new Date());
               transactionDetailService.insertSelective(transactionDetail);
               message.setMsg("操作成功");
               message.setSuccess(true);
           }
        }else {
            message.setMsg("用户ID不能为空");
        }
        return message;
    }

    /**
     *
     * 用户中心
     * @return
     */
    public void insertUserCenter(String phone, String nickName, String avatar) {
        //String josnStr = "{nickName:" + nickName + ",avatar:" + avatar + "}";
        JSONObject json = new JSONObject();
        json.put("nickName",nickName);
        json.put("avatar",avatar);
        String responseBody = null;
        try {
            JSONObject obj = new JSONObject();
            obj.put("phone", phone);
            obj.put("content", json);
            obj.put("type", 1);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(env.getProperty("user.center.url"));
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
            // 解决中文乱码问题
            StringEntity stringEntity = new StringEntity(obj.toString(), "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(stringEntity);
            // CloseableHttpResponse response =
            // httpclient.execute(httpPost);
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(final HttpResponse response)
                        throws ClientProtocolException, IOException {//
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {

                        HttpEntity entity = response.getEntity();

                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException(
                                "Unexpected response status: " + status);
                    }
                }
            };
            responseBody = httpclient.execute(httpPost, responseHandler);
            log.debug("用户中心库返回结果{}", responseBody);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
