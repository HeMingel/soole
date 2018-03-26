package com.songpo.searched.config;
import com.songpo.searched.entity.SlOrder;
import com.songpo.searched.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 定时任务定时更新订单支付超时订单 每30分钟检测一次
 */
@Component
@Slf4j
public class CommonConfig {
    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 30 * * * *")
    void updOrderState() {
        try {
            Example example = new Example(SlOrder.class);
            example.setOrderByClause("create_time ASC");
            example.createCriteria().andEqualTo("paymentState", 0);
            List<SlOrder> list = orderService.selectByExample(example);
            if (list.size()>0){
                for (SlOrder slOrder : list) {
                    SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = sdf.parse(slOrder.getCreateTime());
                    Long res = new Date().getTime() - date.getTime();
                    if (res >=1800) {
                        this.orderService.updateByPrimaryKeySelective(new SlOrder() {{
                            setId(slOrder.getId());
                            setPaymentState(-1);
                        }});
                    }
                }
            }
        }catch (Exception e){
            log.error("更新失败",e);
        }
    }
}
