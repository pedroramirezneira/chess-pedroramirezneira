package edu.austral.dissis.chess.engine.components

import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Game

class Util {
    companion object {
        fun pieceHasMoved(coordinate: Coordinate, game: Game): Boolean {
            val piece = game.board `get piece` coordinate
            return !game.states.all { state ->
                state.board `get piece` coordinate == piece
            }
        }
    }
}
