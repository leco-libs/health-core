package co.l3co.health.core.domain.services.implementation

import co.l3co.health.core.domain.entities.Dependency
import co.l3co.health.core.domain.services.contracts.CacheService
import redis.clients.jedis.HostAndPort
import redis.clients.jedis.JedisCluster

class CacheServiceImpl : CacheService {

    private val CACHE_HOSTNAMES = System.getenv("CACHE_HOSTNAMES") ?: "localhost:7379"
    private val PATTERN = """(\w*\:\d{0,4})"""

    override fun checkStatus(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun statusComplete(): Map<String, Dependency?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun openConnection() {
        val regex = Regex(PATTERN)
        regex.findAll(CACHE_HOSTNAMES).forEach { it.value }
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
        return CACHE_HOSTNAMES.isNotBlank()
    }

}