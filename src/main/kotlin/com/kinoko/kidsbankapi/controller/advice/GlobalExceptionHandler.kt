package com.kinoko.kidsbankapi.controller.advice

import com.kinoko.kidsbankapi.dto.response.ErrorResponse
import com.kinoko.kidsbankapi.exception.AuthenticationException
import io.swagger.v3.oas.annotations.Hidden
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingRequestCookieException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Hidden
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(MissingRequestCookieException::class)
    fun handleMissingCookie(
        ex: MissingRequestCookieException,
    ): ResponseEntity<ErrorResponse> {
        logger.warn("MissingRequestCookieException: ${ex.message}")
        return ResponseEntity.badRequest().body(
            ErrorResponse(
                error = ex.message
            )
        )
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(
        ex: AuthenticationException,
    ): ResponseEntity<ErrorResponse> {
        logger.warn("AuthenticationException: ${ex.message}")
        return ResponseEntity.badRequest().body(
            ErrorResponse(
                error = ex.message ?: "Что-то пошло не так во время аутентификации. Свяжитесь с администратором"
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(
        ex: Exception,
    ): ResponseEntity<ErrorResponse> {
        logger.error("Unhandled exception: ${ex.message}", ex)
        logger.debug(ex.stackTraceToString())
        return ResponseEntity.badRequest().body(
            ErrorResponse(
                error = ex.message ?: "Неизвестная ошибка. Свяжитесь с администратором!"
            )
        )
    }
}
