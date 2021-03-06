package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_transaction_detail")
public class SlTransactionDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 对方id（转账，邀请好友使用）
     */
    @Column(name = "source_id")
    private String sourceId;

    /**
     * 当前用户id
     */
    @Column(name = "target_id")
    private String targetId;

    /**
     * 红包id
     */
    @Column(name = "red_packet_id")
    private String redPacketId;

    /**
     * 订单id（购物相关使用）
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 分享记录id（分享使用）
     */
    @Column(name = "share_id")
    private String shareId;

    /**
     * 消费方式 
（1-99：红包、转账业务）1.转账 2. 接收转账 3.发红包 4.抢红包 5.红包过期退回 6.余额提现  7.中军创券兑换银豆  8.余额充值 

（100-199：活动相关） 100：新人礼包（平台赠送）  101：签到  102：邀请好友  103：分享获得  104:区块链商品邀请人收益  105：金豆兑换搜了贝

  106：SLB兑换暂未开放，退还金豆（200-299：购物相关） 200：购物支付  201：购物赠送  202：评价晒单 

（300-400：收益相关）300店主收益

（500-600：猴王游戏相关）501:收益 502支出
     */
    private Integer type;

    /**
     * 提现转款截图(限后台财务余额提现转款使用)
     */
    @Column(name = "money_img")
    private String moneyImg;

    /**
     * 提现转款确认1待确认2已确认(限后台财务余额提现转款使用)
     */
    @Column(name = "money_state")
    private Integer moneyState;

    /**
     * 提现转款订单确认人员(限后台财务余额提现转款使用)
     */
    @Column(name = "conf_name")
    private String confName;

    /**
     * 转款记录人员(限后台财务余额提现转款使用)
     */
    @Column(name = "money_name")
    private String moneyName;

    /**
     * 交易金额
     */
    private BigDecimal money;

    /**
     * 交易金豆数量
     */
    private Integer coin;

    /**
     * 交易银豆数量
     */
    private Integer silver;

    /**
     * 交易货币类型 1.账户余额 2.略（未使用） 3.钱  4.钱+豆（未使用） 5.金豆 6.银豆
     */
    @Column(name = "deal_type")
    private Integer dealType;

    /**
     * 交易类型  1.支出  2.收入
     */
    @Column(name = "transaction_type")
    private Integer transactionType;

    /**
     * 交易状态：1-已生效；2-未生效
     */
    @Column(name = "transaction_status")
    private Byte transactionStatus;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 最后更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

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
     * 获取对方id（转账，邀请好友使用）
     *
     * @return source_id - 对方id（转账，邀请好友使用）
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * 设置对方id（转账，邀请好友使用）
     *
     * @param sourceId 对方id（转账，邀请好友使用）
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }

    /**
     * 获取当前用户id
     *
     * @return target_id - 当前用户id
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * 设置当前用户id
     *
     * @param targetId 当前用户id
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    /**
     * 获取红包id
     *
     * @return red_packet_id - 红包id
     */
    public String getRedPacketId() {
        return redPacketId;
    }

    /**
     * 设置红包id
     *
     * @param redPacketId 红包id
     */
    public void setRedPacketId(String redPacketId) {
        this.redPacketId = redPacketId == null ? null : redPacketId.trim();
    }

    /**
     * 获取订单id（购物相关使用）
     *
     * @return order_id - 订单id（购物相关使用）
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id（购物相关使用）
     *
     * @param orderId 订单id（购物相关使用）
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 获取分享记录id（分享使用）
     *
     * @return share_id - 分享记录id（分享使用）
     */
    public String getShareId() {
        return shareId;
    }

    /**
     * 设置分享记录id（分享使用）
     *
     * @param shareId 分享记录id（分享使用）
     */
    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    /**
     * 获取消费方式 
（1-99：红包、转账业务）1.转账 2. 接收转账 3.发红包 4.抢红包 5.红包过期退回 6.余额提现  7.中军创券兑换银豆  8.余额充值 

（100-199：活动相关） 100：新人礼包（平台赠送）  101：签到  102：邀请好友  103：分享获得  104:区块链商品邀请人收益  105：金豆兑换搜了贝

  106：SLB兑换暂未开放，退还金豆（200-299：购物相关） 200：购物支付  201：购物赠送  202：评价晒单 

（300-400：收益相关）300店主收益

（500-600：猴王游戏相关）501:收益 502支出
     *
     * @return type - 消费方式 
（1-99：红包、转账业务）1.转账 2. 接收转账 3.发红包 4.抢红包 5.红包过期退回 6.余额提现  7.中军创券兑换银豆  8.余额充值 

（100-199：活动相关） 100：新人礼包（平台赠送）  101：签到  102：邀请好友  103：分享获得  104:区块链商品邀请人收益  105：金豆兑换搜了贝

  106：SLB兑换暂未开放，退还金豆（200-299：购物相关） 200：购物支付  201：购物赠送  202：评价晒单 

（300-400：收益相关）300店主收益

（500-600：猴王游戏相关）501:收益 502支出
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置消费方式 
（1-99：红包、转账业务）1.转账 2. 接收转账 3.发红包 4.抢红包 5.红包过期退回 6.余额提现  7.中军创券兑换银豆  8.余额充值 

（100-199：活动相关） 100：新人礼包（平台赠送）  101：签到  102：邀请好友  103：分享获得  104:区块链商品邀请人收益  105：金豆兑换搜了贝

  106：SLB兑换暂未开放，退还金豆（200-299：购物相关） 200：购物支付  201：购物赠送  202：评价晒单 

（300-400：收益相关）300店主收益

（500-600：猴王游戏相关）501:收益 502支出
     *
     * @param type 消费方式 
（1-99：红包、转账业务）1.转账 2. 接收转账 3.发红包 4.抢红包 5.红包过期退回 6.余额提现  7.中军创券兑换银豆  8.余额充值 

（100-199：活动相关） 100：新人礼包（平台赠送）  101：签到  102：邀请好友  103：分享获得  104:区块链商品邀请人收益  105：金豆兑换搜了贝

  106：SLB兑换暂未开放，退还金豆（200-299：购物相关） 200：购物支付  201：购物赠送  202：评价晒单 

（300-400：收益相关）300店主收益

（500-600：猴王游戏相关）501:收益 502支出
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取提现转款截图(限后台财务余额提现转款使用)
     *
     * @return money_img - 提现转款截图(限后台财务余额提现转款使用)
     */
    public String getMoneyImg() {
        return moneyImg;
    }

    /**
     * 设置提现转款截图(限后台财务余额提现转款使用)
     *
     * @param moneyImg 提现转款截图(限后台财务余额提现转款使用)
     */
    public void setMoneyImg(String moneyImg) {
        this.moneyImg = moneyImg == null ? null : moneyImg.trim();
    }

    /**
     * 获取提现转款确认1待确认2已确认(限后台财务余额提现转款使用)
     *
     * @return money_state - 提现转款确认1待确认2已确认(限后台财务余额提现转款使用)
     */
    public Integer getMoneyState() {
        return moneyState;
    }

    /**
     * 设置提现转款确认1待确认2已确认(限后台财务余额提现转款使用)
     *
     * @param moneyState 提现转款确认1待确认2已确认(限后台财务余额提现转款使用)
     */
    public void setMoneyState(Integer moneyState) {
        this.moneyState = moneyState;
    }

    /**
     * 获取提现转款订单确认人员(限后台财务余额提现转款使用)
     *
     * @return conf_name - 提现转款订单确认人员(限后台财务余额提现转款使用)
     */
    public String getConfName() {
        return confName;
    }

    /**
     * 设置提现转款订单确认人员(限后台财务余额提现转款使用)
     *
     * @param confName 提现转款订单确认人员(限后台财务余额提现转款使用)
     */
    public void setConfName(String confName) {
        this.confName = confName == null ? null : confName.trim();
    }

    /**
     * 获取转款记录人员(限后台财务余额提现转款使用)
     *
     * @return money_name - 转款记录人员(限后台财务余额提现转款使用)
     */
    public String getMoneyName() {
        return moneyName;
    }

    /**
     * 设置转款记录人员(限后台财务余额提现转款使用)
     *
     * @param moneyName 转款记录人员(限后台财务余额提现转款使用)
     */
    public void setMoneyName(String moneyName) {
        this.moneyName = moneyName == null ? null : moneyName.trim();
    }

    /**
     * 获取交易金额
     *
     * @return money - 交易金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置交易金额
     *
     * @param money 交易金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取交易金豆数量
     *
     * @return coin - 交易金豆数量
     */
    public Integer getCoin() {
        return coin;
    }

    /**
     * 设置交易金豆数量
     *
     * @param coin 交易金豆数量
     */
    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    /**
     * 获取交易银豆数量
     *
     * @return silver - 交易银豆数量
     */
    public Integer getSilver() {
        return silver;
    }

    /**
     * 设置交易银豆数量
     *
     * @param silver 交易银豆数量
     */
    public void setSilver(Integer silver) {
        this.silver = silver;
    }

    /**
     * 获取交易货币类型 1.账户余额 2.略（未使用） 3.钱  4.钱+豆（未使用） 5.金豆 6.银豆
     *
     * @return deal_type - 交易货币类型 1.账户余额 2.略（未使用） 3.钱  4.钱+豆（未使用） 5.金豆 6.银豆
     */
    public Integer getDealType() {
        return dealType;
    }

    /**
     * 设置交易货币类型 1.账户余额 2.略（未使用） 3.钱  4.钱+豆（未使用） 5.金豆 6.银豆
     *
     * @param dealType 交易货币类型 1.账户余额 2.略（未使用） 3.钱  4.钱+豆（未使用） 5.金豆 6.银豆
     */
    public void setDealType(Integer dealType) {
        this.dealType = dealType;
    }

    /**
     * 获取交易类型  1.支出  2.收入
     *
     * @return transaction_type - 交易类型  1.支出  2.收入
     */
    public Integer getTransactionType() {
        return transactionType;
    }

    /**
     * 设置交易类型  1.支出  2.收入
     *
     * @param transactionType 交易类型  1.支出  2.收入
     */
    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * 获取交易状态：1-已生效；2-未生效
     *
     * @return transaction_status - 交易状态：1-已生效；2-未生效
     */
    public Byte getTransactionStatus() {
        return transactionStatus;
    }

    /**
     * 设置交易状态：1-已生效；2-未生效
     *
     * @param transactionStatus 交易状态：1-已生效；2-未生效
     */
    public void setTransactionStatus(Byte transactionStatus) {
        this.transactionStatus = transactionStatus;
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
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取最后更新时间
     *
     * @return updated_at - 最后更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置最后更新时间
     *
     * @param updatedAt 最后更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sourceId=").append(sourceId);
        sb.append(", targetId=").append(targetId);
        sb.append(", redPacketId=").append(redPacketId);
        sb.append(", orderId=").append(orderId);
        sb.append(", shareId=").append(shareId);
        sb.append(", type=").append(type);
        sb.append(", moneyImg=").append(moneyImg);
        sb.append(", moneyState=").append(moneyState);
        sb.append(", confName=").append(confName);
        sb.append(", moneyName=").append(moneyName);
        sb.append(", money=").append(money);
        sb.append(", coin=").append(coin);
        sb.append(", silver=").append(silver);
        sb.append(", dealType=").append(dealType);
        sb.append(", transactionType=").append(transactionType);
        sb.append(", transactionStatus=").append(transactionStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}