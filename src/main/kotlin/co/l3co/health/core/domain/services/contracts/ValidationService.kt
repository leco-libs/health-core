package co.l3co.health.core.domain.services.contracts

import com.sun.tools.classfile.Dependency

interface ValidationService<T> {

    fun checkStatus(): Boolean
    fun statusComplete(): Map<String, T?>
    fun parametersValidation(): Boolean
}