package com.songpo.searched.service;

import com.songpo.searched.entity.SlSystemConfig;
import com.songpo.searched.mapper.SlSystemConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class FileService {

    public static final String UPLOAD_FILE_DIR_KEY = "UPLOAD_FILE_DIR";
    @Autowired
    private SlSystemConfigMapper configMapper;

    public String upload(String type, MultipartFile file) {
        // 初始化图片路径
        String fileUrl = "";

        // 获取配置项
        SlSystemConfig config = this.configMapper.selectOne(new SlSystemConfig() {{
            setName(UPLOAD_FILE_DIR_KEY);
        }});

        // 如果配置项为空，则直接返回
        if (null == config) {
            log.error("文件上传存储目录为空");
            return fileUrl;
        }

        // 获取文件存储目录
        String dir = config.getContent();

        // 如果目录为空，则直接返回
        if (StringUtils.isEmpty(dir)) {
            log.error("文件上传存储目录为空");
            return fileUrl;
        }

        // 初始化文件目录
        File uploadDir = new File(dir);

        // 初始化目录是否准备完成标识
        boolean dirReady = true;

        // 如果目录不存在，则创建目录
        if (!uploadDir.exists()) {
            dirReady = uploadDir.mkdirs();
        }

        if (dirReady) {

        }

        if (StringUtils.isEmpty(type)) {
            type = "other";
        }

        // 格式化日期
        String dateDir = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-DD"));

        // 处理全文件名
        String fullPath = dir;
        if (!dir.endsWith(File.pathSeparator)) {
            fullPath = fullPath + File.pathSeparator + type + File.pathSeparator + dateDir;
        }

        // 保存文件
        try {
            FileCopyUtils.copy(file.getBytes(), new File(fullPath));
        } catch (IOException e) {
            log.error("创建文件失败，来源文件：{}，目标文件：{}，{}", file.getOriginalFilename(), fullPath, e);
        }

        return fileUrl;
    }
}
