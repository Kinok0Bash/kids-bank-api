package com.uwu.zooapi.util

import com.uwu.kidsbankapi.dto.User
import com.uwu.zooapi.entity.UserEntity

fun UserEntity.convertToUserDTO() = User(
    login = this.username,
    authPassword = this.password
)