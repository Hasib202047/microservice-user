package com.microservice.userService.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String BLACKLIST_PREFIX = "blacklisted_token:";
    // Retrieve data from Redis
    public Object getFromCache(String key) {
        return redisTemplate.opsForValue().get(BLACKLIST_PREFIX+key);
    }

    public Set<String> getAllBlacklistedKeys() {
        return redisTemplate.keys(BLACKLIST_PREFIX + "*");
    }

    // Delete data from Redis
    public void removeFromCache(String key) {
        redisTemplate.delete(BLACKLIST_PREFIX+key);
    }

    public void flushDB() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().serverCommands().flushDb();
    }
}
