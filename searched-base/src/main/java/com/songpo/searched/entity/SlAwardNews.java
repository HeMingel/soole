package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_award_news")
public class SlAwardNews implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "an_user_id")
    private String anUserId;

    @Column(name = "an_number")
    private Integer anNumber;

    @Column(name = "created_at")
    private Date createdAt;

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
     * @return an_user_id
     */
    public String getAnUserId() {
        return anUserId;
    }

    /**
     * @param anUserId
     */
    public void setAnUserId(String anUserId) {
        this.anUserId = anUserId == null ? null : anUserId.trim();
    }

    /**
     * @return an_number
     */
    public Integer getAnNumber() {
        return anNumber;
    }

    /**
     * @param anNumber
     */
    public void setAnNumber(Integer anNumber) {
        this.anNumber = anNumber;
    }

    /**
     * @return created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return updated_at
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
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
        sb.append(", anUserId=").append(anUserId);
        sb.append(", anNumber=").append(anNumber);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}