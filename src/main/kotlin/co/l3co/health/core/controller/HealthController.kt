package co.l3co.health.core.controller

import co.l3co.health.core.domain.services.contracts.ServicesChecker

interface HealthController<T> {

    val checker: ServicesChecker

    fun basic(context: T)
    fun complete(ctx: T)
}