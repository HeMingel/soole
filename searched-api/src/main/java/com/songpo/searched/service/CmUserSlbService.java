package com.songpo.searched.service;


import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlSlbTransaction;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.entity.SlUserSlb;
import com.songpo.searched.mapper.CmUserSlbMapper;
import com.songpo.searched.mapper.SlSlbTransactionMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yinsl
 */
@Service
public class CmUserSlbService {

    public static final Logger log = LoggerFactory.getLogger(CmUserSlbService.class);

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private UserSlbService userSlbService;
    @Autowired
    private UserService userService;
    @Autowired
    private SlSlbTransactionMapper slbTransactionMapper;
    @Autowired
    private CmUserSlbMapper cmUserSlbMapper;

    /**
     *搜了贝转让
     * @param id
     * @param slb
     * @param  slbType
     * @return
     */
    @Transactional
    public BusinessMessage transferUserSlb (Integer id, BigDecimal slb, Integer slbType) {
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (user == null || StringUtils.isBlank(user.getId())) {
            message.setMsg("获取当前登录用户信息失败");
            message.setSuccess(false);
            return message;
        }
        //转让人搜了贝信息
        SlUserSlb transferSlb = userSlbService.selectOne(new SlUserSlb(){{
            setUserId(user.getId());
            setSlbType(slbType);
        }});
        //接收人搜了贝信息
        SlUser accept = userService.selectOne(new SlUser(){{setUsername(id);}});
        SlUserSlb acceptSlb = userSlbService.selectOne(new SlUserSlb(){{
            setUserId(accept.getId());
            setSlbType(slbType);
        }});
        if (-1==transferSlb.getSlb().compareTo(slb)){
            message.setMsg("搜了贝不足");
            message.setSuccess(false);
            return message;
        }
        //加入搜了贝明细表
        //1转让人明细 以及更新用户搜了贝
        slbTransactionMapper.insertSelective(new SlSlbTransaction(){{
            setSourceId(accept.getId());
            setTargetId(user.getId());
            setSlbType(transferSlb.getSlbType());
            setType(1);
            setSlb(slb);
            setTransactionType(1);
        }});
        transferSlb.setSlb(transferSlb.getSlb().subtract(slb));
        userSlbService.updateByPrimaryKey(transferSlb);
        //2接收人明细 以及更新用户搜了贝
        slbTransactionMapper.insertSelective(new SlSlbTransaction(){{
            setTargetId(accept.getId());
            setSlbType(transferSlb.getSlbType());
            setType(1);
            setSlb(slb);
            setTransactionType(2);
        }});
        if (null != acceptSlb){
            acceptSlb.setSlb(acceptSlb.getSlb().subtract(slb));
            userSlbService.updateByPrimaryKey(acceptSlb);
        }else {
            userSlbService.insertSelective(new SlUserSlb(){{
                setUserId(accept.getId());
                setSlb(slb);
                setSlbType(slbType);
            }});
        }
        message.setMsg("转让搜了贝成功");
        message.setSuccess(true);
        return message;
    }


    /**
     *查询搜了贝
     * @return
     */
    public BusinessMessage selectUserSlb () {
        JSONObject data = new JSONObject();
        BusinessMessage message = new BusinessMessage();
        SlUser user = loginUserService.getCurrentLoginUser();
        if (user == null || StringUtils.isBlank(user.getId())) {
            message.setMsg("获取当前登录用户信息失败");
            message.setSuccess(false);
            return message;
        }
        List<SlUserSlb> list = userSlbService.select(new SlUserSlb(){{
            setUserId(user.getId());
        }});
        data.put("slbList", list);
        List listSum = cmUserSlbMapper.selectSumSlb(user.getId());

        if (null == listSum.get(0)) {
            listSum.set(0, "0");
            Map map = new HashMap();
            map.put("slbSum", listSum.get(0));
            List list1 = new ArrayList();
            list1.add(map);
            data.put("slbSum",list1);
        }else {
            data.put("slbSum",listSum);
        }

        message.setData(data);
        message.setMsg("查询搜了贝成功");
        message.setSuccess(true);
        return message;
    }

    /**
     *查询搜了贝详情
     * @param userId
     * @param slbType
     * @return
     */
    @Transactional
    public BusinessMessage selectUserSlbDetail (String userId, Integer slbType) {
        BusinessMessage message = new BusinessMessage();
        try {
            message.setData(this.cmUserSlbMapper.selectUserSlbDetail(userId, slbType));
            message.setMsg("查询搜了贝详情成功");
            message.setSuccess(true);
        }catch (Exception e){
            log.error("查询搜了贝详情异常", e);
            message.setMsg("查询搜了贝详情异常");
            message.setSuccess(false);
        }

        return message;
    }
}
