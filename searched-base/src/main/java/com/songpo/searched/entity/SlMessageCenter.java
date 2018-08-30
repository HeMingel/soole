package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_message_center")
public class SlMessageCenter implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 消息是否已读  1.未读  2.已读
     */
    @Column(name = "is_read")
    private Byte isRead;

    /**
     * 图片
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 推送标题
     */
    private String title;

    /**
     * 推送内容
     */
    private String content;

    /**
     * 推送类型 1.系统通知 2.账户通知 3.物流通知 4.拼团通知 5.区块链佣金通知
     */
    private Byte type;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 最后更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 消息接收人的username字段（系统通知时，该字段为多个用户的username）
     */
    private String username;

    private static final long serialVersionUID = 1L;

    /**
     * 获取唯一标识符
     *
     * @return id - 唯一标识符
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置唯一标识符
     *
     * @param id 唯一标识符
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取消息是否已读  1.未读  2.已读
     *
     * @return is_read - 消息是否已读  1.未读  2.已读
     */
    public Byte getIsRead() {
        return isRead;
    }

    /**
     * 设置消息是否已读  1.未读  2.已读
     *
     * @param isRead 消息是否已读  1.未读  2.已读
     */
    public void setIsRead(Byte isRead) {
        this.isRead = isRead;
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
     * 获取推送标题
     *
     * @return title - 推送标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置推送标题
     *
     * @param title 推送标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取推送内容
     *
     * @return content - 推送内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置推送内容
     *
     * @param content 推送内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取推送类型 1.系统通知 2.账户通知 3.物流通知 4.拼团通知 5.区块链佣金通知
     *
     * @return type - 推送类型 1.系统通知 2.账户通知 3.物流通知 4.拼团通知 5.区块链佣金通知
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置推送类型 1.系统通知 2.账户通知 3.物流通知 4.拼团通知 5.区块链佣金通知
     *
     * @param type 推送类型 1.系统通知 2.账户通知 3.物流通知 4.拼团通知 5.区块链佣金通知
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取最后更新时间
     *
     * @return updated_at - 最后更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置最后更新时间
     *
     * @param updatedAt 最后更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取消息接收人的username字段（系统通知时，该字段为多个用户的username）
     *
     * @return username - 消息接收人的username字段（系统通知时，该字段为多个用户的username）
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置消息接收人的username字段（系统通知时，该字段为多个用户的username）
     *
     * @param username 消息接收人的username字段（系统通知时，该字段为多个用户的username）
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", isRead=").append(isRead);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", type=").append(type);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", username=").append(username);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}