package com.mediaversetv.chess.plugins

import com.google.gson.Gson
import com.mediaversetv.chess.components.Rooms
import com.mediaversetv.chess.components.Util
import com.mediaversetv.chess.data.GameRoom
import com.mediaversetv.chess.models.MovementRequest
import edu.austral.dissis.chess.engine.components.ChessEnded
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        webSocket("/ws") { // websocketSession
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    outgoing.send(Frame.Text("YOU SAID: $text"))
                    if (text.equals("bye", ignoreCase = true)) {
                        close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                    }
                }
            }
        }
        webSocket("/game/{code}") {
            val code = call.parameters["code"]?.uppercase() ?: return@webSocket
            val room: GameRoom = (Rooms getRoom code) ?: return@webSocket
            room.listeners.add(this)
            for (frame in incoming) {
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    val gson = Gson()
                    println(text)
                    if (text == "Hello from Flutter!") {
                        val data = Util.boardToJson(room.game.board)
                        val json = gson.toJson(data)
                        outgoing.send(Frame.Text(json))
                    } else if(gson.fromJson(text, MovementRequest::class.java) != null) {
                        val request = gson.fromJson(text, MovementRequest::class.java)
                        val coordinates = request.toCoordinates()
                        val state = room.game.moveFrom { coordinates }
                        when (state) {
                            room.game -> outgoing.send(Frame.Text("Update: Invalid move."))
                            is ChessEnded -> {
                                room.game = state
                                room.notifyListeners()
                                Rooms deleteRoom room
                            }
                            else -> {
                                room.game = state
                                room.notifyListeners()
                            }
                        }
                    }
                }
            }
        }
    }
}
