package com.mediaversetv.chess.data

import com.mediaversetv.chess.components.Util
import edu.austral.dissis.chess.engine.interfaces.Game

data class GameRoom(val code: String = Util.createGameCode(), val game: Game) {
    fun toJson(): Map<String, Any> {
        return mapOf("board" to game.board.toString())
    }
}
