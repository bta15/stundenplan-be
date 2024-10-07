package de.stundenplan.model

import Klasse

interface IKlasseRepository {
    suspend fun allKlassen(): List<Klasse>
}