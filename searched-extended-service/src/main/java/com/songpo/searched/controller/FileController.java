package com.songpo.searched.controller;

import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Api(description = "文件管理")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上次图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "文件", required = true, paramType = "body")
    })
    @PostMapping
    public BusinessMessage<Map<String, String>> upload(String type, MultipartFile file) {
        String url = this.fileService.upload(type, file);
        BusinessMessage<Map<String, String>> message = new BusinessMessage<>();
        message.setSuccess(StringUtils.isEmpty(url));
        message.setData(new HashMap<String, String>(1) {{
            put("url", url);
        }});
        return message;
    }
}
