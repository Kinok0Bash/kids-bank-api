package com.kinoko.kidsbankapi

import com.kinoko.kidsbankapi.properties.AuthenticationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties(
    AuthenticationProperties::class
)
class KidsBankApiApplication

fun main(args: Array<String>) {
    runApplication<KidsBankApiApplication>(*args)
}
