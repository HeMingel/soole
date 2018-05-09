package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "sl_presell_returned_record")
public class SlPresellReturnedRecord implements Serializable {
    /**
     * 返利唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 商品唯一标识符
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 产品仓库id
     */
    @Column(name = "product_repository_id")
    private String productRepositoryId;

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
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 1.预售模式2.消费返利模式
     */
    private String type;

    /**
     * 奖励年限(仅用于消费j奖励)
     */
    @Column(name = "award_year")
    private String awardYear;

    /**
     * 每日奖励金额(仅用于消费奖励)
     */
    @Column(name = "everyday_money")
    private BigDecimal everydayMoney;

    /**
     * 期数计数
     */
    @Column(name = "number_of_periods")
    private Integer numberOfPeriods;

    private static final long serialVersionUID = 1L;

    /**
     * 获取返利唯一标识符
     *
     * @return id - 返利唯一标识符
     */
    public String getId() {
        return id;
    }

    /**
     * 设置返利唯一标识符
     *
     * @param id 返利唯一标识符
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
     * 获取产品仓库id
     *
     * @return product_repository_id - 产品仓库id
     */
    public String getProductRepositoryId() {
        return productRepositoryId;
    }

    /**
     * 设置产品仓库id
     *
     * @param productRepositoryId 产品仓库id
     */
    public void setProductRepositoryId(String productRepositoryId) {
        this.productRepositoryId = productRepositoryId == null ? null : productRepositoryId.trim();
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
     * 获取1.预售模式2.消费返利模式
     *
     * @return type - 1.预售模式2.消费返利模式
     */
    public String getType() {
        return type;
    }

    /**
     * 设置1.预售模式2.消费返利模式
     *
     * @param type 1.预售模式2.消费返利模式
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 获取奖励年限(仅用于消费j奖励)
     *
     * @return award_year - 奖励年限(仅用于消费j奖励)
     */
    public String getAwardYear() {
        return awardYear;
    }

    /**
     * 设置奖励年限(仅用于消费j奖励)
     *
     * @param awardYear 奖励年限(仅用于消费j奖励)
     */
    public void setAwardYear(String awardYear) {
        this.awardYear = awardYear == null ? null : awardYear.trim();
    }

    /**
     * 获取每日奖励金额(仅用于消费奖励)
     *
     * @return everyday_money - 每日奖励金额(仅用于消费奖励)
     */
    public BigDecimal getEverydayMoney() {
        return everydayMoney;
    }

    /**
     * 设置每日奖励金额(仅用于消费奖励)
     *
     * @param everydayMoney 每日奖励金额(仅用于消费奖励)
     */
    public void setEverydayMoney(BigDecimal everydayMoney) {
        this.everydayMoney = everydayMoney;
    }

    /**
     * 获取期数计数
     *
     * @return number_of_periods - 期数计数
     */
    public Integer getNumberOfPeriods() {
        return numberOfPeriods;
    }

    /**
     * 设置期数计数
     *
     * @param numberOfPeriods 期数计数
     */
    public void setNumberOfPeriods(Integer numberOfPeriods) {
        this.numberOfPeriods = numberOfPeriods;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", productRepositoryId=").append(productRepositoryId);
        sb.append(", shopId=").append(shopId);
        sb.append(", returnedMoney=").append(returnedMoney);
        sb.append(", returnedNumber=").append(returnedNumber);
        sb.append(", createTime=").append(createTime);
        sb.append(", type=").append(type);
        sb.append(", awardYear=").append(awardYear);
        sb.append(", everydayMoney=").append(everydayMoney);
        sb.append(", numberOfPeriods=").append(numberOfPeriods);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}