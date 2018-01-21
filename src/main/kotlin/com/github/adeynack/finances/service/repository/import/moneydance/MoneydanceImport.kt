package com.github.adeynack.finances.service.repository.import.moneydance

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.log4j.LogManager
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class MoneydanceImport(
    private val objectMapper: ObjectMapper
) {

    private val log = LogManager.getLogger(MoneydanceImport::class.java)

    fun import(inputStream: InputStream, filename: String, forceReset: Boolean) {
        inputStream
            .use { objectMapper.readTree(it) }
            .get("all_items")
            .groupBy { it.get("obj_type").asText() }
            .forEach { entry ->
                log.info("Moneydance items of type ${entry.key}: ${entry.value.size}")
            }
    }

}
