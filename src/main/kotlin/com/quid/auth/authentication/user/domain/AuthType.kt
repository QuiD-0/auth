package com.quid.auth.authentication.user.domain

interface AuthType {
    val name: String

    companion object {
        fun of(authorityName: String): AuthType =
            com.quid.auth.authentication.user.domain.RoleType.values()
                .find { it.name == authorityName }
                ?: com.quid.auth.authentication.user.domain.AuthorityType.values()
                    .find { it.name == authorityName }
                ?: throw IllegalArgumentException("권한이 존재하지 않습니다.")
    }
}

enum class AuthorityType : AuthType {
    WRITE,
    READ,
    UPDATE,
    DELETE,
    ;
}

enum class RoleType : AuthType {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_GUEST
    ;
}