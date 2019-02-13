package co.l3co.health.core.domain.entities

import java.time.LocalDateTime

class Dependency(
    val name: String,
    val address: String,
    val status: Boolean,
    val elapsed: Long?,
    val lastRunning: LocalDateTime
)