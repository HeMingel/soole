package com.songpo.searched.service;

import com.songpo.searched.entity.SlFileInfo;
import com.songpo.searched.entity.SlSystemConfig;
import com.songpo.searched.mapper.SlFileInfoMapper;
import com.songpo.searched.mapper.SlSystemConfigMapper;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author 刘松坡
 */
@Service
public class FileService {

    public static final Logger log = LoggerFactory.getLogger(FileService.class);

    private static final String UPLOAD_FILE_DIR_KEY = "UPLOAD_FILE_DIR";

    private static final String BASE_FILE_DOMAIN = "BASE_FILE_DOMAIN";

    private final SlSystemConfigMapper configMapper;

    private final SlFileInfoMapper fileMapper;

    @Autowired
    public FileService(SlSystemConfigMapper configMapper, SlFileInfoMapper fileMapper) {
        this.configMapper = configMapper;
        this.fileMapper = fileMapper;
    }

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
        try {
            SlFileInfo fileInfo = this.fileMapper.selectOne(new SlFileInfo() {{
                setTargetName(name);
            }});

            // 解决中文文件名乱码关键行
            String fileName = URLEncoder.encode(fileInfo.getSourceName(), StandardCharsets.UTF_8.toString());

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileInfo.getContentType()))
                    .contentLength(fileInfo.getSize())
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName)
                    .body(FileUtils.readFileToByteArray(new File(fileInfo.getPath())));
        } catch (IOException e) {
            log.error("下载文件失败，{}", e);
        }
        return null;
    }
}
