package com.quid.auth.userInfo.blacklist.gateway.repository

import com.quid.auth.userInfo.blacklist.gateway.repository.jpa.BlacklistJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface BlacklistRepository {
    fun existsByUserSeq(userSeq: Long): Boolean

    @Repository
    class BlacklistRepositoryImpl(
        private val jpaRepository: BlacklistJpaRepository
    ) : BlacklistRepository {

        @Transactional(readOnly = true)
        override fun existsByUserSeq(userSeq: Long): Boolean =
            jpaRepository.findByUserSeq(userSeq)
                .map { it.toDomain() }
                .also { println(it) }
                .any { it.isNotExpired() }

    }
}