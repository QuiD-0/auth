package com.quid.auth.global.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http.authorizeRequests()
            .requestMatchers(*allow()).permitAll()
            .requestMatchers(*admin()).hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .csrf().disable()
            .formLogin().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .build()

    private fun admin(): Array<RequestMatcher> = arrayOf(
        AntPathRequestMatcher("/api/admin/**", "GET"),
    )

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    private fun allow(): Array<RequestMatcher> = arrayOf(
        AntPathRequestMatcher("/", "GET"),
        AntPathRequestMatcher("/api/user/login", "POST"),
        AntPathRequestMatcher("/api/user/signup", "POST"),
    )

}