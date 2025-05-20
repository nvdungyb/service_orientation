package com.java.moderationservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    @Value("${time_to_live}")
    private long ttl;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String key, String value) {
        // todo: dùng theo kiểu atomic thì bị lỗi, ko biết sao luôn
//        redisTemplate.execute((RedisCallback<Void>) connection -> {
//            byte[] keyBytes = redisTemplate.getStringSerializer().serialize(key);
//            byte[] valueBytes = redisTemplate.getValueSerializer().serialize(value);
//            connection.set(
//                    keyBytes,
//                    valueBytes,
//                    Expiration.milliseconds(ttl),
//                    RedisStringCommands.SetOption.UPSERT // Ghi đè nếu key đã tồn tại
//            );
//            return null;
//        });

        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, Duration.ofMillis(ttl));
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }
}
