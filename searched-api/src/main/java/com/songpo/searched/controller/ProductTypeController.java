package com.songpo.searched.controller;


import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlProductType;
import com.songpo.searched.service.ProductTypeService;
import com.songpo.searched.validator.ProductTypeValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Y.H
 */
@Api(description = "商品类型管理")
@CrossOrigin
@RestController
@RequestMapping("/api/v2/goods-type")
public class ProductTypeController extends BaseController<SlProductType, String> {

    private Logger logger = LoggerFactory.getLogger(ProductTypeController.class);

    @Autowired
    private ProductTypeService productTypeService;

    public ProductTypeController(ProductTypeService service) {
        // 设置业务服务类
        super.service = service;
        // 设置实体校验器
        super.validatorHandler = new ProductTypeValidator(service);
    }

    /**
     * 搜索一级分类
     *
     * @return
     */
    @ApiOperation(value = "搜索一级分类")
    @RequestMapping(value = "/find-category", method = RequestMethod.POST)
    @ApiImplicitParams(value = {

    })
    public BusinessMessage findCategory() {
        return this.productTypeService.findCategory();
    }

    /**
     * 通过父ID 搜索二级分类
     *
     * @param parentId
     * @return
     */
    @ApiOperation(value = "通过父ID 搜索二级分类")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "parentId", value = "商品名称", paramType = "form", required = true)
    })
    @RequestMapping(value = "/find-category-by-parentId", method = RequestMethod.POST)
    public BusinessMessage findCategoryByParentId(String parentId) {
        if (parentId == null) {
            BusinessMessage businessMessage = new BusinessMessage();
            businessMessage.setSuccess(false);
            businessMessage.setMsg("父ID为空!");
            return businessMessage;
        }
        return this.productTypeService.findCategoryByParentId(parentId);
    }
}
