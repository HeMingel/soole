package com.songpo.searched.domain;

import lombok.Data;

@Data
public class CmProductType {

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

    /**
     * 图片地址
     */
    private String imageUrl;
}
