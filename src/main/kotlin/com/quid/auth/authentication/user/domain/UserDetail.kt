package com.quid.auth.authentication.user.domain

import com.quid.auth.authentication.blacklist.domain.Blacklist
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


data class UserDetail (
    val user: User,
    val authority: List<UserAuthority>,
    val blacklist: List<Blacklist>
): UserDetails{
    val name: String
        get() = user.name

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        authority.map { GrantedAuthority { it.authorityName.name } }.toMutableList()

    override fun getPassword(): String =
        user.password

    override fun getUsername(): String =
        user.username

    override fun isAccountNonExpired(): Boolean =
        !user.deleted

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

}