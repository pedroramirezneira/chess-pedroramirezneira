package edu.austral.dissis.chess.engine.components.validations

import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.Validation

class Check : Validation {
    override fun verify(game: Game): Boolean {
        val kings = game.board `get pieces by type` "king"
        val king = kings.filter { it.piece.color == game.`current player` }[0]
        val opposingPieces = game.board.`get pieces`().filter { tile ->
            tile.piece.color != game.`current player`
        }
        return opposingPieces.all { tile ->
            val state = game `move from` { tile.coordinate to king.coordinate }
            state == game
        }
    }
}
