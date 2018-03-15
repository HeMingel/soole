package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlRepository;
import com.songpo.searched.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * 标签信息校验器
 *
 * @author Y.H
 */
public class RepositoryValidator extends ValidatorHandler<SlRepository> {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryValidator.class);

    private RepositoryService service;

    public RepositoryValidator(RepositoryService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlRepository t) {
        logger.debug("执行校验：实体信息[{}]", t);

        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("店铺库存信息为空");
                setField("respositoryInfo");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getShopId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("店铺Id为空");
                    setField("shopId");
                    setInvalidValue(t.getShopId());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getProductId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("商品信息为空");
                    setField("productId");
                    setInvalidValue(t.getProductId());
                }});
            }
        }

        return flag;
    }
}
