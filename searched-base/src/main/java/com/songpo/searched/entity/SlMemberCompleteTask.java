package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sl_member_complete_task")
public class SlMemberCompleteTask implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 任务id
     */
    @Column(name = "task_id")
    private String taskId;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 完成时间
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
     * 获取任务id
     *
     * @return task_id - 任务id
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 设置任务id
     *
     * @param taskId 任务id
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
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
     * 获取完成时间
     *
     * @return create_time - 完成时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置完成时间
     *
     * @param createTime 完成时间
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
        sb.append(", taskId=").append(taskId);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}