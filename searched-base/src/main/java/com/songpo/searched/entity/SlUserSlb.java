package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_user_slb")
public class SlUserSlb implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户主键
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户搜了贝数量
     */
    private BigDecimal slb;

    /**
     * 搜了贝分类
     */
    @Column(name = "slb_type")
    private Integer slbType;

    /**
     * 创建时间
     */
    @Column(name = "create_at")
    private Date createAt;

    /**
     * 修改时间
     */
    @Column(name = "update_at")
    private Date updateAt;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取用户主键
     *
     * @return user_id - 用户主键
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户主键
     *
     * @param userId 用户主键
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取用户搜了贝数量
     *
     * @return slb - 用户搜了贝数量
     */
    public BigDecimal getSlb() {
        return slb;
    }

    /**
     * 设置用户搜了贝数量
     *
     * @param slb 用户搜了贝数量
     */
    public void setSlb(BigDecimal slb) {
        this.slb = slb;
    }

    /**
     * 获取搜了贝分类
     *
     * @return slb_type - 搜了贝分类
     */
    public Integer getSlbType() {
        return slbType;
    }

    /**
     * 设置搜了贝分类
     *
     * @param slbType 搜了贝分类
     */
    public void setSlbType(Integer slbType) {
        this.slbType = slbType;
    }

    /**
     * 获取创建时间
     *
     * @return create_at - 创建时间
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 设置创建时间
     *
     * @param createAt 创建时间
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取修改时间
     *
     * @return update_at - 修改时间
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * 设置修改时间
     *
     * @param updateAt 修改时间
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", slb=").append(slb);
        sb.append(", slbType=").append(slbType);
        sb.append(", createAt=").append(createAt);
        sb.append(", updateAt=").append(updateAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}