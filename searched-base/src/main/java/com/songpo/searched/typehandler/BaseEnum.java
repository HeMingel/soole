package com.songpo.searched.typehandler;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author 刘松坡
 */
public interface BaseEnum {

    String DEFAULT_VALUE_NAME = "value";

    String DEFAULT_LABEL_NAME = "label";

    static <T extends Enum<T>> T valueOfEnum(Class<T> enumClass, Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("DisplayedEnum value should not be null");
        }
        if (enumClass.isAssignableFrom(BaseEnum.class)) {
            throw new IllegalArgumentException("illegal DisplayedEnum type");
        }
        T[] enums = enumClass.getEnumConstants();
        for (T t : enums) {
            BaseEnum baseEnum = (BaseEnum) t;
            if (baseEnum.getValue().equals(value)) {
                return (T) baseEnum;
            }
        }
        throw new IllegalArgumentException("cannot parse integer: " + value + " to " + enumClass.getName());
    }

    /**
     * 获取枚举值
     *
     * @return 枚举值
     */
    default Integer getValue() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_VALUE_NAME);
        try {
            field.setAccessible(true);
            return Integer.parseInt(field.get(this).toString());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取枚举名称
     *
     * @return 枚举名称
     */
    default String getLabel() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_LABEL_NAME);
        try {
            field.setAccessible(true);
            return field.get(this).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
