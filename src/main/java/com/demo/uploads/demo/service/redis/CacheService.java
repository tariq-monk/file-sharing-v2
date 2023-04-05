package com.demo.uploads.demo.service.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper mapper;

    @Value(value = "${sfox.file-share.cache.expire_time_mills}")
    private Long expireMillis;

    public <T> void set(final String key, T object) {
        String json;
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        redisTemplate.opsForValue().set(key, json, expireMillis, TimeUnit.MILLISECONDS);
    }

    public <T> Optional<T> get(final String key, Class<T> type) {
        String json = redisTemplate.opsForValue().get(key);
        if (json == null) {
            return Optional.empty();
        }
        try {
            T object = mapper.readValue(json, type);
            return Optional.ofNullable(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}