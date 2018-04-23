package com.songpo.searched.controller;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘松坡
 */
@Api(description = "文件管理")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v1/files")
public class FileController {

    public static final Logger log = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param type 文件类型，例如头像、商品图片等
     * @param file 文件
     * @return
     */
    @ApiOperation(value = "文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型", paramType = "form"),
            @ApiImplicitParam(name = "file", value = "文件", paramType = "form", dataType = "java.io.File")
    })
    @PostMapping
    public BusinessMessage<Map<String, String>> upload(String type, MultipartFile file) {
        log.debug("文件上传：名称：{}，类型：{}，大小：{}", file.getOriginalFilename(), file.getContentType(), file.getSize());
        BusinessMessage<Map<String, String>> message = new BusinessMessage<>();
        try {
            String url = this.fileService.upload(type, file);

            message.setSuccess(StringUtils.isEmpty(url));
            message.setData(new HashMap<String, String>(1) {{
                put("fileUrl", url);
            }});
        } catch (Exception e) {
            log.error("文件上传失败，{}", e);
        }
        return message;
    }

    /**
     * 文件上传
     * 如果是图片则会返回宽度和高度
     * 文件路径：imageUrl
     * 图片宽度：width
     * 图片高度：height
     *
     * @param type 文件类型，例如头像、商品图片等
     * @param file 文件
     * @return 商品上传结果
     */
    @ApiOperation(value = "文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型", paramType = "form"),
            @ApiImplicitParam(name = "file", value = "文件", paramType = "form", dataType = "java.io.File")
    })
    @PostMapping("new")
    public BusinessMessage<JSONObject> newUpload(String type, MultipartFile file) {
        log.debug("文件上传：名称：{}，类型：{}，大小：{}", file.getOriginalFilename(), file.getContentType(), file.getSize());
        BusinessMessage<JSONObject> message = new BusinessMessage<>();
        try {
            JSONObject data = this.fileService.newUpload(type, file);

            message.setSuccess(true);
            message.setData(data);
        } catch (Exception e) {
            log.error("文件上传失败，{}", e);
        }
        return message;
    }

    /**
     * 文件下载
     *
     * @param name 文件名称
     */
    @ApiOperation(value = "文件下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "文件名称", paramType = "path"),
            @ApiImplicitParam(name = "width", value = "图片宽度，如果文件为图片，则此参数会配合图片高度，生成一个新的缩略图返回", paramType = "path"),
            @ApiImplicitParam(name = "height", value = "图片高度，如果文件为图片，则此参数会配合图片宽度，生成一个新的缩略图返回", paramType = "path")
    })
    @GetMapping("{name:.*}")
    public ResponseEntity<byte[]> download(@PathVariable String name, Integer width, Integer height) {
        log.debug("文件下载：名称：{}，宽度：{}，高度：{}", name, width, height);
        return this.fileService.download(name, width, height);
    }
}
