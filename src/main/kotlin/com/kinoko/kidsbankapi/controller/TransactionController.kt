package com.kinoko.kidsbankapi.controller

import com.kinoko.kidsbankapi.dto.Transaction
import com.kinoko.kidsbankapi.dto.request.PayRequest
import com.kinoko.kidsbankapi.dto.request.TransferRequest
import com.kinoko.kidsbankapi.dto.response.TransactionResponse
import com.kinoko.kidsbankapi.service.TransactionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
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
class TransactionController(
    private val transactionService: TransactionService,
) {
    private val logger = LoggerFactory.getLogger(TransactionController::class.java)

    @GetMapping("/last")
    @Operation(summary = "Получение последних 5 транзакций чтобы вынести на главную страницу")
    fun getLastTransactions(@RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String): ResponseEntity<List<Transaction>> {
        logger.info("Запрос на получение последних 5 транзакций")
        return ResponseEntity.ok(transactionService.getLastTransactions(token))
    }

    @GetMapping("/history")
    @Operation(summary = "Получение истории транзакций")
    fun getAllTransactions(@RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String): ResponseEntity<List<Transaction>> {
        logger.info("Запрос на получение всех транзакций")
        return ResponseEntity.ok(transactionService.getAllTransactions(token))
    }

    @PutMapping("/transfer")
    @Operation(summary = "Перевод родителя своему ребенку")
    fun transfer(
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String,
        @RequestBody request: TransferRequest
    ): ResponseEntity<TransactionResponse> {
        logger.info("Запрос на перевод ребенку ${request.sum} рублей")
        return ResponseEntity.ok(transactionService.transfer(token, request.sum))
    }

    @PostMapping("/pay")
    @Operation(summary = "Оплата")
    fun pay(
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) token: String,
        @RequestBody request: PayRequest
    ): ResponseEntity<TransactionResponse> {
        logger.info("Запрос на оплату счета от магазина с id=${request.shopId} на сумму ${request.sum} рублей")
        return ResponseEntity.ok(transactionService.pay(token, request))
    }
}
