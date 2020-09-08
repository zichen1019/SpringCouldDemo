package com.zc.login.conf.redis;

import cn.hutool.core.util.StrUtil;
import com.zc.login.util.OnLineUserCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author zichen
 */
@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        log.error("[redis] [" + expiredKey +"] [该key已过期]");
        if (StrUtil.startWith(expiredKey, HttpHeaders.AUTHORIZATION)) {
            OnLineUserCountUtil.reduce();
        }
    }
}
