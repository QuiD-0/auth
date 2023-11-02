package com.quid.auth.global.security

import com.quid.auth.global.security.filter.JwtAuthenticationFilter
import com.quid.auth.global.token.usecase.TokenDecoder
import com.quid.auth.user.usecase.UserAuthService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.util.AntPathMatcher


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userAuthService: UserAuthService,
    private val jwtTokenDecoder: TokenDecoder
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http.httpBasic { it.disable() }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .authorizeHttpRequests{
                it.requestMatchers(*allow()).permitAll()
                it.anyRequest().authenticated()
            }
            .addFilterBefore(
                JwtAuthenticationFilter(
                    jwtTokenDecoder,
                    userAuthService
                ),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    private fun allow(): Array<RequestMatcher> = arrayOf(
        AntPathRequestMatcher("/", "GET"),
        AntPathRequestMatcher("/api/user/login", "POST"),
        AntPathRequestMatcher("/api/user/signup", "POST"),
    )

}