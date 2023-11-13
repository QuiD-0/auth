package com.quid.auth.global.security

import com.quid.auth.authentication.user.usecase.UserAuthService
import com.quid.auth.global.security.filter.FilterAuthenticationEntryPoint
import com.quid.auth.global.security.filter.JwtAuthenticationFilter
import com.quid.auth.token.usecase.TokenDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val filterAuthenticationEntryPoint: FilterAuthenticationEntryPoint,
    private val jwtAuthenticationFailureHandler: AccessDeniedHandler,
    private val userAuthService: UserAuthService,
    private val jwtTokenDecoder: TokenDecoder,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http.httpBasic { it.disable() }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .exceptionHandling{
                it.authenticationEntryPoint(filterAuthenticationEntryPoint)
                it.accessDeniedHandler(jwtAuthenticationFailureHandler)
            }
            .addFilterBefore(
                JwtAuthenticationFilter(
                    jwtTokenDecoder,
                    userAuthService
                ),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests{
                it.requestMatchers(*allow()).permitAll()
                it.requestMatchers(*admin()).hasRole("ADMIN")
                it.anyRequest().authenticated()
            }
            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    private fun allow(): Array<RequestMatcher> = arrayOf(
        AntPathRequestMatcher("/", "GET"),
        AntPathRequestMatcher("/api/user/login", "POST"),
        AntPathRequestMatcher("/api/user/signup", "POST"),
        AntPathRequestMatcher("/api/token/refresh", "POST"),
    )

    private fun admin(): Array<RequestMatcher> = arrayOf(
        AntPathRequestMatcher("/api/admin/**/", "POST"),
        AntPathRequestMatcher("/api/admin/**/", "GET"),
    )

}