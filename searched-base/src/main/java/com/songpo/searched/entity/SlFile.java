package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_file")
public class SlFile implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件大小（bytes）
     */
    private Integer size;
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 保存路径
     */
    private String folder;
    /**
     * 文件类型
     */
    @Column(name = "mime_type")
    private String mimeType;

    /**
     * 文件原名
     */
    @Column(name = "source_name")
    private String sourceName;

    /**
     * 标记类型
     */
    @Column(name = "mark_type")
    private String markType;

    /**
     * 标识值
     */
    @Column(name = "mark_value")
    private String markValue;

    /**
     * 文件的哈希验证字符串
     */
    private String hash;

    /**
     * 会员ID
     */
    private Integer userid;

    /**
     * 后台上传
     */
    @Column(name = "is_admin")
    private Boolean isAdmin;

    /**
     * 文件分类
     */
    private String classify;
    /**
     * 文件扩展名
     */
    private String extension;

    /**
     * 获取唯一标识符
     *
     * @return id - 唯一标识符
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置唯一标识符
     *
     * @param id 唯一标识符
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取文件名
     *
     * @return name - 文件名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置文件名
     *
     * @param name 文件名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取文件大小（bytes）
     *
     * @return size - 文件大小（bytes）
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设置文件大小（bytes）
     *
     * @param size 文件大小（bytes）
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 获取保存路径
     *
     * @return folder - 保存路径
     */
    public String getFolder() {
        return folder;
    }

    /**
     * 设置保存路径
     *
     * @param folder 保存路径
     */
    public void setFolder(String folder) {
        this.folder = folder == null ? null : folder.trim();
    }

    /**
     * 获取文件类型
     *
     * @return mime_type - 文件类型
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * 设置文件类型
     *
     * @param mimeType 文件类型
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType == null ? null : mimeType.trim();
    }

    /**
     * 获取文件扩展名
     *
     * @return extension - 文件扩展名
     */
    public String getExtension() {
        return extension;
    }

    /**
     * 设置文件扩展名
     *
     * @param extension 文件扩展名
     */
    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
    }

    /**
     * 获取文件原名
     *
     * @return source_name - 文件原名
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * 设置文件原名
     *
     * @param sourceName 文件原名
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName == null ? null : sourceName.trim();
    }

    /**
     * 获取标记类型
     *
     * @return mark_type - 标记类型
     */
    public String getMarkType() {
        return markType;
    }

    /**
     * 设置标记类型
     *
     * @param markType 标记类型
     */
    public void setMarkType(String markType) {
        this.markType = markType == null ? null : markType.trim();
    }

    /**
     * 获取标识值
     *
     * @return mark_value - 标识值
     */
    public String getMarkValue() {
        return markValue;
    }

    /**
     * 设置标识值
     *
     * @param markValue 标识值
     */
    public void setMarkValue(String markValue) {
        this.markValue = markValue == null ? null : markValue.trim();
    }

    /**
     * 获取文件的哈希验证字符串
     *
     * @return hash - 文件的哈希验证字符串
     */
    public String getHash() {
        return hash;
    }

    /**
     * 设置文件的哈希验证字符串
     *
     * @param hash 文件的哈希验证字符串
     */
    public void setHash(String hash) {
        this.hash = hash == null ? null : hash.trim();
    }

    /**
     * 获取会员ID
     *
     * @return userid - 会员ID
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * 设置会员ID
     *
     * @param userid 会员ID
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * 获取后台上传
     *
     * @return is_admin - 后台上传
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * 设置后台上传
     *
     * @param isAdmin 后台上传
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * 获取文件分类
     *
     * @return classify - 文件分类
     */
    public String getClassify() {
        return classify;
    }

    /**
     * 设置文件分类
     *
     * @param classify 文件分类
     */
    public void setClassify(String classify) {
        this.classify = classify == null ? null : classify.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", size=").append(size);
        sb.append(", folder=").append(folder);
        sb.append(", mimeType=").append(mimeType);
        sb.append(", extension=").append(extension);
        sb.append(", sourceName=").append(sourceName);
        sb.append(", markType=").append(markType);
        sb.append(", markValue=").append(markValue);
        sb.append(", hash=").append(hash);
        sb.append(", userid=").append(userid);
        sb.append(", isAdmin=").append(isAdmin);
        sb.append(", classify=").append(classify);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}