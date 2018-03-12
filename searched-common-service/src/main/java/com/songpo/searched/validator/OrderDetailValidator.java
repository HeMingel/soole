package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlOrderDetail;
import com.songpo.searched.service.OrderDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderDetailValidator extends ValidatorHandler<SlOrderDetail> {
    private static final Logger logger = LoggerFactory.getLogger(OrderDetailValidator.class);

    private OrderDetailService service;

    public OrderDetailValidator(OrderDetailService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlOrderDetail t) {
        logger.debug("执行校验：实体信息[{}]", t);

        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("订单明细为空");
                setField("OrderDetail");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getOrderId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("订单Id为空");
                    setField("OrderId");
                    setInvalidValue(t.getOrderId());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getShopId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("店铺ID为空");
                    setField("ShopId");
                    setInvalidValue(t.getShopId());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getProductId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("商品ID为空");
                    setField("ProductId");
                    setInvalidValue(t.getProductId());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getAmount())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("总额ID为空");
                    setField("Amount");
                    setInvalidValue(t.getAmount());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getPrice())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("价格为空");
                    setField("Price");
                    setInvalidValue(t.getPrice());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getCreator())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("创建人为空");
                    setField("Creator");
                    setInvalidValue(t.getCreator());
                }});
                flag = false;
            }
        }
        if (flag) {
            try {
                t.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } catch (Exception e) {
                logger.error("校验失败：{}", e);

                context.addError(new ValidationError() {{
                    setErrorMsg("校验失败：" + e.getMessage());
                    setField("OrderDetail");
                    setInvalidValue(t);
                }});
                flag = false;
            }
        }
        return flag;
    }
}
