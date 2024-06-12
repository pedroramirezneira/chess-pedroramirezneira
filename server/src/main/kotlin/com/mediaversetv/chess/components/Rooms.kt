package com.mediaversetv.chess.components

import com.mediaversetv.chess.data.GameRoom
import edu.austral.dissis.chess.engine.interfaces.IGame
import io.ktor.websocket.*

object Rooms {
    private val rooms = mutableListOf<GameRoom>()

    infix fun createRoom(game: IGame): GameRoom {
        val room = GameRoom(game = game)
        rooms.add(room)
        return room
    }

    infix fun getRoom(code: String): GameRoom? {
        return rooms.find { it.code == code }
    }

    suspend infix fun deleteRoom(room: GameRoom) {
        room.listeners.forEach { listener ->
            listener.close(CloseReason(CloseReason.Codes.NORMAL, "Game has ended."))
        }
        rooms.remove(room)
    }
}
