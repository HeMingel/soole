package com.songpo.searched.validator;


import com.alibaba.druid.util.StringUtils;
import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlProductType;
import com.songpo.searched.service.ProductTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 商铺信息校验器
 *
 * @author Y.H
 */
public class ProductTypeValidator extends ValidatorHandler<SlProductType> {

    private static final Logger logger = LoggerFactory.getLogger(ProductTypeValidator.class);

    private ProductTypeService service;

    public ProductTypeValidator(ProductTypeService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlProductType t) {
        logger.debug("执行校验：实体信息[{}]", t);

        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("商铺类别为空");
                setField("productTypeInfo");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getName())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("分类名称为空");
                    setField("name");
                    setInvalidValue(t.getName());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getTagId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("标签Id为空");
                    setField("tagId");
                    setInvalidValue(t.getTagId());
                }});
                flag = false;
            }
        }

        // 校验是否存在，如果不存在，则进行初始化工作
        if (flag) {
            try {
                // 初始化唯一查询条件
                SlProductType item = new SlProductType();
                item.setName(t.getName());

                // 查询数量
                int count = this.service.selectCount(item);

                // 判断数量是否大于0，大于0则为已存在
                if (count > 0) {
                    context.addError(new ValidationError() {{
                        setErrorMsg("商品类别名称已存在");
                        setField("name");
                        setInvalidValue(t.getName());
                    }});
                    flag = false;
                } else {
                    /*t.setSecret(UUID.randomUUID().toString());*/
                    // 设置创建时间
                    /*t.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));*/
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
