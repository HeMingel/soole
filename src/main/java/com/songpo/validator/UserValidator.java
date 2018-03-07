package com.songpo.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.entity.SlUser;
import com.songpo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 权限信息校验器
 *
 * @author SongpoLiu
 */
public class UserValidator extends ValidatorHandler<SlUser> {

    private static final Logger logger = LoggerFactory.getLogger(UserValidator.class);

    private UserService service;

    public UserValidator(UserService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlUser t) {
        logger.debug("执行校验：实体信息[{}]", t);

        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("权限信息为空");
                setField("userInfo");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getUsername())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("用户账号为空");
                    setField("username");
                    setInvalidValue(t.getUsername());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getPassword())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("用户密码为空");
                    setField("password");
                    setInvalidValue(t.getPassword());
                }});
                flag = false;
            }
        }

        // 校验是否存在，如果不存在，则进行初始化工作
        if (flag) {
            try {
                // 初始化唯一查询条件
                SlUser item = new SlUser();
                item.setUsername(t.getUsername());

                // 查询数量
                int count = this.service.selectCount(item);

                // 判断数量是否大于0，大于0则为已存在
                if (count > 0) {
                    context.addError(new ValidationError() {{
                        setErrorMsg("账号已存在");
                        setField("username");
                        setInvalidValue(t.getUsername());
                    }});
                    flag = false;
                } else {
                    t.setSecret(UUID.randomUUID().toString());
                    // 设置创建时间
                    t.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                }
            } catch (Exception e) {
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
