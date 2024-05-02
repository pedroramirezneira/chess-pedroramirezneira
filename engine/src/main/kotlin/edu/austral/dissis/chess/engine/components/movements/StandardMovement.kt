package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.interfaces.Board
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.Movement

class StandardMovement(val pieceType: String, val distance: Int, val coordinate: Coordinate) : Movement {
    override fun verify(coordinates: Pair<Coordinate, Coordinate>, game: Game): Boolean {
        TODO("Not yet implemented")
    }

    override fun execute(coordinates: Pair<Coordinate, Coordinate>, game: Game): Board {
        TODO("Not yet implemented")
    }
}
