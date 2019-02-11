package co.l3co.health.core.domain.services.implementation

import co.l3co.health.core.domain.entities.Dependency
import co.l3co.health.core.domain.services.contracts.SqlDBService
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception
import java.time.LocalDateTime

class SqlDBServiceImpl : SqlDBService {

    private val DATABASE_DRIVER: String = System.getenv("DATABASE_DRIVER") ?: ""
    private val DATABASE_URL: String = System.getenv("DATABASE_URL") ?: ""
    private val DATABASE_USERNAME: String = System.getenv("DATABASE_USERNAME") ?: ""
    private val DATABASE_PASSWORD: String = System.getenv("DATABASE_PASSWORD") ?: ""

    override fun parametersValidation(): Boolean {
        return DATABASE_DRIVER.isNotBlank()
                && DATABASE_PASSWORD.isNotBlank()
                && DATABASE_URL.isNotBlank()
                && DATABASE_USERNAME.isNotBlank()
    }

    override fun checkStatus(): Boolean {
        return if (parametersValidation()) {
            return try {
                createConnection()
                databaseAvailable()
                true
            } catch (e: Exception) {
                false
            }
        } else {
            false
        }
    }

    override fun statusComplete(): Map<String, Dependency> {
        var result = HashMap<String, Dependency>()

        if (parametersValidation()) {
            try {
                val start = System.currentTimeMillis()
                createConnection()
                databaseAvailable()
                val end = System.currentTimeMillis()

                result.put(
                    "database", Dependency(
                        name = getName(),
                        status = true,
                        elapsed = (end - start),
                        lastRunning = LocalDateTime.now(),
                        address = extractAddress()
                    )
                )

            } catch (e: Exception) {
                throw  e
            }
        }
        return result
    }

    private fun createConnection() {
        Database.connect(
            url = DATABASE_URL,
            user = DATABASE_USERNAME,
            password = DATABASE_PASSWORD,
            driver = DATABASE_DRIVER
        )
    }

    private fun extractAddress(): String {
        val regex = Regex("jdbc\\:\\w*\\:\\/\\/(\\w*)\\:\\d*\\/\\w*")
        val address = regex.find(DATABASE_URL)
        return address?.groupValues?.get(1) ?: "UNDEFINED"
    }

    private fun databaseAvailable() {
        transaction {
            val conn = TransactionManager.current().connection
            val statement = conn.createStatement()
            statement.execute(query())
        }
    }

    private fun getName(): String {
        val name = DATABASE_DRIVER.toLowerCase()
        if ("postgres" in name) return "POSTGRES"
        if ("oracle" in name) return "ORACLE"
        if ("mysql" in name) return "MYSQL"
        if ("sqlite" in name) return "SQLITE"
        if ("h2" in name) return "H2"
        return "UNDEFINED"
    }

    private fun query(): String {
        return when (DATABASE_DRIVER) {
            "oracle.jdbc.driver.OracleDriver" -> "Select * from dual"
            else -> "Select 1"
        }
    }
}