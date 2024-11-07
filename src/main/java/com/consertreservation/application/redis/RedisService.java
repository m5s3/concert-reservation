package com.consertreservation.application.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void addItemToSortedSet(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public Long getItemRank(String key, String value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    public Set<Object> getItemsInSortedSet(String key, int start, int end) {
        Set<Object> items = redisTemplate.opsForZSet().range(key, start, end);
        if (Objects.isNull(items)) {
            return Set.of();
        }
        return items;
    }

    public void removeItemsInSortedSet(String key, int start, int end) {
        redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    public void addItemToHash(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public void deleteAllInHash(String key) {
        redisTemplate.opsForHash().delete(key);
    }

    public boolean existInHash(String key, String hashKey) {
        return Objects.nonNull(redisTemplate.opsForHash().get(key, hashKey));
    }
}
