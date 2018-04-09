package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlDistrictCity;
import com.songpo.searched.service.DistrictCityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistrictCityValidator extends ValidatorHandler<SlDistrictCity> {

    private static final Logger logger = LoggerFactory.getLogger(SlDistrictCity.class);

    private DistrictCityService service;

    public DistrictCityValidator(DistrictCityService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlDistrictCity t) {
        logger.debug("执行校验：实体信息[{}]", t);
        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("DistrictCity该实体为空");
                setField("SlDistrictCity");
                setInvalidValue(t);
            }});
            flag = false;
        }
        return flag;
    }
}
