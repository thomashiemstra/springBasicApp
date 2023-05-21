package com.example.books.config


import com.github.tomakehurst.wiremock.WireMockServer
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.flywaydb.test.annotation.FlywayTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest(classes = [TestSecurityConfig::class])
@ActiveProfiles("test")
@AutoConfigureMockMvc
@EnableAutoConfiguration
@FlywayTest
@ContextConfiguration(initializers = [WireMockInitializer::class])
@AutoConfigureEmbeddedDatabase(type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES, provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class IntegrationTests {

    @Autowired
    lateinit var subject: MockMvc

    @Autowired
    private lateinit var wireMockServer: WireMockServer

    @BeforeEach
    fun resetWireMockServer() = wireMockServer.resetAll()

}