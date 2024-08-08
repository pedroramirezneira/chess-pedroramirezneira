package com.mediaversetv.chess.plugins

import com.mediaversetv.chess.components.Configuration
import com.mediaversetv.chess.module
import io.ktor.server.application.*
import io.ktor.server.engine.*
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileOutputStream
import java.security.KeyStore

fun configureEnvironment(): ApplicationEngineEnvironment {
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

    return applicationEngineEnvironment {
        log = LoggerFactory.getLogger("ktor.application")
        connector {
            port = Configuration.httpPort ?: 8080
        }
        sslConnector(
            keyStore = keyStore,
            keyAlias = "sampleAlias",
            keyStorePassword = { "Chess2024.".toCharArray() },
            privateKeyPassword = { "Chess2024.".toCharArray() }) {
            port = Configuration.httpsPort ?: 443
            keyStorePath = keyStoreFile
        }
        module(Application::module)
    }
}
