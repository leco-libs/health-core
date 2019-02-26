package co.l3co.health.core.application.di

import co.l3co.health.core.domain.services.contracts.NoSqlDBService
import co.l3co.health.core.domain.services.contracts.ServicesChecker
import co.l3co.health.core.domain.services.contracts.SqlDBService
import co.l3co.health.core.domain.services.implementation.NoSqlDBServiceImpl
import co.l3co.health.core.domain.services.implementation.koin.ServicesCheckerImpl
import co.l3co.health.core.domain.services.implementation.SqlDBServiceImpl
import com.authzee.kotlinguice4.KotlinModule
import com.google.inject.Singleton

class HealthModuleGuice : KotlinModule() {
    override fun configure() {
        bind<SqlDBService>().to<SqlDBServiceImpl>().`in`<Singleton>()
        bind<NoSqlDBService>().to<NoSqlDBServiceImpl>()
        bind<ServicesChecker>().to<ServicesCheckerImpl>()
    }
}