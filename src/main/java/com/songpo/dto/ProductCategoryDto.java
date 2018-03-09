package com.songpo.dto;

import lombok.Data;

@Data
public class ProductCategoryDto {

    private String id;
    /**
     * 名称
     */
    private String name;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 父Id
     */
    private String parentId;
}
