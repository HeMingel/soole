package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_admin_user")
public class SlAdminUser implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 帐号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * QQ号码
     */
    private String qq;

    /**
     * 本次登录IP
     */
    @Column(name = "login_ip")
    private String loginIp;

    /**
     * 本次登录时间
     */
    @Column(name = "login_time")
    private Integer loginTime;

    /**
     * 最后一次登录IP地址
     */
    @Column(name = "last_ip")
    private String lastIp;

    /**
     * 最后一次登录时间
     */
    @Column(name = "last_time")
    private Integer lastTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Integer createTime;

    /**
     * 用户组权限ID
     */
    @Column(name = "group_id")
    private Short groupId;

    /**
     * 所属部门ID
     */
    @Column(name = "section_id")
    private Short sectionId;

    /**
     * 是否审核
     */
    private Boolean checked;

    /**
     * 登录次数
     */
    @Column(name = "login_total")
    private Integer loginTotal;

    private static final long serialVersionUID = 1L;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取帐号
     *
     * @return username - 帐号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置帐号
     *
     * @param username 帐号
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取联系方式
     *
     * @return phone - 联系方式
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系方式
     *
     * @param phone 联系方式
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取QQ号码
     *
     * @return qq - QQ号码
     */
    public String getQq() {
        return qq;
    }

    /**
     * 设置QQ号码
     *
     * @param qq QQ号码
     */
    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    /**
     * 获取本次登录IP
     *
     * @return login_ip - 本次登录IP
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 设置本次登录IP
     *
     * @param loginIp 本次登录IP
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    /**
     * 获取本次登录时间
     *
     * @return login_time - 本次登录时间
     */
    public Integer getLoginTime() {
        return loginTime;
    }

    /**
     * 设置本次登录时间
     *
     * @param loginTime 本次登录时间
     */
    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 获取最后一次登录IP地址
     *
     * @return last_ip - 最后一次登录IP地址
     */
    public String getLastIp() {
        return lastIp;
    }

    /**
     * 设置最后一次登录IP地址
     *
     * @param lastIp 最后一次登录IP地址
     */
    public void setLastIp(String lastIp) {
        this.lastIp = lastIp == null ? null : lastIp.trim();
    }

    /**
     * 获取最后一次登录时间
     *
     * @return last_time - 最后一次登录时间
     */
    public Integer getLastTime() {
        return lastTime;
    }

    /**
     * 设置最后一次登录时间
     *
     * @param lastTime 最后一次登录时间
     */
    public void setLastTime(Integer lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取用户组权限ID
     *
     * @return group_id - 用户组权限ID
     */
    public Short getGroupId() {
        return groupId;
    }

    /**
     * 设置用户组权限ID
     *
     * @param groupId 用户组权限ID
     */
    public void setGroupId(Short groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取所属部门ID
     *
     * @return section_id - 所属部门ID
     */
    public Short getSectionId() {
        return sectionId;
    }

    /**
     * 设置所属部门ID
     *
     * @param sectionId 所属部门ID
     */
    public void setSectionId(Short sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * 获取是否审核
     *
     * @return checked - 是否审核
     */
    public Boolean getChecked() {
        return checked;
    }

    /**
     * 设置是否审核
     *
     * @param checked 是否审核
     */
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    /**
     * 获取登录次数
     *
     * @return login_total - 登录次数
     */
    public Integer getLoginTotal() {
        return loginTotal;
    }

    /**
     * 设置登录次数
     *
     * @param loginTotal 登录次数
     */
    public void setLoginTotal(Integer loginTotal) {
        this.loginTotal = loginTotal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", email=").append(email);
        sb.append(", phone=").append(phone);
        sb.append(", qq=").append(qq);
        sb.append(", loginIp=").append(loginIp);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", lastIp=").append(lastIp);
        sb.append(", lastTime=").append(lastTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", groupId=").append(groupId);
        sb.append(", sectionId=").append(sectionId);
        sb.append(", checked=").append(checked);
        sb.append(", loginTotal=").append(loginTotal);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}