package com.quid.auth.authentication.blacklist.usecase

import com.quid.auth.authentication.blacklist.domain.Blacklist
import com.quid.auth.authentication.blacklist.gateway.repository.BlacklistRepository
import org.springframework.stereotype.Service

interface BlackUser {
    operator fun invoke(blacklist: Blacklist)

    @Service
    class BlackUserUseCase(
        private val blacklistRepository: BlacklistRepository
    ) : BlackUser {
        override fun invoke(blacklist: Blacklist) {
            blacklistRepository.save(blacklist)
        }
    }
}