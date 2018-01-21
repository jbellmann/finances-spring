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

    /**
     * Imports all data from a Moneydance JSON export.
     * @param inputStream the stream with the JSON content.
     * @param financeBookName the name the user wants for this book.
     * @param forceReset if `false` and the book already exists, the import will fail; otherwise,
     *                   the import will delete everything from this book and then import it.
     */
    fun import(inputStream: InputStream, financeBookName: String, forceReset: Boolean) {
        inputStream
            .use { objectMapper.readTree(it) }
            .get("all_items")
            .groupBy { it.get("obj_type").asText() }
            .forEach { entry ->
                log.info("Moneydance items of type ${entry.key}: ${entry.value.size}")
            }
    }

}
