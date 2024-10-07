package de.stundenplan.db

import Klasse
import Schule
import Schulform
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object KlasseTable : IntIdTable("klasse") {
    val klassenstufe = integer("klassenstufe")
    val bezeichnung = varchar("bezeichnung", 50)
    val unterrichtsfachList = varchar("unterrichtsfachList", 50) //TODO
}

class KlasseDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<KlasseDAO>(KlasseTable)

    var klassenstufe by KlasseTable.klassenstufe
    var bezeichnung by KlasseTable.bezeichnung
    var unterrichtsfachList by KlasseTable.unterrichtsfachList

    fun toModel(): Klasse {
        return Klasse(klassenstufe, bezeichnung, listOf()) //TODO fächer
    }
}
object SchuleTable : IntIdTable("schule") {
    val name = varchar("name", 50)
    val schulId = varchar("schulId", 50)
    val schulform = varchar("schulform", 50)
}

object SchuleKlasseTable : Table("schule_klasse") {
    val schule = reference("schule", SchuleTable, onDelete = ReferenceOption.CASCADE)
    val klasse = reference("klasse", KlasseTable, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(
        schule, klasse,
        name = "PK_SchuleKlasse"
    )
}
class SchuleDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SchuleDAO>(SchuleTable)
    var name by SchuleTable.name
    var schulId by SchuleTable.schulId
    var schulform by SchuleTable.schulform
    var klassen by KlasseDAO via SchuleKlasseTable

    fun toModel(): Schule {
        return Schule(
            name,
            schulId,
            Schulform.valueOf(schulform),
            klassen = klassen.map { klasseDao ->
                Klasse(
                    klasseDao.klassenstufe,
                    klasseDao.bezeichnung,
                    listOf()
                )
            } //TODO fächer)
        )
    }
}

// -----------------------------------------------------------------
suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)