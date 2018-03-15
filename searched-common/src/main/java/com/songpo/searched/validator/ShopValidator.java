package com.songpo.searched.validator;


import com.alibaba.druid.util.StringUtils;
import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlShop;
import com.songpo.searched.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * 商铺信息校验器
 *
 * @author Y.H
 */
public class ShopValidator extends ValidatorHandler<SlShop> {

    private static final Logger logger = LoggerFactory.getLogger(ShopValidator.class);

    private ShopService service;

    public ShopValidator(ShopService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlShop t) {
        logger.debug("执行校验：实体信息[{}]", t);

        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("店铺信息为空");
                setField("userInfo");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getName())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("店铺名称为空");
                    setField("name");
                    setInvalidValue(t.getName());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getAddress())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("商铺地址为空");
                    setField("address");
                    setInvalidValue(t.getAddress());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getDescription())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("商铺简介为空");
                    setField("description");
                    setInvalidValue(t.getDescription());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getImageUrl())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("商铺图片为空");
                    setField("imageUrl");
                    setInvalidValue(t.getDescription());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getOwnerId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("店主Id为空");
                    setField("ownerId");
                    setInvalidValue(t.getOwnerId());
                }});
                flag = false;
            }
        }

        // 校验是否存在，如果不存在，则进行初始化工作
        if (flag) {
            try {
                // 初始化唯一查询条件
                SlShop item = new SlShop();
                item.setName(t.getName());

                // 查询数量
                int count = this.service.selectCount(item);

                // 判断数量是否大于0，大于0则为已存在
                if (count > 0) {
                    context.addError(new ValidationError() {{
                        setErrorMsg("店铺名称已存在");
                        setField("name");
                        setInvalidValue(t.getName());
                    }});
                    flag = false;
                } else {
                    // 设置创建时间
                    t.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
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
