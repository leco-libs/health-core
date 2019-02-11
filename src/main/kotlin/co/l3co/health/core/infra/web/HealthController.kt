package co.l3co.health.core.infra.web

import io.javalin.Context

interface HealthController {
    fun basic(ctx: Context)
    fun complete(ctx: Context)
}