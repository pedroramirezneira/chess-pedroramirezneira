package edu.austral.dissis.chess.engine.interfaces

/**
 * A Movement.
 *
 * This interface should be implemented by all Movements to be executed on a Game.
 */
interface Movement {
    /**
     * Verifies this Movement.
     * @return whether the Movement is verified.
     */
    fun verify(
        coordinates: Pair<Coordinate, Coordinate>,
        game: IGame,
    ): Boolean

    /**
     * Executes this movement.
     * @return a new Board with updated Pieces
     */
    fun execute(
        coordinates: Pair<Coordinate, Coordinate>,
        game: IGame,
    ): IBoard

    fun inverse(): Movement
}
