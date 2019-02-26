package co.l3co.health.core.domain.services.contracts

import co.l3co.health.core.domain.entities.Dependency
import co.l3co.health.core.domain.entities.Status


interface ValidationService {
    fun statusComplete(): Map<String, Dependency?>
    fun createConnection(): Map<String, String>
    fun checkStatus() = !this.createConnection().containsValue(Status.DOWN.name)
    fun getAddress() = this.createConnection().map { "\"${it.key}\" : \"${it.value}\"" }.joinToString { "," }
}