package com.quid.auth.user.usecase

import com.quid.auth.user.domain.User
import com.quid.auth.user.gateway.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

fun interface SignUp {
    operator fun invoke(user: User): User

    @Service
    class SignUpUseCase(
        val userRepository: UserRepository,
        val passwordEncoder: PasswordEncoder
    ) : SignUp {
        private val logger = LoggerFactory.getLogger(SignUpUseCase::class.java)

        override fun invoke(user: User): User =
            takeIf { userRepository.existsByUsername(user.username) }
                ?.let { throw IllegalArgumentException("Username already exists") }
                ?: passwordEncoder.encode(user.password)
                    .let { user.encodePassword(it) }
                    .let { userRepository.save(it) }
                    .also { logger.info("User {} created", it.name) }
    }
}