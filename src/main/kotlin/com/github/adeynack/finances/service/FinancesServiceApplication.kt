package com.github.adeynack.finances.service

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class FinancesServiceApplication

fun main(args: Array<String>) {
    SpringApplication.run(FinancesServiceApplication::class.java, *args)
}
