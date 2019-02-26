package co.l3co.health.core.domain.services.implementation.koin

import co.l3co.health.core.application.config.EnvironmentConfig
import co.l3co.health.core.domain.entities.Dependency
import co.l3co.health.core.domain.entities.Status
import co.l3co.health.core.domain.services.contracts.NoSqlDBService
import com.mongodb.MongoClient
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import org.litote.kmongo.KMongo
import java.time.LocalDateTime

class NoSqlDBServiceImpl(
    private val environmentConfig: EnvironmentConfig
) : NoSqlDBService {

    override fun statusComplete(): Map<String, Dependency?> {
        val start = System.currentTimeMillis()
        val end = System.currentTimeMillis()
        return mapOf(
            "nosql" to Dependency(
                name = "mongo",
                status = this.checkStatus(),
                elapsed = (end - start),
                lastRunning = LocalDateTime.now(),
                address = this.getAddress()
            )
        )
    }

    override fun createConnection(): Map<String, String> {
        var connection: MongoClient? = null
        val result = mutableMapOf<String, String>()
        try {
            connection = environmentConfig.let {
                KMongo.createClient(
                    ServerAddress(
                        it.nosqlDatabaseHostname!!,
                        it.nosqlDatabasePort!!
                    ), listOf(
                        MongoCredential.createCredential(
                            it.nosqlDatabaseUsername!!,
                            it.nosqlDatabaseName!!,
                            it.nosqlDatabasePassword!!.toCharArray()
                        )
                    )
                )
            }
            connection.close()
            result[environmentConfig.nosqlDatabaseHostname!!] = Status.UP.name
        } catch (e: Exception) {
            result[environmentConfig.nosqlDatabaseHostname!!] = Status.DOWN.name
        } finally {
            connection?.close()
        }
        return result
    }
}
