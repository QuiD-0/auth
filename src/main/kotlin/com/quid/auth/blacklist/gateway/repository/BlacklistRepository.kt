package com.quid.auth.blacklist.gateway.repository

import com.quid.auth.blacklist.gateway.repository.jpa.BlacklistJpaRepository
import org.springframework.stereotype.Repository

interface BlacklistRepository {
    fun existsByUserSeq(userSeq: Long): Boolean

    @Repository
    class BlacklistRepositoryImpl(
        private val jpaRepository: BlacklistJpaRepository
    ) : BlacklistRepository {

        override fun existsByUserSeq(userSeq: Long): Boolean =
            jpaRepository.findByUserSeq(userSeq)
                .map { it.toDomain() }
                .any { it.isExpired() }

    }
}