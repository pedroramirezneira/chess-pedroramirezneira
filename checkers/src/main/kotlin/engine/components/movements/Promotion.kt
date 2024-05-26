package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.data.Piece
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.IBoard
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.Movement

class Promotion : Movement {
    override fun verify(
        coordinates: Pair<Coordinate, Coordinate>,
        game: IGame,
    ): Boolean {
        val from = coordinates.first
        val to = coordinates.second
        val piece = game.board getPiece from
        if (piece?.type != "pawn") return false
        val verifiedMovement =
            (game.rules.movements - this).any { movement ->
                if (game.currentPlayer) {
                    movement.verify(coordinates, game)
                } else {
                    movement.inverse().verify(coordinates, game)
                }
            }
        val validVertical =
            if (game.currentPlayer) {
                to.y == game.board.size.height - 1
            } else {
                to.y == 0
            }
        return verifiedMovement && validVertical
    }

    override fun execute(
        coordinates: Pair<Coordinate, Coordinate>,
        game: IGame,
    ): IBoard {
        val newBoard = game.board.movePieceFrom { coordinates }
        val to = coordinates.second
        return newBoard.addPiece(Piece("king", game.currentPlayer), to)
    }

    override fun inverse(): Movement {
        return this
    }
}
