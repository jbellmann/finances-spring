package com.github.adeynack.finances.service

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@SpringBootApplication
class FinancesServiceApplication

fun main(args: Array<String>) {
    SpringApplication.run(FinancesServiceApplication::class.java, *args)
}
