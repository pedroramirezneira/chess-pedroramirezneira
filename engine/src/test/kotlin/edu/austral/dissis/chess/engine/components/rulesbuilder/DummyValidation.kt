package edu.austral.dissis.chess.engine.components.rulesbuilder

import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.Validation

class DummyValidation : Validation {
    override fun verify(game: IGame): Boolean {
        return true
    }
}
