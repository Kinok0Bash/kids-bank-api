package com.uwu.kidsbankapi.controller

import com.uwu.kidsbankapi.dto.request.AuthenticationRequest
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
        logger.info("Request to authorization")
        return ResponseEntity.ok(authenticationService.authorization(request, response))
    }

    @PostMapping("/registration")
    @Operation(summary = "Регистрация пользователя")
    fun registration(@RequestBody request: AuthenticationRequest, response: HttpServletResponse): ResponseEntity<AuthenticationResponse> {
        logger.info("Request to registration")
        return ResponseEntity.ok(authenticationService.registration(request, response))
    }

    @PostMapping("/logout")
    @Operation(summary = "Выход пользователя с сайта")
    fun logout(@CookieValue(value = "refreshToken") token: String,
               response: HttpServletResponse): ResponseEntity<Map<String, String>> {
        logger.info("Request to logout")
        return ResponseEntity.ok(authenticationService.logout(token, response))
    }

    @GetMapping("/refresh")
    @Operation(summary = "Обновление токена")
    fun refresh(@CookieValue(value = "refreshToken") token: String, response: HttpServletResponse): ResponseEntity<AuthenticationResponse> {
        logger.info("Request to refresh token")
        return ResponseEntity.ok(authenticationService.refresh(token, response))
    }

    @GetMapping("/who-am-i")
    @Operation(description = "Полная инфа об аутентифицированном пользователе. Поверь, позже пригодится!!!")
    fun whoAmI(@RequestHeader(value = "Authorization") token: String): ResponseEntity<Map<String, String>> {
        logger.info("Request to WhoAmI")
        return ResponseEntity.ok().body(mapOf("username" to authenticationService.whoAmI(token)))
    }

    @ExceptionHandler
    fun handleException(ex: Exception) : ResponseEntity<*> {
        logger.error("Exception: $ex")
        return ResponseEntity.badRequest().body(mapOf("error" to "${ex.message}"))
    }

}