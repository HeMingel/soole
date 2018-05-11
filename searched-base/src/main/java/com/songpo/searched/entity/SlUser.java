package com.songpo.searched.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_user")
public class SlUser implements Serializable {
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 会员号（自增）
     */
    private Integer username;

    /**
     * 密码
     */
    private String password;

    /**
     * 支付密码
     */
    @Column(name = "pay_password")
    private String payPassword;

    /**
     * 用户昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 用户真实姓名
     */
    private String name;

    /**
     * 证件类型 1.身份证 2.护照
     */
    @Column(name = "card_type")
    private Boolean cardType;

    /**
     * 证件号
     */
    @Column(name = "card_number")
    private String cardNumber;

    /**
     * 1.男  2.女
     */
    private Boolean sex;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 用户常用地址
     */
    private String address;

    /**
     * 行业
     */
    private String industry;

    @Column(name = "group_var")
    private Byte groupVar;

    /**
     * 金币
     */
    private Integer coin;

    /**
     * 银币
     */
    private Integer silver;

    /**
     * 余额
     */
    private BigDecimal money;

    /**
     * 客户端标识
     */
    @Column(name = "client_id")
    private String clientId;

    /**
     * 客户端密钥
     */
    @Column(name = "client_secret")
    private String clientSecret;

    /**
     * 微信OpenId
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 1：微信  2：QQ  登录方式
     */
    private Integer type;

    /**
     * 账号状态：0 禁用， 1 启用
     */
    private Integer status;

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
     * 获取会员号（自增）
     *
     * @return username - 会员号（自增）
     */
    public Integer getUsername() {
        return username;
    }

    /**
     * 设置会员号（自增）
     *
     * @param username 会员号（自增）
     */
    public void setUsername(Integer username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取支付密码
     *
     * @return pay_password - 支付密码
     */
    public String getPayPassword() {
        return payPassword;
    }

    /**
     * 设置支付密码
     *
     * @param payPassword 支付密码
     */
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword == null ? null : payPassword.trim();
    }

    /**
     * 获取用户昵称
     *
     * @return nick_name - 用户昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置用户昵称
     *
     * @param nickName 用户昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * 获取用户真实姓名
     *
     * @return name - 用户真实姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户真实姓名
     *
     * @param name 用户真实姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取证件类型 1.身份证 2.护照
     *
     * @return card_type - 证件类型 1.身份证 2.护照
     */
    public Boolean getCardType() {
        return cardType;
    }

    /**
     * 设置证件类型 1.身份证 2.护照
     *
     * @param cardType 证件类型 1.身份证 2.护照
     */
    public void setCardType(Boolean cardType) {
        this.cardType = cardType;
    }

    /**
     * 获取证件号
     *
     * @return card_number - 证件号
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * 设置证件号
     *
     * @param cardNumber 证件号
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber == null ? null : cardNumber.trim();
    }

    /**
     * 获取1.男  2.女
     *
     * @return sex - 1.男  2.女
     */
    public Boolean getSex() {
        return sex;
    }

    /**
     * 设置1.男  2.女
     *
     * @param sex 1.男  2.女
     */
    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    /**
     * 获取生日
     *
     * @return birthday - 生日
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 设置生日
     *
     * @param birthday 生日
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    /**
     * 获取手机号码
     *
     * @return phone - 手机号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号码
     *
     * @param phone 手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取电子邮箱
     *
     * @return email - 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱
     *
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取头像
     *
     * @return avatar - 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像
     *
     * @param avatar 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * 获取个性签名
     *
     * @return signature - 个性签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 设置个性签名
     *
     * @param signature 个性签名
     */
    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    /**
     * 获取用户常用地址
     *
     * @return address - 用户常用地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置用户常用地址
     *
     * @param address 用户常用地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取行业
     *
     * @return industry - 行业
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * 设置行业
     *
     * @param industry 行业
     */
    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    /**
     * @return group_var
     */
    public Byte getGroupVar() {
        return groupVar;
    }

    /**
     * @param groupVar
     */
    public void setGroupVar(Byte groupVar) {
        this.groupVar = groupVar;
    }

    /**
     * 获取金币
     *
     * @return coin - 金币
     */
    public Integer getCoin() {
        return coin;
    }

    /**
     * 设置金币
     *
     * @param coin 金币
     */
    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    /**
     * 获取银币
     *
     * @return silver - 银币
     */
    public Integer getSilver() {
        return silver;
    }

    /**
     * 设置银币
     *
     * @param silver 银币
     */
    public void setSilver(Integer silver) {
        this.silver = silver;
    }

    /**
     * 获取余额
     *
     * @return money - 余额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置余额
     *
     * @param money 余额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取客户端标识
     *
     * @return client_id - 客户端标识
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 设置客户端标识
     *
     * @param clientId 客户端标识
     */
    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    /**
     * 获取客户端密钥
     *
     * @return client_secret - 客户端密钥
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * 设置客户端密钥
     *
     * @param clientSecret 客户端密钥
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret == null ? null : clientSecret.trim();
    }

    /**
     * 获取微信OpenId
     *
     * @return open_id - 微信OpenId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置微信OpenId
     *
     * @param openId 微信OpenId
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * 获取1：微信  2：QQ  登录方式
     *
     * @return type - 1：微信  2：QQ  登录方式
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1：微信  2：QQ  登录方式
     *
     * @param type 1：微信  2：QQ  登录方式
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取账号状态：0 禁用， 1 启用
     *
     * @return status - 账号状态：0 禁用， 1 启用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置账号状态：0 禁用， 1 启用
     *
     * @param status 账号状态：0 禁用， 1 启用
     */
    public void setStatus(Integer status) {
        this.status = status;
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
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", payPassword=").append(payPassword);
        sb.append(", nickName=").append(nickName);
        sb.append(", name=").append(name);
        sb.append(", cardType=").append(cardType);
        sb.append(", cardNumber=").append(cardNumber);
        sb.append(", sex=").append(sex);
        sb.append(", birthday=").append(birthday);
        sb.append(", phone=").append(phone);
        sb.append(", email=").append(email);
        sb.append(", avatar=").append(avatar);
        sb.append(", signature=").append(signature);
        sb.append(", address=").append(address);
        sb.append(", industry=").append(industry);
        sb.append(", groupVar=").append(groupVar);
        sb.append(", coin=").append(coin);
        sb.append(", silver=").append(silver);
        sb.append(", money=").append(money);
        sb.append(", clientId=").append(clientId);
        sb.append(", clientSecret=").append(clientSecret);
        sb.append(", openId=").append(openId);
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}