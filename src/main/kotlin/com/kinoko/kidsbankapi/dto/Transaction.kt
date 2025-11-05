package com.kinoko.kidsbankapi.dto

import java.time.LocalDateTime

data class Transaction(
    val toId: Int,
    val name: String,
    val category: String,
    val sum: Int,
    val date: LocalDateTime
)
