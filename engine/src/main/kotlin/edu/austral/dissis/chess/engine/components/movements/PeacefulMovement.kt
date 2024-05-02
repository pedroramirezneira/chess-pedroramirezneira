package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.interfaces.Board
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.Movement

class PeacefulMovement(private val pieceType: String, val coordinate: Coordinate, private val distance: Int? = null) :
    Movement {
    override fun verify(coordinates: Pair<Coordinate, Coordinate>, game: Game): Boolean {
        val to = coordinates.second
        val piece = game.board `get piece` to
        if (piece != null) {
            return false
        }
        val movement = StandardMovement(pieceType, coordinate, distance)
        return movement.verify(coordinates, game)
    }

    override fun execute(coordinates: Pair<Coordinate, Coordinate>, game: Game): Board {
        val movement = StandardMovement(pieceType, coordinate, distance)
        return movement.execute(coordinates, game)
    }
}
