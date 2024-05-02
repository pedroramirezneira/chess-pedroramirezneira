package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.components.Util
import edu.austral.dissis.chess.engine.interfaces.Board
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.Movement
import kotlin.math.abs

class StandardMovement(private val pieceType: String, val coordinate: Coordinate, private val distance: Int? = null) :
    Movement {
    override fun verify(coordinates: Pair<Coordinate, Coordinate>, game: Game): Boolean {
        println("piece $pieceType coordinate $coordinate ")
        val increase = increase(coordinates, game) ?: return false
        val multiple = multiple(coordinates, game)
        println(multiple)
        return if (!multiple) {
            false
        } else if (Util.roadBlocked(coordinate, coordinates, game)) {
            false
        } else {
            distance == null || increase in 1..distance
        }
    }

    private fun increase(coordinates: Pair<Coordinate, Coordinate>, game: Game): Int? {
        val from = coordinates.first
        val to = coordinates.second
        val piece = game.board `get piece` from
        if (piece == null || piece.type != pieceType) {
            return null
        }
        val dx = to.x - from.x
        val dy = to.y - from.y
        return if (coordinate.x != 0) {
            abs(dx / coordinate.x)
        } else {
            abs(dy / coordinate.y)
        }
    }

    private fun multiple(coordinates: Pair<Coordinate, Coordinate>, game: Game): Boolean {
        val from = coordinates.first
        val to = coordinates.second
        val dx = to.x - from.x
        val dy = to.y - from.y
        val increase = increase(coordinates, game)!!
        return coordinate.x * increase == dx && coordinate.y * increase == dy
    }

    override fun execute(coordinates: Pair<Coordinate, Coordinate>, game: Game): Board {
        return game.board `move piece from` { coordinates }
    }
}
