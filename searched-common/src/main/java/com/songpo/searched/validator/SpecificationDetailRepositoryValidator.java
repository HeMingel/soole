package com.songpo.searched.validator;

import com.baidu.unbiz.fluentvalidator.ValidationError;
import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.songpo.searched.entity.SlSpecificationDetail;
import com.songpo.searched.entity.SlSpecificationDetailRepository;
import com.songpo.searched.service.SpecificationDetailRepositoryService;
import com.songpo.searched.service.SpecificationDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class SpecificationDetailRepositoryValidator extends ValidatorHandler<SlSpecificationDetailRepository> {

    private static final Logger logger = LoggerFactory.getLogger(SlSpecificationDetailRepository.class);

    private SpecificationDetailRepositoryService service;

    public SpecificationDetailRepositoryValidator(SpecificationDetailRepositoryService service) {
        this.service = service;
    }

    @Override
    public boolean validate(ValidatorContext context, SlSpecificationDetailRepository t) {
        logger.debug("执行校验：实体信息[{}]", t);
        // 初始化验证标识
        boolean flag = true;

        // 校验实体是否为空
        if (null == t) {
            context.addError(new ValidationError() {{
                setErrorMsg("SpecificationDetailRepository该实体为空");
                setField("SlSpecificationDetailRepository");
                setInvalidValue(t);
            }});
            flag = false;
        } else {
            // 校验字段
            if (StringUtils.isEmpty(t.getId())) {
                context.addError(new ValidationError() {{
                    setErrorMsg("id为空");
                    setField("id");
                    setInvalidValue(t.getId());
                }});
                flag = false;
            } else if(StringUtils.isEmpty(t.getRepositoryId())){
                context.addError(new ValidationError() {{
                    setErrorMsg("RepositoryId为空");
                    setField("repositoryId");
                    setInvalidValue(t.getRepositoryId());
                }});
                flag = false;
            } else if(StringUtils.isEmpty(t.getSpecificationDetailId())){
                context.addError(new ValidationError() {{
                    setErrorMsg("SpecificationDetailId为空");
                    setField("specificationDetailId");
                    setInvalidValue(t.getSpecificationDetailId());
                }});
                flag = false;
            }
        }
        return flag;
    }
}
