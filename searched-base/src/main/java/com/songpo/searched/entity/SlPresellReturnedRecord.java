package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "sl_presell_returned_record")
public class SlPresellReturnedRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 商品唯一标识符
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 店铺唯一标识符
     */
    @Column(name = "shop_id")
    private String shopId;

    /**
     * 回款金额
     */
    @Column(name = "returned_money")
    private BigDecimal returnedMoney;

    /**
     * 回款天数
     */
    @Column(name = "returned_number")
    private Integer returnedNumber;

    /**
     * 回款状态1.已发货 2.未发货 3.可发货 4.已逾期
     */
    @Column(name = "returned_num")
    private Integer returnedNum;

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
     * 获取商品唯一标识符
     *
     * @return product_id - 商品唯一标识符
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置商品唯一标识符
     *
     * @param productId 商品唯一标识符
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * 获取店铺唯一标识符
     *
     * @return shop_id - 店铺唯一标识符
     */
    public String getShopId() {
        return shopId;
    }

    /**
     * 设置店铺唯一标识符
     *
     * @param shopId 店铺唯一标识符
     */
    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    /**
     * 获取回款金额
     *
     * @return returned_money - 回款金额
     */
    public BigDecimal getReturnedMoney() {
        return returnedMoney;
    }

    /**
     * 设置回款金额
     *
     * @param returnedMoney 回款金额
     */
    public void setReturnedMoney(BigDecimal returnedMoney) {
        this.returnedMoney = returnedMoney;
    }

    /**
     * 获取回款天数
     *
     * @return returned_number - 回款天数
     */
    public Integer getReturnedNumber() {
        return returnedNumber;
    }

    /**
     * 设置回款天数
     *
     * @param returnedNumber 回款天数
     */
    public void setReturnedNumber(Integer returnedNumber) {
        this.returnedNumber = returnedNumber;
    }

    /**
     * 获取回款状态1.已发货 2.未发货 3.可发货 4.已逾期
     *
     * @return returned_num - 回款状态1.已发货 2.未发货 3.可发货 4.已逾期
     */
    public Integer getReturnedNum() {
        return returnedNum;
    }

    /**
     * 设置回款状态1.已发货 2.未发货 3.可发货 4.已逾期
     *
     * @param returnedNum 回款状态1.已发货 2.未发货 3.可发货 4.已逾期
     */
    public void setReturnedNum(Integer returnedNum) {
        this.returnedNum = returnedNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", shopId=").append(shopId);
        sb.append(", returnedMoney=").append(returnedMoney);
        sb.append(", returnedNumber=").append(returnedNumber);
        sb.append(", returnedNum=").append(returnedNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}