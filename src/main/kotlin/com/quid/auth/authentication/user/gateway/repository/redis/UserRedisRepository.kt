package com.quid.auth.authentication.user.gateway.repository.redis

import com.quid.auth.authentication.user.domain.User
import com.quid.auth.global.redis.RedisBaseRepository
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