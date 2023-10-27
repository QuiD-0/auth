package com.quid.auth.global.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.authorizeRequests()
            .requestMatchers(*allow()).permitAll()
            .anyRequest().authenticated()
            .and()
            .csrf().disable()
            .formLogin()
            .and()
            .build()
    }

    private fun allow(): Array<RequestMatcher> = arrayOf(
        AntPathRequestMatcher("/api/user/signup", "POST"),
        AntPathRequestMatcher("/signup", "GET"),
        AntPathRequestMatcher("/", "GET"),
    )

}