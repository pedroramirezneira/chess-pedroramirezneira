package edu.austral.dissis.chess.engine.engine.components.movements

import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.IBoard
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.Movement
import kotlin.math.abs

class EnPassant : Movement {
    override fun verify(
        coordinates: Pair<Coordinate, Coordinate>,
        game: IGame,
    ): Boolean {
        val from = coordinates.first
        val to = coordinates.second
        val piece = game.board getPiece from
        if (piece?.type != "pawn") return false
        val validDestination =
            when (piece.color) {
                true -> abs(to.x - from.x) == 1 && to.y - from.y == 1
                false -> abs(to.x - from.x) == 1 && to.y - from.y == -1
            }
        val leftJustMoved = pawnJustMoved(P(from.x - 1, from.y), game)
        val rightJustMoved = pawnJustMoved(P(from.x + 1, from.y), game)
        return when {
            !validDestination -> false
            to.x - from.x == -1 -> leftJustMoved
            to.x - from.x == 1 -> rightJustMoved
            else -> true
        }
    }

    private fun pawnJustMoved(
        coordinate: Coordinate,
        game: IGame,
    ): Boolean {
        val invalidVertical = coordinate.y < 0 || coordinate.y >= game.board.size.height
        val invalidHorizontal = coordinate.x < 0 || coordinate.x >= game.board.size.width
        if (invalidVertical || invalidHorizontal) return false
        val piece = game.board getPiece coordinate
        val invalidOrigin =
            when (piece?.color) {
                true -> coordinate.y - 2 < 0
                false -> coordinate.y + 2 >= game.board.size.height
                else -> true
            }
        return when {
            piece?.type != "pawn" -> false
            invalidOrigin -> false
            piece.color -> game.states.last().board getPiece P(coordinate.x, coordinate.y - 2) == piece
            !piece.color -> game.states.last().board getPiece P(coordinate.x, coordinate.y + 2) == piece
            else -> false
        }
    }

    override fun execute(
        coordinates: Pair<Coordinate, Coordinate>,
        game: IGame,
    ): IBoard {
        val from = coordinates.first
        val to = coordinates.second
        val opponent = P(to.x, from.y)
        val newBoard = game.board movePieceFrom { from to opponent }
        return newBoard movePieceFrom { opponent to to }
    }

    override fun inverse(): Movement {
        return this
    }
}
