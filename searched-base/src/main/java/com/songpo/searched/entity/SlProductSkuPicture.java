package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_product_sku_picture")
public class SlProductSkuPicture implements Serializable {
    /**
     * sku图片id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private Integer goodsId;

    /**
     * 店铺id
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 主规格id
     */
    @Column(name = "spec_id")
    private Integer specId;

    /**
     * 规格值id
     */
    @Column(name = "spec_value_id")
    private Integer specValueId;

    /**
     * 图片id 多个用逗号隔开
     */
    @Column(name = "sku_img_array")
    private String skuImgArray;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Integer createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Integer modifyTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取sku图片id
     *
     * @return id - sku图片id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置sku图片id
     *
     * @param id sku图片id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品id
     *
     * @return goods_id - 商品id
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品id
     *
     * @param goodsId 商品id
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取店铺id
     *
     * @return shop_id - 店铺id
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 设置店铺id
     *
     * @param shopId 店铺id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取主规格id
     *
     * @return spec_id - 主规格id
     */
    public Integer getSpecId() {
        return specId;
    }

    /**
     * 设置主规格id
     *
     * @param specId 主规格id
     */
    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    /**
     * 获取规格值id
     *
     * @return spec_value_id - 规格值id
     */
    public Integer getSpecValueId() {
        return specValueId;
    }

    /**
     * 设置规格值id
     *
     * @param specValueId 规格值id
     */
    public void setSpecValueId(Integer specValueId) {
        this.specValueId = specValueId;
    }

    /**
     * 获取图片id 多个用逗号隔开
     *
     * @return sku_img_array - 图片id 多个用逗号隔开
     */
    public String getSkuImgArray() {
        return skuImgArray;
    }

    /**
     * 设置图片id 多个用逗号隔开
     *
     * @param skuImgArray 图片id 多个用逗号隔开
     */
    public void setSkuImgArray(String skuImgArray) {
        this.skuImgArray = skuImgArray == null ? null : skuImgArray.trim();
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
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Integer getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Integer modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", shopId=").append(shopId);
        sb.append(", specId=").append(specId);
        sb.append(", specValueId=").append(specValueId);
        sb.append(", skuImgArray=").append(skuImgArray);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}