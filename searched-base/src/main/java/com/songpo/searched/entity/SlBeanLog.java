package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}