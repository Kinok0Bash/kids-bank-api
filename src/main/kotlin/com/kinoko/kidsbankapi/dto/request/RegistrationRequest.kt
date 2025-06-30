package com.kinoko.kidsbankapi.dto.request

import java.time.LocalDate

data class RegistrationRequest(
    val lastname: String = "",
    val name: String = "",
    val fatherName: String = "",
    val username: String = "",
    val password: String = "",
    val birthDate: LocalDate = LocalDate.now(),
    val city: String = ""
)
