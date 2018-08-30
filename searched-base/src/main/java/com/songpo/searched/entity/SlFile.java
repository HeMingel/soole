package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_file")
public class SlFile implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 名称
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 文件大小
     */
    private String size;

    /**
     * 宽（针对图片）
     */
    private String width;

    /**
     * 高（针对图片）
     */
    private String height;

    /**
     * 类型
     */
    private String type;

    /**
     * mime类型
     */
    private String mime;

    /**
     * 文件类型  1.图片  2.文件
     */
    @Column(name = "file_type")
    private Byte fileType;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取路径
     *
     * @return path - 路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置路径
     *
     * @param path 路径
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * 获取文件大小
     *
     * @return size - 文件大小
     */
    public String getSize() {
        return size;
    }

    /**
     * 设置文件大小
     *
     * @param size 文件大小
     */
    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    /**
     * 获取宽（针对图片）
     *
     * @return width - 宽（针对图片）
     */
    public String getWidth() {
        return width;
    }

    /**
     * 设置宽（针对图片）
     *
     * @param width 宽（针对图片）
     */
    public void setWidth(String width) {
        this.width = width == null ? null : width.trim();
    }

    /**
     * 获取高（针对图片）
     *
     * @return height - 高（针对图片）
     */
    public String getHeight() {
        return height;
    }

    /**
     * 设置高（针对图片）
     *
     * @param height 高（针对图片）
     */
    public void setHeight(String height) {
        this.height = height == null ? null : height.trim();
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 获取mime类型
     *
     * @return mime - mime类型
     */
    public String getMime() {
        return mime;
    }

    /**
     * 设置mime类型
     *
     * @param mime mime类型
     */
    public void setMime(String mime) {
        this.mime = mime == null ? null : mime.trim();
    }

    /**
     * 获取文件类型  1.图片  2.文件
     *
     * @return file_type - 文件类型  1.图片  2.文件
     */
    public Byte getFileType() {
        return fileType;
    }

    /**
     * 设置文件类型  1.图片  2.文件
     *
     * @param fileType 文件类型  1.图片  2.文件
     */
    public void setFileType(Byte fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", name=").append(name);
        sb.append(", path=").append(path);
        sb.append(", size=").append(size);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", type=").append(type);
        sb.append(", mime=").append(mime);
        sb.append(", fileType=").append(fileType);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}