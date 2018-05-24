package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_system_version")
public class SlSystemVersion implements Serializable {
    /**
     * 唯一标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * app名称
     */
    @Column(name = "app_name")
    private String appName;

    /**
     * 版本号
     */
    @Column(name = "server_version")
    private String serverVersion;

    /**
     * 是否强制更新 0不 1是
     */
    @Column(name = "last_force")
    private Integer lastForce;

    /**
     * 更新地址
     */
    @Column(name = "update_url")
    private String updateUrl;

    /**
     * 更新描述
     */
    @Column(name = "upgrade_info")
    private String upgradeInfo;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    private static final long serialVersionUID = 1L;

    /**
     * 获取唯一标识
     *
     * @return id - 唯一标识
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置唯一标识
     *
     * @param id 唯一标识
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取app名称
     *
     * @return app_name - app名称
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置app名称
     *
     * @param appName app名称
     */
    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    /**
     * 获取版本号
     *
     * @return server_version - 版本号
     */
    public String getServerVersion() {
        return serverVersion;
    }

    /**
     * 设置版本号
     *
     * @param serverVersion 版本号
     */
    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion == null ? null : serverVersion.trim();
    }

    /**
     * 获取是否强制更新 0不 1是
     *
     * @return last_force - 是否强制更新 0不 1是
     */
    public Integer getLastForce() {
        return lastForce;
    }

    /**
     * 设置是否强制更新 0不 1是
     *
     * @param lastForce 是否强制更新 0不 1是
     */
    public void setLastForce(Integer lastForce) {
        this.lastForce = lastForce;
    }

    /**
     * 获取更新地址
     *
     * @return update_url - 更新地址
     */
    public String getUpdateUrl() {
        return updateUrl;
    }

    /**
     * 设置更新地址
     *
     * @param updateUrl 更新地址
     */
    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl == null ? null : updateUrl.trim();
    }

    /**
     * 获取更新描述
     *
     * @return upgrade_info - 更新描述
     */
    public String getUpgradeInfo() {
        return upgradeInfo;
    }

    /**
     * 设置更新描述
     *
     * @param upgradeInfo 更新描述
     */
    public void setUpgradeInfo(String upgradeInfo) {
        this.upgradeInfo = upgradeInfo == null ? null : upgradeInfo.trim();
    }

    /**
     * 获取更新时间
     *
     * @return updated_at - 更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置更新时间
     *
     * @param updatedAt 更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", appName=").append(appName);
        sb.append(", serverVersion=").append(serverVersion);
        sb.append(", lastForce=").append(lastForce);
        sb.append(", updateUrl=").append(updateUrl);
        sb.append(", upgradeInfo=").append(upgradeInfo);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}