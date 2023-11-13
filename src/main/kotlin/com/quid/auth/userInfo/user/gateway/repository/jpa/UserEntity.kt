package com.quid.auth.userInfo.user.gateway.repository.jpa

import com.quid.auth.userInfo.user.domain.User
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import org.hibernate.annotations.Where

@Entity
@Where(clause = "deleted = false")
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
