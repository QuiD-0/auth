package com.quid.auth.global.redis

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisConfig {

    @Value("\${spring.redis.host}")
    private lateinit var redisHost: String

    @Value("\${spring.redis.port}")
    private var redisPort: Int = 0


    @Bean
    fun <VALUE> redisTemplate(): RedisTemplate<String, VALUE> =
        RedisTemplate<String, VALUE>().apply {
            setConnectionFactory(redisConnectionFactory())
        }

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory =
        LettuceConnectionFactory(redisHost, redisPort)
}