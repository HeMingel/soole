package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_product")
public class SlProduct implements Serializable {
    /**
     * 商品id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 店铺id
     */
    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 商品分类id
     */
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 一级分类id
     */
    @Column(name = "category_id_1")
    private Integer categoryId1;

    /**
     * 二级分类id
     */
    @Column(name = "category_id_2")
    private Integer categoryId2;

    /**
     * 三级分类id
     */
    @Column(name = "category_id_3")
    private Integer categoryId3;

    /**
     * 商品销售模式id
     */
    @Column(name = "sales_mode_id")
    private String salesModeId;

    /**
     * （暂时无用，销售模式即为活动类型）活动类型 0无促销，1拼团，2限时折扣 3.新人专享
     */
    @Column(name = "activity_type")
    private Byte activityType;

    /**
     * （暂时无用）活动ID
     */
    @Column(name = "activity_id")
    private Integer activityId;

    /**
     * 实物或虚拟商品标志 1实物商品 0 虚拟商品 2 F码商品
     */
    @Column(name = "goods_type")
    private Byte goodsType;

    /**
     * 市场价
     */
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 成本价
     */
    @Column(name = "cost_price")
    private BigDecimal costPrice;

    /**
     * 商品原价格（原售价）
     */
    private BigDecimal price;

    /**
     * 商品促销价格
     */
    @Column(name = "promotion_price")
    private BigDecimal promotionPrice;

    /**
     * 购买商品赠送积分
     */
    @Column(name = "give_point")
    private Integer givePoint;

    /**
     * 运费 0为免运费
     */
    @Column(name = "shipping_fee")
    private BigDecimal shippingFee;

    /**
     * 售卖区域id （0为全国包邮，不为0则对应查询shipping_area表中对应数据，获取包邮，不包邮地区）
     */
    @Column(name = "shipping_area_id")
    private Integer shippingAreaId;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 限购 0 不限购
     */
    @Column(name = "max_buy")
    private Integer maxBuy;

    /**
     * 商品点击数量（查看数）
     */
    private Integer clicks;

    /**
     * 库存预警值
     */
    @Column(name = "min_stock_alarm")
    private Integer minStockAlarm;

    /**
     * 虚拟销量
     */
    @Column(name = "sales_virtual")
    private Integer salesVirtual;

    /**
     * 实际销量
     */
    @Column(name = "sales_volume")
    private Integer salesVolume;

    /**
     * 收藏数量
     */
    private Integer collects;

    /**
     * 评价数
     */
    @Column(name = "comment_num")
    private Integer commentNum;

    /**
     * 分享数
     */
    private Integer shares;

    /**
     * 商品主图
     */
    private String picture;

    /**
     * 商品关键词
     */
    private String keywords;

    /**
     * 商品描述
     */
    private String introduction;

    /**
     * 商品二维码
     */
    @Column(name = "QRcode")
    private String qrcode;

    /**
     * 是否热销商品
     */
    @Column(name = "is_hot")
    private Integer isHot;

    /**
     * 是否推荐
     */
    @Column(name = "is_recommend")
    private Integer isRecommend;

    /**
     * 是否新品
     */
    @Column(name = "is_new")
    private Integer isNew;

    /**
     * 商品状态 0下架，1正常，2.待审核   3.未通过   10违规（禁售）
     */
    private Byte state;

    /**
     * 逻辑删除 0不删除 1删除
     */
    private Boolean del;

    /**
     * 商品所有图片集合（多个图片id之间用 , 隔开）
     */
    @Column(name = "img_id_array")
    private String imgIdArray;

    /**
     * 商品sku应用图片列表  属性,属性值，图片ID
     */
    @Column(name = "sku_img_array")
    private String skuImgArray;

    /**
     * 商品重量（规划字段）
     */
    @Column(name = "goods_weight")
    private BigDecimal goodsWeight;

    /**
     * 商品体积（规划字段）
     */
    @Column(name = "goods_volume")
    private BigDecimal goodsVolume;

    /**
     * 计价方式1.重量2.体积3.计件（规划字段）
     */
    @Column(name = "shipping_fee_type")
    private Integer shippingFeeType;

    /**
     * 上下架时间
     */
    @Column(name = "sale_date")
    private Date saleDate;

    /**
     * 审核时间(预售商品/了豆商品/消费返利商品审核时间)
     */
    @Column(name = "pro_check_time")
    private Date proCheckTime;

    /**
     * 商品添加时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 商品编辑时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 商品规格
     */
    @Column(name = "goods_spec_format")
    private String goodsSpecFormat;

    private static final long serialVersionUID = 1L;

    /**
     * 获取商品id
     *
     * @return id - 商品id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品id
     *
     * @param id 商品id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取商品分类id
     *
     * @return category_id - 商品分类id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置商品分类id
     *
     * @param categoryId 商品分类id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取一级分类id
     *
     * @return category_id_1 - 一级分类id
     */
    public Integer getCategoryId1() {
        return categoryId1;
    }

    /**
     * 设置一级分类id
     *
     * @param categoryId1 一级分类id
     */
    public void setCategoryId1(Integer categoryId1) {
        this.categoryId1 = categoryId1;
    }

    /**
     * 获取二级分类id
     *
     * @return category_id_2 - 二级分类id
     */
    public Integer getCategoryId2() {
        return categoryId2;
    }

    /**
     * 设置二级分类id
     *
     * @param categoryId2 二级分类id
     */
    public void setCategoryId2(Integer categoryId2) {
        this.categoryId2 = categoryId2;
    }

    /**
     * 获取三级分类id
     *
     * @return category_id_3 - 三级分类id
     */
    public Integer getCategoryId3() {
        return categoryId3;
    }

    /**
     * 设置三级分类id
     *
     * @param categoryId3 三级分类id
     */
    public void setCategoryId3(Integer categoryId3) {
        this.categoryId3 = categoryId3;
    }

    /**
     * 获取商品销售模式id
     *
     * @return sales_mode_id - 商品销售模式id
     */
    public String getSalesModeId() {
        return salesModeId;
    }

    /**
     * 设置商品销售模式id
     *
     * @param salesModeId 商品销售模式id
     */
    public void setSalesModeId(String salesModeId) {
        this.salesModeId = salesModeId == null ? null : salesModeId.trim();
    }

    /**
     * 获取（暂时无用，销售模式即为活动类型）活动类型 0无促销，1拼团，2限时折扣 3.新人专享
     *
     * @return activity_type - （暂时无用，销售模式即为活动类型）活动类型 0无促销，1拼团，2限时折扣 3.新人专享
     */
    public Byte getActivityType() {
        return activityType;
    }

    /**
     * 设置（暂时无用，销售模式即为活动类型）活动类型 0无促销，1拼团，2限时折扣 3.新人专享
     *
     * @param activityType （暂时无用，销售模式即为活动类型）活动类型 0无促销，1拼团，2限时折扣 3.新人专享
     */
    public void setActivityType(Byte activityType) {
        this.activityType = activityType;
    }

    /**
     * 获取（暂时无用）活动ID
     *
     * @return activity_id - （暂时无用）活动ID
     */
    public Integer getActivityId() {
        return activityId;
    }

    /**
     * 设置（暂时无用）活动ID
     *
     * @param activityId （暂时无用）活动ID
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     * 获取实物或虚拟商品标志 1实物商品 0 虚拟商品 2 F码商品
     *
     * @return goods_type - 实物或虚拟商品标志 1实物商品 0 虚拟商品 2 F码商品
     */
    public Byte getGoodsType() {
        return goodsType;
    }

    /**
     * 设置实物或虚拟商品标志 1实物商品 0 虚拟商品 2 F码商品
     *
     * @param goodsType 实物或虚拟商品标志 1实物商品 0 虚拟商品 2 F码商品
     */
    public void setGoodsType(Byte goodsType) {
        this.goodsType = goodsType;
    }

    /**
     * 获取市场价
     *
     * @return market_price - 市场价
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * 设置市场价
     *
     * @param marketPrice 市场价
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * 获取成本价
     *
     * @return cost_price - 成本价
     */
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    /**
     * 设置成本价
     *
     * @param costPrice 成本价
     */
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    /**
     * 获取商品原价格（原售价）
     *
     * @return price - 商品原价格（原售价）
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置商品原价格（原售价）
     *
     * @param price 商品原价格（原售价）
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取商品促销价格
     *
     * @return promotion_price - 商品促销价格
     */
    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    /**
     * 设置商品促销价格
     *
     * @param promotionPrice 商品促销价格
     */
    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    /**
     * 获取购买商品赠送积分
     *
     * @return give_point - 购买商品赠送积分
     */
    public Integer getGivePoint() {
        return givePoint;
    }

    /**
     * 设置购买商品赠送积分
     *
     * @param givePoint 购买商品赠送积分
     */
    public void setGivePoint(Integer givePoint) {
        this.givePoint = givePoint;
    }

    /**
     * 获取运费 0为免运费
     *
     * @return shipping_fee - 运费 0为免运费
     */
    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    /**
     * 设置运费 0为免运费
     *
     * @param shippingFee 运费 0为免运费
     */
    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    /**
     * 获取售卖区域id （0为全国包邮，不为0则对应查询shipping_area表中对应数据，获取包邮，不包邮地区）
     *
     * @return shipping_area_id - 售卖区域id （0为全国包邮，不为0则对应查询shipping_area表中对应数据，获取包邮，不包邮地区）
     */
    public Integer getShippingAreaId() {
        return shippingAreaId;
    }

    /**
     * 设置售卖区域id （0为全国包邮，不为0则对应查询shipping_area表中对应数据，获取包邮，不包邮地区）
     *
     * @param shippingAreaId 售卖区域id （0为全国包邮，不为0则对应查询shipping_area表中对应数据，获取包邮，不包邮地区）
     */
    public void setShippingAreaId(Integer shippingAreaId) {
        this.shippingAreaId = shippingAreaId;
    }

    /**
     * 获取商品库存
     *
     * @return stock - 商品库存
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置商品库存
     *
     * @param stock 商品库存
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取限购 0 不限购
     *
     * @return max_buy - 限购 0 不限购
     */
    public Integer getMaxBuy() {
        return maxBuy;
    }

    /**
     * 设置限购 0 不限购
     *
     * @param maxBuy 限购 0 不限购
     */
    public void setMaxBuy(Integer maxBuy) {
        this.maxBuy = maxBuy;
    }

    /**
     * 获取商品点击数量（查看数）
     *
     * @return clicks - 商品点击数量（查看数）
     */
    public Integer getClicks() {
        return clicks;
    }

    /**
     * 设置商品点击数量（查看数）
     *
     * @param clicks 商品点击数量（查看数）
     */
    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    /**
     * 获取库存预警值
     *
     * @return min_stock_alarm - 库存预警值
     */
    public Integer getMinStockAlarm() {
        return minStockAlarm;
    }

    /**
     * 设置库存预警值
     *
     * @param minStockAlarm 库存预警值
     */
    public void setMinStockAlarm(Integer minStockAlarm) {
        this.minStockAlarm = minStockAlarm;
    }

    /**
     * 获取虚拟销量
     *
     * @return sales_virtual - 虚拟销量
     */
    public Integer getSalesVirtual() {
        return salesVirtual;
    }

    /**
     * 设置虚拟销量
     *
     * @param salesVirtual 虚拟销量
     */
    public void setSalesVirtual(Integer salesVirtual) {
        this.salesVirtual = salesVirtual;
    }

    /**
     * 获取实际销量
     *
     * @return sales_volume - 实际销量
     */
    public Integer getSalesVolume() {
        return salesVolume;
    }

    /**
     * 设置实际销量
     *
     * @param salesVolume 实际销量
     */
    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    /**
     * 获取收藏数量
     *
     * @return collects - 收藏数量
     */
    public Integer getCollects() {
        return collects;
    }

    /**
     * 设置收藏数量
     *
     * @param collects 收藏数量
     */
    public void setCollects(Integer collects) {
        this.collects = collects;
    }

    /**
     * 获取评价数
     *
     * @return comment_num - 评价数
     */
    public Integer getCommentNum() {
        return commentNum;
    }

    /**
     * 设置评价数
     *
     * @param commentNum 评价数
     */
    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    /**
     * 获取分享数
     *
     * @return shares - 分享数
     */
    public Integer getShares() {
        return shares;
    }

    /**
     * 设置分享数
     *
     * @param shares 分享数
     */
    public void setShares(Integer shares) {
        this.shares = shares;
    }

    /**
     * 获取商品主图
     *
     * @return picture - 商品主图
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 设置商品主图
     *
     * @param picture 商品主图
     */
    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    /**
     * 获取商品关键词
     *
     * @return keywords - 商品关键词
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * 设置商品关键词
     *
     * @param keywords 商品关键词
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    /**
     * 获取商品描述
     *
     * @return introduction - 商品描述
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 设置商品描述
     *
     * @param introduction 商品描述
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    /**
     * 获取商品二维码
     *
     * @return QRcode - 商品二维码
     */
    public String getQrcode() {
        return qrcode;
    }

    /**
     * 设置商品二维码
     *
     * @param qrcode 商品二维码
     */
    public void setQrcode(String qrcode) {
        this.qrcode = qrcode == null ? null : qrcode.trim();
    }

    /**
     * 获取是否热销商品
     *
     * @return is_hot - 是否热销商品
     */
    public Integer getIsHot() {
        return isHot;
    }

    /**
     * 设置是否热销商品
     *
     * @param isHot 是否热销商品
     */
    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    /**
     * 获取是否推荐
     *
     * @return is_recommend - 是否推荐
     */
    public Integer getIsRecommend() {
        return isRecommend;
    }

    /**
     * 设置是否推荐
     *
     * @param isRecommend 是否推荐
     */
    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    /**
     * 获取是否新品
     *
     * @return is_new - 是否新品
     */
    public Integer getIsNew() {
        return isNew;
    }

    /**
     * 设置是否新品
     *
     * @param isNew 是否新品
     */
    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    /**
     * 获取商品状态 0下架，1正常，2.待审核   3.未通过   10违规（禁售）
     *
     * @return state - 商品状态 0下架，1正常，2.待审核   3.未通过   10违规（禁售）
     */
    public Byte getState() {
        return state;
    }

    /**
     * 设置商品状态 0下架，1正常，2.待审核   3.未通过   10违规（禁售）
     *
     * @param state 商品状态 0下架，1正常，2.待审核   3.未通过   10违规（禁售）
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * 获取逻辑删除 0不删除 1删除
     *
     * @return del - 逻辑删除 0不删除 1删除
     */
    public Boolean getDel() {
        return del;
    }

    /**
     * 设置逻辑删除 0不删除 1删除
     *
     * @param del 逻辑删除 0不删除 1删除
     */
    public void setDel(Boolean del) {
        this.del = del;
    }

    /**
     * 获取商品所有图片集合（多个图片id之间用 , 隔开）
     *
     * @return img_id_array - 商品所有图片集合（多个图片id之间用 , 隔开）
     */
    public String getImgIdArray() {
        return imgIdArray;
    }

    /**
     * 设置商品所有图片集合（多个图片id之间用 , 隔开）
     *
     * @param imgIdArray 商品所有图片集合（多个图片id之间用 , 隔开）
     */
    public void setImgIdArray(String imgIdArray) {
        this.imgIdArray = imgIdArray == null ? null : imgIdArray.trim();
    }

    /**
     * 获取商品sku应用图片列表  属性,属性值，图片ID
     *
     * @return sku_img_array - 商品sku应用图片列表  属性,属性值，图片ID
     */
    public String getSkuImgArray() {
        return skuImgArray;
    }

    /**
     * 设置商品sku应用图片列表  属性,属性值，图片ID
     *
     * @param skuImgArray 商品sku应用图片列表  属性,属性值，图片ID
     */
    public void setSkuImgArray(String skuImgArray) {
        this.skuImgArray = skuImgArray == null ? null : skuImgArray.trim();
    }

    /**
     * 获取商品重量（规划字段）
     *
     * @return goods_weight - 商品重量（规划字段）
     */
    public BigDecimal getGoodsWeight() {
        return goodsWeight;
    }

    /**
     * 设置商品重量（规划字段）
     *
     * @param goodsWeight 商品重量（规划字段）
     */
    public void setGoodsWeight(BigDecimal goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    /**
     * 获取商品体积（规划字段）
     *
     * @return goods_volume - 商品体积（规划字段）
     */
    public BigDecimal getGoodsVolume() {
        return goodsVolume;
    }

    /**
     * 设置商品体积（规划字段）
     *
     * @param goodsVolume 商品体积（规划字段）
     */
    public void setGoodsVolume(BigDecimal goodsVolume) {
        this.goodsVolume = goodsVolume;
    }

    /**
     * 获取计价方式1.重量2.体积3.计件（规划字段）
     *
     * @return shipping_fee_type - 计价方式1.重量2.体积3.计件（规划字段）
     */
    public Integer getShippingFeeType() {
        return shippingFeeType;
    }

    /**
     * 设置计价方式1.重量2.体积3.计件（规划字段）
     *
     * @param shippingFeeType 计价方式1.重量2.体积3.计件（规划字段）
     */
    public void setShippingFeeType(Integer shippingFeeType) {
        this.shippingFeeType = shippingFeeType;
    }

    /**
     * 获取上下架时间
     *
     * @return sale_date - 上下架时间
     */
    public Date getSaleDate() {
        return saleDate;
    }

    /**
     * 设置上下架时间
     *
     * @param saleDate 上下架时间
     */
    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    /**
     * 获取审核时间(预售商品/了豆商品/消费返利商品审核时间)
     *
     * @return pro_check_time - 审核时间(预售商品/了豆商品/消费返利商品审核时间)
     */
    public Date getProCheckTime() {
        return proCheckTime;
    }

    /**
     * 设置审核时间(预售商品/了豆商品/消费返利商品审核时间)
     *
     * @param proCheckTime 审核时间(预售商品/了豆商品/消费返利商品审核时间)
     */
    public void setProCheckTime(Date proCheckTime) {
        this.proCheckTime = proCheckTime;
    }

    /**
     * 获取商品添加时间
     *
     * @return create_time - 商品添加时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置商品添加时间
     *
     * @param createTime 商品添加时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取商品编辑时间
     *
     * @return update_time - 商品编辑时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置商品编辑时间
     *
     * @param updateTime 商品编辑时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取商品详情
     *
     * @return detail - 商品详情
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 设置商品详情
     *
     * @param detail 商品详情
     */
    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    /**
     * 获取商品规格
     *
     * @return goods_spec_format - 商品规格
     */
    public String getGoodsSpecFormat() {
        return goodsSpecFormat;
    }

    /**
     * 设置商品规格
     *
     * @param goodsSpecFormat 商品规格
     */
    public void setGoodsSpecFormat(String goodsSpecFormat) {
        this.goodsSpecFormat = goodsSpecFormat == null ? null : goodsSpecFormat.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", shopId=").append(shopId);
        sb.append(", sort=").append(sort);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", categoryId1=").append(categoryId1);
        sb.append(", categoryId2=").append(categoryId2);
        sb.append(", categoryId3=").append(categoryId3);
        sb.append(", salesModeId=").append(salesModeId);
        sb.append(", activityType=").append(activityType);
        sb.append(", activityId=").append(activityId);
        sb.append(", goodsType=").append(goodsType);
        sb.append(", marketPrice=").append(marketPrice);
        sb.append(", costPrice=").append(costPrice);
        sb.append(", price=").append(price);
        sb.append(", promotionPrice=").append(promotionPrice);
        sb.append(", givePoint=").append(givePoint);
        sb.append(", shippingFee=").append(shippingFee);
        sb.append(", shippingAreaId=").append(shippingAreaId);
        sb.append(", stock=").append(stock);
        sb.append(", maxBuy=").append(maxBuy);
        sb.append(", clicks=").append(clicks);
        sb.append(", minStockAlarm=").append(minStockAlarm);
        sb.append(", salesVirtual=").append(salesVirtual);
        sb.append(", salesVolume=").append(salesVolume);
        sb.append(", collects=").append(collects);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", shares=").append(shares);
        sb.append(", picture=").append(picture);
        sb.append(", keywords=").append(keywords);
        sb.append(", introduction=").append(introduction);
        sb.append(", qrcode=").append(qrcode);
        sb.append(", isHot=").append(isHot);
        sb.append(", isRecommend=").append(isRecommend);
        sb.append(", isNew=").append(isNew);
        sb.append(", state=").append(state);
        sb.append(", del=").append(del);
        sb.append(", imgIdArray=").append(imgIdArray);
        sb.append(", skuImgArray=").append(skuImgArray);
        sb.append(", goodsWeight=").append(goodsWeight);
        sb.append(", goodsVolume=").append(goodsVolume);
        sb.append(", shippingFeeType=").append(shippingFeeType);
        sb.append(", saleDate=").append(saleDate);
        sb.append(", proCheckTime=").append(proCheckTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", detail=").append(detail);
        sb.append(", goodsSpecFormat=").append(goodsSpecFormat);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}