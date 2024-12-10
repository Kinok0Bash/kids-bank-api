package com.uwu.kidsbankapi.controller

import com.uwu.kidsbankapi.dto.Transaction
import com.uwu.kidsbankapi.dto.request.PayRequest
import com.uwu.kidsbankapi.dto.request.TransferRequest
import com.uwu.kidsbankapi.enum.TransactionStatus
import com.uwu.kidsbankapi.service.TransactionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/transaction")
@Tag(
    name = "Транзакции",
    description = "Контроллер функционала, связанного с оплатой переводами и прочим"
)
class TransactionController(private val transactionService: TransactionService) {
    private val logger = LoggerFactory.getLogger(TransactionController::class.java)

    @GetMapping("/last")
    @Operation(summary = "Получение последних 5 транзакций чтобы вынести на главную страницу")
    fun getLastTransactions(@RequestHeader(name = "Authorization") token: String): ResponseEntity<MutableList<Transaction>> {
        logger.info("Запрос на получение последних 5 транзакций")
        return ResponseEntity.ok(transactionService.getLastTransactions(token))
    }

    @GetMapping("/history")
    @Operation(summary = "Получение истории транзакций")
    fun getAllTransactions(@RequestHeader(name = "Authorization") token: String): ResponseEntity<MutableList<Transaction>> {
        logger.info("Запрос на получение всех транзакций")
        return ResponseEntity.ok(transactionService.getAllTransactions(token))
    }

    @PutMapping("/transfer")
    @Operation(summary = "Перевод родителя своему ребенку")
    fun transfer(
        @RequestHeader(name = "Authorization") token: String,
        @RequestBody request: TransferRequest
    ): ResponseEntity<Map<String, TransactionStatus>> {
        logger.info("Запрос на перевод ребенку ${request.sum} рубль(ей)")
        return ResponseEntity.ok(mapOf("status" to transactionService.transfer(token, request.sum)))
    }

    @PostMapping("/pay")
    @Operation(summary = "Оплата")
    fun pay(
        @RequestHeader(name = "Authorization") token: String,
        @RequestBody request: PayRequest
    ): ResponseEntity<Map<String, TransactionStatus>> {
        logger.info("Запрос на оплату счета от ${request.shopId} на ${request.sum}")
        return ResponseEntity.ok(mapOf("status" to transactionService.pay(token, request)))
    }

    @ExceptionHandler
    fun handleException(ex: Exception): ResponseEntity<Map<String, String>> {
        logger.error("Ошибка: ${ex.message}")
        return ResponseEntity.badRequest().body(mapOf("error" to ex.message.orEmpty()))
    }
}