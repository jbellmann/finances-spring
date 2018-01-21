package com.github.adeynack.finances.service.repository.import.moneydance

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.adeynack.finances.service.model.Account
import com.github.adeynack.finances.service.model.BookContext
import com.github.adeynack.finances.service.repository.AccountRepository
import com.github.adeynack.finances.service.service.LogService
import org.springframework.stereotype.Component
import java.io.InputStream
import java.time.LocalDate

@Component
class MoneydanceImport(
    logService: LogService,
    private val objectMapper: ObjectMapper,
    private val accountRepository: AccountRepository
) {

    private val log = logService.getLogger(MoneydanceImport::class)

    /**
     * Imports all data from a Moneydance JSON export.
     * @param inputStream the stream with the JSON content.
     * @param financeBookName the name the user wants for this book.
     * @param forceReset if `false` and the book already exists, the import will fail; otherwise,
     *                   the import will delete everything from this book and then import it.
     */
    fun import(userId: String, inputStream: InputStream, financeBookName: String, forceReset: Boolean) {
        val context = BookContext(userId, financeBookName) // todo: Resolve ID of the book, not using its name as ID
        val elements = inputStream
            .use { objectMapper.readTree(it) }
            .get("all_items")
            .groupBy { it.get("obj_type").asText() }
        importAccounts(context, elements["acct"] ?: emptyList())
    }

    private fun importAccounts(bookContext: BookContext, nodes: List<JsonNode>) {
        log.info("User: ${bookContext.userId} / Book: ${bookContext.bookId} / Adding ${nodes.size} accounts")
        nodes.forEach {
            fun needText(field: String): String {
                val f = it.get(field)?.asText()
                if (f == null || f.isEmpty()) {
                    throw IllegalArgumentException("Account does not have an `id`: ${objectMapper.writeValueAsString(it)}")
                }
                return f
            }

            val id = needText("id")
            val parentId = it["parentid"]?.asText()
            val name = needText("name")
            val type = needText("type")

            val account = Account(id, parentId, name, type)

            accountRepository.addAccount(bookContext, account)
        }
    }

}
