package com.songpo.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.entity.SlProductComment;
import com.songpo.entity.SlProductCommentImage;
import com.songpo.service.ProductCommentImageService;
import com.songpo.service.ProductCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * 标签信息校验器
 *
 * @author Y.H
 */
public class ProductCommentImageValidator extends ValidatorHandler<SlProductCommentImage> {

    private static final Logger logger = LoggerFactory.getLogger(ProductCommentImageValidator.class);

    private ProductCommentImageService service;

    public ProductCommentImageValidator(ProductCommentImageService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlProductCommentImage t) {
        logger.debug("执行校验：实体信息[{}]", t);

        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("商品评论图片为空");
                setField("ProductCommentInfo");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getProductCommentId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("商品评论id为空");
                    setField("ProductId");
                    setInvalidValue(t.getProductCommentId());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getImageUrl())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("商品评论图片为空");
                    setField("CommentatorId");
                    setInvalidValue(t.getImageUrl());
                }});
                flag = false;
            }
        }


        return flag;
    }
}
