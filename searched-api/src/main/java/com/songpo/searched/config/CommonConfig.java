package com.songpo.searched.config;

import com.songpo.searched.entity.SlSignIn;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlSignInMapper;
import com.songpo.searched.service.LoginUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;
@Component
public class CommonConfig {

    public static final Logger log = LoggerFactory.getLogger(CommonConfig.class);

    @Autowired
    private SlSignInMapper slSignInMapper;
    @Autowired
    private LoginUserService loginUserService;

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
