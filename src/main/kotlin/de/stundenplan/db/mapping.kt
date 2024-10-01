package de.stundenplan.db

import Schule
import Schulform
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object SchuleTable : IntIdTable("schule") {
    val name = varchar("name", 50)
    val schulform = varchar("schulform", 50)
    val klassen = varchar("klassen", 50) //TODO
}

class SchuleDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SchuleDAO>(SchuleTable)

    var name by SchuleTable.name
    var schulform by SchuleTable.schulform
    var klassen by SchuleTable.klassen
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: SchuleDAO) = Schule(
    dao.name,
    Schulform.valueOf(dao.schulform),
    klassen = listOf() //TODO
)