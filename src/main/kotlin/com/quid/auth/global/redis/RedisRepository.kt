package com.quid.auth.global.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisRepository <VALUE> (
    private val redisTemplate: RedisTemplate<String, VALUE>
) {

    operator fun set(key: String, value: VALUE) {
        requireNotNull(value)
        redisTemplate.opsForValue().set(key, value)
    }

    operator fun get(key: String): VALUE =
        redisTemplate.opsForValue().get(key)
            ?: throw IllegalArgumentException("value not found")

    fun delete(key: String) {
        redisTemplate.delete(key)
    }

}