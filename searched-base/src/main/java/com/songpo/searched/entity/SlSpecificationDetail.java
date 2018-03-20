package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_specification_detail")
public class SlSpecificationDetail implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 名称，比如颜色中的某一种，红
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 规格唯一标识符
     */
    @Column(name = "specification_id")
    private String specificationId;

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
     * 获取名称，比如颜色中的某一种，红
     *
     * @return name - 名称，比如颜色中的某一种，红
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称，比如颜色中的某一种，红
     *
     * @param name 名称，比如颜色中的某一种，红
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取规格唯一标识符
     *
     * @return specification_id - 规格唯一标识符
     */
    public String getSpecificationId() {
        return specificationId;
    }

    /**
     * 设置规格唯一标识符
     *
     * @param specificationId 规格唯一标识符
     */
    public void setSpecificationId(String specificationId) {
        this.specificationId = specificationId == null ? null : specificationId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", remark=").append(remark);
        sb.append(", specificationId=").append(specificationId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}