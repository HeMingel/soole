package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_specification_detail_repository")
public class SlSpecificationDetailRepository implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 规格明细唯一标识符
     */
    @Column(name = "specification_detail_id")
    private String specificationDetailId;

    /**
     * 库存唯一标识符
     */
    @Column(name = "repository_id")
    private String repositoryId;

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
     * 获取规格明细唯一标识符
     *
     * @return specification_detail_id - 规格明细唯一标识符
     */
    public String getSpecificationDetailId() {
        return specificationDetailId;
    }

    /**
     * 设置规格明细唯一标识符
     *
     * @param specificationDetailId 规格明细唯一标识符
     */
    public void setSpecificationDetailId(String specificationDetailId) {
        this.specificationDetailId = specificationDetailId == null ? null : specificationDetailId.trim();
    }

    /**
     * 获取库存唯一标识符
     *
     * @return repository_id - 库存唯一标识符
     */
    public String getRepositoryId() {
        return repositoryId;
    }

    /**
     * 设置库存唯一标识符
     *
     * @param repositoryId 库存唯一标识符
     */
    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId == null ? null : repositoryId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", specificationDetailId=").append(specificationDetailId);
        sb.append(", repositoryId=").append(repositoryId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}