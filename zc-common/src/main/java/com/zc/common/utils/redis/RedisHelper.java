package com.zc.common.utils.redis;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zc.common.config.enums.ConvertEnum;
import com.zc.common.config.enums.ResultCode;
import com.zc.common.exception.BusinessException;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Set;

/**
 * redis 序列化辅助类
 *
 * @author zichen
 */
@Component
public class RedisHelper {

    private static RedisHelper redisHelper;

    @Resource
    private JedisPool jedisPool;

    private Jedis jedis;

    @PostConstruct
    public void init() {
        redisHelper = this;
        redisHelper.jedis = this.jedisPool.getResource();
    }

    /**
     * 添加缓存信息
     *
     * @param key
     * @param value     使用String进行序列化
     */
    public static void add(String key, Object value) {
        add(key, value, null);
    }

    /**
     * 添加缓存信息
     *
     * @param key
     * @param value     使用String进行序列化
     * @param expire
     */
    public static void add(String key, Object value, Integer expire) {
        if (ObjectUtil.isNull(value)) {
            throw new BusinessException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        try {
            redisHelper.jedis.set(key, Convert.toStr(value));
            if (ObjectUtil.isNotNull(expire)) {
                updateExpire(key, expire);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
    }

    /**
     * 添加缓存信息
     * @param value     使用json进行序列化
     */
    public static void addJSONSerializer(String key, Object value) {
        addJSONSerializer(key, value, null);
    }

    /**
     * 添加缓存信息
     * @param value     使用json进行序列化
     */
    public static void addJSONSerializer(String key, Object value, Integer expire) {
        try {
            redisHelper.jedis.set(key, ObjectTranscoder.serialize(value));
            if (ObjectUtil.isNotNull(expire)) {
                updateExpire(key, expire);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
    }

    /**
     * 更新指定key的失效时间
     */
    public static void updateExpire(String key, Integer expire) {
        redisHelper.jedis.expire(key, expire);
    }

    /**
     * 获取匹配的key
     */
    public static Set<String> keys(String key) {
        if (StrUtil.isEmpty(key)) {
            return null;
        }
        return redisHelper.jedis.keys(key);
    }

    /**
     * 获取缓存信息
     */
    public static <T> T get(String key, Class<T> clazz) {
        if (StrUtil.isEmpty(key) || !exists(key)) {
            return null;
        }
        String value = redisHelper.jedis.get(key);
        String method = EnumUtil.getEnumMap(ConvertEnum.class).getOrDefault(clazz.getSimpleName().toUpperCase(), ConvertEnum.NONE).getMethod();
        if (StrUtil.isNotEmpty(method)) {
            return ReflectUtil.invoke(new Convert(), method, value);
        } else {
            throw new BusinessException(ResultCode.UNDEFINED_FIELD_TYPE_ERROR, clazz.getName());
        }
    }

    /**
     * 获取缓存信息
     */
    public static <T> T getJSONSerializer(String key, Class<T> clazz) {
        if (StrUtil.isEmpty(key)) {
            return null;
        }
        if (!exists(key)) {
            return null;
        }
        String in = redisHelper.jedis.get(key);
        return ObjectTranscoder.deserialize(in, clazz);
    }

    /**
     * 判断 key 是否存在
     *
     * @param key 键信息
     * @return 是否存在
     */
    public static Boolean exists(String key) {
        if (StrUtil.isEmpty(key)) {
            return false;
        }
        return redisHelper.jedis.exists(key);
    }

    /**
     * 删除key
     *
     * @param key 键信息
     */
    public static void delKey(String key) {
        redisHelper.jedis.del(key);
    }

    static class ObjectTranscoder {

        /**
         * 序列化参数
         *
         * @param value object
         * @return byte
         */
        static String serialize(Object value) {
            if (value == null) {
                throw new NullPointerException("参数不能为空");
            }
            return JSON.toJSONString(value);
        }

        static <T> T deserialize(String in, Class<T> clazz) {
            return JSON.parseObject(in, clazz);
        }
    }

}
