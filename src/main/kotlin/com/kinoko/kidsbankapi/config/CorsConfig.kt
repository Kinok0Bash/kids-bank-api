package com.kinoko.kidsbankapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.applyPermitDefaultValues() // Включает стандартные разрешения
        configuration.allowedOrigins = listOf("http://localhost:5173", "https://uwu-devcrew.ru") // Разрешить запросы с localhost и uwu-devcrew.ru
        configuration.allowedMethods = listOf("*") // Разрешить любые методы (GET, POST, и т.д.)
        configuration.allowedHeaders = listOf("*") // Разрешить любые заголовки
        configuration.allowCredentials = true // Разрешить учетные данные (по необходимости)

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration) // Применить конфигурацию ко всем маршрутам
        return source
    }

    @Bean
    fun corsFilter(): CorsFilter {
        return CorsFilter(corsConfigurationSource())
    }

}
