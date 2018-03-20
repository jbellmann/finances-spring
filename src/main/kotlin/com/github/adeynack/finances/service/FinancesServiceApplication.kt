package com.github.adeynack.finances.service

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class FinancesServiceApplication

fun main(args: Array<String>) {
    SpringApplication.run(FinancesServiceApplication::class.java, *args)
}
