package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sl_search_ring_news_image")
public class SlSearchRingNewsImage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 消息id
     */
    @Column(name = "news_id")
    private String newsId;
    /**
     * 图片url
     */
    @Column(name = "image_url")
    private String imageUrl;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取图片url
     *
     * @return image_url - 图片url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置图片url
     *
     * @param imageUrl 图片url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
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
        sb.append(", newsId=").append(newsId);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}