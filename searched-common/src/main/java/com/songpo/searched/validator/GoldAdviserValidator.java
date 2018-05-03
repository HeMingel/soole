package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlGoldAdviser;
import com.songpo.searched.service.GoldAdviserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class GoldAdviserValidator extends ValidatorHandler<SlGoldAdviser> {

    private static final Logger logger = LoggerFactory.getLogger(SlGoldAdviser.class);

    private GoldAdviserService service;

    public GoldAdviserValidator(GoldAdviserService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlGoldAdviser t) {
        logger.debug("执行校验：实体信息[{}]", t);
        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("GoldAdviser该实体为空");
                setField("SlGoldAdviser");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getNickname())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("昵称为空");
                    setField("imageUrl");
                    setInvalidValue(t.getNickname());
                }});
                flag = false;
            }
        }

        return flag;
    }
}
