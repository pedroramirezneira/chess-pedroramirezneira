package edu.austral.dissis.chess.engine.components.movements

import edu.austral.dissis.chess.engine.components.Util
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.Board
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.Movement

class Castling : Movement {
    override fun verify(
        coordinates: Pair<Coordinate, Coordinate>,
        game: Game,
    ): Boolean {
        val from = coordinates.first
        val to = coordinates.second
        val whiteKingSelected = from.x == FOUR && from.y == ZERO
        val blackKingSelected = from.x == FOUR && from.y == SEVEN
        val kingSelected = whiteKingSelected || blackKingSelected
        val validVertical = from.y == to.y
        val validHorizontal = from.x + TWO == to.x || from.x - TWO == to.x
        val validMovement = validVertical && validHorizontal
        if (!kingSelected || !validMovement) {
            return false
        }
        val kingHasMoved = Util.pieceHasMoved(from, game)
        val rookFromX = if (from.x < to.x) SEVEN else ZERO
        val rookToX = if (rookFromX == SEVEN) FIVE else THREE
        val vector = if (rookToX == THREE) P(ONE, ZERO) else P(-ONE, ZERO)
        val rookHasMoved = Util.pieceHasMoved(P(rookFromX, from.y), game)
        val roadBlocked = Util.roadBlocked(vector, (P(rookFromX, from.y) to P(rookToX, to.y)), game)
        return !kingHasMoved && !rookHasMoved && !roadBlocked
    }

    override fun execute(
        coordinates: Pair<Coordinate, Coordinate>,
        game: Game,
    ): Board {
        val from = coordinates.first
        val to = coordinates.second
        val state = game.board movePieceFrom { P(from.x, from.y) to P(to.x, to.y) }
        val rookFromX = if (from.x < to.x) SEVEN else ZERO
        val rookToX = if (from.x < to.x) from.x + ONE else from.x - ONE
        return state movePieceFrom { P(rookFromX, from.y) to P(rookToX, to.y) }
    }

    override fun inverse(): Movement {
        return this
    }

    companion object {
        private const val ZERO = 0
        private const val ONE = 1
        private const val TWO = 2
        private const val THREE = 3
        private const val FOUR = 4
        private const val FIVE = 5
        private const val SEVEN = 7
    }
}
