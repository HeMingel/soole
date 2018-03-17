package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlActivity;
import com.songpo.searched.mapper.SlActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author 刘松坡
 */
@Component
public class ActivityValidator extends ValidatorHandler<SlActivity> {

    private static final Logger logger = LoggerFactory.getLogger(SlActivity.class);

    @Autowired
    private SlActivityMapper mapper;

    @Override
    public boolean validate(ValidatorContext context, SlActivity t) {
        logger.debug("执行校验：实体信息[{}]", t);
        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("实体为空");
                setField("activity");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getName())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("名称为空");
                    setField("name");
                    setInvalidValue(t.getName());
                }});
                flag = false;
            }
        }
        // 校验是否存在，如果不存在，则进行初始化工作
        if (flag) {
            try {
                // 初始化唯一查询条件
                SlActivity item = new SlActivity();
                item.setName(t.getName());

                // 查询数量
                int count = this.mapper.selectCount(item);

                // 判断数量是否大于0，大于0则为已存在
                if (count > 0) {
                    context.addError(new ValidationError() {{
                        setErrorMsg("名称已存在");
                        setField("name");
                        setInvalidValue(t.getName());
                    }});
                    flag = false;
                }
            } catch (Exception e) {
                logger.error("校验失败：{}", e);

                context.addError(new ValidationError() {{
                    setErrorMsg("校验失败：" + e.getMessage());
                    setField("activity");
                    setInvalidValue(t);
                }});
                flag = false;
            }
        }
        return flag;
    }
}
