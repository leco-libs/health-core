package co.l3co.health.core.controller

import io.javalin.Context

interface HealthController<T> {
    fun basic(context: T)
    fun complete(ctx: T)
}