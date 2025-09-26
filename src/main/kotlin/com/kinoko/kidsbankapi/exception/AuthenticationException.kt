package com.kinoko.kidsbankapi.exception

class AuthenticationException(
    message: String,
    e: Throwable? = null
) : Exception("Authentication exception: $message", e)
