package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_friend_group_member")
public class SlFriendGroupMember implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 群id
     */
    @Column(name = "group_id")
    private String groupId;

    /**
     * 群成员id
     */
    @Column(name = "member_id")
    private String memberId;

    /**
     * 群成员身份  1.普通成员   2.群管理   3.群主
     */
    private Boolean type;

    /**
     * 群成员在该群的群昵称
     */
    @Column(name = "member_name")
    private String memberName;

    /**
     * 消息免打扰   1.不开启   2.开启
     */
    @Column(name = "no_disturb")
    private Boolean noDisturb;

    /**
     * 加入群聊时间
     */
    @Column(name = "add_time")
    private Date addTime;

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
     * 获取群id
     *
     * @return group_id - 群id
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 设置群id
     *
     * @param groupId 群id
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    /**
     * 获取群成员id
     *
     * @return member_id - 群成员id
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * 设置群成员id
     *
     * @param memberId 群成员id
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    /**
     * 获取群成员身份  1.普通成员   2.群管理   3.群主
     *
     * @return type - 群成员身份  1.普通成员   2.群管理   3.群主
     */
    public Boolean getType() {
        return type;
    }

    /**
     * 设置群成员身份  1.普通成员   2.群管理   3.群主
     *
     * @param type 群成员身份  1.普通成员   2.群管理   3.群主
     */
    public void setType(Boolean type) {
        this.type = type;
    }

    /**
     * 获取群成员在该群的群昵称
     *
     * @return member_name - 群成员在该群的群昵称
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * 设置群成员在该群的群昵称
     *
     * @param memberName 群成员在该群的群昵称
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    /**
     * 获取消息免打扰   1.不开启   2.开启
     *
     * @return no_disturb - 消息免打扰   1.不开启   2.开启
     */
    public Boolean getNoDisturb() {
        return noDisturb;
    }

    /**
     * 设置消息免打扰   1.不开启   2.开启
     *
     * @param noDisturb 消息免打扰   1.不开启   2.开启
     */
    public void setNoDisturb(Boolean noDisturb) {
        this.noDisturb = noDisturb;
    }

    /**
     * 获取加入群聊时间
     *
     * @return add_time - 加入群聊时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 设置加入群聊时间
     *
     * @param addTime 加入群聊时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", groupId=").append(groupId);
        sb.append(", memberId=").append(memberId);
        sb.append(", type=").append(type);
        sb.append(", memberName=").append(memberName);
        sb.append(", noDisturb=").append(noDisturb);
        sb.append(", addTime=").append(addTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}