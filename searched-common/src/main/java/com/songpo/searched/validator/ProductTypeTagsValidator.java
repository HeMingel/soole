package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlProductTypeTags;
import com.songpo.searched.service.ProductTypeTagsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * 标签信息校验器
 *
 * @author Y.H
 */
public class ProductTypeTagsValidator extends ValidatorHandler<SlProductTypeTags> {

    private static final Logger logger = LoggerFactory.getLogger(ProductTypeTagsValidator.class);

    private ProductTypeTagsService service;

    public ProductTypeTagsValidator(ProductTypeTagsService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlProductTypeTags t) {
        logger.debug("执行校验：实体信息[{}]", t);

        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("商品标签类为空");
                setField("productTypeTagsInfo");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getProductTagId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("商品标签Id为空");
                    setField("ProductTagId");
                    setInvalidValue(t.getProductTagId());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getProductTypeId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("商品类别为空");
                    setField("ProductTypeId");
                    setInvalidValue(t.getProductTypeId());
                }});
                flag = false;
            }
        }


        return flag;
    }
}
