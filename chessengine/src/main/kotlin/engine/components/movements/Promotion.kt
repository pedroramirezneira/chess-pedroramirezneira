package edu.austral.dissis.chess.engine.engine.components.movements

import edu.austral.dissis.chess.engine.data.Piece
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.IBoard
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.Movement
import kotlin.math.abs

class Promotion : Movement {
    override fun verify(
        coordinates: Pair<Coordinate, Coordinate>,
        game: IGame,
    ): Boolean {
        val from = coordinates.first
        val to = coordinates.second
        val piece = game.board getPiece from
        if (piece?.type != "pawn") return false
        val validVertical =
            when (piece.color) {
                true -> to.y - 1 == from.y && to.y == game.board.size.height - 1
                false -> to.y + 1 == from.y && to.y == 0
            }
        val peacefulHorizontal = to.x == from.x
        val attackHorizontal = abs(from.x - to.x) == 1
        val opponent = game.board getPiece to
        return when {
            !validVertical -> false
            peacefulHorizontal -> opponent == null
            opponent == null -> false
            attackHorizontal -> opponent.color != piece.color
            else -> false
        }
    }

    override fun execute(
        coordinates: Pair<Coordinate, Coordinate>,
        game: IGame,
    ): IBoard {
        val newBoard = game.board.movePieceFrom { coordinates }
        val to = coordinates.second
        return newBoard.addPiece(Piece("queen", game.currentPlayer), to)
    }

    override fun inverse(): Movement {
        return this
    }
}
