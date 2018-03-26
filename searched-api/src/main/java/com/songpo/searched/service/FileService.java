package com.songpo.searched.service;

import com.songpo.searched.entity.SlFileInfo;
import com.songpo.searched.entity.SlSystemConfig;
import com.songpo.searched.mapper.SlFileInfoMapper;
import com.songpo.searched.mapper.SlSystemConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    public static final String UPLOAD_FILE_DIR_KEY = "UPLOAD_FILE_DIR";

    public static final String BASE_FILE_DOMAIN = "BASE_FILE_DOMAIN";

    @Autowired
    private SlSystemConfigMapper configMapper;

    @Autowired
    private SlFileInfoMapper fileMapper;

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

        if (StringUtils.isEmpty(type)) {
            type = "other";
        }

        // 格式化日期
        String dateDir = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 处理全文件名
        String fullPath = dir;
        if (!dir.endsWith("\\") || !dir.endsWith("/")) {
            fullPath = fullPath + File.separator + type + File.separator + dateDir;
        }

        // 保存文件
        try {
            // 文件后缀名
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

            // 目标文件名
            String fileName = UUID.randomUUID().toString() + "." + suffix;

            if (!Files.exists(Paths.get(fullPath))) {
                Files.createDirectories(Paths.get(fullPath));
            }

            // 写出文件
            Files.write(Paths.get(fullPath + File.separator + fileName), file.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

            SlFileInfo uploadFile = new SlFileInfo();
            uploadFile.setTargetName(fileName);
            uploadFile.setPath(fullPath + File.separator + fileName);
            uploadFile.setContentType(file.getContentType());
            uploadFile.setSize((int) file.getSize());
            uploadFile.setSourceName(file.getOriginalFilename());

            // 保持文件信息到数据库
            this.fileMapper.insertSelective(uploadFile);

            SlSystemConfig baseDomainConfig = this.configMapper.selectOne(new SlSystemConfig() {{
                setName(BASE_FILE_DOMAIN);
            }});

            if (null != baseDomainConfig && !StringUtils.isEmpty(baseDomainConfig.getContent())) {
                String domain = baseDomainConfig.getContent();
                if (domain.endsWith("/")) {
                    fileUrl = domain + uploadFile.getTargetName();
                } else {
                    fileUrl = domain + "/" + uploadFile.getTargetName();
                }
            }
        } catch (IOException e) {
            log.error("上传文件失败，来源文件：{}，目标文件：{}，{}", file.getOriginalFilename(), fullPath, e);
        }

        return fileUrl;
    }

    public ResponseEntity<byte[]> download(String name) {
        HttpHeaders headers = new HttpHeaders();

        SlFileInfo fileInfo = this.fileMapper.selectOne(new SlFileInfo() {{
            setTargetName(name);
        }});

        headers.add("Content-Length", fileInfo.getSize().toString());
        headers.add("Content-Type", fileInfo.getContentType());
        headers.add("Content-Disposition", "attchement;filename=" + fileInfo.getSourceName());

        byte[] bytes;
        ResponseEntity<byte[]> entity = null;
        try {
            bytes = Files.readAllBytes(Paths.get(fileInfo.getPath()));
            entity = new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            log.error("下载文件失败，{}", e);
        }
        return entity;
    }
}
