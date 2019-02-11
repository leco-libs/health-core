package co.l3co.health.core.domain.services.contracts

import com.sun.tools.classfile.Dependency

interface ServicesChecker {

    fun checkAllStatus(validations: List<ValidationService<Dependency>>): Map<String, Boolean>
}