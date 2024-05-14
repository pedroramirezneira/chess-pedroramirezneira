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
    embeddedServer(Netty, configureEnvironment()).start(wait = true)
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
