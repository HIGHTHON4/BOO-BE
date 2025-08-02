package com.example.global.config

import com.example.global.error.GlobalExceptionFilter
import com.example.global.security.jwt.JwtTokenFilter
import com.example.global.security.jwt.JwtTokenProvider
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsUtils

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper
) {
    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors(Customizer.withDefaults())
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(CorsUtils::isCorsRequest).permitAll()
                    .requestMatchers(
                        "/swagger-ui/index.html",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html",
                        "/user/signup",
                        "/user/login",
                        "/error",
                        "/debate/wait",
                        "/debate/done-list",
                        "/debate/done",
                        "/user/check/**",
                        "/error"
                    ).permitAll()
                    .requestMatchers(
                        "/debate/create"
                    ).authenticated()
                    .anyRequest().authenticated()
            }

        http
            .addFilterBefore(JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(GlobalExceptionFilter(objectMapper), JwtTokenFilter::class.java)

        return http.build()
    }

    @Bean
    protected fun passwordEncoder() = BCryptPasswordEncoder()
}
