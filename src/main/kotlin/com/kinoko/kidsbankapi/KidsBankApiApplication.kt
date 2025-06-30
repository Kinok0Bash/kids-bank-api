package com.kinoko.kidsbankapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class KidsBankApiApplication

fun main(args: Array<String>) {
    runApplication<KidsBankApiApplication>(*args)
}
