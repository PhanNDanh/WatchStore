package com.example.identityservice.repository;

import com.example.identityservice.entity.InvalidatedToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class InvalidatedTokenRepository {

    private final RedisTemplate<String, InvalidatedToken> redisTemplate;

    public boolean existsById(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }

    public void save(InvalidatedToken invalidatedToken) {

        // Lưu InvalidatedToken vào Redis với key là token
        redisTemplate.opsForValue().set(invalidatedToken.getToken(), invalidatedToken);

        // Tính toán thời gian hết hạn
        long timeout = 30L * 24 * 60 * 60 * 1000; // 1 month

        // Thiết lập thời gian hết hạn cho token
        redisTemplate.expire(invalidatedToken.getToken(), timeout, TimeUnit.MILLISECONDS);
    }


}
