package com.mediaversetv.chess.plugins

import com.mediaversetv.chess.components.Rooms
import edu.austral.dissis.chess.engine.components.Game
import edu.austral.dissis.chess.engine.engine.components.movements.Castling
import edu.austral.dissis.chess.engine.engine.components.movements.EnPassant
import edu.austral.dissis.chess.engine.engine.components.movements.Promotion
import edu.austral.dissis.chess.engine.engine.components.validations.Check
import edu.austral.dissis.chess.engine.engine.components.winconditions.CheckMate
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
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
            val resource = Application::class.java.getResourceAsStream("/config/config.json")!!
            val config = resource.readAllBytes().toString(Charsets.UTF_8)
            val arrangement = call.receiveText().ifEmpty { config }
            val chess = Game fromJson arrangement changeRules {
                add movement Castling()
                add movement Promotion()
                add movement EnPassant()
                add validation Check()
                add winCondition CheckMate()
                if (arrangement != config) add fromJson config
            }
            val room = Rooms createRoom chess
            println(room.code)
            GlobalScope.launch {
                delay(60 * 60 * 1000L)
                Rooms deleteRoom room
            }
            call.respondText(room.code, status = HttpStatusCode.Created)
        }
        get("/game/{code}") {
            val code =
                call.parameters["code"]?.uppercase() ?: return@get call.respondText(
                    "Bad Request",
                    status = HttpStatusCode.BadRequest
                )
            println(code)
            val room =
                (Rooms getRoom code) ?: return@get call.respondText("Not Found", status = HttpStatusCode.NotFound)
            call.respondText(room.code, status = HttpStatusCode.OK)
        }
    }
}
