package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_product_comment_image")
public class SlProductCommentImage implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 商品评论唯一标识符
     */
    @Column(name = "product_comment_id")
    private String productCommentId;

    /**
     * 图片地址
     */
    @Column(name = "image_url")
    private String imageUrl;

    private static final long serialVersionUID = 1L;

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
     * 获取商品评论唯一标识符
     *
     * @return product_comment_id - 商品评论唯一标识符
     */
    public String getProductCommentId() {
        return productCommentId;
    }

    /**
     * 设置商品评论唯一标识符
     *
     * @param productCommentId 商品评论唯一标识符
     */
    public void setProductCommentId(String productCommentId) {
        this.productCommentId = productCommentId == null ? null : productCommentId.trim();
    }

    /**
     * 获取图片地址
     *
     * @return image_url - 图片地址
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置图片地址
     *
     * @param imageUrl 图片地址
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productCommentId=").append(productCommentId);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}