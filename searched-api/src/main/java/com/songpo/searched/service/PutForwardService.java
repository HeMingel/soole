package com.songpo.searched.service;

import com.songpo.searched.entity.SlPutForward;
import com.songpo.searched.entity.SlTransactionDetail;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlPutForwardMapper;
import com.songpo.searched.mapper.SlUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class PutForwardService {

    public static final Logger log = LoggerFactory.getLogger(PutForwardService.class);

    @Autowired
    private SlPutForwardMapper slPutForwardMapper;

    @Autowired
    private ProcessOrders processOrders;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionDetailService transactionDetailService;

    /**
     *
     * @param pfUserId 提现用户ID
     * @param bankId  用户银行卡ID
     * @param pfMoney  提现金额
     */
    public void add(String pfUserId, String bankId,String pfMoney) {
        try {
            String trid=getMoney(pfUserId,BigDecimal.valueOf(Double.parseDouble(pfMoney)));
            this.slPutForwardMapper.insertSelective(new SlPutForward(){{
                setBankId(bankId);
                setPfUserId(pfUserId);
                setCreatedAt(new Date());
                setUpdatedAt(new Date());
                setDetailId(trid);
                setPfMoney(BigDecimal.valueOf(Double.parseDouble(pfMoney)));
            }});

        } catch (Exception e) {
            log.error("提现失败 {}", e);
        }
    }


    /**
     * 提现产生记录，以及扣除用户余额
     */
    @Transactional(rollbackFor = Exception.class)
    public String getMoney(String userId,BigDecimal money) {
        SlTransactionDetail detail = new SlTransactionDetail();
        try {

            SlUser slUser = this.userService.selectOne(new SlUser() {{
                setId(userId);
            }});

            slUser.setMoney(slUser.getMoney().subtract(money));
            userService.updateByPrimaryKeySelective(slUser);



            detail.setSourceId(userId);
            detail.setTargetId(slUser.getId());
            detail.setType(6);
            detail.setMoney(money);
            detail.setDealType(1);
            detail.setTransactionType(1);
            detail.setCreateTime(new Date());
            transactionDetailService.insertSelective(detail);

            /**
             * 极光推送
             */
            String content = "提现成功,"+money+"元，您的提现申请将在3~10个工作日内汇入您的账户！";
            processOrders.sendPush(slUser.getUsername().toString(),content,2,"余额提现");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail.getId();
    }


}
