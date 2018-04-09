package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlDistrictProvince;
import com.songpo.searched.service.DistrictProvinceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistrictProvinceValidator extends ValidatorHandler<SlDistrictProvince> {

    private static final Logger logger = LoggerFactory.getLogger(SlDistrictProvince.class);

    private DistrictProvinceService service;

    public DistrictProvinceValidator(DistrictProvinceService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlDistrictProvince t) {
        logger.debug("执行校验：实体信息[{}]", t);
        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("DistrictProvince该实体为空");
                setField("SlDistrictProvince");
                setInvalidValue(t);
            }});
            flag = false;
        }
        return flag;
    }
}
