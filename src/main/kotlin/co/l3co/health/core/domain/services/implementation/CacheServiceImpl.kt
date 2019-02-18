package co.l3co.health.core.domain.services.implementation

import co.l3co.health.core.domain.entities.Dependency
import co.l3co.health.core.domain.services.contracts.CacheService
import redis.clients.jedis.HostAndPort
import redis.clients.jedis.JedisCluster
import java.time.LocalDateTime

class CacheServiceImpl : CacheService {

    private val CACHE_HOSTNAME = System.getenv("CACHE_HOSTNAME") ?: "localhost:7379"
    private val PATTERN = """(\w*\:\d{0,4})"""

    override fun checkStatus() = try {
        openConnection()
        true
    } catch (ex: Exception) {
        false
    }

    override fun statusComplete(): Map<String, Dependency?> {
        var result = HashMap<String, Dependency>()
        val start = System.currentTimeMillis()
        openConnection()
        val end = System.currentTimeMillis()

        result["cache"] = Dependency(
            name = "redis",
            status = true,
            elapsed = (end - start),
            lastRunning = LocalDateTime.now(),
            address = extractHostAndPort(CACHE_HOSTNAME).map { it.host }.joinToString { "," }
        )
        return result
    }

    private fun openConnection(): List<String> {
        val result = mutableListOf<String>()
        val jedisCluster = JedisCluster(extractHostAndPort(CACHE_HOSTNAME))
        jedisCluster.clusterNodes.forEach { t, u -> u.resource.ping(); result.add(t) }
        jedisCluster.close()
        return result
    }

    fun extractHostAndPort(string: String): Set<HostAndPort> {
        val regex = Regex(PATTERN)
        val result = regex.findAll(string)
        var response = mutableSetOf<HostAndPort>()
        result.forEach {
            it.groupValues
                .distinct()
                .forEach { group ->
                    group.split(":").let { extract -> response.add(HostAndPort(extract[0], extract[1].toInt())) }
                }
        }
        return response
    }

    override fun parametersValidation(): Boolean {
        return CACHE_HOSTNAME.isNotBlank()
    }

}