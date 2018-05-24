package com.songpo.searched.service;

import com.songpo.searched.domain.BusinessMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 账户管理
 */
@Service
public class AccountService {

    public static final Logger log = LoggerFactory.getLogger(AfterSalesService.class);

    /**
     * 余额充值
     *
     * @param req
     * @param money   充值金额，单位：元
     * @param payType 支付方式：1-微信；2-支付宝；3-银行卡支付
     * @return
     */
    @Transactional
    public BusinessMessage<Map> recharge(HttpServletRequest req, double money, int payType) {
        BusinessMessage<Map> message = new BusinessMessage<>();

        return message;
    }
}
