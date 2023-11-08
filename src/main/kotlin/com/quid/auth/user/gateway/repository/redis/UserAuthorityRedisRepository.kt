package com.quid.auth.user.gateway.repository.redis

import com.quid.auth.global.redis.RedisRepository
import com.quid.auth.user.domain.UserAuthority
import org.springframework.stereotype.Repository

@Repository
class UserAuthorityRedisRepository(
    private val redisListRepository: RedisRepository<List<UserAuthority>>
) {
    operator fun get(userSeq: Long): List<UserAuthority>? =
        redisListRepository[userSeq.toString()]

    operator fun set(userSeq: Long, value: List<UserAuthority>) {
        redisListRepository[userSeq.toString(), value] = 15
    }
}