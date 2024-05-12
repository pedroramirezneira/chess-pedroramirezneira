package com.mediaversetv.chess

import com.mediaversetv.chess.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.io.File
import org.slf4j.LoggerFactory
import java.io.FileOutputStream
import java.security.KeyStore

fun main() {
    val keyStoreStream = Application::class.java.getResourceAsStream("/keystore.jks")
    val keyStoreFile = File.createTempFile("keystore", ".jks")

    keyStoreStream.use { inputStream ->
        FileOutputStream(keyStoreFile).use { outputStream ->
            inputStream?.copyTo(outputStream)
        }
    }

    val keyStore = KeyStore.getInstance("JKS").apply {
        load(keyStoreFile.inputStream(), "Chess2024.".toCharArray())
    }

    val environment = applicationEngineEnvironment {
        log = LoggerFactory.getLogger("ktor.application")
        connector {
            port = 8080
        }
        sslConnector(
            keyStore = keyStore,
            keyAlias = "sampleAlias",
            keyStorePassword = { "Chess2024.".toCharArray() },
            privateKeyPassword = { "Chess2024.".toCharArray() }) {
            port = 443
            keyStorePath = keyStoreFile
        }
        module(Application::module)
    }

    embeddedServer(Netty, environment).start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureTemplating()
    configureSockets()
    configureAdministration()
    configureRouting()
}
