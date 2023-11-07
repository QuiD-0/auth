package com.quid.auth.user.usecase

import com.quid.auth.user.gateway.repository.UserAuthorityRepository
import org.springframework.stereotype.Service

interface RevokeAuthority {

    operator fun invoke(userAuthoritySeq: Long)

    @Service
    class RevokeAuthorityUseCase(
        private val userAuthorityRepository: UserAuthorityRepository
    ) : RevokeAuthority {
        override fun invoke(userAuthoritySeq: Long) {
            userAuthorityRepository.findById(userAuthoritySeq)
                .delete()
                .also { userAuthorityRepository.save(it) }
        }
    }

}