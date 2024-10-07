package de.stundenplan.model

import Schule
import Schulform
import de.stundenplan.db.*
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.deleteWhere

class SchuleRepository : ISchuleRepository {

    override suspend fun allSchulen(): List<Schule> = suspendTransaction {
        SchuleDAO
            .all()
            .map { schuleDAO -> schuleDAO.toModel() }
    }

    override suspend fun schuleBySchulId(schulId: String): Schule? = suspendTransaction {
        SchuleDAO.find { (SchuleTable.schulId eq schulId) }
            .limit(1)
            .map { schuleDAO -> schuleDAO.toModel() }
            .firstOrNull()
    }

    override suspend fun schulenBySchulform(schulform: Schulform): List<Schule> = suspendTransaction {
        SchuleDAO
            .find { (SchuleTable.schulform eq schulform.toString()) }
            .map { schuleDAO -> schuleDAO.toModel() }
    }

    override suspend fun schuleByName(name: String): Schule? = suspendTransaction {
        SchuleDAO.find { (SchuleTable.name eq name) }
            .limit(1)
            .map { schuleDAO -> schuleDAO.toModel() }
            .firstOrNull()
    }


    override suspend fun addSchule(schule: Schule): Unit = suspendTransaction {
        SchuleDAO.new {
            name = schule.name
            schulId = schule.schulId
            schulform = schule.schulform.toString()
            klassen = SizedCollection(
                schule.klassen.map { klasseModel ->
                    KlasseDAO.new {
                        klassenstufe = klasseModel.klassenstufe
                        bezeichnung = klasseModel.bezeichnung
                        unterrichtsfachList = "" // todo klasseModel.unterrichtsfachList
                    }
                }
            )
        }
    }

    override suspend fun removeSchuleBySchulId(schulId: String): Boolean = suspendTransaction {
        val schuleToDelete = SchuleDAO.find { (SchuleTable.schulId eq schulId) }.limit(1).first()
        val klassenIdsToDelete = schuleToDelete.klassen.map { bar -> bar.id }
        KlasseTable.deleteWhere {
            KlasseTable.id inList klassenIdsToDelete
        }
        val rowsDeleted = SchuleTable.deleteWhere {
            SchuleTable.schulId eq schulId
        }
        rowsDeleted == 1
    }
}