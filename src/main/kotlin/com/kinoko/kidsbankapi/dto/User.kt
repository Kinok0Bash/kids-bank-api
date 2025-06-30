package com.kinoko.kidsbankapi.dto

import com.kinoko.kidsbankapi.enum.Role
import java.time.LocalDate
import java.util.*

data class User(
    val id: UUID = UUID.randomUUID(),
    val fullName: String = "",
    val lastname: String = "",
    val name: String = "",
    val fatherName: String = "",
    val username: String = "",
    val birthDate: LocalDate = LocalDate.now(),
    val city: String = "",
    val role: Role = Role.PARENT,
    val isGetKid: Boolean = false
)
