package com.quid.auth.authentication.blacklist.gateway.repository

import com.quid.auth.authentication.blacklist.domain.Blacklist
import com.quid.auth.authentication.blacklist.gateway.repository.jpa.BlacklistJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface BlacklistRepository {
    fun findByUserSeq(userSeq: Long): List<Blacklist>

    @Repository
    class BlacklistRepositoryImpl(
        private val jpaRepository: BlacklistJpaRepository
    ) : BlacklistRepository {

        @Transactional(readOnly = true)
        override fun findByUserSeq(userSeq: Long): List<Blacklist> {
            return jpaRepository.findByUserSeq(userSeq)
                .map { it.toDomain() }
        }

    }
}