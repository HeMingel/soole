package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_transaction_detail")
public class SlTransactionDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 来源id，
     */
    @Column(name = "source_id")
    private String sourceId;

    /**
     * 目标id
     */
    @Column(name = "target_id")
    private String targetId;

    /**
     * 红包id
     */
    @Column(name = "red_packet_id")
    private String redPacketId;

    /**
     * 消费方式   1.转账  2.红包
     */
    private Boolean type;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取来源id，
     *
     * @return source_id - 来源id，
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * 设置来源id，
     *
     * @param sourceId 来源id，
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    /**
     * 获取目标id
     *
     * @return target_id - 目标id
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * 设置目标id
     *
     * @param targetId 目标id
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    /**
     * 获取红包id
     *
     * @return red_packet_id - 红包id
     */
    public String getRedPacketId() {
        return redPacketId;
    }

    /**
     * 设置红包id
     *
     * @param redPacketId 红包id
     */
    public void setRedPacketId(String redPacketId) {
        this.redPacketId = redPacketId == null ? null : redPacketId.trim();
    }

    /**
     * 获取消费方式   1.转账  2.红包
     *
     * @return type - 消费方式   1.转账  2.红包
     */
    public Boolean getType() {
        return type;
    }

    /**
     * 设置消费方式   1.转账  2.红包
     *
     * @param type 消费方式   1.转账  2.红包
     */
    public void setType(Boolean type) {
        this.type = type;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sourceId=").append(sourceId);
        sb.append(", targetId=").append(targetId);
        sb.append(", redPacketId=").append(redPacketId);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}