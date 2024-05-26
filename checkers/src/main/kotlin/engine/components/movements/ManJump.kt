package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.IBoard
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.Movement

class ManJump : Movement {
    override fun verify(coordinates: Pair<Coordinate, Coordinate>, game: IGame): Boolean {
        val from = coordinates.first
        val piece = game.board getPiece from
        if (piece?.type != "pawn") return false
        val color = game.currentPlayer
        return Jump(color).verify(coordinates, game)
    }

    override fun execute(coordinates: Pair<Coordinate, Coordinate>, game: IGame): IBoard {
        val color = game.currentPlayer
        return Jump(color).execute(coordinates, game)
    }

    override fun inverse(): Movement {
        return this
    }
}
