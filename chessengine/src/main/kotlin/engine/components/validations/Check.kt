package edu.austral.dissis.chess.engine.engine.components.validations

import edu.austral.dissis.chess.engine.components.Game
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.Validation

class Check : Validation {
    override fun verify(game: IGame): Boolean {
        val nextTurn = Game(game.board, game.rules, !game.currentPlayer, game.states)
        val kings = nextTurn.board getPiecesByType "king"
        val king = kings.firstOrNull { it.piece.color != nextTurn.currentPlayer }
        if (king == null) return false
        val pieces = nextTurn.board.getPieces().filter { it.piece.color == nextTurn.currentPlayer }
        return pieces.all { tile ->
            val state = nextTurn moveFrom { tile.coordinate to king.coordinate }
            state == nextTurn
        }
    }
}
