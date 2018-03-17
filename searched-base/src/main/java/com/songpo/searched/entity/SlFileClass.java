package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_file_class")
public class SlFileClass implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String var;

    private String type;

    @Column(name = "home_show")
    private Boolean homeShow;

    @Column(name = "admin_show")
    private Boolean adminShow;

    private Short myorder;

    private String suffix;

    private Integer size;

    @Column(name = "home_upload")
    private Boolean homeUpload;

    @Column(name = "home_login")
    private Boolean homeLogin;

    private String mimetype;

    @Column(name = "is_thumb")
    private Boolean isThumb;

    @Column(name = "thumb_type")
    private Byte thumbType;

    private Integer width;

    private Integer height;

    @Column(name = "delete_source")
    private Boolean deleteSource;

    private Boolean watermark;

    /**
     * 系统
     */
    @Column(name = "is_system")
    private Boolean isSystem;

    /**
     * 附加参数
     */
    private String params;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return var
     */
    public String getVar() {
        return var;
    }

    /**
     * @param var
     */
    public void setVar(String var) {
        this.var = var == null ? null : var.trim();
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return home_show
     */
    public Boolean getHomeShow() {
        return homeShow;
    }

    /**
     * @param homeShow
     */
    public void setHomeShow(Boolean homeShow) {
        this.homeShow = homeShow;
    }

    /**
     * @return admin_show
     */
    public Boolean getAdminShow() {
        return adminShow;
    }

    /**
     * @param adminShow
     */
    public void setAdminShow(Boolean adminShow) {
        this.adminShow = adminShow;
    }

    /**
     * @return myorder
     */
    public Short getMyorder() {
        return myorder;
    }

    /**
     * @param myorder
     */
    public void setMyorder(Short myorder) {
        this.myorder = myorder;
    }

    /**
     * @return suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    /**
     * @return size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * @return home_upload
     */
    public Boolean getHomeUpload() {
        return homeUpload;
    }

    /**
     * @param homeUpload
     */
    public void setHomeUpload(Boolean homeUpload) {
        this.homeUpload = homeUpload;
    }

    /**
     * @return home_login
     */
    public Boolean getHomeLogin() {
        return homeLogin;
    }

    /**
     * @param homeLogin
     */
    public void setHomeLogin(Boolean homeLogin) {
        this.homeLogin = homeLogin;
    }

    /**
     * @return mimetype
     */
    public String getMimetype() {
        return mimetype;
    }

    /**
     * @param mimetype
     */
    public void setMimetype(String mimetype) {
        this.mimetype = mimetype == null ? null : mimetype.trim();
    }

    /**
     * @return is_thumb
     */
    public Boolean getIsThumb() {
        return isThumb;
    }

    /**
     * @param isThumb
     */
    public void setIsThumb(Boolean isThumb) {
        this.isThumb = isThumb;
    }

    /**
     * @return thumb_type
     */
    public Byte getThumbType() {
        return thumbType;
    }

    /**
     * @param thumbType
     */
    public void setThumbType(Byte thumbType) {
        this.thumbType = thumbType;
    }

    /**
     * @return width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * @param width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * @return height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * @param height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * @return delete_source
     */
    public Boolean getDeleteSource() {
        return deleteSource;
    }

    /**
     * @param deleteSource
     */
    public void setDeleteSource(Boolean deleteSource) {
        this.deleteSource = deleteSource;
    }

    /**
     * @return watermark
     */
    public Boolean getWatermark() {
        return watermark;
    }

    /**
     * @param watermark
     */
    public void setWatermark(Boolean watermark) {
        this.watermark = watermark;
    }

    /**
     * 获取系统
     *
     * @return is_system - 系统
     */
    public Boolean getIsSystem() {
        return isSystem;
    }

    /**
     * 设置系统
     *
     * @param isSystem 系统
     */
    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    /**
     * 获取附加参数
     *
     * @return params - 附加参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置附加参数
     *
     * @param params 附加参数
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", var=").append(var);
        sb.append(", type=").append(type);
        sb.append(", homeShow=").append(homeShow);
        sb.append(", adminShow=").append(adminShow);
        sb.append(", myorder=").append(myorder);
        sb.append(", suffix=").append(suffix);
        sb.append(", size=").append(size);
        sb.append(", homeUpload=").append(homeUpload);
        sb.append(", homeLogin=").append(homeLogin);
        sb.append(", mimetype=").append(mimetype);
        sb.append(", isThumb=").append(isThumb);
        sb.append(", thumbType=").append(thumbType);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", deleteSource=").append(deleteSource);
        sb.append(", watermark=").append(watermark);
        sb.append(", isSystem=").append(isSystem);
        sb.append(", params=").append(params);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}