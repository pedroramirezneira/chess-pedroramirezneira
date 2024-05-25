package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.components.Util
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.IBoard
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.Movement
import kotlin.math.abs

class StandardMovement(private val pieceType: String, val coordinate: Coordinate, private val distance: Int? = null) :
    Movement {
    override fun verify(
        coordinates: Pair<Coordinate, Coordinate>,
        game: IGame,
    ): Boolean {
        val increase = increase(coordinates, game) ?: return false
        val multiple = multiple(coordinates, game)
        return when {
            !multiple -> false
            Util.roadBlocked(coordinate, coordinates, game) -> false
            else -> distance == null || increase in 1..distance
        }
    }

    private fun increase(
        coordinates: Pair<Coordinate, Coordinate>,
        game: IGame,
    ): Int? {
        val from = coordinates.first
        val to = coordinates.second
        val piece = game.board getPiece from
        if (piece == null || piece.type != pieceType) {
            return null
        }
        val dx = to.x - from.x
        val dy = to.y - from.y
        return when {
            coordinate.x != 0 -> abs(dx / coordinate.x)
            else -> abs(dy / coordinate.y)
        }
    }

    private fun multiple(
        coordinates: Pair<Coordinate, Coordinate>,
        game: IGame,
    ): Boolean {
        val from = coordinates.first
        val to = coordinates.second
        val dx = to.x - from.x
        val dy = to.y - from.y
        val increase = increase(coordinates, game)!!
        return coordinate.x * increase == dx && coordinate.y * increase == dy
    }

    override fun execute(
        coordinates: Pair<Coordinate, Coordinate>,
        game: IGame,
    ): IBoard {
        return game.board movePieceFrom { coordinates }
    }

    override fun inverse(): Movement {
        val newCoordinate = P(-1 * coordinate.x, -1 * coordinate.y)
        return StandardMovement(pieceType, newCoordinate, distance)
    }
}
