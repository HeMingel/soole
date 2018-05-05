package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_gold_adviser")
public class SlGoldAdviser implements Serializable {
    /**
     * 唯一标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 昵称，例如：金牌顾问李白
     */
    private String nickname;

    /**
     * 顾问介绍
     */
    private String description;

    /**
     * 封面图片
     */
    @Column(name = "cover_url")
    private String coverUrl;

    /**
     * 介绍视频
     */
    @Column(name = "video_url")
    private String videoUrl;

    private static final long serialVersionUID = 1L;

    /**
     * 获取唯一标识
     *
     * @return id - 唯一标识
     */
    public String getId() {
        return id;
    }

    /**
     * 设置唯一标识
     *
     * @param id 唯一标识
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取昵称，例如：金牌顾问李白
     *
     * @return nickname - 昵称，例如：金牌顾问李白
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称，例如：金牌顾问李白
     *
     * @param nickname 昵称，例如：金牌顾问李白
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 获取顾问介绍
     *
     * @return description - 顾问介绍
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置顾问介绍
     *
     * @param description 顾问介绍
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取封面图片
     *
     * @return cover_url - 封面图片
     */
    public String getCoverUrl() {
        return coverUrl;
    }

    /**
     * 设置封面图片
     *
     * @param coverUrl 封面图片
     */
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl == null ? null : coverUrl.trim();
    }

    /**
     * 获取介绍视频
     *
     * @return video_url - 介绍视频
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * 设置介绍视频
     *
     * @param videoUrl 介绍视频
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", nickname=").append(nickname);
        sb.append(", description=").append(description);
        sb.append(", coverUrl=").append(coverUrl);
        sb.append(", videoUrl=").append(videoUrl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}