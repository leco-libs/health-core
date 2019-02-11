package co.l3co.health.core.service.integration

import co.l3co.health.core.domain.services.implementation.SqlDBServiceImpl
import org.junit.Assert
import org.junit.Test

class SqlDBServiceImplIT {

    val service = SqlDBServiceImpl()

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
        val result = resultMap.get("database")
        Assert.assertNotNull(result)
        Assert.assertTrue(result!!.name == "POSTGRES")
    }
}