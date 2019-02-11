package co.l3co.health.core.config

import co.l3co.health.core.domain.services.contracts.NoSqlDBService
import co.l3co.health.core.domain.services.contracts.ServicesChecker
import co.l3co.health.core.domain.services.contracts.SqlDBService
import co.l3co.health.core.domain.services.implementation.NoSqlDBServiceImpl
import co.l3co.health.core.domain.services.implementation.koin.ServicesCheckerImpl
import co.l3co.health.core.domain.services.implementation.SqlDBServiceImpl
import org.koin.dsl.module.module

val healthModule = module {
    // Service
    single { SqlDBServiceImpl() as SqlDBService }
    single { NoSqlDBServiceImpl() as NoSqlDBService }
    single { ServicesCheckerImpl() as ServicesChecker }
}