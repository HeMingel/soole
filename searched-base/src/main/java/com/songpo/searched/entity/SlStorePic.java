package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_store_pic")
public class SlStorePic implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 高级店铺审核表ID
     */
    @Column(name = "sp_top_store_id")
    private String spTopStoreId;
    /**
     * 高级店铺资质图片
     */
    @Column(name = "top_store_pic")
    private String topStorePic;
    /**
     * 图片分类1营业执照2房产证3汽车证4其他
     */
    @Column(name = "store_class")
    private Byte storeClass;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取高级店铺审核表ID
     *
     * @return sp_top_store_id - 高级店铺审核表ID
     */
    public String getSpTopStoreId() {
        return spTopStoreId;
    }

    /**
     * 设置高级店铺审核表ID
     *
     * @param spTopStoreId 高级店铺审核表ID
     */
    public void setSpTopStoreId(String spTopStoreId) {
        this.spTopStoreId = spTopStoreId == null ? null : spTopStoreId.trim();
    }

    /**
     * 获取高级店铺资质图片
     *
     * @return top_store_pic - 高级店铺资质图片
     */
    public String getTopStorePic() {
        return topStorePic;
    }

    /**
     * 设置高级店铺资质图片
     *
     * @param topStorePic 高级店铺资质图片
     */
    public void setTopStorePic(String topStorePic) {
        this.topStorePic = topStorePic == null ? null : topStorePic.trim();
    }

    /**
     * 获取图片分类1营业执照2房产证3汽车证4其他
     *
     * @return store_class - 图片分类1营业执照2房产证3汽车证4其他
     */
    public Byte getStoreClass() {
        return storeClass;
    }

    /**
     * 设置图片分类1营业执照2房产证3汽车证4其他
     *
     * @param storeClass 图片分类1营业执照2房产证3汽车证4其他
     */
    public void setStoreClass(Byte storeClass) {
        this.storeClass = storeClass;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", spTopStoreId=").append(spTopStoreId);
        sb.append(", topStorePic=").append(topStorePic);
        sb.append(", storeClass=").append(storeClass);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}