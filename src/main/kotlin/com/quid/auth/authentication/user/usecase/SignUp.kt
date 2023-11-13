package com.quid.auth.authentication.user.usecase

import com.quid.auth.authentication.user.domain.User
import com.quid.auth.authentication.user.gateway.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

fun interface SignUp {
    operator fun invoke(user: User)

    @Service
    class SignUpUseCase(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
    ) : SignUp {

        override fun invoke(user: User) {
            isUserExist(user)
                ?.let { throw IllegalArgumentException("Username already exists") }
                ?: passwordEncoder.encode(user.password)
                    .let { user.encodePassword(it) }
                    .let { userRepository.save(it) }
        }

        private fun isUserExist(user: User) =
            takeIf { userRepository.existsByUsername(user.username) }
    }
}