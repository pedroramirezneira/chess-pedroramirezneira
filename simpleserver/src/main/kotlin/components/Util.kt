package edu.austral.dissis.chess.components

import edu.austral.dissis.chess.engine.interfaces.IGame

object Util {
    fun gameToJson(game: IGame): Map<String, Any> {
        return mapOf(
            "board" to
                mapOf(
                    "height" to game.board.size.height,
                    "width" to game.board.size.width,
                    "pieces" to game.board.getPieces(),
                ),
            "player" to game.currentPlayer,
        )
    }
}
