package com.kinoko.kidsbankapi.dto

import java.util.*

data class Transaction(
    val name: String,
    val category: String,
    val sum: Int,
    val date: Date
)
