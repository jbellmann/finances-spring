package com.github.adeynack.finances.service.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
@RequestMapping("health")
class HealthController {

    data class HealthStatus(
        val status: String,
        val systemTime: OffsetDateTime
    )

    @GetMapping
    fun getHealth(): HealthStatus = HealthStatus("healthy", OffsetDateTime.now())

}
