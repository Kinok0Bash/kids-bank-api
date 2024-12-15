package com.uwu.kidsbankapi.dto.response

import com.uwu.kidsbankapi.enum.TransactionStatus

data class TransactionResponse(
    val status: TransactionStatus,
    val sum: Int
)
