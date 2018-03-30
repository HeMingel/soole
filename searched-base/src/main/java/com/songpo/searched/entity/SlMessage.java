package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_message")
public class SlMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 来源标识
     */
    @Column(name = "sourceId")
    private String sourceid;
    /**
     * 来源名称
     */
    @Column(name = "sourceName")
    private String sourcename;
    /**
     * 目标标识
     */
    @Column(name = "targetId")
    private String targetid;
    /**
     * 目标名称
     */
    @Column(name = "targetName")
    private String targetname;
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
     * @return sourceId - 来源标识
     */
    public String getSourceid() {
        return sourceid;
    }

    /**
     * 设置来源标识
     *
     * @param sourceid 来源标识
     */
    public void setSourceid(String sourceid) {
        this.sourceid = sourceid == null ? null : sourceid.trim();
    }

    /**
     * 获取来源名称
     *
     * @return sourceName - 来源名称
     */
    public String getSourcename() {
        return sourcename;
    }

    /**
     * 设置来源名称
     *
     * @param sourcename 来源名称
     */
    public void setSourcename(String sourcename) {
        this.sourcename = sourcename == null ? null : sourcename.trim();
    }

    /**
     * 获取目标标识
     *
     * @return targetId - 目标标识
     */
    public String getTargetid() {
        return targetid;
    }

    /**
     * 设置目标标识
     *
     * @param targetid 目标标识
     */
    public void setTargetid(String targetid) {
        this.targetid = targetid == null ? null : targetid.trim();
    }

    /**
     * 获取目标名称
     *
     * @return targetName - 目标名称
     */
    public String getTargetname() {
        return targetname;
    }

    /**
     * 设置目标名称
     *
     * @param targetname 目标名称
     */
    public void setTargetname(String targetname) {
        this.targetname = targetname == null ? null : targetname.trim();
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
        sb.append(", sourceid=").append(sourceid);
        sb.append(", sourcename=").append(sourcename);
        sb.append(", targetid=").append(targetid);
        sb.append(", targetname=").append(targetname);
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