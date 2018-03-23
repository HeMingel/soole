package com.songpo.searched.validator;


import com.alibaba.druid.util.StringUtils;
import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlSalesMode;
import com.songpo.searched.service.SalesModeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 商铺信息校验器
 *
 * @author Y.H
 */
public class SalesModeValidator extends ValidatorHandler<SlSalesMode> {

    private static final Logger logger = LoggerFactory.getLogger(SalesModeValidator.class);

    private SalesModeService service;

    public SalesModeValidator(SalesModeService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlSalesMode t) {
        logger.debug("执行校验：实体信息[{}]", t);

        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("销售模式信息为空");
                setField("userInfo");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getName())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("销售模式名称为空");
                    setField("name");
                    setInvalidValue(t.getName());
                }});
                flag = false;
            }
        }

        // 校验是否存在，如果不存在，则进行初始化工作
        if (flag) {
            try {
                // 初始化唯一查询条件
                SlSalesMode item = new SlSalesMode();
                item.setName(t.getName());

                // 查询数量
                int count = this.service.selectCount(item);

                // 判断数量是否大于0，大于0则为已存在
                if (count > 0) {
                    context.addError(new ValidationError() {{
                        setErrorMsg("销售模式名称已存在");
                        setField("name");
                        setInvalidValue(t.getName());
                    }});
                    flag = false;
                }
            } catch (Exception e) {
                logger.error("校验失败：{}", e);

                context.addError(new ValidationError() {{
                    setErrorMsg("校验失败：" + e.getMessage());
                    setField("shopInfo");
                    setInvalidValue(t);
                }});
                flag = false;
            }
        }

        return flag;
    }

}
