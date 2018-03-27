package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_system_email_template")
public class SlSystemEmailTemplate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板变量
     */
    private String var;

    /**
     * 模板标题
     */
    private String title;

    /**
     * 模板内容
     */
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
     * 获取模板名称
     *
     * @return name - 模板名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置模板名称
     *
     * @param name 模板名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取模板变量
     *
     * @return var - 模板变量
     */
    public String getVar() {
        return var;
    }

    /**
     * 设置模板变量
     *
     * @param var 模板变量
     */
    public void setVar(String var) {
        this.var = var == null ? null : var.trim();
    }

    /**
     * 获取模板标题
     *
     * @return title - 模板标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置模板标题
     *
     * @param title 模板标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取模板内容
     *
     * @return content - 模板内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置模板内容
     *
     * @param content 模板内容
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
        sb.append(", var=").append(var);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}