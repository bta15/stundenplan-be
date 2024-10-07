package de.stundenplan.model

import Schule
import Schulform

interface ISchuleRepository {
    suspend fun allSchulen(): List<Schule>
    suspend fun schulenBySchulform(schulform: Schulform): List<Schule>
    suspend fun schuleByName(name: String): Schule?
    suspend fun schuleBySchulId(schulId: String): Schule?
    suspend fun addSchule(schule: Schule)
    suspend fun removeSchuleBySchulId(schulId: String): Boolean
}