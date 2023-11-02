package com.quid.auth.user.usecase

import com.quid.auth.user.domain.UserDetail
import com.quid.auth.user.gateway.repository.UserAuthorityRepository
import com.quid.auth.user.gateway.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserAuthService(
    private val userRepository: UserRepository,
    private val userAuthorityRepository: UserAuthorityRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
        val authority = userAuthorityRepository.findById(user.authorityId)
        return UserDetail(user, authority)
    }
}