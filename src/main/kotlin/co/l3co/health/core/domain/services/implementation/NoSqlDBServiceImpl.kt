package co.l3co.health.core.domain.services.implementation

import co.l3co.health.core.domain.entities.Dependency
import co.l3co.health.core.domain.services.contracts.NoSqlDBService

class NoSqlDBServiceImpl : NoSqlDBService {

    private val DATABASE_NAME = System.getenv("DATABASE_NAME") ?: ""
    private val DATABASE_HOSTNAME = System.getenv("DATABASE_HOSTNAME") ?: ""

    override fun checkStatus(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun statusComplete(): Map<String, Dependency> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun parametersValidation(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
