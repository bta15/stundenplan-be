package de.stundenplan.model

import Schule
import Schulform
import de.stundenplan.db.SchuleDAO
import de.stundenplan.db.SchuleTable
import de.stundenplan.db.daoToModel
import de.stundenplan.db.suspendTransaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

class SchuleRepository : ISchuleRepository {
//    private val faecher = mutableListOf(Unterrichtsfach("Deutsch", 3))
//    private val klassen1 = mutableListOf(Klasse(1, "1a", faecher))
//    private val schulen = mutableListOf(
//        Schule("Beispiel-Schule 1", Schulform.Grundschule, klassen1),
//
//        )

    override suspend fun allSchulen(): List<Schule> = suspendTransaction {
        SchuleDAO.all().map(::daoToModel)
    }

    override suspend fun schulenBySchulform(schulform: Schulform): List<Schule> = suspendTransaction {
        SchuleDAO
            .find { (SchuleTable.schulform eq schulform.toString()) }
            .map(::daoToModel)
    }

    override suspend fun schuleByName(name: String): Schule? = suspendTransaction {
        SchuleDAO.find { (SchuleTable.name eq name) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }


    override suspend fun addSchule(schule: Schule): Unit = suspendTransaction {
        SchuleDAO.new {
            name = schule.name
            schulform = schule.schulform.toString()
            klassen = ""//TODO
        }
    }

    override suspend fun removeSchule(name: String): Boolean = suspendTransaction {
        val rowsDeleted = SchuleTable.deleteWhere {
            SchuleTable.name eq name
        }
        rowsDeleted == 1
    }
}