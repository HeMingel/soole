package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "sl_member")
public class SlMember implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 唯一标识符
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
     * 金币
     */
    private Integer coin;
    /**
     * 余额
     */
    private BigDecimal money;
    /**
     * 等级
     */
    private Integer level;
    /**
     * 经验值
     */
    private Integer exp;
    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    @Column(name = "modification_time")
    private String modificationTime;
    /**
     * 是否开启消息推送  1.开启  2.不开启
     */
    @Column(name = "is_push")
    private Boolean isPush;

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
     * 获取金币
     *
     * @return coin - 金币
     */
    public Integer getCoin() {
        return coin;
    }

    /**
     * 设置金币
     *
     * @param coin 金币
     */
    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    /**
     * 获取余额
     *
     * @return money - 余额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置余额
     *
     * @param money 余额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取等级
     *
     * @return level - 等级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置等级
     *
     * @param level 等级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取经验值
     *
     * @return exp - 经验值
     */
    public Integer getExp() {
        return exp;
    }

    /**
     * 设置经验值
     *
     * @param exp 经验值
     */
    public void setExp(Integer exp) {
        this.exp = exp;
    }

    /**
     * 获取是否开启消息推送  1.开启  2.不开启
     *
     * @return is_push - 是否开启消息推送  1.开启  2.不开启
     */
    public Boolean getIsPush() {
        return isPush;
    }

    /**
     * 设置是否开启消息推送  1.开启  2.不开启
     *
     * @param isPush 是否开启消息推送  1.开启  2.不开启
     */
    public void setIsPush(Boolean isPush) {
        this.isPush = isPush;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
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
     * 获取修改人
     *
     * @return modifier - 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置修改人
     *
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * 获取修改时间
     *
     * @return modification_time - 修改时间
     */
    public String getModificationTime() {
        return modificationTime;
    }

    /**
     * 设置修改时间
     *
     * @param modificationTime 修改时间
     */
    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime == null ? null : modificationTime.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", coin=").append(coin);
        sb.append(", money=").append(money);
        sb.append(", level=").append(level);
        sb.append(", exp=").append(exp);
        sb.append(", isPush=").append(isPush);
        sb.append(", creator=").append(creator);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifier=").append(modifier);
        sb.append(", modificationTime=").append(modificationTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}