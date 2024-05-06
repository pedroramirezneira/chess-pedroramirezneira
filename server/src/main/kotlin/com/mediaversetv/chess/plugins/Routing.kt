package com.mediaversetv.chess.plugins

import com.mediaversetv.chess.components.Rooms
import edu.austral.dissis.chess.engine.CONFIG
import edu.austral.dissis.chess.engine.components.Chess
import edu.austral.dissis.chess.engine.components.movements.Castling
import edu.austral.dissis.chess.engine.components.validations.Check
import edu.austral.dissis.chess.engine.components.winconditions.CheckMate
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/game") {
            val chess = Chess fromJson CONFIG changeRules {
                add movement Castling()
                add validation Check()
                add winCondition CheckMate()
            }
            val room = Rooms createRoom chess
            println(room.code)
            call.respondText(room.code, status = HttpStatusCode.Created)
        }
        get("/game/{id}") {
            val id =
                call.parameters["id"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
            println(id)
            val room = (Rooms getRoom id) ?: return@get call.respondText("Not Found", status = HttpStatusCode.NotFound)
            call.respondText(room.code, status = HttpStatusCode.OK)
        }
    }
}
