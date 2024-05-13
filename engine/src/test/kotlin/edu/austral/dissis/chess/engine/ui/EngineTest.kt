package edu.austral.dissis.chess.engine.ui

import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.dissis.chess.gui.Move
import edu.austral.dissis.chess.gui.PlayerColor
import edu.austral.dissis.chess.gui.Position
import edu.austral.dissis.chess.ui.Engine
import org.junit.jupiter.api.Test

class EngineTest {
    @Test
    fun constructor() {
        kotlin.runCatching {
            Engine()
        }.onFailure {
            assert(false)
        }.onFailure {
            assert(true)
        }
    }

    @Test
    fun applyMove() {
        val engine = Engine()
        val from = Position(1, 2)
        val to = Position(3, 3)
        val move = Move(from, to)
        val result = engine.applyMove(move)
        assert(result is InvalidMove)
    }

    @Test
    fun init() {
        val engine = Engine()
        val state = engine.init()
        assert(state.currentPlayer == PlayerColor.WHITE)
    }
}
