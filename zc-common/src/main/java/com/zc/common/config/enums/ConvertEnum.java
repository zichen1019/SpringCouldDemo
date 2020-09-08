package com.zc.common.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zichen
 */
@AllArgsConstructor
@Getter
public enum ConvertEnum {

    /**
     * 转换对饮枚举
     */
    STRING("toStr"),
    INTEGER("toInt"),
    LONG("toLong"),
    FLOAT("toFloat"),
    DOUBLE("toDouble"),
    DATE("toDate"),
    BOOLEAN("toBool"),
    BIGDECIMAL("toBigDecimal"),
    NONE(null);

    /**
     * 方法名
     */
    private String method;

}
