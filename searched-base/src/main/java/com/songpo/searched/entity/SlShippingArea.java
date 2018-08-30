package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_shipping_area")
public class SlShippingArea implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 包邮地区id字符串
     */
    @Column(name = "no_postage_area_id")
    private String noPostageAreaId;

    /**
     * 包邮地区文字字符串
     */
    @Column(name = "no_postage_area_text")
    private String noPostageAreaText;

    /**
     * 不包邮地区id字符串
     */
    @Column(name = "postage_area_id")
    private String postageAreaId;

    /**
     * 不包邮地区文字字符串
     */
    @Column(name = "postage_area_text")
    private String postageAreaText;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

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
     * 获取包邮地区id字符串
     *
     * @return no_postage_area_id - 包邮地区id字符串
     */
    public String getNoPostageAreaId() {
        return noPostageAreaId;
    }

    /**
     * 设置包邮地区id字符串
     *
     * @param noPostageAreaId 包邮地区id字符串
     */
    public void setNoPostageAreaId(String noPostageAreaId) {
        this.noPostageAreaId = noPostageAreaId == null ? null : noPostageAreaId.trim();
    }

    /**
     * 获取包邮地区文字字符串
     *
     * @return no_postage_area_text - 包邮地区文字字符串
     */
    public String getNoPostageAreaText() {
        return noPostageAreaText;
    }

    /**
     * 设置包邮地区文字字符串
     *
     * @param noPostageAreaText 包邮地区文字字符串
     */
    public void setNoPostageAreaText(String noPostageAreaText) {
        this.noPostageAreaText = noPostageAreaText == null ? null : noPostageAreaText.trim();
    }

    /**
     * 获取不包邮地区id字符串
     *
     * @return postage_area_id - 不包邮地区id字符串
     */
    public String getPostageAreaId() {
        return postageAreaId;
    }

    /**
     * 设置不包邮地区id字符串
     *
     * @param postageAreaId 不包邮地区id字符串
     */
    public void setPostageAreaId(String postageAreaId) {
        this.postageAreaId = postageAreaId == null ? null : postageAreaId.trim();
    }

    /**
     * 获取不包邮地区文字字符串
     *
     * @return postage_area_text - 不包邮地区文字字符串
     */
    public String getPostageAreaText() {
        return postageAreaText;
    }

    /**
     * 设置不包邮地区文字字符串
     *
     * @param postageAreaText 不包邮地区文字字符串
     */
    public void setPostageAreaText(String postageAreaText) {
        this.postageAreaText = postageAreaText == null ? null : postageAreaText.trim();
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

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
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
        sb.append(", noPostageAreaId=").append(noPostageAreaId);
        sb.append(", noPostageAreaText=").append(noPostageAreaText);
        sb.append(", postageAreaId=").append(postageAreaId);
        sb.append(", postageAreaText=").append(postageAreaText);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}