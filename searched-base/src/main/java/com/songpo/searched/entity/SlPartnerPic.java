package com.songpo.searched.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "sl_partner_pic")
public class SlPartnerPic implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 股东信息表ID
     */
    @Column(name = "pp_partner_id")
    private String ppPartnerId;

    /**
     * 股东图片
     */
    @Column(name = "partner_pic")
    private String partnerPic;

    /**
     * 图片分类1身份证正面2身份证反面3征信报告4担保责任书5其他
     */
    @Column(name = "partner_class")
    private Byte partnerClass;

    private static final long serialVersionUID = 1L;

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
     * 获取股东信息表ID
     *
     * @return pp_partner_id - 股东信息表ID
     */
    public String getPpPartnerId() {
        return ppPartnerId;
    }

    /**
     * 设置股东信息表ID
     *
     * @param ppPartnerId 股东信息表ID
     */
    public void setPpPartnerId(String ppPartnerId) {
        this.ppPartnerId = ppPartnerId == null ? null : ppPartnerId.trim();
    }

    /**
     * 获取股东图片
     *
     * @return partner_pic - 股东图片
     */
    public String getPartnerPic() {
        return partnerPic;
    }

    /**
     * 设置股东图片
     *
     * @param partnerPic 股东图片
     */
    public void setPartnerPic(String partnerPic) {
        this.partnerPic = partnerPic == null ? null : partnerPic.trim();
    }

    /**
     * 获取图片分类1身份证正面2身份证反面3征信报告4担保责任书5其他
     *
     * @return partner_class - 图片分类1身份证正面2身份证反面3征信报告4担保责任书5其他
     */
    public Byte getPartnerClass() {
        return partnerClass;
    }

    /**
     * 设置图片分类1身份证正面2身份证反面3征信报告4担保责任书5其他
     *
     * @param partnerClass 图片分类1身份证正面2身份证反面3征信报告4担保责任书5其他
     */
    public void setPartnerClass(Byte partnerClass) {
        this.partnerClass = partnerClass;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ppPartnerId=").append(ppPartnerId);
        sb.append(", partnerPic=").append(partnerPic);
        sb.append(", partnerClass=").append(partnerClass);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}