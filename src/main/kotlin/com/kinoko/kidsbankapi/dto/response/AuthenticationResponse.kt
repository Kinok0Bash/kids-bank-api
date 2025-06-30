package com.kinoko.kidsbankapi.dto.response

import com.kinoko.kidsbankapi.dto.User

data class AuthenticationResponse (
    var token: String,
    var user: User
)
