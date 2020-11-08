package com.zc.login.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zc.common.config.redis.RedisHelper;
import org.springframework.http.HttpHeaders;

import java.util.Set;

/**
 * @author zichen
 */
public class OnLineUserCountUtil {

    private final static String KEY = "onLineUserCount";

    /**
     * 新增在线用户
     */
    public static void add() {
        int onLineUserCount = 0;
        if (RedisHelper.exists(KEY)) {
            onLineUserCount = ObjectUtil.defaultIfNull(RedisHelper.get(KEY, Integer.class), onLineUserCount);
        }
        RedisHelper.add(KEY, ++onLineUserCount);
        System.err.println("当前在线人数：" + onLineUserCount);
    }

    /**
     * 在线用户掉线或下线
     */
    public static void reduce() {
        int onLineUserCount = ObjectUtil.defaultIfNull(RedisHelper.get(KEY, Integer.class), 1);
        if (onLineUserCount > 0) {
            RedisHelper.add(KEY, --onLineUserCount);
        }
        System.err.println("当前在线人数：" + onLineUserCount);
    }

    /**
     * 获取在线用户信息
     * @return  当前在线用户数量
     */
    public static int get() {
        // 获取认证开头的key
        Set<String> keys = RedisHelper.keys(HttpHeaders.AUTHORIZATION + ":*");
        // 根据指定key获取当前在线用户
        int onLineUserCount = ObjectUtil.defaultIfNull(RedisHelper.get(KEY, Integer.class), 0);
        if (CollUtil.isNotEmpty(keys) && onLineUserCount != keys.size()) {
            int patternOnLineUserCount = keys.size();
            RedisHelper.add(KEY, patternOnLineUserCount);
            return patternOnLineUserCount;
        }
        return onLineUserCount;
    }

}
