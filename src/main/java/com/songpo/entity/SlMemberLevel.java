package com.songpo.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sl_member_level")
public class SlMemberLevel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 等级名称
     */
    private String name;
    /**
     * 该等级范围最大值
     */
    private Integer maximum;
    /**
     * 等级描述
     */
    private String descriptional;

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
     * 获取等级名称
     *
     * @return name - 等级名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置等级名称
     *
     * @param name 等级名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取该等级范围最大值
     *
     * @return maximum - 该等级范围最大值
     */
    public Integer getMaximum() {
        return maximum;
    }

    /**
     * 设置该等级范围最大值
     *
     * @param maximum 该等级范围最大值
     */
    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    /**
     * 获取等级描述
     *
     * @return descriptional - 等级描述
     */
    public String getDescriptional() {
        return descriptional;
    }

    /**
     * 设置等级描述
     *
     * @param descriptional 等级描述
     */
    public void setDescriptional(String descriptional) {
        this.descriptional = descriptional == null ? null : descriptional.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", maximum=").append(maximum);
        sb.append(", descriptional=").append(descriptional);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}