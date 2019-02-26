package co.l3co.health.core.service.integration

import co.l3co.health.core.domain.services.implementation.koin.NoSqlDBServiceImpl
import org.junit.Assert
import org.junit.Test

class NoSqlServiceImplIT {

    val service = NoSqlDBServiceImpl()

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
        val result = resultMap.get("nosql")
        Assert.assertNotNull(result)
        Assert.assertTrue(result!!.name == "mongo")
    }
}