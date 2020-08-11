package com.zc.common.config.redis;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.io.*;

/**
 * redis 序列化辅助类
 *
 * @author zhoujl
 * @date 2019-04-30
 */
@Component
public class RedisHelper {

    @Value("${spring.redis.host}")
    private String host;

    private static RedisHelper redisHelper;

    @PostConstruct
    public void init() {
        redisHelper = this;
        redisHelper.host = this.host;
    }

    /**
     * 添加缓存信息
     */
    public static void add(String key, Object value) {
        Jedis jedis = new Jedis(redisHelper.host);
        jedis.set(key.getBytes(), ObjectTranscoder.serialize(value));
    }

    /**
     * 获取缓存信息
     */
    public static Object get(String key) {
        if (StrUtil.isEmpty(key)) {
            return null;
        }
        Jedis jedis = new Jedis(redisHelper.host);
        if (!jedis.exists(key.getBytes())) {
            return null;
        }
        byte[] in = jedis.get(key.getBytes());
        return ObjectTranscoder.deserialize(in);
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
        Jedis jedis = new Jedis(redisHelper.host);
        return jedis.exists(key.getBytes());
    }

    /**
     * 删除key
     *
     * @param key 键信息
     */
    public static void delKey(String key) {
        Jedis jedis = new Jedis(redisHelper.host);
        jedis.del(key.getBytes());
    }

    static class ObjectTranscoder {

        /**
         * 序列化参数
         *
         * @param value object
         * @return byte
         */
        static byte[] serialize(Object value) {
            if (value == null) {
                throw new NullPointerException("参数不能为空");
            }
            byte[] rv;
            ByteArrayOutputStream bos = null;
            ObjectOutputStream os = null;
            try {
                bos = new ByteArrayOutputStream();
                os = new ObjectOutputStream(bos);
                os.writeObject(value);
                os.close();
                bos.close();
                rv = bos.toByteArray();
            } catch (IOException e) {
                throw new IllegalArgumentException("该对象不可序列化", e);
            } finally {
                try {
                    if (os != null)
                        os.close();
                    if (bos != null)
                        bos.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            return rv;
        }

        /**
         * 反序列化参数
         *
         * @param in byte
         * @return object
         */
        static Object deserialize(byte[] in) {

            Object rv = null;
            ByteArrayInputStream bis = null;
            ObjectInputStream is = null;
            try {
                if (in != null) {
                    bis = new ByteArrayInputStream(in);
                    is = new ObjectInputStream(bis);
                    rv = is.readObject();
                    is.close();
                    bis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null)
                        is.close();
                    if (bis != null)
                        bis.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            return rv;
        }
    }

}
