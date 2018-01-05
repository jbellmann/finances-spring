package com.github.adeynack.finances.service.repository.import.moneydance

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.log4j.LogManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File

@Component
class MoneydanceImport(
    @Value("\${moneydance-input-file}") val moneydanceInputFile: String,
    private val objectMapper: ObjectMapper
) {

    private val log = LogManager.getLogger(MoneydanceImport::class.java)

    fun import() {
        log.info("Moneydance Import from $moneydanceInputFile")
        File(moneydanceInputFile)
            .inputStream()
            .use { objectMapper.readTree(it) }
            .get("all_items")
            .groupBy { it.get("obj_type").asText() }
            .forEach { entry ->
                log.info("Moneydance items of type ${entry.key}: ${entry.value.size}")
            }
    }

}
