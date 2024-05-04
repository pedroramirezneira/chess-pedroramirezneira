package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.components.Util
import edu.austral.dissis.chess.engine.interfaces.Board
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.Movement

class InitialMovement(private val movement: Movement) :
    Movement {
    override fun verify(
        coordinates: Pair<Coordinate, Coordinate>,
        game: Game,
    ): Boolean {
        if (Util.pieceHasMoved(coordinates.first, game)) {
            return false
        }
        return movement.verify(coordinates, game)
    }

    override fun execute(
        coordinates: Pair<Coordinate, Coordinate>,
        game: Game,
    ): Board {
        return movement.execute(coordinates, game)
    }

    override fun inverse(): Movement {
        return InitialMovement(movement.inverse())
    }
}
