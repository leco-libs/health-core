package co.l3co.health.core.service.unit

import co.l3co.health.core.domain.services.implementation.koin.CacheServiceImpl
import org.junit.Assert
import org.junit.Test

class CacheServiceImplTest {

    val service = CacheServiceImpl()

    @Test
    fun extractHostAndPort() {
        val hostAndPort = service.extractHostAndPort("localhost:7000, localhost:3000")
        Assert.assertTrue(hostAndPort.size > 1)
    }
}