package edu.austral.dissis.chess.engine.engine.components.movements

import edu.austral.dissis.chess.engine.components.Util
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
        if (game.board.getPiece(from)?.type != "pawn") return false
        val to = coordinates.second
        val movements = Util.playerMovements(game).filter { it !is Promotion }
        return when {
            game.currentPlayer && to.y != game.board.size.height - 1 -> false
            !game.currentPlayer && to.y != 0 -> false
            else -> movements.any { it.verify(coordinates, game) }
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
