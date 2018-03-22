package com.songpo.searched.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sl_agent_application")
public class SlAgentApplication implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区县
     */
    private String county;
    /**
     * 是否有公司或团队，0：没有 1：有
     */
    @Column(name = "own_a_company")
    private Boolean ownACompany;
    /**
     * 姓名
     */
    @Column(name = "real_name")
    private String realName;
    /**
     * 电话
     */
    private String phone;
    /**
     * 身份证正面
     */
    @Column(name = "id_card_front_image_url")
    private String idCardFrontImageUrl;
    /**
     * 身份证反面
     */
    @Column(name = "id_card_back_image_url")
    private String idCardBackImageUrl;
    /**
     * 手持身份证
     */
    @Column(name = "id_card_hand_image_url")
    private String idCardHandImageUrl;

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
     * 获取省份
     *
     * @return province - 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省份
     *
     * @param province 省份
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 获取城市
     *
     * @return city - 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置城市
     *
     * @param city 城市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 获取区县
     *
     * @return county - 区县
     */
    public String getCounty() {
        return county;
    }

    /**
     * 设置区县
     *
     * @param county 区县
     */
    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    /**
     * 获取是否有公司或团队，0：没有 1：有
     *
     * @return own_a_company - 是否有公司或团队，0：没有 1：有
     */
    public Boolean getOwnACompany() {
        return ownACompany;
    }

    /**
     * 设置是否有公司或团队，0：没有 1：有
     *
     * @param ownACompany 是否有公司或团队，0：没有 1：有
     */
    public void setOwnACompany(Boolean ownACompany) {
        this.ownACompany = ownACompany;
    }

    /**
     * 获取姓名
     *
     * @return real_name - 姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置姓名
     *
     * @param realName 姓名
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取身份证正面
     *
     * @return id_card_front_image_url - 身份证正面
     */
    public String getIdCardFrontImageUrl() {
        return idCardFrontImageUrl;
    }

    /**
     * 设置身份证正面
     *
     * @param idCardFrontImageUrl 身份证正面
     */
    public void setIdCardFrontImageUrl(String idCardFrontImageUrl) {
        this.idCardFrontImageUrl = idCardFrontImageUrl == null ? null : idCardFrontImageUrl.trim();
    }

    /**
     * 获取身份证反面
     *
     * @return id_card_back_image_url - 身份证反面
     */
    public String getIdCardBackImageUrl() {
        return idCardBackImageUrl;
    }

    /**
     * 设置身份证反面
     *
     * @param idCardBackImageUrl 身份证反面
     */
    public void setIdCardBackImageUrl(String idCardBackImageUrl) {
        this.idCardBackImageUrl = idCardBackImageUrl == null ? null : idCardBackImageUrl.trim();
    }

    /**
     * 获取手持身份证
     *
     * @return id_card_hand_image_url - 手持身份证
     */
    public String getIdCardHandImageUrl() {
        return idCardHandImageUrl;
    }

    /**
     * 设置手持身份证
     *
     * @param idCardHandImageUrl 手持身份证
     */
    public void setIdCardHandImageUrl(String idCardHandImageUrl) {
        this.idCardHandImageUrl = idCardHandImageUrl == null ? null : idCardHandImageUrl.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", county=").append(county);
        sb.append(", ownACompany=").append(ownACompany);
        sb.append(", realName=").append(realName);
        sb.append(", phone=").append(phone);
        sb.append(", idCardFrontImageUrl=").append(idCardFrontImageUrl);
        sb.append(", idCardBackImageUrl=").append(idCardBackImageUrl);
        sb.append(", idCardHandImageUrl=").append(idCardHandImageUrl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}