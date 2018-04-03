package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "sl_money_log")
public class SlMoneyLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "transaction_id")
    private String transactionId;

    private BigDecimal money;

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
     * @return transaction_id
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    /**
     * @return money
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * @param money
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
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
        sb.append(", money=").append(money);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}