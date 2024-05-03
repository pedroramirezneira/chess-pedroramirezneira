package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.Board
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.Movement

class AttackMovement(private val pieceType: String, val coordinate: Coordinate, private val distance: Int? = null) :
    Movement {
    override fun verify(
        coordinates: Pair<Coordinate, Coordinate>,
        game: Game,
    ): Boolean {
        val to = coordinates.second
        (game.board getPiece to) ?: return false
        val movement = StandardMovement(pieceType, coordinate, distance)
        return movement.verify(coordinates, game)
    }

    override fun execute(
        coordinates: Pair<Coordinate, Coordinate>,
        game: Game,
    ): Board {
        val movement = StandardMovement(pieceType, coordinate, distance)
        return movement.execute(coordinates, game)
    }

    override fun inverse(): Movement {
        val newCoordinate = P(-1 * coordinate.x, -1 * coordinate.y)
        return AttackMovement(pieceType, newCoordinate, distance)
    }
}
