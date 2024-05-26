package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.IBoard
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.Movement
import kotlin.math.abs

internal class Jump(private val color: Boolean?) : Movement {
    override fun verify(
        coordinates: Pair<Coordinate, Coordinate>,
        game: IGame,
    ): Boolean {
        val from = coordinates.first
        val to = coordinates.second
        val validHorizontal = abs(to.x - from.x) == 2
        val validVertical = validVertical(coordinates)
        val piece = game.board getPiece to
        if (!validHorizontal || !validVertical || piece != null) return false
        val stop = stop(coordinates)
        val stopPiece = game.board getPiece stop.second
        val isValid = stopPiece != null && stopPiece.color != game.currentPlayer
        return isValid
    }

    private fun validVertical(coordinates: Pair<Coordinate, Coordinate>): Boolean {
        val from = coordinates.first
        val to = coordinates.second
        return when (color) {
            true -> to.y == from.y + 2
            false -> to.y == from.y - 2
            else -> abs(to.y - from.y) == 2
        }
    }

    override fun execute(
        coordinates: Pair<Coordinate, Coordinate>,
        game: IGame,
    ): IBoard {
        val to = coordinates.second
        val stop = stop(coordinates)
        val board = game.board movePieceFrom { stop }
        return board movePieceFrom { stop.second to to }
    }

    private fun stop(coordinates: Pair<Coordinate, Coordinate>): Pair<Coordinate, Coordinate> {
        val from = coordinates.first
        val to = coordinates.second
        val xDirection = to.x - from.x
        val yDirection = to.y - from.y
        val stop = P(if (xDirection > 0) from.x + 1 else from.x - 1, if (yDirection > 0) from.y + 1 else from.y - 1)
        return from to stop
    }

    override fun inverse(): Movement {
        return Jump(color = color?.let { !it })
    }
}
