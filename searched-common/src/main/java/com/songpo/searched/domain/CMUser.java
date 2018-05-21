package com.songpo.searched.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CMUser implements Serializable {
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
}