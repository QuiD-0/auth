package com.quid.auth.authentication.blacklist.usecase

import com.quid.auth.authentication.blacklist.domain.Blacklist
import com.quid.auth.authentication.blacklist.gateway.repository.BlacklistRepository
import com.quid.auth.authentication.user.gateway.repository.UserRepository
import org.springframework.stereotype.Service

interface BlackUser {
    operator fun invoke(blacklist: Blacklist): Blacklist

    @Service
    class BlackUserUseCase(
        private val blacklistRepository: BlacklistRepository,
        private val userRepository: UserRepository
    ) : BlackUser {
        override fun invoke(blacklist: Blacklist) =
            takeIf { userRepository.existsById(blacklist.userSeq) }
                ?.let { blacklistRepository.save(blacklist) }
                ?: throw IllegalArgumentException("User not found: ${blacklist.userSeq}")
    }
}