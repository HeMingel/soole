package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.SlOrder;
import com.songpo.searched.entity.SlOrderExtend;
import com.songpo.searched.entity.SlSharingLinks;
import com.songpo.searched.entity.SlUser;
import com.songpo.searched.mapper.SlOrderExtendMapper;
import com.songpo.searched.mapper.SlSharingLinksMapper;
import com.songpo.searched.service.CmSharingLinksService;
import com.songpo.searched.service.ProductService;
import com.songpo.searched.service.UserService;
import com.songpo.searched.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * 分享链接
 * @author  heming
 * @Date 2018年6月25日11:51:11
 */
@Api(description = "分享链接管理")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v2/sharing-links")
public class SharingLinksController {
    @Autowired
    private CmSharingLinksService cmSharingLinksService;
    @Autowired
    private SlSharingLinksMapper slSharingLinksMapper;
    /**
     * /add 添加分享链接记录
     * @param userId
     * @param productId
     * @return
     */
    @ApiOperation(value = "添加分享链接记录")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "userId", value = "用户ID", paramType = "String", required = true),
            @ApiImplicitParam(name = "productId", value = "产品ID", paramType = "String", required = true)
    })
    @PostMapping("insert")
    public BusinessMessage insert(String userId,String productId) {
        BusinessMessage message = cmSharingLinksService.insert(userId,productId);
        return message;
    }


    public BusinessMessage listByuserId(String userId) {
        BusinessMessage message = cmSharingLinksService.listByUserId(userId);
        return message;
    }

    @Autowired
    private UserService userService;
    @Autowired
    private SlOrderExtendMapper slOrderExtendMapper;
    @GetMapping("test")
    public void Test(){
        /*SlUser user = userService.selectOne(new SlUser(){{
            setPhone("17611235811");
        }}) ;
*/
        slOrderExtendMapper.insertSelective(new SlOrderExtend(){{
            setServiceId("cd9d566f792311e886c57cd30abeb1d8");
            setServiceType((byte)1);
            setOrderId(String.valueOf(UUID.randomUUID()));
        }});

    }
}
