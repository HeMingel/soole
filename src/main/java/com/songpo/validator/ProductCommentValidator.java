package com.songpo.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.entity.SlProductComment;
import com.songpo.entity.SlProductImage;
import com.songpo.service.ProductCommentService;
import com.songpo.service.ProductImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * 标签信息校验器
 *
 * @author Y.H
 */
public class ProductCommentValidator extends ValidatorHandler<SlProductComment> {

    private static final Logger logger = LoggerFactory.getLogger(ProductCommentValidator.class);

    private ProductCommentService service;

    public ProductCommentValidator(ProductCommentService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlProductComment t) {
        logger.debug("执行校验：实体信息[{}]", t);

        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("商品评论为空");
                setField("ProductCommentInfo");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getProductId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("商品Id为空");
                    setField("ProductId");
                    setInvalidValue(t.getProductId());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getCommentatorId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("商品问题Id为空");
                    setField("CommentatorId");
                    setInvalidValue(t.getCommentatorId());
                }});
                flag = false;
            }else if (StringUtils.isEmpty(t.getContent())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("内容为空");
                    setField("Content");
                    setInvalidValue(t.getCommentatorId());
                }});
                flag = false;
            }
        }


        return flag;
    }
}
