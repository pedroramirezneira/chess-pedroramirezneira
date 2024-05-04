package edu.austral.dissis.chess.engine.components.validations

import edu.austral.dissis.chess.engine.components.Chess
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.Validation

class Check : Validation {
    override fun verify(game: Game): Boolean {
        val nextTurn = Chess(game.board, game.rules, !game.currentPlayer, game.states)
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
