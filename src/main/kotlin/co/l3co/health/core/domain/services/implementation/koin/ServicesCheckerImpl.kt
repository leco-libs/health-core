package co.l3co.health.core.domain.services.implementation.koin

import co.l3co.health.core.domain.entities.Dependency
import co.l3co.health.core.domain.services.contracts.NoSqlDBService
import co.l3co.health.core.domain.services.contracts.ServicesChecker
import co.l3co.health.core.domain.services.contracts.SqlDBService
import co.l3co.health.core.domain.services.contracts.ValidationService
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ServicesCheckerImpl : KoinComponent, ServicesChecker {

    private val SQL_DATABASE_CHECK = System.getenv("SQL_DATABASE_CHECK")?.toBoolean() ?: false
    private val NOSQL_DATABASE_CHECK = System.getenv("NOSQL_DATABASE_CHECK")?.toBoolean() ?: false

    override fun getDependencies(): List<ValidationService> {
        var dependencies = mutableListOf<ValidationService>()
        if (SQL_DATABASE_CHECK) {
            val sqlDBService by inject<SqlDBService>()
            dependencies.add(sqlDBService)
        }

        if (NOSQL_DATABASE_CHECK) {
            val noSqlDBService by inject<NoSqlDBService>()
            dependencies.add(noSqlDBService)
        }
        return dependencies
    }

    override fun checkAllStatus(): Map<String, Boolean> {
        for (validation in getDependencies()) {
            if (!validation.checkStatus())
                return hashMapOf("status" to validation.checkStatus())
        }
        return hashMapOf("status" to true)
    }

    override fun checkAllComplete(): Map<String, Dependency> {
        val result = HashMap<String, Dependency>()
        for (validation in getDependencies()) {
            val complete = validation.statusComplete()
            complete.keys.forEach {
                result[it] = complete[it]!!
            }
        }
        return result
    }
}