package edu.austral.dissis.chess.engine.engine.components.validations

import edu.austral.dissis.chess.engine.components.Game
import edu.austral.dissis.chess.engine.data.Tile
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.Validation

class Check : Validation {
    override fun verify(game: IGame): Boolean {
        val nextTurn = Game(game.board, game.rules, !game.currentPlayer, game.states)
        val kings = getPlayerKings(game)
        return when {
            kings.isEmpty() -> false
            kings.size > 1 -> true
            else -> kingIsSafe(kings.first(), nextTurn)
        }
    }

    private fun kingIsSafe(
        king: Tile,
        game: Game,
    ): Boolean {
        val pieces = game.board.getPieces().filter { it.piece.color == game.currentPlayer }
        return pieces.all { tile ->
            val state = game moveFrom { tile.coordinate to king.coordinate }
            state == game
        }
    }

    private fun getPlayerKings(game: IGame) =
        (game.board getPiecesByType "king").filter {
            it.piece.color == game.currentPlayer
        }
}
