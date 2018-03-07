package com.songpo.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.entity.SlMember;
import com.songpo.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MemberValidator extends ValidatorHandler<SlMember> {
    private static final Logger logger = LoggerFactory.getLogger(MemberValidator.class);

    private MemberService service;

    public MemberValidator(MemberService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlMember t) {
        logger.debug("执行校验：实体信息[{}]", t);

        // 初始化验证标识
        boolean flag = true;
        // 校验实体是否为空
        if (null == t)
        {
            context.addError(new ValidationError() {{
                setErrorMsg("权限信息为空");
                setField("SlMember");
                setInvalidValue(t);
            }});
            flag = false;
        } else
        {
            // 校验字段
            if (StringUtils.isEmpty(t.getUserId()))
            {
                context.addError(new ValidationError() {{
                    setErrorMsg("用户账号为空");
                    setField("username");
                    setInvalidValue(t.getUserId());
                }});
                flag = false;

            }
        }
        // 校验是否存在，如果不存在，则进行初始化工作
        if (flag)
        {
            try
            {
                t.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } catch (Exception e)
            {
                logger.error("校验失败：{}", e);

                context.addError(new ValidationError() {{
                    setErrorMsg("校验失败：" + e.getMessage());
                    setField("userInfo");
                    setInvalidValue(t);
                }});
                flag = false;
            }
        }
        return flag;
    }
}
