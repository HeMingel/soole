package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlDistrictStreet;
import com.songpo.searched.service.DistrictStreetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistrictStreetValidator extends ValidatorHandler<SlDistrictStreet> {

    private static final Logger logger = LoggerFactory.getLogger(SlDistrictStreet.class);

    private DistrictStreetService service;

    public DistrictStreetValidator(DistrictStreetService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlDistrictStreet t) {
        logger.debug("执行校验：实体信息[{}]", t);
        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("DistrictStreet该实体为空");
                setField("SlDistrictStreet");
                setInvalidValue(t);
            }});
            flag = false;
        }
        return flag;
    }
}
