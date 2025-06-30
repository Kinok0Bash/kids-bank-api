package com.kinoko.kidsbankapi.dto.response

import com.kinoko.kidsbankapi.enum.TransactionStatus

data class TransactionResponse(
    val status: TransactionStatus,
    val sum: Int
)
