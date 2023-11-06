package com.quid.auth.global.security

import com.quid.auth.global.security.filter.JwtAuthenticationEntryPoint
import com.quid.auth.global.security.filter.JwtAuthenticationFilter
import com.quid.auth.token.usecase.TokenDecoder
import com.quid.auth.user.usecase.UserAuthService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val userAuthService: UserAuthService,
    private val jwtTokenDecoder: TokenDecoder,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http.httpBasic { it.disable() }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .exceptionHandling{
                it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
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
    )

}