package co.l3co.health.core.application.config

import com.natpryce.konfig.Configuration
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.booleanType
import com.natpryce.konfig.getValue
import com.natpryce.konfig.intType
import com.natpryce.konfig.stringType

class EnvironmentConfig(
    configuration: Configuration = EnvironmentVariables()
) {
    // REDIS
    val cacheHostname = configuration.getOrNull(CACHE_HOSTNAME)
    val cachePattern = configuration.getOrElse(PATTERN, """(\w*\:\d{0,4})""")
    // MONGO
    val nosqlDatabaseHostname = configuration.getOrNull(NOSQL_DATABASE_HOSTNAME)
    val nosqlDatabasePort = configuration.getOrNull(NOSQL_DATABASE_PORT)
    val nosqlDatabaseName = configuration.getOrNull(NOSQL_DATABASE_NAME)
    val nosqlDatabaseUsername= configuration.getOrNull(NOSQL_DATABASE_USERNAME)
    val nosqlDatabasePassword= configuration.getOrNull(NOSQL_DATABASE_PASSWORD)

    companion object {
        // REDIS
        val CACHE_HOSTNAME by stringType
        val PATTERN by stringType
        // MONGO
        val NOSQL_DATABASE_HOSTNAME by stringType
        val NOSQL_DATABASE_PORT by intType
        val NOSQL_DATABASE_NAME by stringType
        val NOSQL_DATABASE_USERNAME by stringType
        val NOSQL_DATABASE_PASSWORD by stringType
    }
}