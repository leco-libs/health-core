package co.l3co.health.core.domain.services.implementation.koin

import co.l3co.health.core.application.config.EnvironmentConfig
import co.l3co.health.core.domain.entities.Dependency
import co.l3co.health.core.domain.entities.Status
import co.l3co.health.core.domain.services.contracts.CacheService
import redis.clients.jedis.HostAndPort
import redis.clients.jedis.Jedis
import java.time.LocalDateTime

class CacheServiceImpl(
    private val environmentConfig: EnvironmentConfig
) : CacheService {

    private val hostname: String =
        environmentConfig.cacheHostname ?: throw NullPointerException("Cache hostname is required!")

    override fun statusComplete(): Map<String, Dependency?> {
        val start = System.currentTimeMillis()
        val end = System.currentTimeMillis()
        return mapOf<String, Dependency?>(
            "cache" to Dependency(
                name = "redis",
                status = this.checkStatus(),
                elapsed = (end - start),
                lastRunning = LocalDateTime.now(),
                address = this.getAddress()
            )
        )
    }

    override fun createConnection(): Map<String, String> {
        val hostTested = mutableMapOf<String, String>()
        extractHostAndPort(hostname).forEach {
            try {
                Jedis(hostname).ping()
                hostTested[it.host] = Status.UP.name
            } catch (e: Exception) {
                hostTested[it.host] = Status.DOWN.name
            }
        }
        return hostTested
    }

    fun extractHostAndPort(string: String): Set<HostAndPort> {
        val regex = Regex(environmentConfig.cachePattern)
        val result = regex.findAll(string)
        val response = mutableSetOf<HostAndPort>()
        result.forEach {
            it.groupValues
                .distinct()
                .forEach { group ->
                    group.split(":").let { extract ->
                        response.add(HostAndPort(extract[0], extract[1].toInt()))
                    }
                }
        }
        return response
    }
}