package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.components.Util
import edu.austral.dissis.chess.engine.data.P
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
        val kingHasMoved = Util.pieceHasMoved(from, game)
        val rookFromX = if (from.x < to.x) 7 else 0
        val rookToX = if (rookFromX == 7) 5 else 3
        val vector = if (rookToX == 3) P(1, 0) else P(-1, 0)
        val rookHasMoved = Util.pieceHasMoved(P(rookFromX, from.y), game)
        val roadBlocked = Util.roadBlocked(vector, (P(rookFromX, from.y) to P(rookToX, to.y)), game)
        return !kingHasMoved && !rookHasMoved && !roadBlocked
    }

    override fun execute(coordinates: Pair<Coordinate, Coordinate>, game: Game): Board {
        val from = coordinates.first
        val to = coordinates.second
        val state = game.board `move piece from` { P(from.x, from.y) to P(to.x, to.y) }
        val rookFromX = if (from.x < to.x) 7 else 0
        println(rookFromX)
        val rookToX = if (from.x < to.x) from.x + 1 else from.x - 1
        return state `move piece from` { P(rookFromX, from.y) to P(rookToX, to.y) }
    }

    override fun inverse(): Movement {
        return this
    }
}
