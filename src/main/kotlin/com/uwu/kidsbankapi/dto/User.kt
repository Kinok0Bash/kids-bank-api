package com.uwu.kidsbankapi.dto

import com.uwu.kidsbankapi.enum.Role
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.util.*

data class User(
    val lastname: String = "",
    val name: String = "",
    val fatherName: String = "",
    val username: String = "",
    val birthDate: LocalDate = LocalDate.now(),
    val city: String = "",
    val role: Role = Role.PARENT
)
