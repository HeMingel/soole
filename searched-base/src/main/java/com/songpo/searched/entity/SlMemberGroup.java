package com.songpo.searched.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sl_member_group")
public class SlMemberGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 组名称
     */
    private String name;
    /**
     * 会员组标识
     */
    private String var;

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
     * 获取组名称
     *
     * @return name - 组名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置组名称
     *
     * @param name 组名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取会员组标识
     *
     * @return var - 会员组标识
     */
    public String getVar() {
        return var;
    }

    /**
     * 设置会员组标识
     *
     * @param var 会员组标识
     */
    public void setVar(String var) {
        this.var = var == null ? null : var.trim();
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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}