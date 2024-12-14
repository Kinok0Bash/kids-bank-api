package com.uwu.kidsbankapi.controller

import com.uwu.kidsbankapi.dto.User
import com.uwu.kidsbankapi.dto.request.RegistrationRequest
import com.uwu.kidsbankapi.service.ParentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/parent")
@CrossOrigin
@Tag(
    name = "Функционал родителя",
    description = "Контроллер функционала, связанного с родителем"
)
class ParentController(private val parentService: ParentService) {
    private val logger = LoggerFactory.getLogger(ParentController::class.java)

    @PostMapping("/new-child")
    @Operation(summary = "Регистрация детского аккаунта")
    fun createChildAccount(
        @RequestBody request: RegistrationRequest,
        @RequestHeader(name = "Authorization") token: String
    ): ResponseEntity<User> {
        logger.info("Запрос на регистрацию детского аккаунта")
        return ResponseEntity.ok(parentService.createChildAccount(request, token))
    }

    @PutMapping("/salary")
    @Operation(summary = "Получить зарплату пользователю")
    fun getSalary(@RequestHeader(name = "Authorization") token: String): ResponseEntity<Map<String, String>> {
        logger.info("Запрос на получение зарплаты")
        parentService.getSalary(token)
        return ResponseEntity.ok(mapOf("message" to "OK"))
    }

    @ExceptionHandler
    fun handleException(ex: Exception): ResponseEntity<Map<String, String>> {
        logger.error("Ошибка: ${ex.stackTraceToString()}")
        return ResponseEntity.badRequest().body(mapOf("error" to ex.message.orEmpty()))
    }
}