package com.example.demo.infrastructure.security.config

import com.example.demo.infrastructure.security.jwt.JWTAuthenticationFilter
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig (
    private final val jwtAuthenticationFilter: JWTAuthenticationFilter,
    private final val authProvider: AuthenticationProvider,
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .exceptionHandling { exceptions ->
                exceptions.authenticationEntryPoint { _, response, exception ->
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                    response.contentType = "application/json"
                    response.writer.write("""
                        {
                            "error": "No autorizado",
                            "message": "Debes iniciar sesión para acceder a este recurso.",
                            "debug": "${exception.message}"
                        }
                    """.trimIndent())
                }
            }
            .authorizeHttpRequests { request ->
                request
                    .requestMatchers("/api/users/**").authenticated()
                    .anyRequest().permitAll()
            }
            .sessionManagement { sessionManager ->
                sessionManager
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}