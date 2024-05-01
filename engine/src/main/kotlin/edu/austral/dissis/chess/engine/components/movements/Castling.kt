package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.components.P
import edu.austral.dissis.chess.engine.interfaces.Board
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.Movement

class Castling : Movement {
    override fun verify(coordinates: Pair<Coordinate, Coordinate>, game: Game): Boolean {
        val from = coordinates.first
        val to = coordinates.second
        val whiteKingSelected = from.x == 4 && from.y == 0
        val blackKingSelected = from.x == 4 && from.y == 7
        val kingSelected = whiteKingSelected || blackKingSelected
        val validVertical = from.y == to.y
        val validHorizontal = from.x + 2 == to.x || from.x - 2 == to.x
        val validMovement = validVertical && validHorizontal
        if (!kingSelected || !validMovement) {
            return false
        }
        val kingHasMoved = pieceHasMoved(from, game)
        val rookX = if (from.x < to.x) 7 else 0
        val rookHasMoved = pieceHasMoved(P(rookX, from.y), game)
        return !kingHasMoved && !rookHasMoved
    }

    private fun pieceHasMoved(coordinate: Coordinate, game: Game): Boolean {
        val piece = game.board `get piece` coordinate
        return !game.states.all { state ->
            state.board `get piece` coordinate == piece
        }
    }

    override fun execute(coordinates: Pair<Coordinate, Coordinate>, game: Game): Board {
        val from = coordinates.first
        val to = coordinates.second
        val state = game.board `move piece` { P(from.x, from.y) to P(to.x, to.y) }
        val rookFromX = if (from.x < to.x) 7 else 0
        val rookToX = if (from.x < to.x) from.x - 1 else from.x + 1
        return state `move piece` { P(rookFromX, from.y) to P(rookToX, to.y) }
    }
}
