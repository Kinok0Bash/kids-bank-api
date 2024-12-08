package com.uwu.kidsbankapi.dto.response

import com.uwu.kidsbankapi.dto.User

data class AuthenticationResponse (
    var token: String,
    var user: User
)
