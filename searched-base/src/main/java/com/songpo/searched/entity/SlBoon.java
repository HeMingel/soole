package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_boon")
public class SlBoon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 福利类型 1.大转盘  2.刮刮乐
     */
    private Byte type;

    /**
     * 该活动是否启用  1.启用  2.不启用
     */
    private Byte result;

    /**
     * 活动开始时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 活动结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 数据每次更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取活动名称
     *
     * @return name - 活动名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置活动名称
     *
     * @param name 活动名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取福利类型 1.大转盘  2.刮刮乐
     *
     * @return type - 福利类型 1.大转盘  2.刮刮乐
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置福利类型 1.大转盘  2.刮刮乐
     *
     * @param type 福利类型 1.大转盘  2.刮刮乐
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取该活动是否启用  1.启用  2.不启用
     *
     * @return result - 该活动是否启用  1.启用  2.不启用
     */
    public Byte getResult() {
        return result;
    }

    /**
     * 设置该活动是否启用  1.启用  2.不启用
     *
     * @param result 该活动是否启用  1.启用  2.不启用
     */
    public void setResult(Byte result) {
        this.result = result;
    }

    /**
     * 获取活动开始时间
     *
     * @return create_time - 活动开始时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置活动开始时间
     *
     * @param createTime 活动开始时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取活动结束时间
     *
     * @return end_time - 活动结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置活动结束时间
     *
     * @param endTime 活动结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取数据每次更新时间
     *
     * @return update_time - 数据每次更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置数据每次更新时间
     *
     * @param updateTime 数据每次更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        sb.append(", result=").append(result);
        sb.append(", createTime=").append(createTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}