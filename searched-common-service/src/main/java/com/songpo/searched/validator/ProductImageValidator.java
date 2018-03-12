package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlProductImage;
import com.songpo.searched.service.ProductImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * 标签信息校验器
 *
 * @author Y.H
 */
public class ProductImageValidator extends ValidatorHandler<SlProductImage> {

    private static final Logger logger = LoggerFactory.getLogger(ProductImageValidator.class);

    private ProductImageService service;

    public ProductImageValidator(ProductImageService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlProductImage t) {
        logger.debug("执行校验：实体信息[{}]", t);

        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("商品图片为空");
                setField("ProductImageInfo");
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
            } else if (StringUtils.isEmpty(t.getImageUrl())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("商品图片地址为空");
                    setField("ImageUrl()");
                    setInvalidValue(t.getImageUrl());
                }});
                flag = false;
            }
        }


        return flag;
    }
}
