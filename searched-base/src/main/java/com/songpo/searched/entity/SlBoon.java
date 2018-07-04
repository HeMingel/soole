package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_boon")
public class SlBoon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 福利类型 1.大转盘  2.刮刮乐
     */
    private Byte type;

    /**
     * 该活动是否启用  1.启用  2.不启用
     */
    private Byte result;

    /**
     * 虚拟参与人数
     */
    @Column(name = "mock_join_people_number")
    private Integer mockJoinPeopleNumber;

    /**
     * 实际参加人数
     */
    @Column(name = "fact_join_people_number")
    private Integer factJoinPeopleNumber;

    /**
     * 活动规则说明
     */
    @Column(name = "rule_declaration")
    private String ruleDeclaration;

    /**
     * 是否轮播展示中奖信息 1.是  2.否
     */
    @Column(name = "is_wheel_sowing")
    private Byte isWheelSowing;

    /**
     * 单人总抽奖机会 0.表示不限
     */
    @Column(name = "one_draw_count")
    private Integer oneDrawCount;

    /**
     * 每人每天抽奖次数 0.表示不限
     */
    @Column(name = "day_draw_count")
    private Integer dayDrawCount;

    /**
     * 新用户免费次数 0.表示无
     */
    @Column(name = "new_user_free_draw_count")
    private Integer newUserFreeDrawCount;

    /**
     * 是否收费  0.表示不收费
     */
    @Column(name = "collect_money")
    private Integer collectMoney;

    /**
     * 用户等级限制，此处填写等级id
     */
    @Column(name = "level_id")
    private String levelId;

    /**
     * 活动开始时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 活动结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 数据每次更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取活动名称
     *
     * @return name - 活动名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置活动名称
     *
     * @param name 活动名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取福利类型 1.大转盘  2.刮刮乐
     *
     * @return type - 福利类型 1.大转盘  2.刮刮乐
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置福利类型 1.大转盘  2.刮刮乐
     *
     * @param type 福利类型 1.大转盘  2.刮刮乐
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取该活动是否启用  1.启用  2.不启用
     *
     * @return result - 该活动是否启用  1.启用  2.不启用
     */
    public Byte getResult() {
        return result;
    }

    /**
     * 设置该活动是否启用  1.启用  2.不启用
     *
     * @param result 该活动是否启用  1.启用  2.不启用
     */
    public void setResult(Byte result) {
        this.result = result;
    }

    /**
     * 获取虚拟参与人数
     *
     * @return mock_join_people_number - 虚拟参与人数
     */
    public Integer getMockJoinPeopleNumber() {
        return mockJoinPeopleNumber;
    }

    /**
     * 设置虚拟参与人数
     *
     * @param mockJoinPeopleNumber 虚拟参与人数
     */
    public void setMockJoinPeopleNumber(Integer mockJoinPeopleNumber) {
        this.mockJoinPeopleNumber = mockJoinPeopleNumber;
    }

    /**
     * 获取实际参加人数
     *
     * @return fact_join_people_number - 实际参加人数
     */
    public Integer getFactJoinPeopleNumber() {
        return factJoinPeopleNumber;
    }

    /**
     * 设置实际参加人数
     *
     * @param factJoinPeopleNumber 实际参加人数
     */
    public void setFactJoinPeopleNumber(Integer factJoinPeopleNumber) {
        this.factJoinPeopleNumber = factJoinPeopleNumber;
    }

    /**
     * 获取活动规则说明
     *
     * @return rule_declaration - 活动规则说明
     */
    public String getRuleDeclaration() {
        return ruleDeclaration;
    }

    /**
     * 设置活动规则说明
     *
     * @param ruleDeclaration 活动规则说明
     */
    public void setRuleDeclaration(String ruleDeclaration) {
        this.ruleDeclaration = ruleDeclaration == null ? null : ruleDeclaration.trim();
    }

    /**
     * 获取是否轮播展示中奖信息 1.是  2.否
     *
     * @return is_wheel_sowing - 是否轮播展示中奖信息 1.是  2.否
     */
    public Byte getIsWheelSowing() {
        return isWheelSowing;
    }

    /**
     * 设置是否轮播展示中奖信息 1.是  2.否
     *
     * @param isWheelSowing 是否轮播展示中奖信息 1.是  2.否
     */
    public void setIsWheelSowing(Byte isWheelSowing) {
        this.isWheelSowing = isWheelSowing;
    }

    /**
     * 获取单人总抽奖机会 0.表示不限
     *
     * @return one_draw_count - 单人总抽奖机会 0.表示不限
     */
    public Integer getOneDrawCount() {
        return oneDrawCount;
    }

    /**
     * 设置单人总抽奖机会 0.表示不限
     *
     * @param oneDrawCount 单人总抽奖机会 0.表示不限
     */
    public void setOneDrawCount(Integer oneDrawCount) {
        this.oneDrawCount = oneDrawCount;
    }

    /**
     * 获取每人每天抽奖次数 0.表示不限
     *
     * @return day_draw_count - 每人每天抽奖次数 0.表示不限
     */
    public Integer getDayDrawCount() {
        return dayDrawCount;
    }

    /**
     * 设置每人每天抽奖次数 0.表示不限
     *
     * @param dayDrawCount 每人每天抽奖次数 0.表示不限
     */
    public void setDayDrawCount(Integer dayDrawCount) {
        this.dayDrawCount = dayDrawCount;
    }

    /**
     * 获取新用户免费次数 0.表示无
     *
     * @return new_user_free_draw_count - 新用户免费次数 0.表示无
     */
    public Integer getNewUserFreeDrawCount() {
        return newUserFreeDrawCount;
    }

    /**
     * 设置新用户免费次数 0.表示无
     *
     * @param newUserFreeDrawCount 新用户免费次数 0.表示无
     */
    public void setNewUserFreeDrawCount(Integer newUserFreeDrawCount) {
        this.newUserFreeDrawCount = newUserFreeDrawCount;
    }

    /**
     * 获取是否收费  0.表示不收费
     *
     * @return collect_money - 是否收费  0.表示不收费
     */
    public Integer getCollectMoney() {
        return collectMoney;
    }

    /**
     * 设置是否收费  0.表示不收费
     *
     * @param collectMoney 是否收费  0.表示不收费
     */
    public void setCollectMoney(Integer collectMoney) {
        this.collectMoney = collectMoney;
    }

    /**
     * 获取用户等级限制，此处填写等级id
     *
     * @return level_id - 用户等级限制，此处填写等级id
     */
    public String getLevelId() {
        return levelId;
    }

    /**
     * 设置用户等级限制，此处填写等级id
     *
     * @param levelId 用户等级限制，此处填写等级id
     */
    public void setLevelId(String levelId) {
        this.levelId = levelId == null ? null : levelId.trim();
    }

    /**
     * 获取活动开始时间
     *
     * @return create_time - 活动开始时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置活动开始时间
     *
     * @param createTime 活动开始时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取活动结束时间
     *
     * @return end_time - 活动结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置活动结束时间
     *
     * @param endTime 活动结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取数据每次更新时间
     *
     * @return update_time - 数据每次更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置数据每次更新时间
     *
     * @param updateTime 数据每次更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", result=").append(result);
        sb.append(", mockJoinPeopleNumber=").append(mockJoinPeopleNumber);
        sb.append(", factJoinPeopleNumber=").append(factJoinPeopleNumber);
        sb.append(", ruleDeclaration=").append(ruleDeclaration);
        sb.append(", isWheelSowing=").append(isWheelSowing);
        sb.append(", oneDrawCount=").append(oneDrawCount);
        sb.append(", dayDrawCount=").append(dayDrawCount);
        sb.append(", newUserFreeDrawCount=").append(newUserFreeDrawCount);
        sb.append(", collectMoney=").append(collectMoney);
        sb.append(", levelId=").append(levelId);
        sb.append(", createTime=").append(createTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}