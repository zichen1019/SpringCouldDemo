package com.zc.common.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zichen
 */
@AllArgsConstructor
@Data
public class RedisInfo {

    /**
     * 主键
     */
    private String key;

    /**
     * 键值
     */
    private Object value;

}
