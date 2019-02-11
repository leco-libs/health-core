package co.l3co.health.core.domain.services.implementation

import co.l3co.health.core.domain.entities.Dependency
import co.l3co.health.core.domain.services.contracts.NoSqlDBService
import com.mongodb.MongoClient
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import org.litote.kmongo.KMongo
import java.lang.Exception
import java.time.LocalDateTime

class NoSqlDBServiceImpl : NoSqlDBService {

    private val NOSQL_DATABASE_HOSTNAME: String = System.getenv("NOSQL_DATABASE_HOSTNAME") ?: ""
    private val NOSQL_DATABASE_PORT: Int = System.getenv("NOSQL_DATABASE_PORT")?.toInt() ?: 27017
    private val NOSQL_DATABASE_NAME: String = System.getenv("NOSQL_DATABASE_NAME") ?: ""
    private val NOSQL_DATABASE_USERNAME: String = System.getenv("NOSQL_DATABASE_USERNAME") ?: ""
    private val NOSQL_DATABASE_PASSWORD: String = System.getenv("NOSQL_DATABASE_PASSWORD") ?: ""

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
        var result = HashMap<String, Dependency>()
        if (parametersValidation()) {
            try {
                val start = System.currentTimeMillis()

                connection = buildConnection()

                val end = System.currentTimeMillis()

                result["nosql"] = Dependency(
                    name = "mongo",
                    status = true,
                    elapsed = (end - start),
                    lastRunning = LocalDateTime.now(),
                    address = NOSQL_DATABASE_HOSTNAME
                )
            } finally {
                connection?.close()
            }
        }
        return result
    }

    override fun parametersValidation(): Boolean {
        return NOSQL_DATABASE_HOSTNAME.isNotBlank()
                && NOSQL_DATABASE_USERNAME.isNotBlank()
                && NOSQL_DATABASE_PASSWORD.isNotBlank()
                && NOSQL_DATABASE_NAME.isNotBlank()
    }

    private fun buildConnection(): MongoClient {
        val credentials = listOf(
            MongoCredential.createCredential(
                NOSQL_DATABASE_USERNAME,
                NOSQL_DATABASE_NAME,
                NOSQL_DATABASE_PASSWORD.toCharArray()
            )
        )
        val serverAddress = ServerAddress(NOSQL_DATABASE_HOSTNAME, NOSQL_DATABASE_PORT)

        return KMongo.createClient(serverAddress, credentials)
    }

}
