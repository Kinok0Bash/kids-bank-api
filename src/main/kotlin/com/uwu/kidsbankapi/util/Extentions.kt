package com.uwu.kidsbankapi.util

import com.uwu.kidsbankapi.dto.User
import com.uwu.kidsbankapi.entity.UserEntity

fun UserEntity.convertToUserDTO() = User(
    username = this.login,
    lastname = this.lastname,
    name = this.name,
    fatherName = this.fatherName,
    birthDate = this.birthDate,
    city = this.city,
    role = this.role
)