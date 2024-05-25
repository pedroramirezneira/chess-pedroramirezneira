package edu.austral.dissis.chess.engine.interfaces

/**
 * A Win Condition.
 *
 * This interface should be implemented by all Win Conditions to be applied to a Game.
 */
interface WinCondition {
    /**
     * Verifies the Game's end condition.
     * @return whether this Win Condition is satisfied.
     */
    infix fun verify(game: IGame): Boolean
}
