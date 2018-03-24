package com.songpo.searched.service;

import com.songpo.searched.entity.SlOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class test {
    @Autowired
    private OrderService orderService;

    public static void main(String[] args) {
        test test = new test();
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.err.println(format0.format(new Date().getSeconds() + 60000));
        test.corn();
    }

    void corn() {
        Timer timer = new Timer();
        // 线程用于关闭订单
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                orderService.updateByPrimaryKeySelective(new SlOrder() {{
//                    setPaymentState(2);
//                    setId("453bdc8d-5693-4f7c-b694-40cd9a2b137e");
//                }});
                System.err.println("1111111111111111111");
            }
        }, new Date().getSeconds() + 60000);
    }
}
