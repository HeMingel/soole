package com.songpo.searched.service;

import com.alibaba.fastjson.JSONObject;
import com.songpo.searched.entity.SlFileInfo;
import com.songpo.searched.entity.SlSystemConfig;
import com.songpo.searched.mapper.SlFileInfoMapper;
import com.songpo.searched.mapper.SlSystemConfigMapper;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
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

    private static List<String> IMAGE_TYPE_ARRAY = Arrays.asList("image/bmp", "image/jpg", "image/jpeg", "image/png");

    @Autowired
    public FileService(SlSystemConfigMapper configMapper, SlFileInfoMapper fileMapper) {
        this.configMapper = configMapper;
        this.fileMapper = fileMapper;
    }

    @Deprecated
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

    /**
     * 上传文件，并返回图片URL、图片名称和图片的宽度和高度
     *
     * @param type 文件类型，例如用于头像、商品等
     * @param file 文件
     * @return 文件URL、图片宽度、高度
     */
    public JSONObject newUpload(String type, MultipartFile file) {
        JSONObject data = new JSONObject();
        data.put("fileName", "");
        data.put("fileUrl", "");
        data.put("width", 0);
        data.put("height", 0);
        // 初始化图片路径
        String fileUrl = "";

        // 获取配置项
        SlSystemConfig config = this.configMapper.selectOne(new SlSystemConfig() {{
            setName(UPLOAD_FILE_DIR_KEY);
        }});

        // 如果配置项为空，则直接返回
        if (null == config) {
            log.error("文件上传存储目录为空");
            return data;
        }

        // 获取文件存储目录
        String dir = config.getContent();

        // 如果目录为空，则直接返回
        if (StringUtils.isEmpty(dir)) {
            log.error("文件上传存储目录为空");
            return data;
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

            // 缓存图片字节数组
            byte[] bytes = file.getBytes();

            // 检测文件是否为图片类型，如果有，就加上图片宽度和高度
            if (IMAGE_TYPE_ARRAY.contains(file.getContentType())) {
                BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
                data.replace("width", bufferedImage.getWidth());
                data.replace("height", bufferedImage.getHeight());
            }

            // 写出文件
            Files.write(Paths.get(fullPath + File.separator + fileName), bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

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
            data.replace("filePath", uploadFile.getTargetName());
        } catch (IOException e) {
            log.error("上传文件失败，来源文件：{}，目标文件：{}，{}", file.getOriginalFilename(), fullPath, e);
        }

        data.replace("fileUrl", fileUrl);

        return data;
    }

    /**
     * 下载文件
     *
     * @param name   文件名称
     * @param width  图片宽度
     * @param height 图片高度
     * @return 文件字节集合
     */
    public ResponseEntity<byte[]> download(String name, Integer width, Integer height) {
        try {
            SlFileInfo fileInfo = this.fileMapper.selectOne(new SlFileInfo() {{
                setTargetName(name);
            }});

            byte[] bytes;
            if (IMAGE_TYPE_ARRAY.contains(fileInfo.getContentType())) {
                if (null != width && null != height) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Thumbnails.of(Paths.get(fileInfo.getPath()).toFile()).size(width, height).toOutputStream(baos);
                    bytes = baos.toByteArray();
                } else {
                    bytes = FileUtils.readFileToByteArray(new File(fileInfo.getPath()));
                }
            } else {
                bytes = FileUtils.readFileToByteArray(new File(fileInfo.getPath()));
            }

            // 解决中文文件名乱码关键行
            String fileName = URLEncoder.encode(fileInfo.getSourceName(), StandardCharsets.UTF_8.toString());

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileInfo.getContentType()))
                    .contentLength(bytes.length)
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName)
                    .body(bytes);
        } catch (IOException e) {
            log.error("下载文件失败，{}", e);
        }
        return null;
    }
}
