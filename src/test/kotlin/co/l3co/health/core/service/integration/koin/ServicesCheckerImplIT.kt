package co.l3co.health.core.service.integration.koin

import co.l3co.health.core.application.config.healthModule
import co.l3co.health.core.domain.services.contracts.ServicesChecker
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import org.koin.test.AutoCloseKoinTest

class ServicesCheckerImplIT : AutoCloseKoinTest() {

    val service: ServicesChecker by inject()

    @Before
    fun setUp() {
        StandAloneContext.startKoin(arrayListOf(healthModule))
    }

    @Test
    fun checkAllStatus() {
        val result = service.checkAllStatus()
        Assert.assertTrue(result.get("status")!!)
    }

    @Test
    fun checkAllComplete() {
        val result = service.checkAllComplete()
        Assert.assertTrue(result.isNotEmpty())
    }
}