package co.l3co.health.core.service.integration

import co.l3co.health.core.domain.services.implementation.koin.CacheServiceImpl
import org.junit.Assert
import org.junit.Test

class CacheServiceImplIT {

    val service = CacheServiceImpl()

    @Test
    fun parametersValidationOk() {
        val result = service.parametersValidation()
        Assert.assertTrue(result)
    }

    @Test
    fun checkStatus() {
        val statusOk = this.service.checkStatus()
        Assert.assertTrue(statusOk)
    }

    @Test
    fun statusComplete() {
        val resultMap = this.service.statusComplete()
        val result = resultMap.get("cache")
        Assert.assertNotNull(result)
        Assert.assertTrue(result!!.name == "redis")
    }
}