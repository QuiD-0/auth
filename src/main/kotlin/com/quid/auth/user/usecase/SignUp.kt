package com.quid.auth.user.usecase

import com.quid.auth.user.domain.User
import com.quid.auth.user.gateway.repository.UserRepository
import com.quid.auth.user.gateway.web.response.UserResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

fun interface SignUp {
    operator fun invoke(user: User): UserResponse

    @Service
    class SignUpUseCase(
        val userRepository: UserRepository,
        val passwordEncoder: PasswordEncoder
    ) : SignUp {

        override fun invoke(user: User): UserResponse =
            isUserExist(user)
                ?.let { throw IllegalArgumentException("Username already exists") }
                ?: passwordEncoder.encode(user.password)
                    .let { user.encodePassword(it) }
                    .let { userRepository.save(it) }
                    .let { UserResponse{ it } }

        private fun isUserExist(user: User) =
            takeIf { userRepository.existsByUsername(user.username) }
    }
}