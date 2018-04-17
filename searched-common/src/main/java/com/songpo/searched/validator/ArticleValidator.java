package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlArticle;
import com.songpo.searched.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleValidator extends ValidatorHandler<SlArticle> {

    private static final Logger logger = LoggerFactory.getLogger(SlArticle.class);

    private ArticleService service;

    public ArticleValidator(ArticleService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlArticle t) {
        logger.debug("执行校验：实体信息[{}]", t);
        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("权限信息为空");
                setField("SlArticle");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getTitle())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("标题为空");
                    setField("title");
                    setInvalidValue(t.getTitle());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getContent())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("内容为空");
                    setField("content");
                    setInvalidValue(t.getContent());
                }});
                flag = false;
            }
        }
        // 校验是否存在，如果不存在，则进行初始化工作
        if (flag) {
            try {
                // 设置创建时间
//                t.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } catch (Exception e) {
                logger.error("校验失败：{}", e);

                context.addError(new ValidationError() {{
                    setErrorMsg("校验失败：" + e.getMessage());
                    setField("SlArticle");
                    setInvalidValue(t);
                }});
                flag = false;
            }
        }
        return flag;
    }
}
