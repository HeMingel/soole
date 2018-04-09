package com.songpo.searched.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sl_district_province")
public class SlDistrictProvince implements Serializable {
    @Id
    private Long code;

    private String name;

    private static final long serialVersionUID = 1L;

    /**
     * @return code
     */
    public Long getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(Long code) {
        this.code = code;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}