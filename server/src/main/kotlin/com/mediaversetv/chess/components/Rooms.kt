package com.mediaversetv.chess.components

import com.mediaversetv.chess.data.GameRoom
import edu.austral.dissis.chess.engine.interfaces.Game

object Rooms {
    private val rooms = mutableListOf<GameRoom>()

    infix fun createRoom(game: Game): GameRoom {
        val room = GameRoom(game = game)
        rooms.add(room)
        return room
    }

    infix fun getRoom(code: String): GameRoom? {
        return rooms.find { it.code == code }
    }
}
