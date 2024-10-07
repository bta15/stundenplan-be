package de.stundenplan.plugins

import de.stundenplan.db.KlasseTable
import de.stundenplan.db.SchuleKlasseTable
import de.stundenplan.db.SchuleTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SchemaUtils.createDatabase
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    Database.connect(
        "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        "org.h2.Driver",
//        user = "postgres",
//        password = "password"
    )

    transaction {
        createDatabase("irgendwas")
        create(SchuleTable)
        create(KlasseTable)
        create(SchuleKlasseTable)
    }
}