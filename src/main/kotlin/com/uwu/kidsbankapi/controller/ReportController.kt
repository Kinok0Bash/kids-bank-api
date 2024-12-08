package com.uwu.kidsbankapi.controller

import com.uwu.kidsbankapi.service.ReportService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/report")
@Tag(
    name = "Отчёты",
    description = "Контроллер для выкачки отчетов"
)
class ReportController(private val reportService: ReportService) {
    private val logger = LoggerFactory.getLogger(ReportController::class.java)

//    @GetMapping("/animals")
//    @Operation(summary = "Выкачка отчета обо всех животных")
//    fun getAnimalReport(): ResponseEntity<ByteArray> {
//        val txt = reportService.generateAnimalReport()
//        val headers = HttpHeaders()
//        headers.add("Content-Disposition", "attachment; filename=animal_report.txt")
//        headers.add("Content-Type", "text/plain; charset=UTF-8")
//
//        return ResponseEntity(txt, headers, HttpStatus.OK)
//    }
//
//    @GetMapping("/medical")
//    @Operation(summary = "Выкачка отчета о здоровье животных за определенный год и месяц")
//    fun getMedicalReport(
//        @RequestParam("month") month: Int,
//        @RequestParam("year") year: Int
//    ): ResponseEntity<ByteArray> {
//        val txt = reportService.generateMedicalReport(month, year)
//        val headers = HttpHeaders()
//        headers.add("Content-Disposition", "attachment; filename=medical_report_${year}_${month}.txt")
//        headers.add("Content-Type", "text/plain; charset=UTF-8")
//
//        return ResponseEntity(txt, headers, HttpStatus.OK)
//    }
//
//    @GetMapping("/tickets")
//    @Operation(summary = "Выкачка отчета о продаже билетов за определенный год и месяц")
//    fun getTicketSalesReport(
//        @RequestParam("month") month: Int,
//        @RequestParam("year") year: Int
//    ): ResponseEntity<ByteArray> {
//        val txt = reportService.generateTicketSalesReport(month, year)
//        val headers = HttpHeaders()
//        headers.add("Content-Disposition", "attachment; filename=ticket_sales_report_${year}_${month}.txt")
//        headers.add("Content-Type", "text/plain; charset=UTF-8")
//
//        return ResponseEntity(txt, headers, HttpStatus.OK)
//    }
//
//    @ExceptionHandler
//    fun handleException(ex: Exception) : ResponseEntity<*> {
//        logger.error("Exception: $ex")
//        return ResponseEntity.badRequest().body(mapOf("error" to "${ex.message}"))
//    }
}