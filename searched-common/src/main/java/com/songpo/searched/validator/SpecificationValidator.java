package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlSpecification;
import com.songpo.searched.service.SpecificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class SpecificationValidator extends ValidatorHandler<SlSpecification> {

    private static final Logger logger = LoggerFactory.getLogger(SlSpecification.class);

    private SpecificationService service;

    public SpecificationValidator(SpecificationService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlSpecification t) {
        logger.debug("执行校验：实体信息[{}]", t);
        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("Specification该实体为空");
                setField("SlSpecification");
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
            } else if(StringUtils.isEmpty(t.getId())){
                context.addError(new ValidationError() {{
                    setErrorMsg("id为空");
                    setField("id");
                    setInvalidValue(t.getId());
                }});
                flag = false;
            }
        }
        return flag;
    }
}
