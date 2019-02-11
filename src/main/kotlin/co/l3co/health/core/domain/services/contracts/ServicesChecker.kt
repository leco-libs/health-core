package co.l3co.health.core.domain.services.contracts

import co.l3co.health.core.domain.entities.Dependency

interface ServicesChecker {

    fun getDependencies(): List<ValidationService>

    fun checkAllStatus(): Map<String, Boolean>

    fun checkAllComplete(): Map<String, Dependency>
}