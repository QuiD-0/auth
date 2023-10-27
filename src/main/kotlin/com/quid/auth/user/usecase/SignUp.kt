package com.quid.auth.user.usecase

import com.quid.auth.user.domain.UserInfo
import org.springframework.stereotype.Service

fun interface SignUp {
    operator fun invoke(userInfo: UserInfo)

    @Service
    class SignUpUseCase : SignUp {
        override fun invoke(userInfo: UserInfo) {
            println("sign up")
        }
    }
}