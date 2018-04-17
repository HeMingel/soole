package com.songpo.searched.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sl_user_relation")
public class SlUserRelation implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 用户唯一标识
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 被推荐人电话
     */
    @Column(name = "recommended_phone")
    private String recommendedPhone;
    /**
     * 商品Id
     */
    @Column(name = "goodsId")
    private String goodsid;
    /**
     * 创建时间
     */
    @Column(name = "creat_time")
    private String creatTime;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取用户唯一标识
     *
     * @return user_id - 用户唯一标识
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户唯一标识
     *
     * @param userId 用户唯一标识
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取被推荐人电话
     *
     * @return recommended_phone - 被推荐人电话
     */
    public String getRecommendedPhone() {
        return recommendedPhone;
    }

    /**
     * 设置被推荐人电话
     *
     * @param recommendedPhone 被推荐人电话
     */
    public void setRecommendedPhone(String recommendedPhone) {
        this.recommendedPhone = recommendedPhone == null ? null : recommendedPhone.trim();
    }

    /**
     * 获取商品Id
     *
     * @return goodsId - 商品Id
     */
    public String getGoodsid() {
        return goodsid;
    }

    /**
     * 设置商品Id
     *
     * @param goodsid 商品Id
     */
    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid == null ? null : goodsid.trim();
    }

    /**
     * 获取创建时间
     *
     * @return creat_time - 创建时间
     */
    public String getCreatTime() {
        return creatTime;
    }

    /**
     * 设置创建时间
     *
     * @param creatTime 创建时间
     */
    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime == null ? null : creatTime.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", recommendedPhone=").append(recommendedPhone);
        sb.append(", goodsid=").append(goodsid);
        sb.append(", creatTime=").append(creatTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}