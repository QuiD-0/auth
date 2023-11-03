package com.quid.auth.global.security.filter

import com.quid.auth.global.token.usecase.TokenDecoder
import com.quid.auth.user.usecase.UserAuthService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val tokenDecoder: TokenDecoder,
    private val userAuthService: UserAuthService
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val accessToken = getToken(request)
                .run { tokenDecoder(this) }

            UsernamePasswordAuthenticationToken(
                userAuthService.loadUserByUsername(accessToken.payload.username),
                null,
                emptyList()
            ).also { SecurityContextHolder.getContext().authentication = it }
        } catch (e: Exception) {
            SecurityContextHolder.clearContext()
        }
        filterChain.doFilter(request, response)
    }

    private fun getToken(request: HttpServletRequest): String {
        val header = request.getHeader("Authorization")
        require(header.startsWith("Bearer ")){ "Authorization header must start with Bearer" }
        return header.substring(7)
    }

}