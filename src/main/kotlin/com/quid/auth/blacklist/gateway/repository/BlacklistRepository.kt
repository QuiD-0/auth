package com.quid.auth.blacklist.gateway.repository

import com.quid.auth.blacklist.gateway.repository.jpa.BlacklistJpaRepository
import org.springframework.stereotype.Repository

interface BlacklistRepository {

    @Repository
    class BlacklistRepositoryImpl(
        private val jpaRepository: BlacklistJpaRepository,
    ) : BlacklistRepository
}