package com.songpo.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.entity.SlActionNavigation;
import com.songpo.entity.SlArticle;
import com.songpo.service.ActionNavigationService;
import com.songpo.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActionNavigationValidator extends ValidatorHandler<SlActionNavigation> {

    private static final Logger logger = LoggerFactory.getLogger(SlActionNavigation.class);

    private ActionNavigationService service;

    public ActionNavigationValidator(ActionNavigationService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlActionNavigation t) {
        logger.debug("执行校验：实体信息[{}]", t);
        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t)
        {
            context.addError(new ValidationError() {{
                setErrorMsg("ActionNavigation该实体为空");
                setField("SlActionNavigation");
                setInvalidValue(t);
            }});
            flag = false;
        } else
        {
            // 校验字段
            if (StringUtils.isEmpty(t.getImageUrl()))
            {
                context.addError(new ValidationError() {{
                    setErrorMsg("图片为空");
                    setField("imageUrl");
                    setInvalidValue(t.getImageUrl());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getUrl()))
            {
                context.addError(new ValidationError() {{
                    setErrorMsg("路径为空");
                    setField("Url");
                    setInvalidValue(t.getUrl());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getTypeId()))
            {
                context.addError(new ValidationError() {{
                    setErrorMsg("路径为空");
                    setField("Url");
                    setInvalidValue(t.getUrl());
                }});
                flag = false;
            }
        }
        // 校验是否存在，如果不存在，则进行初始化工作
        if (flag)
        {
            try
            {
                // 设置创建时间
                t.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } catch (Exception e)
            {
                logger.error("校验失败：{}", e);

                context.addError(new ValidationError() {{
                    setErrorMsg("校验失败：" + e.getMessage());
                    setField("SlActionNavigation");
                    setInvalidValue(t);
                }});
                flag = false;
            }
        }
        return flag;
    }
}
