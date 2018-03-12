package com.songpo.searched.domain;

import lombok.Data;

/**
 * 通用业务消息类
 *
 * @param <T> 实体
 */
@Data
public class BusinessMessage<T> {

    /**
     * 业务执行结果
     */
    private Boolean success = false;
    /**
     * 业务代码
     */
    private String code = "";
    /**
     * 业务消息
     */
    private String msg = "";
    /**
     * 业务数据
     */
    private T data;

    public BusinessMessage(Boolean success) {
        this.success = success;
    }

    public BusinessMessage() {
    }
}
