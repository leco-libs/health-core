package co.l3co.health.core.application.di

import co.l3co.health.core.application.controller.HealthControllerJavalin
import co.l3co.health.core.domain.services.contracts.CacheService
import co.l3co.health.core.domain.services.contracts.NoSqlDBService
import co.l3co.health.core.domain.services.contracts.ServicesChecker
import co.l3co.health.core.domain.services.contracts.SqlDBService
import co.l3co.health.core.domain.services.implementation.koin.CacheServiceImpl
import co.l3co.health.core.domain.services.implementation.koin.NoSqlDBServiceImpl
import co.l3co.health.core.domain.services.implementation.koin.ServicesCheckerImpl
import co.l3co.health.core.domain.services.implementation.koin.SqlDBServiceImpl
import org.koin.dsl.module.module

val healthModule = module {
    // Service
    single { SqlDBServiceImpl(get()) as SqlDBService }
    single { NoSqlDBServiceImpl(get()) as NoSqlDBService }
    single { CacheServiceImpl(get()) as CacheService }
    single { ServicesCheckerImpl() as ServicesChecker }
    single { HealthControllerJavalin(get()) }
}