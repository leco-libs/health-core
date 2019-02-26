package co.l3co.health.core.domain.services.implementation

import co.l3co.health.core.application.config.EnvironmentConfig
import co.l3co.health.core.application.config.EnvironmentConfig.Companion.NOSQL_DATABASE_HOSTNAME
import co.l3co.health.core.application.config.EnvironmentConfig.Companion.NOSQL_DATABASE_NAME
import co.l3co.health.core.application.config.EnvironmentConfig.Companion.NOSQL_DATABASE_PASSWORD
import co.l3co.health.core.application.config.EnvironmentConfig.Companion.NOSQL_DATABASE_PORT
import co.l3co.health.core.application.config.EnvironmentConfig.Companion.NOSQL_DATABASE_USERNAME
import co.l3co.health.core.domain.entities.Dependency
import co.l3co.health.core.domain.services.contracts.NoSqlDBService
import com.mongodb.MongoClient
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import org.litote.kmongo.KMongo
import java.lang.Exception
import java.time.LocalDateTime

class NoSqlDBServiceImpl(
    private val environmentConfig: EnvironmentConfig
) : NoSqlDBService {

    override fun checkStatus(): Boolean {
        var connection: MongoClient? = null
        return try {
            connection = buildConnection()
            true
        } catch (e: Exception) {
            false
        } finally {
            connection?.close()
        }
    }

    override fun statusComplete(): Map<String, Dependency?> {
        var connection: MongoClient? = null
        val result = HashMap<String, Dependency>()

            try {
                val start = System.currentTimeMillis()

                connection = buildConnection()

                val end = System.currentTimeMillis()

                result["nosql"] = Dependency(
                    name = "mongo",
                    status = true,
                    elapsed = (end - start),
                    lastRunning = LocalDateTime.now(),
                    address = en
                )
            } finally {
                connection?.close()
            }
        }
        return result
    }

    private fun buildConnection(): MongoClient {
        val credentials = listOf(
            MongoCredential.createCredential(
                environmentConfig.nosqlDatabaseUsername!!,
                environmentConfig.nosqlDatabaseName!!,
                environmentConfig.nosqlDatabasePassword!!.toCharArray()
            )
        )
        val serverAddress = ServerAddress(
            environmentConfig.nosqlDatabaseHostname!!,
            environmentConfig.nosqlDatabasePort!!
        )

        return KMongo.createClient(serverAddress, credentials)
    }

}
