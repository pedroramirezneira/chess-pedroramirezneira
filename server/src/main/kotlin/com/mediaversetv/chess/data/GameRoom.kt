package com.mediaversetv.chess.data

import com.google.gson.Gson
import com.mediaversetv.chess.components.Util
import edu.austral.dissis.chess.engine.components.ChessEnded
import edu.austral.dissis.chess.engine.interfaces.Game
import io.ktor.server.websocket.*
import io.ktor.websocket.*

data class GameRoom(
    val code: String = Util.createGameCode(), var game: Game
) {
    val listeners: MutableList<DefaultWebSocketServerSession> = mutableListOf()

    suspend fun notifyListeners() {
        val gson = Gson()
        val data = Util.boardToJson(game.board)
        val json = gson.toJson(data)
        listeners.forEach { listener ->
            when {
                game is ChessEnded -> {
                    val winner = if (game.currentPlayer) "white" else "black"
                    val endData = data.plus("ended" to true).plus("winner" to winner)
                    val endJson = gson.toJson(endData)
                    listener.outgoing.send(Frame.Text(endJson))
                }

                else -> listener.outgoing.send(Frame.Text(json))
            }
        }
    }
}
