package com.mediaversetv.chess

import com.mediaversetv.chess.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

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
