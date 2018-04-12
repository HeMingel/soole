package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_search_ring_news_reply")
public class SlSearchRingNewsReply implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 被回复的评论id
     */
    @Column(name = "comment_id")
    private String commentId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 被回复人id
     */
    @Column(name = "to_user_id")
    private String toUserId;

    /**
     * 回复时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 回复内容
     */
    private String content;

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
     * 获取被回复的评论id
     *
     * @return comment_id - 被回复的评论id
     */
    public String getCommentId() {
        return commentId;
    }

    /**
     * 设置被回复的评论id
     *
     * @param commentId 被回复的评论id
     */
    public void setCommentId(String commentId) {
        this.commentId = commentId == null ? null : commentId.trim();
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
     * 获取被回复人id
     *
     * @return to_user_id - 被回复人id
     */
    public String getToUserId() {
        return toUserId;
    }

    /**
     * 设置被回复人id
     *
     * @param toUserId 被回复人id
     */
    public void setToUserId(String toUserId) {
        this.toUserId = toUserId == null ? null : toUserId.trim();
    }

    /**
     * 获取回复时间
     *
     * @return create_time - 回复时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置回复时间
     *
     * @param createTime 回复时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取回复内容
     *
     * @return content - 回复内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置回复内容
     *
     * @param content 回复内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", commentId=").append(commentId);
        sb.append(", userId=").append(userId);
        sb.append(", toUserId=").append(toUserId);
        sb.append(", createTime=").append(createTime);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}