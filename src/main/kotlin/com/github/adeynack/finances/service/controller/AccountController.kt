package com.github.adeynack.finances.service.controller

import com.github.adeynack.finances.service.model.Account
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/account")
class AccountController {

    @GetMapping
    fun listAccounts(): List<Account> {
        return listOf( // todo: Real implementation
            Account("a", null, "A", "bank"),
            Account("a1", "a", "A1", "bank"),
            Account("a2", "a", "A2", "bank"),
            Account("b", null, "B", "bank")
        )
    }

}
