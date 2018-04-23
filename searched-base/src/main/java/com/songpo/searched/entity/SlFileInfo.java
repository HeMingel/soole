package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_file_info")
public class SlFileInfo implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 原始文件名
     */
    @Column(name = "source_name")
    private String sourceName;

    /**
     * 目标文件名
     */
    @Column(name = "target_name")
    private String targetName;

    /**
     * 文件类型
     */
    @Column(name = "content_type")
    private String contentType;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;

    private static final long serialVersionUID = 1L;

    /**
     * 获取唯一标识符
     *
     * @return id - 唯一标识符
     */
    public String getId() {
        return id;
    }

    /**
     * 设置唯一标识符
     *
     * @param id 唯一标识符
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取原始文件名
     *
     * @return source_name - 原始文件名
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * 设置原始文件名
     *
     * @param sourceName 原始文件名
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName == null ? null : sourceName.trim();
    }

    /**
     * 获取目标文件名
     *
     * @return target_name - 目标文件名
     */
    public String getTargetName() {
        return targetName;
    }

    /**
     * 设置目标文件名
     *
     * @param targetName 目标文件名
     */
    public void setTargetName(String targetName) {
        this.targetName = targetName == null ? null : targetName.trim();
    }

    /**
     * 获取文件类型
     *
     * @return content_type - 文件类型
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * 设置文件类型
     *
     * @param contentType 文件类型
     */
    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    /**
     * 获取文件大小
     *
     * @return size - 文件大小
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设置文件大小
     *
     * @param size 文件大小
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 获取文件路径
     *
     * @return path - 文件路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置文件路径
     *
     * @param path 文件路径
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * 获取图片宽度
     *
     * @return width - 图片宽度
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * 设置图片宽度
     *
     * @param width 图片宽度
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * 获取图片高度
     *
     * @return height - 图片高度
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * 设置图片高度
     *
     * @param height 图片高度
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sourceName=").append(sourceName);
        sb.append(", targetName=").append(targetName);
        sb.append(", contentType=").append(contentType);
        sb.append(", size=").append(size);
        sb.append(", path=").append(path);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}