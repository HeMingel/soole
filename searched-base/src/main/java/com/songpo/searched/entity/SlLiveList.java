package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_live_list")
public class SlLiveList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 直播用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 直播地址
     */
    @Column(name = "live_url")
    private String liveUrl;

    /**
     * 直播标题
     */
    private String title;

    /**
     * 直播封面url
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 直播状态  1.直播中  2.直播暂停  3.直播结束
     */
    private Boolean status;

    /**
     * 直播人数
     */
    @Column(name = "people_number")
    private Integer peopleNumber;

    /**
     * 直播点赞数
     */
    @Column(name = "goods_number")
    private Integer goodsNumber;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 数据更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 直播结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

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
     * 获取直播用户id
     *
     * @return user_id - 直播用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置直播用户id
     *
     * @param userId 直播用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取直播地址
     *
     * @return live_url - 直播地址
     */
    public String getLiveUrl() {
        return liveUrl;
    }

    /**
     * 设置直播地址
     *
     * @param liveUrl 直播地址
     */
    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl == null ? null : liveUrl.trim();
    }

    /**
     * 获取直播标题
     *
     * @return title - 直播标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置直播标题
     *
     * @param title 直播标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取直播封面url
     *
     * @return image_url - 直播封面url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置直播封面url
     *
     * @param imageUrl 直播封面url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    /**
     * 获取直播状态  1.直播中  2.直播暂停  3.直播结束
     *
     * @return status - 直播状态  1.直播中  2.直播暂停  3.直播结束
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置直播状态  1.直播中  2.直播暂停  3.直播结束
     *
     * @param status 直播状态  1.直播中  2.直播暂停  3.直播结束
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 获取直播人数
     *
     * @return people_number - 直播人数
     */
    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    /**
     * 设置直播人数
     *
     * @param peopleNumber 直播人数
     */
    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    /**
     * 获取直播点赞数
     *
     * @return goods_number - 直播点赞数
     */
    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    /**
     * 设置直播点赞数
     *
     * @param goodsNumber 直播点赞数
     */
    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
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
     * 获取数据更新时间
     *
     * @return update_time - 数据更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置数据更新时间
     *
     * @param updateTime 数据更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取直播结束时间
     *
     * @return end_time - 直播结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置直播结束时间
     *
     * @param endTime 直播结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", liveUrl=").append(liveUrl);
        sb.append(", title=").append(title);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", status=").append(status);
        sb.append(", peopleNumber=").append(peopleNumber);
        sb.append(", goodsNumber=").append(goodsNumber);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}