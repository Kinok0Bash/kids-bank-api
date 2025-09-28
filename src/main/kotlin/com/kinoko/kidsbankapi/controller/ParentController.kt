package com.kinoko.kidsbankapi.controller

import com.kinoko.kidsbankapi.dto.User
import com.kinoko.kidsbankapi.dto.request.RegistrationRequest
import com.kinoko.kidsbankapi.service.ParentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/parent")
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
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String
    ): ResponseEntity<User> {
        logger.info("Запрос на регистрацию детского аккаунта")
        return ResponseEntity.ok(parentService.createChildAccount(request, token))
    }

    @PutMapping("/salary")
    @Operation(summary = "Получить зарплату пользователю")
    fun getSalary(@RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String): ResponseEntity<Map<String, String>> {
        logger.info("Запрос на получение зарплаты")
        parentService.getSalary(token)
        return ResponseEntity.ok(mapOf("message" to "OK"))
    }
}
