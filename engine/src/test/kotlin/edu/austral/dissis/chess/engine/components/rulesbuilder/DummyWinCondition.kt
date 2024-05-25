package edu.austral.dissis.chess.engine.components.rulesbuilder

import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.WinCondition

class DummyWinCondition : WinCondition {
    override fun verify(game: IGame): Boolean {
        return true
    }
}
