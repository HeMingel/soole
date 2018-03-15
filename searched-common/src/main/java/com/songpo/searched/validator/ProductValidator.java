package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlProduct;
import com.songpo.searched.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProductValidator extends ValidatorHandler<SlProduct> {

    private static final Logger logger = LoggerFactory.getLogger(SlProduct.class);

    private ProductService service;

    public ProductValidator(ProductService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlProduct t) {
        logger.debug("执行校验：实体信息[{}]", t);
        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("Product该实体为空");
                setField("SlProduct");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getImageUrl())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("商品图片为空");
                    setField("imageUrl");
                    setInvalidValue(t.getImageUrl());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getName())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("商品名称为空");
                    setField("name");
                    setInvalidValue(t.getName());
                }});
                flag = false;
            }
        }
        // 校验是否存在，如果不存在，则进行初始化工作
        if (flag) {
            try {
                // 设置创建时间
                t.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } catch (Exception e) {
                logger.error("校验失败：{}", e);

                context.addError(new ValidationError() {{
                    setErrorMsg("校验失败：" + e.getMessage());
                    setField("SlProduct-CreateTime");
                    setInvalidValue(t);
                }});
                flag = false;
            }
        }
        return flag;
    }
}
