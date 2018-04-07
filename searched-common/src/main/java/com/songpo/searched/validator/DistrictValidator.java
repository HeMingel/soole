package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlDistrict;
import com.songpo.searched.service.DistrictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 权限信息校验器
 *
 * @author SongpoLiu
 */
public class DistrictValidator extends ValidatorHandler<SlDistrict> {

    private static final Logger logger = LoggerFactory.getLogger(DistrictValidator.class);

    private DistrictService service;

    public DistrictValidator(DistrictService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlDistrict t) {
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
        }

        return flag;
    }
}
