package com.zc.common.config.enums;

/**
 * @author zichen
 */

public enum SourceEnum {

    /**
     * WEB网站
     */
    WEB,
    /**
     * PC客户端
     */
    PC,
    /**
     * 微信公众号
     */
    WECHAT,
    /**
     * IOS平台
     **/
    IOS,
    /**
     * 安卓平台
     */
    ANDROID;

    public static boolean isValid(String name) {
        for (SourceEnum callSource : SourceEnum.values()) {
            if (callSource.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
