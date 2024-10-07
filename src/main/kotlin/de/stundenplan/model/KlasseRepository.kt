package de.stundenplan.model

import Klasse
import de.stundenplan.db.KlasseDAO
import de.stundenplan.db.suspendTransaction

class KlasseRepository : IKlasseRepository {

    override suspend fun allKlassen(): List<Klasse> = suspendTransaction {
        KlasseDAO
            .all()
            .map { klasseDAO -> klasseDAO.toModel() }
    }
}