package de.stundenplan.model

import Klasse
import Schule
import Schulform
import Unterrichtsfach

object StundenplanRepository {
    private val faecher = mutableListOf(Unterrichtsfach("Deutsch", 3))
    private val klassen1 = mutableListOf(Klasse(1, "1a", faecher))
    private val schulen = mutableListOf(
        Schule("Beispiel-Schule 1", Schulform.Grundschule, klassen1),

        )

    fun schulen(): List<Schule> = schulen

    fun schulenBySchulform(priority: Schulform) = schulen.filter {
        it.schulform == priority
    }

    fun schuleByName(name: String) = schulen.find {
        it.name.equals(name, ignoreCase = true)
    }

    fun addSchule(schule: Schule) {
        if (schuleByName(schule.name) != null) {
            throw IllegalStateException("Cannot duplicate school names!")
        }
        schulen.add(schule)
    }

    fun removeSchule(name: String): Boolean {
        return schulen.removeIf { it.name == name }
    }
}