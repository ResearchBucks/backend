package org.researchbucks.util.jwt;

import org.researchbucks.util.CommonMessages;
import org.researchbucks.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenRevokeService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void blacklistToken(String token, long ttlSeconds) {
        redisTemplate.opsForValue().set(SecurityUtil.hashToken(token), CommonMessages.BLACKLIST, ttlSeconds, TimeUnit.SECONDS);
    }

    public boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(SecurityUtil.hashToken(token)));
    }
}
