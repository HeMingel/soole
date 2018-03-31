package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_message")
public class SlMessage implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 来源标识
     */
    @Column(name = "source_id")
    private String sourceId;

    /**
     * 来源名称
     */
    @Column(name = "source_name")
    private String sourceName;

    /**
     * 目标标识
     */
    @Column(name = "target_id")
    private String targetId;

    /**
     * 目标名称
     */
    @Column(name = "target_name")
    private String targetName;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否已读，1：已读，0：未读
     */
    private Integer read;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 读取时间
     */
    @Column(name = "read_time")
    private String readTime;

    /**
     * 消息类型，1：单播 2：广播
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
     * 获取来源标识
     *
     * @return source_id - 来源标识
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * 设置来源标识
     *
     * @param sourceId 来源标识
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    /**
     * 获取来源名称
     *
     * @return source_name - 来源名称
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * 设置来源名称
     *
     * @param sourceName 来源名称
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName == null ? null : sourceName.trim();
    }

    /**
     * 获取目标标识
     *
     * @return target_id - 目标标识
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * 设置目标标识
     *
     * @param targetId 目标标识
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    /**
     * 获取目标名称
     *
     * @return target_name - 目标名称
     */
    public String getTargetName() {
        return targetName;
    }

    /**
     * 设置目标名称
     *
     * @param targetName 目标名称
     */
    public void setTargetName(String targetName) {
        this.targetName = targetName == null ? null : targetName.trim();
    }

    /**
     * 获取消息内容
     *
     * @return content - 消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容
     *
     * @param content 消息内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取是否已读，1：已读，0：未读
     *
     * @return read - 是否已读，1：已读，0：未读
     */
    public Integer getRead() {
        return read;
    }

    /**
     * 设置是否已读，1：已读，0：未读
     *
     * @param read 是否已读，1：已读，0：未读
     */
    public void setRead(Integer read) {
        this.read = read;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    /**
     * 获取读取时间
     *
     * @return read_time - 读取时间
     */
    public String getReadTime() {
        return readTime;
    }

    /**
     * 设置读取时间
     *
     * @param readTime 读取时间
     */
    public void setReadTime(String readTime) {
        this.readTime = readTime == null ? null : readTime.trim();
    }

    /**
     * 获取消息类型，1：单播 2：广播
     *
     * @return type - 消息类型，1：单播 2：广播
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置消息类型，1：单播 2：广播
     *
     * @param type 消息类型，1：单播 2：广播
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
        sb.append(", sourceId=").append(sourceId);
        sb.append(", sourceName=").append(sourceName);
        sb.append(", targetId=").append(targetId);
        sb.append(", targetName=").append(targetName);
        sb.append(", content=").append(content);
        sb.append(", read=").append(read);
        sb.append(", createTime=").append(createTime);
        sb.append(", readTime=").append(readTime);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}