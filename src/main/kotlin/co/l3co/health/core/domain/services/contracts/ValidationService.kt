package co.l3co.health.core.domain.services.contracts

import co.l3co.health.core.domain.entities.Dependency


interface ValidationService {

    fun checkStatus(): Boolean
    fun statusComplete(): Map<String, Dependency?>
}