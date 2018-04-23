package com.songpo.searched.config;

import com.songpo.searched.entity.SlReturnsDetail;
import com.songpo.searched.entity.SlSignIn;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlReturnsDetailMapper;
import com.songpo.searched.mapper.SlSignInMapper;
import com.songpo.searched.service.LoginUserService;
import com.songpo.searched.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDate;
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


//    @Autowired
//    private OrderService orderService;

    //    @Scheduled(cron = "0 30 * * * *")
//    void updOrderState() {
//        try {
//            Example example = new Example(SlOrder.class);
//            example.setOrderByClause("create_time ASC");
//            example.createCriteria().andEqualTo("paymentState", 0);
//            List<SlOrder> list = orderService.selectByExample(example);
//            if (list.size()>0){
//                for (SlOrder slOrder : list) {
//                    SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date date = sdf.parse(slOrder.getCreateTime());
//                    Long res = new Date().getTime() - date.getTime();
//                    if (res >=1800) {
//                        this.orderService.updateByPrimaryKeySelective(new SlOrder() {{
//                            setId(slOrder.getId());
//                            setPaymentState(-1);
//                        }});
//                    }
//                }
//            }
//        }catch (Exception e){
//            log.error("更新失败",e);
//        }
//    }
}
