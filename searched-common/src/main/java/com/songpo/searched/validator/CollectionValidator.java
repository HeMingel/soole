package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlMyCollection;
import com.songpo.searched.service.CollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class CollectionValidator extends ValidatorHandler<SlMyCollection> {

    private static final Logger logger = LoggerFactory.getLogger(CollectionValidator.class);

    private CollectionService service;

    public CollectionValidator(CollectionService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlMyCollection t) {
        logger.debug("执行校验：实体信息[{}]", t);
        // 初始化验证标识
        boolean flag = true;

        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("收藏信息为空");
                setField("SlMyCollection");
                setInvalidValue(t);
            }});
            flag = false;
        } else if (StringUtils.isEmpty(t.getUserId())) {
            context.addError(new ValidationError() {{
                setErrorMsg("用户ID为空");
                setField("UserId");
                setInvalidValue(t.getUserId());
            }});
            flag = false;
        } else if (StringUtils.isEmpty(t.getCollectionId())) {
            context.addError(new ValidationError() {{
                setErrorMsg("收藏ID为空");
                setField("CollectionId");
                setInvalidValue(t.getCollectionId());
            }});
            flag = false;
        } else if (StringUtils.isEmpty(t.getType())) {
            context.addError(new ValidationError() {{
                setErrorMsg("类型为空");
                setField("Type");
                setInvalidValue(t.getType());
            }});
            flag = false;
        }
        return flag;
    }
}
