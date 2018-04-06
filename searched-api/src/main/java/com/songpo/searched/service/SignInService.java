package com.songpo.searched.service;
import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlSignIn;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlSignInMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class SignInService {

    @Autowired
    private SlSignInMapper slSignInMapper;
    @Autowired
    private LoginUserService loginUserService;

    public BusinessMessage addSignIn() {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        SlSignIn slSignIn = new SlSignIn();
        Example example = new Example(SlSignIn.class);
        example.createCriteria().andEqualTo("id", user.getId());
        example.setOrderByClause("sign_time DESC");
        List<SlSignIn> list = this.slSignInMapper.selectByExample(example);
        //获取当前时间
        Calendar checkdateCalendar = Calendar.getInstance();
        if (list.size() == 0) {
//如果为空则设置为当前时间
            checkdateCalendar.set(checkdateCalendar.get(Calendar.YEAR), checkdateCalendar.get(Calendar.MONTH), checkdateCalendar.get(Calendar.DATE), 0, 0, 0);
        } else {
//如果不为空则设置为用户签到时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SlSignIn signIn = list.get(0);
            Date checkDate = null;
            try {
                checkDate = format.parse(signIn.getSignTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            checkdateCalendar.setTime(checkDate);
        }
//获取今天凌晨时间
        Calendar today = Calendar.getInstance();
        today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE), 0, 0, 0);
//获取昨天凌晨时间
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(yesterday.get(Calendar.YEAR), yesterday.get(Calendar.MONTH), yesterday.get(Calendar.DATE) - 1, 0, 0, 0);
//判断用户签到时间是否是在今天凌晨之前
        if (checkdateCalendar.before(today)) {
//如果上次签到是昨天凌晨之前，说明没有连续签到
            if (checkdateCalendar.before(yesterday)) {
//将签到天数归为1
                slSignIn.setUserId(user.getId());
                slSignIn.setId(UUID.randomUUID().toString());
                slSignIn.setSignNum(1);
            } else {
                int checkTimes = slSignIn.getSignNum();
                checkTimes++;
                slSignIn.setAwardSilver(2);
                if (checkTimes == 3) {
                    slSignIn.setAwardSilver(slSignIn.getAwardSilver() + 3);
                } else if (checkTimes == 7) {
                    slSignIn.setAwardSilver(slSignIn.getAwardSilver() + 12);
                }
                //连续签到天数加1
                slSignIn.setSignNum(checkTimes);
            }
            slSignIn.setSignTime(String.valueOf(checkdateCalendar));
            slSignInMapper.insertSelective(slSignIn);
            message.setMsg("今日签到成功");
            message.setSuccess(true);
        } else {
            message.setMsg("您已经签到过了");
        }
        return message;
    }

    public BusinessMessage selectSignIn() {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        Example example = new Example(SlSignIn.class);
        example.createCriteria().andEqualTo("userId", user.getId());
        example.setOrderByClause("sign_time DESC");
        List<SlSignIn> signInList = this.slSignInMapper.selectByExample(example);
        if (signInList.size() > 0) {
            SlSignIn signIn = signInList.get(0);
            JSONObject object = new JSONObject();
            if (null != user) {
                //当前用户的银豆数量
                object.put("silver", user.getSilver());
                //当前用户的金豆数量
                object.put("coin", user.getCoin());
                //当前用户签到天数
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
        }
        return message;
    }
}