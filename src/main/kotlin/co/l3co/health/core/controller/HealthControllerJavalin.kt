package co.l3co.health.core.controller

import io.javalin.Context

class HealthControllerJavalin : HealthController<Context> {
    override fun complete(ctx: Context) {
    }

    override fun basic(ctx: Context) {
    }
}