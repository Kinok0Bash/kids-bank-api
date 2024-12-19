package com.uwu.kidsbankapi.controller

import com.uwu.kidsbankapi.dto.User
import com.uwu.kidsbankapi.dto.request.AuthenticationRequest
import com.uwu.kidsbankapi.dto.request.RegistrationRequest
import com.uwu.kidsbankapi.dto.response.AuthenticationResponse
import com.uwu.kidsbankapi.service.AuthenticationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@Tag(
    name = "Аутентификация",
    description = "Основной контроллер аутентификации"
)
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {
    private val logger: Logger = LoggerFactory.getLogger(AuthenticationController::class.java)

    @PostMapping("/authorization")
    @Operation(summary = "Авторизация пользователя")
    fun authorization(@RequestBody request: AuthenticationRequest, response: HttpServletResponse): ResponseEntity<AuthenticationResponse> {
        logger.info("Запрос на авторизацию")
        return ResponseEntity.ok(authenticationService.authorization(request, response))
    }

    @PostMapping("/registration")
    @Operation(summary = "Регистрация пользователя")
    fun registration(@RequestBody request: RegistrationRequest, response: HttpServletResponse): ResponseEntity<AuthenticationResponse> {
        logger.info("Запрос на регистрацию")
        return ResponseEntity.ok(authenticationService.registration(request, response))
    }

    @PostMapping("/logout")
    @Operation(summary = "Выход пользователя с сайта")
    fun logout(@CookieValue(value = "refreshToken") token: String,
               response: HttpServletResponse): ResponseEntity<Map<String, String>> {
        logger.info("Запрос на выход из аккаунта")
        return ResponseEntity.ok(authenticationService.logout(token, response))
    }

    @GetMapping("/refresh")
    @Operation(summary = "Обновление токена")
    fun refresh(@CookieValue(value = "refreshToken") token: String, response: HttpServletResponse): ResponseEntity<AuthenticationResponse> {
        logger.info("Запрос на обновление токена")
        return ResponseEntity.ok(authenticationService.refresh(token, response))
    }

    @GetMapping("/who-am-i")
    @Operation(description = "Информация об аутентифицированном пользователе")
    fun whoAmI(@RequestHeader(value = "Authorization") token: String): ResponseEntity<User> {
        logger.info("Запрос на WhoAmI")
        return ResponseEntity.ok().body(authenticationService.whoAmI(token))
    }

    @ExceptionHandler
    fun handleException(ex: Exception) : ResponseEntity<*> {
        logger.error("Ошибка: ${ex.stackTraceToString()}")
        return ResponseEntity.badRequest().body(mapOf("error" to "${ex.message}"))
    }

}