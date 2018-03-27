package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_other")
public class SlOther implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 备注
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 系统配置
     */
    @Column(name = "is_system")
    private Boolean isSystem;

    private String content;

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
     * 获取备注
     *
     * @return name - 备注
     */
    public String getName() {
        return name;
    }

    /**
     * 设置备注
     *
     * @param name 备注
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * 获取系统配置
     *
     * @return is_system - 系统配置
     */
    public Boolean getIsSystem() {
        return isSystem;
    }

    /**
     * 设置系统配置
     *
     * @param isSystem 系统配置
     */
    public void setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", isSystem=").append(isSystem);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}