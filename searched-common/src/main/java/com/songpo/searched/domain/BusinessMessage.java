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
    public Boolean success = false;
    /**
     * 业务代码
     */
    public String code = "";
    /**
     * 业务消息
     */
    public String msg = "";
    /**
     * 业务数据
     */
    public T data;

    public BusinessMessage(Boolean success) {
        this.success = success;
    }

    public BusinessMessage() {
    }
}
