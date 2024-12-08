package com.uwu.kidsbankapi.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
        title = "Информационная система \"Детский банкинг\"",
        description = """
            Информационная система для работы с детским банкингом 
            Выполнено студентом ВПР32 Запара Иваном""",
        version = "1.0.0"
    )
)
class SwaggerConfig