package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sl_notification_content")
public class SlNotificationContent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 通知正文
     */
    private String text;
    /**
     * 标题
     */
    private String title;
    /**
     * 所属类别
     */
    @Column(name = "type_id")
    private String typeId;
    /**
     * 是否已读.0:未读;1:已读
     */
    private Boolean read;
    /**
     * 通知创建时间
     */
    @Column(name = "createTime")
    private Date createtime;

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
     * 获取通知正文
     *
     * @return text - 通知正文
     */
    public String getText() {
        return text;
    }

    /**
     * 设置通知正文
     *
     * @param text 通知正文
     */
    public void setText(String text) {
        this.text = text == null ? null : text.trim();
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
     * 获取所属类别
     *
     * @return type_id - 所属类别
     */
    public String getTypeId() {
        return typeId;
    }

    /**
     * 设置所属类别
     *
     * @param typeId 所属类别
     */
    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    /**
     * 获取是否已读.0:未读;1:已读
     *
     * @return read - 是否已读.0:未读;1:已读
     */
    public Boolean getRead() {
        return read;
    }

    /**
     * 设置是否已读.0:未读;1:已读
     *
     * @param read 是否已读.0:未读;1:已读
     */
    public void setRead(Boolean read) {
        this.read = read;
    }

    /**
     * 获取通知创建时间
     *
     * @return createTime - 通知创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置通知创建时间
     *
     * @param createtime 通知创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", text=").append(text);
        sb.append(", title=").append(title);
        sb.append(", typeId=").append(typeId);
        sb.append(", read=").append(read);
        sb.append(", createtime=").append(createtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}