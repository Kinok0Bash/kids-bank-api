package com.uwu.kidsbankapi.util

import com.uwu.kidsbankapi.dto.User
import com.uwu.kidsbankapi.entity.UserEntity
import com.uwu.kidsbankapi.enum.Role

fun UserEntity.convertToUserDTO(): User {
    val isGetChild = (this.role == Role.PARENT && this.child != null)
    return User(
        fullName = String.format("%s %s %s", this.lastname, this.name, this.fatherName),
        username = this.login,
        lastname = this.lastname,
        name = this.name,
        fatherName = this.fatherName,
        birthDate = this.birthDate,
        city = this.city,
        role = this.role,
        isGetKid = isGetChild
    )
}