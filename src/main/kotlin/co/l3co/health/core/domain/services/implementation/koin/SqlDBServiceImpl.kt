package co.l3co.health.core.domain.services.implementation.koin

import co.l3co.health.core.application.config.EnvironmentConfig
import co.l3co.health.core.application.config.EnvironmentConfig.Companion.SQL_DATABASE_DRIVER
import co.l3co.health.core.application.config.EnvironmentConfig.Companion.SQL_DATABASE_PASSWORD
import co.l3co.health.core.application.config.EnvironmentConfig.Companion.SQL_DATABASE_URL
import co.l3co.health.core.application.config.EnvironmentConfig.Companion.SQL_DATABASE_USERNAME
import co.l3co.health.core.domain.entities.Dependency
import co.l3co.health.core.domain.entities.Status
import co.l3co.health.core.domain.services.contracts.SqlDBService
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception
import java.time.LocalDateTime

class SqlDBServiceImpl(
    private val environment: EnvironmentConfig
) : SqlDBService {

    override fun statusComplete(): Map<String, Dependency> {
        val result = mutableMapOf<String, Dependency>()

        try {
            val start = System.currentTimeMillis()
            createConnection()
            databaseAvailable()
            val end = System.currentTimeMillis()

            result["database"] = Dependency(
                name = getName(),
                status = true,
                elapsed = (end - start),
                lastRunning = LocalDateTime.now(),
                address = extractAddress()
            )
        } catch (e: Exception) {
            throw  e
        }
        return result
    }

    override fun createConnection(): Map<String, String> {
        val result = mutableMapOf<String, String>()
        try {
            Database.connect(
                url = environment.sqlDatabaseUrl!!,
                user = environment.sqlDatabaseUsername!!,
                password = environment.sqlDatabasePassword!!,
                driver = environment.sqlDatabaseDriver!!
            )
            result[environment.sqlDatabaseUrl!!] = Status.UP.name
        } catch (ex: Exception) {
            result[environment.sqlDatabaseUrl!!] = Status.DOWN.name
        }
        return result
    }

    private fun extractAddress() =
        Regex(PATTERN).find(environment.sqlDatabaseDriver!!)?.groupValues?.get(1) ?: DEFAULT_MESSAGE

    private fun databaseAvailable() {
        transaction {
            val conn = TransactionManager.current().connection
            val statement = conn.createStatement()
            statement.execute(query())
        }
    }

    private fun getName(): String {
        val name = environment.sqlDatabaseDriver?.toLowerCase()
        if ("postgres" in name!!) return "POSTGRES"
        if ("oracle" in name) return "ORACLE"
        if ("mysql" in name) return "MYSQL"
        if ("sqlite" in name) return "SQLITE"
        if ("h2" in name) return "H2"
        return DEFAULT_MESSAGE
    }

    private fun query() = when (environment.sqlDatabaseDriver) {
        "oracle.jdbc.driver.OracleDriver" -> "Select * from dual"
        else -> "Select 1"

    }

    companion object {
        const val PATTERN = "jdbc\\:\\w*\\:\\/\\/(\\w*)\\:\\d*\\/\\w*"
        const val DEFAULT_MESSAGE = "UNKNOWN"
    }
}