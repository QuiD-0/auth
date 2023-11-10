package com.quid.auth.token.gateway.repository

import com.quid.auth.global.redis.RedisBaseRepository
import com.quid.auth.token.gateway.repository.model.UserTokenJti
import org.springframework.stereotype.Repository

interface RefreshTokenRepository {

    fun save(userToken: UserTokenJti)
    fun findByUsername(username: String): String
    fun deleteByUsername(username: String)

    @Repository
    class RedisRefreshTokenRepository(
        private val redisTemplate: RedisBaseRepository<String>
    ) : RefreshTokenRepository {

        override fun save(userToken: UserTokenJti) {
            redisTemplate[makeKey(userToken.username), userToken.refreshTokenJti] = 604800
        }

        override fun findByUsername(username: String): String=
            redisTemplate[makeKey(username)]
                ?: throw IllegalArgumentException("RefreshToken not found: $username")

        override fun deleteByUsername(username: String) {
            redisTemplate.delete(makeKey(username))
        }

        private fun makeKey(key:String) =
            "refreshToken:$key"
    }
}