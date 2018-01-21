package com.github.adeynack.finances.service.model

import java.time.LocalDate

data class Account(
    val id: String,
    val parentId: String?,
    val name: String,
    val type: String // todo: change to something more programmatic
)

