package edu.austral.dissis.chess.engine.components

import edu.austral.dissis.chess.engine.TEST_CONFIG
import edu.austral.dissis.chess.engine.data.P
import org.junit.jupiter.api.Test

class UtilTest {
    @Test
    fun pieceHasMoved() {
        val game = Chess fromJson TEST_CONFIG
        val hasMoved = Util.pieceHasMoved(P(1, 0), game)
        assert(!hasMoved)
        val state = game moveFrom { P(1, 0) to P(2, 2) }
        val stateHasMoved = Util.pieceHasMoved(P(2, 2), state)
        assert(stateHasMoved)
    }

    @Test
    fun roadBlocked() {
        val game = Chess fromJson TEST_CONFIG
        val coordinates = P(1, 0) to P(2, 2)
        val coordinate = P(1, 2)
        val roadBlocked = Util.roadBlocked(coordinate, coordinates, game)
        assert(!roadBlocked)
    }

    @Test
    fun playerMovements() {
        val game = Chess fromJson TEST_CONFIG
        val movements = Util.playerMovements(game)
        assert(movements.isNotEmpty())
    }

    @Test
    fun verifiedMovement() {
        val game = Chess fromJson TEST_CONFIG
        val movements = Util.playerMovements(game)
        val movement =
            movements.find { movement ->
                val coordinates = P(1, 0) to P(2, 2)
                movement.verify(coordinates, game)
            }
        assert(movement != null)
    }

    @Test
    fun isValid() {
        val game = Chess fromJson TEST_CONFIG
        val isValid = Util.isValid(game)
        assert(isValid)
    }
}
