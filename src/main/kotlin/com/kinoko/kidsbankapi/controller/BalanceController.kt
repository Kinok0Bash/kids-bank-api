package com.kinoko.kidsbankapi.controller

import com.kinoko.kidsbankapi.dto.response.ChildBalanceResponse
import com.kinoko.kidsbankapi.dto.response.ParentBalanceResponse
import com.kinoko.kidsbankapi.service.BalanceService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/balance")
@Tag(
    name = "Баланс",
    description = "Контроллер выдающий текущий баланс пользователя"
)
class BalanceController(private val balanceService: BalanceService) {
    private val logger = LoggerFactory.getLogger(BalanceController::class.java)

    @GetMapping("/parent")
    @Operation(summary = "Метод на получение баланса родителя и его ребенка")
    fun getParentBalance(@RequestHeader(name = "Authorization") token: String): ParentBalanceResponse {
       logger.info("Запрос на метод получения баланса родителя и его ребенка")
       return balanceService.getParentAccountBalance(token)
    }

    @GetMapping("/child")
    @Operation(summary = "Метод на получение баланса ребенка")
    fun getChildBalance(@RequestHeader(name = "Authorization") token: String): ChildBalanceResponse {
       logger.info("Запрос на метод получения баланса ребенка")
       return balanceService.getChildAccountBalance(token)
    }

    @ExceptionHandler
    fun handleException(ex: Exception): ResponseEntity<Map<String, String>> {
        logger.error("Ошибка: ${ex.stackTraceToString()}")
        return ResponseEntity.badRequest().body(mapOf("error" to ex.message.orEmpty()))
    }
}
