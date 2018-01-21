package com.github.adeynack.finances.service.repository

import com.github.adeynack.finances.service.model.Account
import com.github.adeynack.finances.service.model.BookContext
import com.github.adeynack.finances.service.service.LogService
import org.springframework.stereotype.Repository

@Repository
class AccountRepository(
    logService: LogService
) {

    private val log = logService.getLogger(AccountRepository::class)

    fun addAccount(bookContext: BookContext, account: Account) {
        log.info("User: ${bookContext.userId} / Book: ${bookContext.bookId} / Adding account $account")
    }

}
