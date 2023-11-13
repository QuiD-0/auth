package com.quid.auth.authentication.blacklist.gateway.repository.redis

import com.quid.auth.authentication.blacklist.domain.Blacklist
import com.quid.auth.global.redis.RedisBaseRepository
import org.springframework.stereotype.Component

@Component
class BlackListRedisRepository(
    private val redisListRepository: RedisBaseRepository<BlacklistRedisHash>
) {

    operator fun set(userSeq: Long, value: List<Blacklist>) {
        value.forEach { redisListRepository.add(makeKey(userSeq), BlacklistRedisHash(it), 15) }
            .also { redisListRepository.expire(makeKey(userSeq), 15) }
    }

    operator fun get(userSeq: Long): List<Blacklist>? =
        redisListRepository.getAll(makeKey(userSeq))
            ?.map { it.toDomain() }
            ?.let { it.ifEmpty { null } }

    private fun makeKey(userSeq: Long): String =
        "blacklist:$userSeq"
}
