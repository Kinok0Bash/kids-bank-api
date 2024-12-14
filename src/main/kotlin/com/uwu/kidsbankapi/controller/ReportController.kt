package com.uwu.kidsbankapi.controller

import com.uwu.kidsbankapi.dao.ReportDAO
import com.uwu.kidsbankapi.dto.report.LimitsReport
import com.uwu.kidsbankapi.dto.report.ShopTransactionReport
import com.uwu.kidsbankapi.dto.report.TransactionReport
import com.uwu.kidsbankapi.service.ReportService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reports")
@Tag(
    name = "Отчёты",
    description = "Контроллер для работы с отчетами"
)
class ReportController(
    private val reportService: ReportService,
    private val reportDAO: ReportDAO
) {
    private val logger = LoggerFactory.getLogger(ReportController::class.java)

    @GetMapping("/transactions")
    @Operation(summary = "Получение отчета о транзакциях пользователей")
    fun getTransactionsReport(): ResponseEntity<List<TransactionReport>> {
        logger.info("Запрос на получение отчета о транзакциях пользователей")
        return ResponseEntity.ok(reportDAO.getTransactionsReport())
    }

    @GetMapping("/transactions/download")
    @Operation(summary = "Выкачка отчета о транзакциях пользователей")
    fun downloadTransactionsReport(): ResponseEntity<ByteArray> {
        logger.info("Запрос на скачивание отчета о транзакциях пользователей")
        val report = reportService.generateTransactionsTxtReport()
        val headers = HttpHeaders()
        headers.add("Content-Disposition", "attachment; filename=transactions_report.txt")
        headers.add("Content-Type", "text/plain; charset=UTF-8")
        return ResponseEntity(report, headers, HttpStatus.OK)
    }

    @GetMapping("/limits")
    @Operation(summary = "Получение отчета об ограничениях пользователей")
    fun getLimitsReport(): ResponseEntity<List<LimitsReport>> {
        logger.info("Запрос на получение отчета об ограничениях пользователей")
        return ResponseEntity.ok(reportDAO.getLimitsReport())
    }

    @GetMapping("/limits/download")
    @Operation(summary = "Выкачка отчета об ограничениях пользователей")
    fun downloadLimitsReport(): ResponseEntity<ByteArray> {
        logger.info("Запрос на скачивание отчета об ограничениях пользователей")
        val report = reportService.generateLimitsTxtReport()
        val headers = HttpHeaders()
        headers.add("Content-Disposition", "attachment; filename=limits_report.txt")
        headers.add("Content-Type", "text/plain; charset=UTF-8")
        return ResponseEntity(report, headers, HttpStatus.OK)
    }

    @GetMapping("/shops")
    @Operation(summary = "Получение отчета о суммах потраченных на магазин")
    fun getShopTransactionsReport(): ResponseEntity<List<ShopTransactionReport>> {
        logger.info("Запрос на получение отчета о суммах потраченных на магазин")
        return ResponseEntity.ok(reportDAO.getShopTransactionsReport())
    }

    @GetMapping("/shops/download")
    @Operation(summary = "Выкачка отчета о суммах потраченных на магазин")
    fun downloadShopTransactionsReport(): ResponseEntity<ByteArray> {
        logger.info("Запрос на скачивание отчета о суммах потраченных на магазин")
        val report = reportService.generateShopTransactionsTxtReport()
        val headers = HttpHeaders()
        headers.add("Content-Disposition", "attachment; filename=shop_transactions_report.txt")
        headers.add("Content-Type", "text/plain; charset=UTF-8")
        return ResponseEntity(report, headers, HttpStatus.OK)
    }

    @ExceptionHandler
    fun handleException(ex: Exception): ResponseEntity<Map<String, String>> {
        logger.error("Ошибка: ${ex.stackTraceToString()}")
        return ResponseEntity.badRequest().body(mapOf("error" to ex.message.orEmpty()))
    }
}