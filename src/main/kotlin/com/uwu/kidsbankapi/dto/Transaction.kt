package com.uwu.kidsbankapi.dto

import java.util.Date

data class Transaction(
    val name: String,
    val category: String,
    val sum: Int,
    val date: Date
)
