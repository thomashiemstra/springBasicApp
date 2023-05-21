package com.example.books.config

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextStoppedEvent

class WireMockInitializer:ApplicationContextInitializer<ConfigurableApplicationContext> {
    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val wireMockServer = WireMockServer(9000)
        wireMockServer.start()
        applicationContext.beanFactory.registerSingleton("wireMockServer", wireMockServer)
        applicationContext.addApplicationListener {
            if (it is ContextClosedEvent || it is ContextStoppedEvent ) {
                wireMockServer.stop()
            }
        }
    }
}