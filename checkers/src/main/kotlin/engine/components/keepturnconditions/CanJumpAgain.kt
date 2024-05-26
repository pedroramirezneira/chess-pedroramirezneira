package edu.austral.dissis.chess.engine.components.keepturnconditions

import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.KeepTurnCondition

class CanJumpAgain : KeepTurnCondition {
    override fun verify(game: IGame): Boolean {
        return false
    }
}
