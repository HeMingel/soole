package com.songpo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_my_collection")
public class SlMyCollection implements Serializable {
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
     * 收藏唯一标识符
     */
    @Column(name = "collection_id")
    private String collectionId;
    /**
     * 1：收藏的为店铺
     * 2：收藏的为商品
     */
    private Byte type;

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
     * 获取收藏唯一标识符
     *
     * @return collection_id - 收藏唯一标识符
     */
    public String getCollectionId() {
        return collectionId;
    }

    /**
     * 设置收藏唯一标识符
     *
     * @param collectionId 收藏唯一标识符
     */
    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId == null ? null : collectionId.trim();
    }

    /**
     * 获取1：收藏的为店铺
     * 2：收藏的为商品
     *
     * @return type - 1：收藏的为店铺
     * 2：收藏的为商品
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置1：收藏的为店铺
     * 2：收藏的为商品
     *
     * @param type 1：收藏的为店铺
     *             2：收藏的为商品
     */
    public void setType(Byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", collectionId=").append(collectionId);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}