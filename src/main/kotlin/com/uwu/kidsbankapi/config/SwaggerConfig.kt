package com.uwu.kidsbankapi.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
        title = "Система управления зоопарком",
        description = """
            Система управления зоопарком для курсовой работы по Базам Данных 
            Выполнено студентом ВПР33 Гринько Максимом""",
        version = "1.0.0"
    )
)
class SwaggerConfig