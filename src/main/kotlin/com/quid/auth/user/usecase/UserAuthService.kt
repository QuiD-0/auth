package com.quid.auth.user.usecase

import com.quid.auth.user.domain.UserAuthority
import com.quid.auth.user.domain.UserDetail
import com.quid.auth.user.gateway.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserAuthService(
    val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByUsername(username!!)

        return UserDetail(user, UserAuthority(1,1, "USER", LocalDateTime.now(), false))
    }
}