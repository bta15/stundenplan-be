package de.stundenplan

import Schulform
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class ApplicationJsonPathTest : TestData() {

    @BeforeTest
    fun setup() = testApplication {
        super.createAndPostSchule()
    }

    @Test
    fun schulenCanBeFound() = testApplication {
        val jsonDoc = client.getAsJsonPath("/schule")

        val result: List<String> = jsonDoc.read("$[*].name")
        assertEquals("Beispiel-Schule 1", result[0])
    }

    @Test
    fun schulenCanBeFoundBySchulform() = testApplication {
        val schulform = Schulform.Grundschule
        val jsonDoc = client.getAsJsonPath("/schule/bySchulform/$schulform")

        val result: List<String> =
            jsonDoc.read("$[?(@.schulform == '$schulform')].name")
        assertEquals(1, result.size)

        assertEquals("Beispiel-Schule 1", result[0])
    }

    suspend fun HttpClient.getAsJsonPath(url: String): DocumentContext {
        val response = this.get(url) {
            accept(ContentType.Application.Json)
        }
        return JsonPath.parse(response.bodyAsText())
    }
}