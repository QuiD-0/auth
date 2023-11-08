package com.quid.auth.user.gateway.repository

import com.quid.auth.user.domain.UserAuthority
import com.quid.auth.user.gateway.repository.jpa.UserAuthorityEntity
import com.quid.auth.user.gateway.repository.jpa.UserAuthorityJpaRepository
import com.quid.auth.user.gateway.repository.redis.UserAuthorityRedisRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface UserAuthorityRepository {
    fun findByName(name: String): List<UserAuthority>
    fun findByUserSeq(userSeq: Long): List<UserAuthority>
    fun save(userAuthority: UserAuthority): UserAuthority
    fun findById(userAuthoritySeq: Long) : UserAuthority

    @Repository
    class UserAuthorityRepositoryImpl(
        val jpaRepository: UserAuthorityJpaRepository,
        val cache: UserAuthorityRedisRepository
    ) : UserAuthorityRepository {

        @Transactional(readOnly = true)
        override fun findByName(name: String): List<UserAuthority> =
            jpaRepository.findByAuthorityName(name)
                .map { it.toUserAuthority() }

        @Transactional(readOnly = true)
        override fun findByUserSeq(userSeq: Long): List<UserAuthority> =
            cache[userSeq]
                ?: jpaRepository.findByUserSeq(userSeq).map { it.toUserAuthority() }
                    .also { cache[userSeq] = it }

        @Transactional
        override fun save(userAuthority: UserAuthority): UserAuthority =
            jpaRepository.save(UserAuthorityEntity(userAuthority))
                .toUserAuthority()

        @Transactional(readOnly = true)
        override fun findById(userAuthoritySeq: Long): UserAuthority =
            jpaRepository.findByIdOrNull(userAuthoritySeq)
                ?.toUserAuthority()
                ?: throw IllegalArgumentException("UserAuthority not found")
    }
}