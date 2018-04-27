package com.songpo.searched.config;

import com.songpo.searched.entity.SlOrderDetail;
import com.songpo.searched.entity.SlReturnsDetail;
import com.songpo.searched.entity.SlSignIn;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlReturnsDetailMapper;
import com.songpo.searched.mapper.SlSignInMapper;
import com.songpo.searched.service.LoginUserService;
import com.songpo.searched.service.OrderDetailService;
import com.songpo.searched.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class CommonConfig {

    public static final Logger log = LoggerFactory.getLogger(CommonConfig.class);

    @Autowired
    private SlSignInMapper slSignInMapper;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private SlReturnsDetailMapper returnsDetailMapper;
    @Autowired
    private OrderDetailService orderDetailService;


    /**
     * 定时更新签到天数
     */
    @Scheduled(cron = "0 0 0 * * ?")
    void updSignState() {
        try {
            Example example = new Example(SlSignIn.class);
            example.createCriteria().andEqualTo("signNum", 7);
            // 更新连续签到天数
            int count = this.slSignInMapper.updateByExampleSelective(new SlSignIn() {{
                setSignNum(0);
            }}, example);
            if (count == 1) {
                SlUser user = loginUserService.getCurrentLoginUser();
                this.slSignInMapper.delete(new SlSignIn() {{
                    setUserId(user.getId());
                }});
            }
        } catch (Exception e) {
            log.error("更新失败", e);
        }
    }

    /**
     * 更新用户预售返现状态
     */
    @Scheduled(cron = "0 0 0 * * ?")
    void updOrderPreSaleState() {
        LocalTime localTime = LocalTime.now();
        localTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<SlReturnsDetail> list = this.returnsDetailMapper.selectAll();
        for (SlReturnsDetail detail : list) {
            if (detail.getReturnTime().equals(localTime)) {
                returnsDetailMapper.updateByPrimaryKeySelective(new SlReturnsDetail() {{
                    setId(detail.getId());
                    // 当前时间和返现时间相同的话把状态改为可返
                    setReturnedStatus(4);
                }});
            }
            if (detail.getReturnedStatus().equals(4)) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate ldt = LocalDate.parse(detail.getReturnTime(), df);
                // 如果返现时间+3天 并且状态还是可返状态
                if (ldt.plusDays(3).equals(localTime)) {
                    // 把状态改为已逾期
                    returnsDetailMapper.updateByPrimaryKeySelective(new SlReturnsDetail() {{
                        setId(detail.getId());
                        setReturnedStatus(3);
                    }});
                }
            }
        }
    }

    /**
     * 预售订单确认收货(商家发货3天,自动确认收货)
     */
    @Scheduled(cron = "0 0 0 * * ?")
    void updReturnsDetailOrderPreSaleState() {
        Example e = new Example(SlReturnsDetail.class);
        e.setOrderByClause("return_time ASC");
        List<SlReturnsDetail> list = this.returnsDetailMapper.selectByExample(e);
        for (SlReturnsDetail detail : list) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime time = LocalDateTime.now();
            LocalDateTime ldt = LocalDateTime.parse(detail.getReturnTime(), df);
            Duration duration = Duration.between(ldt, time);
            if (duration.toDays() == 3) {
                Example example = new Example(SlReturnsDetail.class);
                example.createCriteria()
                        .andEqualTo("id", detail.getId())
                        //商家已返状态
                        .andEqualTo("returnedStatus", 1)
                        //但是用户并没有确认收货的
                        .andEqualTo("confirmReceipt", false);
                int count = returnsDetailMapper.updateByExampleSelective(new SlReturnsDetail() {{
                    setConfirmReceipt(true);
                }}, example);
                if (count == 1) {
                    if (detail.getId().equals(list.get(0).getId())) {
                        Example example1 = new Example(SlOrderDetail.class);
                        example1.createCriteria().andEqualTo("orderId", detail.getOrderId());
                        this.orderDetailService.updateByExampleSelective(new SlOrderDetail() {{
                            // 已完成/未评价
                            setShippingState(5);
                        }}, example1);
                        //把改订单号的所有订单更新为已完成状态
                        this.returnsDetailMapper.updateByExampleSelective(new SlReturnsDetail() {{
                            setReturnedStatus(5);
                        }}, example);

                    }
                }
            }
        }
    }
}
