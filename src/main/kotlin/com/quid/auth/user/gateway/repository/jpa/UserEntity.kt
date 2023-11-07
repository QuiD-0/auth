package com.quid.auth.user.gateway.repository.jpa

import com.quid.auth.user.domain.User
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "user", indexes = [Index(name = "idx_username", columnList = "username", unique = true)])
class UserEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val userSeq: Long? = null,
    val username: String,
    val password: String,
    val email: String,
    val name: String,
    val deleted: Boolean,
) {
    fun toUser() = User(
        userSeq = userSeq,
        username = username,
        password = password,
        email = email,
        name = name,
        deleted = deleted,
    )
}

fun UserEntity(user: User) = UserEntity(
    userSeq = user.userSeq,
    username = user.username,
    password = user.password,
    email = user.email,
    name = user.name,
    deleted = user.deleted,
)
