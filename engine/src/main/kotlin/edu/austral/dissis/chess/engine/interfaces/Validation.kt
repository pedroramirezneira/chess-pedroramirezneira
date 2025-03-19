package edu.austral.dissis.chess.engine.interfaces

/**
 * A Validation.
 *
 * This interface should be implemented by all Validations to be executed on a Game's state.
 */
fun interface Validation {
    /**
     * Verifies the Game's state after making a Movement.
     * @return whether this Validation is successful.
     */
    infix fun verify(game: IGame): Boolean
}
