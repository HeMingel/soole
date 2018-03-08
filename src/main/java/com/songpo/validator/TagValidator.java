package com.songpo.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.entity.SlTag;
import com.songpo.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * 标签信息校验器
 *
 * @author Y.H
 */
public class TagValidator extends ValidatorHandler<SlTag> {

    private static final Logger logger = LoggerFactory.getLogger(TagValidator.class);

    private TagService service;

    public TagValidator(TagService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlTag t) {
        logger.debug("执行校验：实体信息[{}]", t);

        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("标签为空");
                setField("userInfo");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getName())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("标签名称为空");
                    setField("tagTame");
                    setInvalidValue(t.getName());
                }});
                flag = false;
            }
        }

        return flag;
    }
}
