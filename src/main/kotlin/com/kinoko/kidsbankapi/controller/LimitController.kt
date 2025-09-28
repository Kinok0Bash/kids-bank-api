package com.kinoko.kidsbankapi.controller

import com.kinoko.kidsbankapi.dto.Limit
import com.kinoko.kidsbankapi.service.LimitService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
    fun getUserLimitList(@RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String): ResponseEntity<MutableList<Limit>> {
        logger.info("Запрос о получении списка ограничений пользователя")
        return ResponseEntity.ok(limitService.getUserLimitList(token))
    }

    @PutMapping
    @Operation(summary = "Метод для изменения ограничений категорий пользователя")
    fun setUserLimitList(
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String,
        @RequestBody request: List<Limit>
    ): ResponseEntity<MutableList<Limit>> {
        logger.info("Запрос для изменения списка ограничений пользователя")
        return ResponseEntity.ok(limitService.setUserLimitList(token, request))
    }
}
