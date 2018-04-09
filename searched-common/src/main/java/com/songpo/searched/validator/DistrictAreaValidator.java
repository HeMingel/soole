package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlDistrictArea;
import com.songpo.searched.service.DistrictAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistrictAreaValidator extends ValidatorHandler<SlDistrictArea> {

    private static final Logger logger = LoggerFactory.getLogger(SlDistrictArea.class);

    private DistrictAreaService service;

    public DistrictAreaValidator(DistrictAreaService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlDistrictArea t) {
        logger.debug("执行校验：实体信息[{}]", t);
        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("DistrictArea该实体为空");
                setField("SlDistrictArea");
                setInvalidValue(t);
            }});
            flag = false;
        }
        return flag;
    }
}
