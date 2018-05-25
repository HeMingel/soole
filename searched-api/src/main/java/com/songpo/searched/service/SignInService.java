package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.cache.UserCache;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlSignIn;
import com.songpo.searched.entity.SlTransactionDetail;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlSignInMapper;
import com.songpo.searched.mapper.SlTransactionDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SignInService {

    @Autowired
    private SlSignInMapper slSignInMapper;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserCache userCache;
    @Autowired
    private SlTransactionDetailMapper transactionDetailMapper;

    public BusinessMessage addSignIn() {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        SlSignIn signIn = new SlSignIn();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Example example = new Example(SlSignIn.class);
        example.createCriteria().andEqualTo("userId", user.getId());
        example.setOrderByClause("sign_time DESC");
        List<SlSignIn> list = this.slSignInMapper.selectByExample(example);
        //获取当前时间
        Calendar checkdateCalendar = Calendar.getInstance();
        Boolean flag = false;
        Boolean flag1 = false;
//获取今天凌晨时间
        Calendar today = Calendar.getInstance();
        today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE), 0, 0, 0);
        System.err.println(today.getTime());
//获取昨天凌晨时间
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(yesterday.get(Calendar.YEAR), yesterday.get(Calendar.MONTH), yesterday.get(Calendar.DATE) - 1, 0, 0, 0);

        if (list.size() == 0) {
//如果为空则设置为当前时间
            checkdateCalendar.set(
                    checkdateCalendar.get(Calendar.YEAR),
                    checkdateCalendar.get(Calendar.MONTH),
                    checkdateCalendar.get(Calendar.DATE),
                    checkdateCalendar.get(Calendar.HOUR_OF_DAY),
                    checkdateCalendar.get(Calendar.MINUTE),
                    checkdateCalendar.get(Calendar.SECOND)
            );
            flag = checkdateCalendar.after(today);
            flag1 = true;
        } else {
//如果不为空则设置为用户签到时间
            signIn = list.get(0);
            Date checkDate = null;
            try {
                checkDate = format.parse(signIn.getSignTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            checkdateCalendar.setTime(checkDate);
            flag = checkdateCalendar.before(today);
            flag1 = checkdateCalendar.before(yesterday);
            System.err.println(checkdateCalendar.getTime());
        }
//判断用户签到时间是否是在今天凌晨之前
        if (flag) {
//如果上次签到是昨天凌晨之前，说明没有连续签到
            signIn.setAwardSilver(1);
            signIn.setUserId(user.getId());
            signIn.setId(UUID.randomUUID().toString());
            if (flag1) {
//将签到天数归为1
                signIn.setSignNum(1);
//如果中断的话就
                if (list.size() > 0) {
                    this.slSignInMapper.delete(new SlSignIn() {{
                        setUserId(user.getId());
                    }});
                }
            } else {
                int checkTimes = signIn.getSignNum();
                checkTimes++;
                if (checkTimes == 3) {
                    signIn.setAwardSilver(signIn.getAwardSilver() + 1);
                } else if (checkTimes == 7) {
                    signIn.setAwardSilver(signIn.getAwardSilver() + 2);
                }
                //连续签到天数加1
                signIn.setSignNum(checkTimes);
            }
            signIn.setSignTime(format.format(new Date()));
            int count = slSignInMapper.insertSelective(signIn);
            if (count == 1) {
                SlSignIn finalSignIn = signIn;
                int silver = user.getSilver() + finalSignIn.getAwardSilver();
                user.setSilver(silver);
                userCache.put(user.getClientId(), user);
                userService.updateByPrimaryKeySelective(new SlUser() {{
                    setId(user.getId());
                    setSilver(silver);
                }});
                // 加入明细表
                transactionDetailMapper.insertSelective(new SlTransactionDetail() {{
                    // 目标id
                    setTargetId(user.getId());
                    // 购物类型(签到)
                    setType(101);
                    // 奖励了豆数量
                    setSilver(finalSignIn.getAwardSilver());
                    // 插入时间
                    setCreateTime(new Date());
                    // 银豆
                    setDealType(6);
                    // 收入
                    setTransactionType(2);
                }});
            } else {
                message.setMsg("签到信息错误");
            }
            message.setMsg("今日签到成功");
            message.setSuccess(true);
        } else {
            message.setMsg("您已经签到过了");
        }
        return message;
    }

    /**
     * 查询签到信息
     *
     * @return
     */
    public BusinessMessage selectSignIn() {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        Example example = new Example(SlSignIn.class);
        example.createCriteria().andEqualTo("userId", user.getId());
        example.setOrderByClause("sign_time DESC");
        List<SlSignIn> signInList = this.slSignInMapper.selectByExample(example);
        JSONObject object = new JSONObject();
        if (signInList.size() > 0) {
            SlSignIn signIn = signInList.get(0);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Boolean flag = false;
            Date checkDate = new Date();
            try {
                checkDate = format.parse(signIn.getSignTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar checkdateCalendar = Calendar.getInstance();
            Calendar today = Calendar.getInstance();
            today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE), 0, 0, 0);
            checkdateCalendar.setTime(checkDate);
            flag = checkdateCalendar.after(today);
            if (flag) {
                object.put("todaySign", 1);
            } else {
                object.put("todaySign", 0);
            }
            if (null != user) {
                //当前用户的银豆数量
                object.put("silver", user.getSilver());
                //当前用户的金豆数量
                object.put("coin", user.getCoin());
                //当前用户连续签到天数
                object.put("signNum", signIn.getSignNum());
                message.setMsg("查询成功");
                message.setData(object);
                message.setSuccess(true);
            } else {
                message.setMsg("用户不存在");
            }
        } else {
            message.setSuccess(true);
            message.setMsg("当前签到信息为空");
            object.put("todaySign", 0);
            //当前用户的银豆数量
            object.put("silver", user.getSilver());
            //当前用户的金豆数量
            object.put("coin", user.getCoin());
            message.setData(object);
        }
        return message;
    }
}