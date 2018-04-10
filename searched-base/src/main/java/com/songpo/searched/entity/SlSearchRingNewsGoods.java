package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sl_search_ring_news_goods")
public class SlSearchRingNewsGoods implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 消息id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 消息id
     */
    @Column(name = "news_id")
    private String newsId;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取消息id
     *
     * @return id - 消息id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置消息id
     *
     * @param id 消息id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取消息id
     *
     * @return news_id - 消息id
     */
    public String getNewsId() {
        return newsId;
    }

    /**
     * 设置消息id
     *
     * @param newsId 消息id
     */
    public void setNewsId(String newsId) {
        this.newsId = newsId == null ? null : newsId.trim();
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
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
        sb.append(", newsId=").append(newsId);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}