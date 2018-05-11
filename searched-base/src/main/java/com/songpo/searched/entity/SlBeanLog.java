package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_bean_log")
public class SlBeanLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 总交易明细id
     */
    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * 豆数量
     */
    private Integer bean;

    /**
     * 支出收入   1.支出  2.收入
     */
    private Boolean type;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 最后更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取总交易明细id
     *
     * @return transaction_id - 总交易明细id
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * 设置总交易明细id
     *
     * @param transactionId 总交易明细id
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    /**
     * 获取豆数量
     *
     * @return bean - 豆数量
     */
    public Integer getBean() {
        return bean;
    }

    /**
     * 设置豆数量
     *
     * @param bean 豆数量
     */
    public void setBean(Integer bean) {
        this.bean = bean;
    }

    /**
     * 获取支出收入   1.支出  2.收入
     *
     * @return type - 支出收入   1.支出  2.收入
     */
    public Boolean getType() {
        return type;
    }

    /**
     * 设置支出收入   1.支出  2.收入
     *
     * @param type 支出收入   1.支出  2.收入
     */
    public void setType(Boolean type) {
        this.type = type;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取最后更新时间
     *
     * @return updated_at - 最后更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置最后更新时间
     *
     * @param updatedAt 最后更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", transactionId=").append(transactionId);
        sb.append(", bean=").append(bean);
        sb.append(", type=").append(type);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}