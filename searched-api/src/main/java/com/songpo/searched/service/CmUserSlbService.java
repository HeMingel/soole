package com.songpo.searched.service;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlSlbTransaction;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.entity.SlUserSlb;
import com.songpo.searched.mapper.SlSlbTransactionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;

/**
 * @author yinsl
 */
@Service
public class CmUserSlbService {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private UserSlbService userSlbService;
    @Autowired
    private UserService userService;
    @Autowired
    private SlSlbTransactionMapper slbTransactionMapper;

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
}
