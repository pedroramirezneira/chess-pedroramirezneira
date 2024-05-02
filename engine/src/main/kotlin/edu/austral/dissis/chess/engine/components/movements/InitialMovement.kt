package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.components.Util
import edu.austral.dissis.chess.engine.interfaces.Board
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.Movement

class InitialMovement(private val pieceType: String, private val distance: Int, val coordinate: Coordinate) : Movement {
    override fun verify(coordinates: Pair<Coordinate, Coordinate>, game: Game): Boolean {
        if (Util.pieceHasMoved(coordinates.first, game)) {
            return false
        }
        val movement = StandardMovement(pieceType, distance, coordinate)
        return movement.verify(coordinates, game)
    }

    override fun execute(coordinates: Pair<Coordinate, Coordinate>, game: Game): Board {
        val movement = StandardMovement(pieceType, distance, coordinate)
        return movement.execute(coordinates, game)
    }
}
