package com.kinoko.kidsbankapi.exception

class UserNotFoundException(
    message: String = "User not found",
    e: Throwable? = null
) : Exception(message, e)
