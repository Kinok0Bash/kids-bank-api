package com.kinoko.kidsbankapi.config

import com.kinoko.kidsbankapi.service.JwtService
import com.kinoko.kidsbankapi.util.CustomAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors { }
            .csrf { it.disable() }
            .addFilterBefore(
                JwtAuthenticationFilter(userDetailsService, jwtService),
                UsernamePasswordAuthenticationFilter::class.java,
            )
            .authorizeHttpRequests { authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                    .requestMatchers(
                        "/api/auth/authorization",
                        "/api/auth/registration",
                        "/api/auth/refresh",
                        "/api/auth/logout",
                        "/swagger-ui/*",
                        "/v3/api-docs",
                        "/v3/api-docs/*"
                    )
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .exceptionHandling { exceptionHandlingConfigurer ->

                exceptionHandlingConfigurer
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        return http.build()
    }

}
