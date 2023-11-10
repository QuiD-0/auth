package com.quid.auth.user.gateway.repository.redis

import com.quid.auth.global.redis.RedisBaseRepository
import com.quid.auth.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class UserRedisRepository(
    private val redisBaseRepository: RedisBaseRepository<UserRedisHash>
){
    operator fun get(username: String): User? =
        redisBaseRepository[makeKey(username)]?.toDomain()

    operator fun set(username: String, value: User) {
        redisBaseRepository[makeKey(username), UserRedisHash(value)] = 15
    }

    private fun makeKey(username: String): String =
        "user:$username"
}