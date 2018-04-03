package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlHotKeywords;
import com.songpo.searched.service.HotKeywordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 权限信息校验器
 *
 * @author SongpoLiu
 */
public class HotKeywordsValidator extends ValidatorHandler<SlHotKeywords> {

    private static final Logger logger = LoggerFactory.getLogger(HotKeywordsValidator.class);

    private HotKeywordsService service;

    public HotKeywordsValidator(HotKeywordsService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlHotKeywords t) {
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
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getContent())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("关键词为空");
                    setField("content");
                    setInvalidValue(t.getContent());
                }});
                flag = false;
            }
        }

        // 校验是否存在，如果不存在，则进行初始化工作
        if (flag) {
            try {
                // 初始化唯一查询条件
                SlHotKeywords item = new SlHotKeywords();
                item.setContent(t.getContent());

                // 查询数量
                int count = this.service.selectCount(item);

                // 判断数量是否大于0，大于0则为已存在
                if (count > 0) {
                    context.addError(new ValidationError() {{
                        setErrorMsg("关键词已存在");
                        setField("content");
                        setInvalidValue(t.getContent());
                    }});
                    flag = false;
                }
            } catch (Exception e) {
                logger.error("校验失败：{}", e);

                context.addError(new ValidationError() {{
                    setErrorMsg("校验失败：" + e.getMessage());
                    setField("hotKeywords");
                    setInvalidValue(t);
                }});
                flag = false;
            }
        }

        return flag;
    }
}
