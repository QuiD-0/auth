package com.quid.auth.user.domain

data class User(
    val userSeq: Long? = null,
    val username: String,
    val password: String,
    val email: String,
    val name: String,
    val deleted: Boolean = false,
) {
    init {
        require(username.length in 4..10) { "ID는 4자 이상 10자 이하 여야 합니다." }
        require(email.matches(emailRegex)) { "이메일 형식이 올바르지 않습니다." }
    }

    fun encodePassword(encodedPassword : String): User {
        return this.copy(password = encodedPassword)
    }

    companion object{
        private val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    }
}