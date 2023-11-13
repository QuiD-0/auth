package com.quid.auth.authentication.user.usecase

import com.quid.auth.authentication.blacklist.gateway.repository.BlacklistRepository
import com.quid.auth.authentication.user.domain.UserDetail
import com.quid.auth.authentication.user.gateway.repository.UserAuthorityRepository
import com.quid.auth.authentication.user.gateway.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserAuthService(
    private val userRepository: UserRepository,
    private val userAuthorityRepository: UserAuthorityRepository,
    private val blacklistRepository: BlacklistRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
        val authority = userAuthorityRepository.findByUserSeq(user.userSeq!!)
        val blacklist = blacklistRepository.findByUserSeq(user.userSeq)
        return UserDetail(user, authority, blacklist)
    }
}