package de.stundenplan

import Klasse
import Schule
import Schulform
import Unterrichtsfach
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*

abstract class TestData {

    fun createAndPostSchule() = testApplication {
        val faecher = mutableListOf(Unterrichtsfach("Deutsch", 3))
        val klassen1 = mutableListOf(Klasse(1, "1a", faecher))
        val schule = Schule("Beispiel-Schule 1", Schulform.Grundschule, klassen1)
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        client.post("/schule") {
            contentType(ContentType.Application.Json)
            setBody(schule)
        }
    }
}