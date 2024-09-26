package com.example

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Assert
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }

    @Test
    fun testIrgendwas() = testApplication {
        val response = client.get("/irgendwas")

        Assert.assertEquals(HttpStatusCode.OK, response.status)
        Assert.assertEquals("<h1>hier ist der irgendwas-Endpunkt</h1>", response.bodyAsText())
    }

    @Test
    fun testStundenplan() = testApplication {
        val response = client.get("/stundenplan")

        Assert.assertEquals(HttpStatusCode.OK, response.status)
        Assert.assertEquals("json", response.contentType()?.contentSubtype)

        assertContains(response.bodyAsText(), "\"name\":\"Meine Schule\"")
    }
}
