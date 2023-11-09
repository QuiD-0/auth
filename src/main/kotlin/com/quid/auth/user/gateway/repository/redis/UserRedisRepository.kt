package com.quid.auth.user.gateway.repository.redis

import com.quid.auth.global.redis.RedisRepository
import com.quid.auth.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class UserRedisRepository(
    private val redisRepository: RedisRepository<User>
){
    operator fun get(username: String): User? =
        redisRepository[makeKey(username)]

    operator fun set(username: String, value: User) {
        redisRepository[makeKey(username), value] = 15
    }

    private fun makeKey(username: String): String =
        "user:$username"
}