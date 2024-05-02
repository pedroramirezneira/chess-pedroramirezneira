package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.Board
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.Movement
import kotlin.math.abs

class StandardMovement(private val pieceType: String, val coordinate: Coordinate, private val distance: Int? = null) :
    Movement {
    override fun verify(coordinates: Pair<Coordinate, Coordinate>, game: Game): Boolean {
        val increase = increase(coordinates, game) ?: return false
        val multiple = multiple(coordinates, game)
        return if (!multiple) {
            false
        } else if (roadBlocked(coordinates, game)) {
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

    private fun roadBlocked(coordinates: Pair<Coordinate, Coordinate>, game: Game): Boolean {
        val from = coordinates.first
        val to = coordinates.second
        val steps = if (coordinate.x != 0) {
            abs((to.x - from.x) / coordinate.x)
        } else {
            abs((to.y - from.y) / coordinate.y)
        }
        for (i in 1..steps) {
            val piece = game.board `get piece` P(from.x + i * coordinate.x, from.y + i * coordinate.y)
            val isPlayersPiece = to.x == from.x + i * coordinate.x && piece?.color == game.`current player`
            if (isPlayersPiece || piece != null) {
                return true
            }
        }
        return false
    }

    override fun execute(coordinates: Pair<Coordinate, Coordinate>, game: Game): Board {
        return game.board `move piece from` { coordinates }
    }
}
