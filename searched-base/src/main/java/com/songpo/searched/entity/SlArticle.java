package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_article")
public class SlArticle implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 阅读数量
     */
    @Column(name = "read_count")
    private Integer readCount;

    /**
     * 赞扬数量
     */
    @Column(name = "praise_count")
    private Integer praiseCount;

    /**
     * 引用地址，用来存放消息引用地址
     */
    @Column(name = "reference_url")
    private String referenceUrl;

    /**
     * 添加时间
     */
    @Column(name = "add_time")
    private Integer addTime;

    /**
     * 文章类型：1搜了头条，2海南之窗，3搜了故事
     */
    private Integer type;

    private static final long serialVersionUID = 1L;

    /**
     * 获取唯一标识符
     *
     * @return id - 唯一标识符
     */
    public String getId() {
        return id;
    }

    /**
     * 设置唯一标识符
     *
     * @param id 唯一标识符
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取图片
     *
     * @return image_url - 图片
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置图片
     *
     * @param imageUrl 图片
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    /**
     * 获取阅读数量
     *
     * @return read_count - 阅读数量
     */
    public Integer getReadCount() {
        return readCount;
    }

    /**
     * 设置阅读数量
     *
     * @param readCount 阅读数量
     */
    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    /**
     * 获取赞扬数量
     *
     * @return praise_count - 赞扬数量
     */
    public Integer getPraiseCount() {
        return praiseCount;
    }

    /**
     * 设置赞扬数量
     *
     * @param praiseCount 赞扬数量
     */
    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }

    /**
     * 获取引用地址，用来存放消息引用地址
     *
     * @return reference_url - 引用地址，用来存放消息引用地址
     */
    public String getReferenceUrl() {
        return referenceUrl;
    }

    /**
     * 设置引用地址，用来存放消息引用地址
     *
     * @param referenceUrl 引用地址，用来存放消息引用地址
     */
    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl == null ? null : referenceUrl.trim();
    }

    /**
     * 获取添加时间
     *
     * @return add_time - 添加时间
     */
    public Integer getAddTime() {
        return addTime;
    }

    /**
     * 设置添加时间
     *
     * @param addTime 添加时间
     */
    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取文章类型：1搜了头条，2海南之窗，3搜了故事
     *
     * @return type - 文章类型：1搜了头条，2海南之窗，3搜了故事
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置文章类型：1搜了头条，2海南之窗，3搜了故事
     *
     * @param type 文章类型：1搜了头条，2海南之窗，3搜了故事
     */
    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", readCount=").append(readCount);
        sb.append(", praiseCount=").append(praiseCount);
        sb.append(", referenceUrl=").append(referenceUrl);
        sb.append(", addTime=").append(addTime);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}