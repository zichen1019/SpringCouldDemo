package com.zc.common.config.constants;

import com.zc.common.config.enums.SourceEnum;

/**
 * Header的key罗列
 *
 * @author zichen
 */
public final class HeaderConstants {

    private HeaderConstants() {
    }

    /**
     * 用户的登录token
     */
    public static final String X_TOKEN = "X-Token";

    /**
     * 调用来源 {@link SourceEnum}
     */
    public static final String CALL_SOURCE = "Call-Source";
}
