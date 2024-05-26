package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.IBoard
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.Movement
import kotlin.math.abs

class Jump(private val color: Boolean) : Movement {
    override fun verify(coordinates: Pair<Coordinate, Coordinate>, game: IGame): Boolean {
        val from = coordinates.first
        val to = coordinates.second
        val validHorizontal = abs(to.x - from.x) == 2
        val validVertical = validVertical(coordinates, game)
        val piece = game.board getPiece to
        if (!validHorizontal || !validVertical || piece != null) return false
        val stop = stop(coordinates, game)
        val stopPiece = game.board getPiece stop.second
        val isValid = stopPiece != null && stopPiece.color != game.currentPlayer
        return isValid
    }

    private fun validVertical(coordinates: Pair<Coordinate, Coordinate>, game: IGame): Boolean {
        val from = coordinates.first
        val to = coordinates.second
        return if (color) {
            to.y == from.y + 2
        } else {
            to.y == from.y - 2
        }
    }

    override fun execute(coordinates: Pair<Coordinate, Coordinate>, game: IGame): IBoard {
        val to = coordinates.second
        val stop = stop(coordinates, game)
        val board = game.board movePieceFrom { stop }
        return board movePieceFrom { stop.second to to }
    }

    private fun stop(coordinates: Pair<Coordinate, Coordinate>, game: IGame): Pair<Coordinate, Coordinate> {
        val from = coordinates.first
        val to = coordinates.second
        val horizontalDirection = to.x - from.x
        val stop = if (color) {
            from to P(if (horizontalDirection > 0) from.x + 1 else from.x - 1, from.y + 1)
        } else {
            from to P(if (horizontalDirection > 0) from.x + 1 else from.x - 1, from.y - 1)
        }
        return stop
    }

    override fun inverse(): Movement {
        return Jump(color = !color)
    }
}
