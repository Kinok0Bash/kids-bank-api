package com.kinoko.kidsbankapi.dto.report

data class LimitsReport(
    val parentFullName: String,
    val childFullName: String,
    val category: String
)
