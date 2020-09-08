package com.zc.common.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zichen
 */
@AllArgsConstructor
@Getter
public enum ConvertEnum {

    /**
     * 转换对饮枚举
     */
    STRING(String.class, "toStr"),
    INTEGER(Integer.class, "toInt"),
    LONG(Long.class, "toLong"),
    FLOAT(Float.class, "toFloat"),
    DOUBLE(Double.class, "toDouble"),
    DATE(Date.class, "toDate"),
    BOOLEAN(Boolean.class, "toBool"),
    BIGDECIMAL(BigDecimal.class, "toBigDecimal"),
    NONE(null, "");

    /**
     * 类名
     */
    private Class clazz;

    /**
     * 方法名
     */
    private String method;

}
