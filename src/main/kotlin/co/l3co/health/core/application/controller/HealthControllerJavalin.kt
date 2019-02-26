package co.l3co.health.core.application.controller

import co.l3co.health.core.domain.services.contracts.ServicesChecker
import io.javalin.Context

class HealthControllerJavalin(override val checker: ServicesChecker) : HealthController<Context> {
    override inline fun basic(context: Context) {
        context.json(checker.checkAllStatus())
    }

    override inline fun complete(ctx: Context) {
        ctx.json(checker.checkAllComplete())
    }
}