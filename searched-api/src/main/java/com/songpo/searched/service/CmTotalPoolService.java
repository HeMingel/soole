package com.songpo.searched.service;

import com.songpo.searched.constant.BaseConstant;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlTotalPool;
import com.songpo.searched.entity.SlTotalPoolDetail;
import com.songpo.searched.mapper.SlTotalPoolDetailMapper;
import com.songpo.searched.mapper.SlTotalPoolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author  heming
 * @Date 2018年7月5日09:58:53
 * 资金池操作
 */
@Service
public class CmTotalPoolService {

    public static final Logger log = LoggerFactory.getLogger(CmTotalPoolService.class);

     @Autowired
     private SlTotalPoolMapper slTotalPoolMapper;
     @Autowired
     private SlTotalPoolDetailMapper slTotalPoolDetailMapper;

    /**
     * 更新资金池操作
     * @param coin 金豆数量 可为空
     * @param silver 银豆数量 可为空
     * @param slb 搜了贝数量 可为空
     * @param addAndSubtract 加减 (相对于资金池)1 增加 2 减少 不能为空
     * @param orderId 订单ID 可为空
     * @param userId 用户ID
     * @param type 交易类型(针对纯系统产生的豆和贝) 1用户注册(银豆) 2用户签到(金豆) 3购物分享(针对现金购物分享赠送的银豆)
     *            4邀请好友(金豆) 5交易购物(针对购买的搜了贝) 6水果竞猜(金豆) 7券兑换银豆(银豆) 8 给邀请人发送搜了贝 不能为空
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public BusinessMessage<SlTotalPool> updatePool(Integer coin,
                                                   Integer silver,
                                                   BigDecimal slb,
                                                   Integer addAndSubtract,
                                                   String orderId,
                                                   String userId,
                                                   Integer type) {
        BusinessMessage message = new BusinessMessage();
        if (addAndSubtract == null || addAndSubtract == 0) {
            message.setMsg("操作类型不能为空");
            return message;
        }
        if (type == null || type == 0) {
            message.setMsg("交易类型不能为空");
            return message;
        }
        if ((coin == null || coin == 0) && (silver == null || silver == 0) && (slb == null
                || slb.compareTo(new BigDecimal(0)) == 0)) {
            message.setMsg("金豆、银豆和搜了贝不能同时为空");
            return message;
        }
        //资金池
        SlTotalPool slTotalPool = slTotalPoolMapper.selectByPrimaryKey(BaseConstant.TOTAL_POOL_ID);
        SlTotalPoolDetail slTotalPoolDetail = new SlTotalPoolDetail();
        int poolCoin = slTotalPool.getPoolCoin();
        int poolSilver = slTotalPool.getPoolSilver();
        BigDecimal poolSlb = slTotalPool.getPoolSlb();
        try {
            //根据参数更新资金池信息
            if (coin != null && coin > 0) {
                slTotalPool.setPoolCoin(addAndSubtract == 1 ? (poolCoin + coin) : (poolCoin - coin));
                slTotalPoolDetail.setPdCoin(coin);
            }
            if (silver != null && silver > 0) {
                slTotalPool.setPoolSilver(addAndSubtract == 1 ? (poolSilver + silver) : (poolSilver - silver));
                slTotalPoolDetail.setPdSilver(silver);
            }
            if (slb != null && slb.compareTo(new BigDecimal(0)) == 1) {
                slTotalPool.setPoolSlb(addAndSubtract == 1 ? (poolSlb.add(slb)) : (poolSlb.divide(slb)));
                slTotalPoolDetail.setPdSlb(slb);
            }
            Integer result = slTotalPoolMapper.updateByPrimaryKeySelective(slTotalPool);
            if (result > 0) {
                log.debug("资金池更新成功");
                //添加更新资金池记录
                slTotalPoolDetail.setPdType(type);
                if (orderId != null) {
                    slTotalPoolDetail.setPdOrderId(orderId);
                }
                if (userId != null) {
                    slTotalPoolDetail.setPdUserId(userId);
                }
                Integer result1 = slTotalPoolDetailMapper.insertSelective(slTotalPoolDetail);
                if (result1 > 0) {
                    log.debug("资金池记录添加成功");
                    message.setMsg("资金池记录添加成功");
                    message.setSuccess(true);
                } else {
                    log.debug("资金池记录添加失败");
                    message.setMsg("资金池更新失败");
                }
            } else {
                log.debug("资金池更新失败");
                message.setMsg("资金池更新失败");
                return message;
            }

        } catch (Exception e) {
            log.error("更新资金池信息失败", e);
            message.setMsg("资金池更新失败");
        } finally {
            return message;
        }
    }
}
