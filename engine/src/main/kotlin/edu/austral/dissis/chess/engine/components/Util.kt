package edu.austral.dissis.chess.engine.components

import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Game
import kotlin.math.abs

object Util {
    fun pieceHasMoved(
        coordinate: Coordinate,
        game: Game,
    ): Boolean {
        val piece = game.board getPiece coordinate
        return !game.states.all { state ->
            state.board getPiece coordinate == piece
        }
    }

    fun roadBlocked(
        coordinate: Coordinate,
        coordinates: Pair<Coordinate, Coordinate>,
        game: Game,
    ): Boolean {
        val from = coordinates.first
        val to = coordinates.second
        val steps =
            if (coordinate.x != 0) {
                abs((to.x - from.x) / coordinate.x)
            } else {
                abs((to.y - from.y) / coordinate.y)
            }
        for (i in 1..steps) {
            val piece = game.board getPiece P(from.x + i * coordinate.x, from.y + i * coordinate.y)
            val isPlayersPiece = to.x == from.x + i * coordinate.x && piece?.color == game.currentPlayer
            if (piece != null && isPlayersPiece) {
                return true
            }
        }
        return false
    }
}
