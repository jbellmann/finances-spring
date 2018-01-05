package com.github.adeynack.finances.service

import com.github.adeynack.finances.service.repository.import.moneydance.MoneydanceImport
import org.apache.log4j.LogManager
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class FinancesServiceApplication(
    val moneydanceImport: MoneydanceImport
) : ApplicationRunner {

    private val log = LogManager.getLogger(FinancesServiceApplication::class.java)

    override fun run(args: ApplicationArguments) {

        // For the moment, the service imports automatically from Moneydance (later, will be just an option)
        moneydanceImport.import()

    }

}

fun main(args: Array<String>) {
    SpringApplication.run(FinancesServiceApplication::class.java, *args)
}
