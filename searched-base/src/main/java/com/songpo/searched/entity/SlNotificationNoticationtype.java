package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_notification_noticationtype")
public class SlNotificationNoticationtype implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 所属类型id
     */
    @Column(name = "parentId")
    private String parentid;

    private String name;

    private String description;

    /**
     * 修改时间
     */
    @Column(name = "updateTime")
    private Date updatetime;

    /**
     * 修改人
     */
    @Column(name = "updateUser")
    private String updateuser;

    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createtime;

    /**
     * 创建人
     */
    @Column(name = "createUser")
    private String createuser;

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
     * 获取所属类型id
     *
     * @return parentId - 所属类型id
     */
    public String getParentid() {
        return parentid;
    }

    /**
     * 设置所属类型id
     *
     * @param parentid 所属类型id
     */
    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取修改时间
     *
     * @return updateTime - 修改时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 设置修改时间
     *
     * @param updatetime 修改时间
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 获取修改人
     *
     * @return updateUser - 修改人
     */
    public String getUpdateuser() {
        return updateuser;
    }

    /**
     * 设置修改人
     *
     * @param updateuser 修改人
     */
    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }

    /**
     * 获取创建时间
     *
     * @return createTime - 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取创建人
     *
     * @return createUser - 创建人
     */
    public String getCreateuser() {
        return createuser;
    }

    /**
     * 设置创建人
     *
     * @param createuser 创建人
     */
    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", parentid=").append(parentid);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", updateuser=").append(updateuser);
        sb.append(", createtime=").append(createtime);
        sb.append(", createuser=").append(createuser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}