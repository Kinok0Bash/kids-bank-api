package com.uwu.kidsbankapi.controller

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler

@Controller("/api/parent")
class ParentController {
    private val logger = LoggerFactory.getLogger(ParentController::class.java)



    @ExceptionHandler
    fun handleException(ex: Exception): ResponseEntity<Map<String, String>> {
        logger.error("Ошибка: ${ex.message}")
        return ResponseEntity.badRequest().body(mapOf("error" to ex.message.orEmpty()))
    }
}