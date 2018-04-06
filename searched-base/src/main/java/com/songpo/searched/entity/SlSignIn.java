package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_sign_in")
public class SlSignIn implements Serializable {
    /**
     * 用户签到唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户唯一标识符
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 签到时间
     */
    @Column(name = "sign_time")
    private String signTime;

    /**
     * 连续签到天数
     */
    @Column(name = "sign_num")
    private Integer signNum;

    /**
     * 奖励银豆
     */
    @Column(name = "award_silver")
    private Integer awardSilver;

    private static final long serialVersionUID = 1L;

    /**
     * 获取用户签到唯一标识符
     *
     * @return id - 用户签到唯一标识符
     */
    public String getId() {
        return id;
    }

    /**
     * 设置用户签到唯一标识符
     *
     * @param id 用户签到唯一标识符
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取用户唯一标识符
     *
     * @return user_id - 用户唯一标识符
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户唯一标识符
     *
     * @param userId 用户唯一标识符
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取签到时间
     *
     * @return sign_time - 签到时间
     */
    public String getSignTime() {
        return signTime;
    }

    /**
     * 设置签到时间
     *
     * @param signTime 签到时间
     */
    public void setSignTime(String signTime) {
        this.signTime = signTime == null ? null : signTime.trim();
    }

    /**
     * 获取连续签到天数
     *
     * @return sign_num - 连续签到天数
     */
    public Integer getSignNum() {
        return signNum;
    }

    /**
     * 设置连续签到天数
     *
     * @param signNum 连续签到天数
     */
    public void setSignNum(Integer signNum) {
        this.signNum = signNum;
    }

    /**
     * 获取奖励银豆
     *
     * @return award_silver - 奖励银豆
     */
    public Integer getAwardSilver() {
        return awardSilver;
    }

    /**
     * 设置奖励银豆
     *
     * @param awardSilver 奖励银豆
     */
    public void setAwardSilver(Integer awardSilver) {
        this.awardSilver = awardSilver;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", signTime=").append(signTime);
        sb.append(", signNum=").append(signNum);
        sb.append(", awardSilver=").append(awardSilver);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}