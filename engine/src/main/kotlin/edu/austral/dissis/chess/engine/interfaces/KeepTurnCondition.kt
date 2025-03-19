package edu.austral.dissis.chess.engine.interfaces

/**
 * A condition to maintain the current player's turn.
 *
 * This interface should be implemented by all KeepTurnConditions to be applied to a Game.
 */
fun interface KeepTurnCondition {
    /**
     * Verifies the Game's condition to maintain the current players' turn.
     * @return whether the KeepTurnCondition is satisfied.
     */
    infix fun verify(game: IGame): Boolean
}
