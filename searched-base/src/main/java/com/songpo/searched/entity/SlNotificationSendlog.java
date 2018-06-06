package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sl_notification_sendlog")
public class SlNotificationSendlog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 发送通知的消息队列账户
     */
    @Column(name = "sendUser_id")
    private String senduserId;

    /**
     * 消息队列发送通知的正文
     */
    @Column(name = "sendContent")
    private String sendcontent;

    /**
     * 发送时间
     */
    @Column(name = "sendTime")
    private Date sendtime;

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
     * 获取发送通知的消息队列账户
     *
     * @return sendUser_id - 发送通知的消息队列账户
     */
    public String getSenduserId() {
        return senduserId;
    }

    /**
     * 设置发送通知的消息队列账户
     *
     * @param senduserId 发送通知的消息队列账户
     */
    public void setSenduserId(String senduserId) {
        this.senduserId = senduserId == null ? null : senduserId.trim();
    }

    /**
     * 获取消息队列发送通知的正文
     *
     * @return sendContent - 消息队列发送通知的正文
     */
    public String getSendcontent() {
        return sendcontent;
    }

    /**
     * 设置消息队列发送通知的正文
     *
     * @param sendcontent 消息队列发送通知的正文
     */
    public void setSendcontent(String sendcontent) {
        this.sendcontent = sendcontent == null ? null : sendcontent.trim();
    }

    /**
     * 获取发送时间
     *
     * @return sendTime - 发送时间
     */
    public Date getSendtime() {
        return sendtime;
    }

    /**
     * 设置发送时间
     *
     * @param sendtime 发送时间
     */
    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", senduserId=").append(senduserId);
        sb.append(", sendcontent=").append(sendcontent);
        sb.append(", sendtime=").append(sendtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}