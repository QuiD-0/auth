package com.quid.auth.user.domain

data class User(
    val userSeq: Long? = null,
    val username: String,
    val password: String,
    val email: String,
    val name: String,
    val deleted: Boolean = false,
) {
    fun encodePassword(encodedPassword : String): User {
        return this.copy(password = encodedPassword)
    }

    init {
        require(username.length in 4..10) { "ID는 4자 이상 10자 이하 여야 합니다." }
    }
}