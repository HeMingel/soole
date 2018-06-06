package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sl_boon_detail")
public class SlBoonDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 福利id
     */
    @Column(name = "boon_id")
    private String boonId;

    /**
     * 奖项类型  1.虚拟商品  2.实物商品
     */
    @Column(name = "prize_type")
    private Boolean prizeType;

    /**
     * 奖励虚拟商品类别：0.奖励商品不是虚拟商品  1.金豆 2.银豆 3.券
     */
    @Column(name = "deal_type")
    private String dealType;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品图片路径（全域名）
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 奖励数量
     */
    @Column(name = "prize_count")
    private Boolean prizeCount;

    /**
     * 抽奖商品总数量
     */
    private Integer count;

    /**
     * 剩余商品数量
     */
    @Column(name = "surplus_count")
    private Integer surplusCount;

    /**
     * 该奖品状态 1.正常 2.下架
     */
    private Integer result;

    /**
     * 抽奖概率（百分比，例：50%）
     */
    private Double chance;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取福利id
     *
     * @return boon_id - 福利id
     */
    public String getBoonId() {
        return boonId;
    }

    /**
     * 设置福利id
     *
     * @param boonId 福利id
     */
    public void setBoonId(String boonId) {
        this.boonId = boonId == null ? null : boonId.trim();
    }

    /**
     * 获取奖项类型  1.虚拟商品  2.实物商品
     *
     * @return prize_type - 奖项类型  1.虚拟商品  2.实物商品
     */
    public Boolean getPrizeType() {
        return prizeType;
    }

    /**
     * 设置奖项类型  1.虚拟商品  2.实物商品
     *
     * @param prizeType 奖项类型  1.虚拟商品  2.实物商品
     */
    public void setPrizeType(Boolean prizeType) {
        this.prizeType = prizeType;
    }

    /**
     * 获取奖励虚拟商品类别：0.奖励商品不是虚拟商品  1.金豆 2.银豆 3.券
     *
     * @return deal_type - 奖励虚拟商品类别：0.奖励商品不是虚拟商品  1.金豆 2.银豆 3.券
     */
    public String getDealType() {
        return dealType;
    }

    /**
     * 设置奖励虚拟商品类别：0.奖励商品不是虚拟商品  1.金豆 2.银豆 3.券
     *
     * @param dealType 奖励虚拟商品类别：0.奖励商品不是虚拟商品  1.金豆 2.银豆 3.券
     */
    public void setDealType(String dealType) {
        this.dealType = dealType == null ? null : dealType.trim();
    }

    /**
     * 获取商品名称
     *
     * @return name - 商品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品名称
     *
     * @param name 商品名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取商品图片路径（全域名）
     *
     * @return image_url - 商品图片路径（全域名）
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置商品图片路径（全域名）
     *
     * @param imageUrl 商品图片路径（全域名）
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    /**
     * 获取奖励数量
     *
     * @return prize_count - 奖励数量
     */
    public Boolean getPrizeCount() {
        return prizeCount;
    }

    /**
     * 设置奖励数量
     *
     * @param prizeCount 奖励数量
     */
    public void setPrizeCount(Boolean prizeCount) {
        this.prizeCount = prizeCount;
    }

    /**
     * 获取抽奖商品总数量
     *
     * @return count - 抽奖商品总数量
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置抽奖商品总数量
     *
     * @param count 抽奖商品总数量
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 获取剩余商品数量
     *
     * @return surplus_count - 剩余商品数量
     */
    public Integer getSurplusCount() {
        return surplusCount;
    }

    /**
     * 设置剩余商品数量
     *
     * @param surplusCount 剩余商品数量
     */
    public void setSurplusCount(Integer surplusCount) {
        this.surplusCount = surplusCount;
    }

    /**
     * 获取该奖品状态 1.正常 2.下架
     *
     * @return result - 该奖品状态 1.正常 2.下架
     */
    public Integer getResult() {
        return result;
    }

    /**
     * 设置该奖品状态 1.正常 2.下架
     *
     * @param result 该奖品状态 1.正常 2.下架
     */
    public void setResult(Integer result) {
        this.result = result;
    }

    /**
     * 获取抽奖概率（百分比，例：50%）
     *
     * @return chance - 抽奖概率（百分比，例：50%）
     */
    public Double getChance() {
        return chance;
    }

    /**
     * 设置抽奖概率（百分比，例：50%）
     *
     * @param chance 抽奖概率（百分比，例：50%）
     */
    public void setChance(Double chance) {
        this.chance = chance;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        sb.append(", boonId=").append(boonId);
        sb.append(", prizeType=").append(prizeType);
        sb.append(", dealType=").append(dealType);
        sb.append(", name=").append(name);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", prizeCount=").append(prizeCount);
        sb.append(", count=").append(count);
        sb.append(", surplusCount=").append(surplusCount);
        sb.append(", result=").append(result);
        sb.append(", chance=").append(chance);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}