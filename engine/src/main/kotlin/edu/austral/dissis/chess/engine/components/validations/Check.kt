package edu.austral.dissis.chess.engine.components.validations

import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.Validation

class Check : Validation {
    override fun verify(game: Game): Boolean {
        val kings = game.board getPiecesByType "king"
        val king = kings.first { it.piece.color == game.currentPlayer }
        val opposingPieces =
            game.board.getPieces().filter { tile ->
                tile.piece.color != game.currentPlayer
            }
        return opposingPieces.all { tile ->
            val state = game moveFrom { tile.coordinate to king.coordinate }
            state == game
        }
    }
}
