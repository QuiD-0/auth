package com.quid.auth.global.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RedisRepository <VALUE> (
    private val redisTemplate: RedisTemplate<String, VALUE>
) {

    operator fun set(key: String, value: VALUE, seconds: Long) {
        requireNotNull(value)
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(seconds))
    }

    operator fun get(key: String): VALUE? =
        redisTemplate.opsForValue().get(key)

    fun delete(key: String) {
        redisTemplate.delete(key)
    }

}