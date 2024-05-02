package edu.austral.dissis.chess.engine.components.winconditions

import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.WinCondition

class CheckMate : WinCondition {
    override fun verify(game: Game): Boolean {
        return false
    }
}
