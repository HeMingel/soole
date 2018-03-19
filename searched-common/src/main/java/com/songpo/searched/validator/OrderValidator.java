package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlOrder;
import com.songpo.searched.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderValidator extends ValidatorHandler<SlOrder> {
    private static final Logger logger = LoggerFactory.getLogger(OrderValidator.class);

    private OrderService service;

    public OrderValidator(OrderService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlOrder t) {
        logger.debug("执行校验：实体信息[{}]", t);
        boolean flag = true;
        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("订单信息为空");
                setField("order");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getUserId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("用户账号为空");
                    setField("getUserId");
                    setInvalidValue(t.getUserId());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getShippngAddressId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("收货地址为空");
                    setField("ShippingAddressId");
                    setInvalidValue(t.getShippngAddressId());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getTotalAmount())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("订单总金额为空");
                    setField("TotalAmount");
                    setInvalidValue(t.getTotalAmount());
                }});
                flag = false;
            } else if (StringUtils.isEmpty(t.getFee())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("手续费为空");
                    setField("Fee");
                    setInvalidValue(t.getFee());
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
                    setField("orderInfo");
                    setInvalidValue(t);
                }});
                flag = false;
            }
        }
        return flag;
    }
}
