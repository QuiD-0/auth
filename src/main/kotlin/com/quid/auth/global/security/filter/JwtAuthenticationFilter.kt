package com.quid.auth.global.security.filter

import com.quid.auth.token.usecase.TokenDecoder
import com.quid.auth.userInfo.user.usecase.UserAuthService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val tokenDecoder: TokenDecoder,
    private val userAuthService: UserAuthService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val accessToken = getToken(request)
                .run { tokenDecoder(this) }
            require(accessToken.isNotExpired()) { "access token is expired" }

            val user = userAuthService.loadUserByUsername(accessToken.username)

            UsernamePasswordAuthenticationToken(user, null, user.authorities)
                .also { SecurityContextHolder.getContext().authentication = it }

        } catch (e: Exception) {
            request.setAttribute("Exception", e)
            SecurityContextHolder.clearContext()
        }
        filterChain.doFilter(request, response)
    }

    private fun getToken(request: HttpServletRequest): String {
        val header = request.getHeader("Authorization")
        require(header.startsWith("Bearer ")) { "Authorization header must start with Bearer" }
        return header.substring(7)
    }

}