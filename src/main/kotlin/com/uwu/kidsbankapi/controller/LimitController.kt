package com.uwu.kidsbankapi.controller

import com.uwu.kidsbankapi.dto.Limit
import com.uwu.kidsbankapi.service.LimitService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/limit")
@Tag(
    name = "Ограничения",
    description = "Контроллер для управления ограничениями по категориям"
)
class LimitController(private val limitService: LimitService) {
    private val logger = LoggerFactory.getLogger(LimitController::class.java)

    @GetMapping
    @Operation(summary = "Метод для получения списка ограничений пользователя")
    fun getUserLimitList(@RequestHeader(name = "Authorization") token: String): ResponseEntity<MutableList<Limit>> {
        logger.info("Запрос о получении списка ограничений пользователя")
        return ResponseEntity.ok(limitService.getUserLimitList(token))
    }

    @PutMapping
    @Operation(summary = "Метод для изменения ограничений категорий пользователя")
    fun setUserLimitList(
        @RequestHeader(name = "Authorization") token: String,
        @RequestBody request: List<Limit>
    ): ResponseEntity<MutableList<Limit>> {
        logger.info("Запрос для изменения списка ограничений пользователя")
        return ResponseEntity.ok(limitService.setUserLimitList(token, request))
    }

    @ExceptionHandler
    fun handleException(ex: Exception): ResponseEntity<Map<String, String>> {
        logger.error("Ошибка: ${ex.message}")
        return ResponseEntity.badRequest().body(mapOf("error" to ex.message.orEmpty()))
    }
}