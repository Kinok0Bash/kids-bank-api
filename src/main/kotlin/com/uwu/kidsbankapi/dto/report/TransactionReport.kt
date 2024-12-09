package com.uwu.kidsbankapi.dto.report

data class TransactionReport(
    val childFullName: String,
    val shopName: String,
    val shopCategory: String,
    val sum: String,
    val date: String
)
