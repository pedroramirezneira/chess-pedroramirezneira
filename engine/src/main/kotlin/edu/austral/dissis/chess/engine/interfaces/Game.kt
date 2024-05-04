package edu.austral.dissis.chess.engine.interfaces

import edu.austral.dissis.chess.engine.components.RulesBuilder
import edu.austral.dissis.chess.engine.data.Rules

/**
 * A Board Game.
 *
 * This interface should be implemented by all Board Games.
 */
interface Game {
    val board: Board
    val rules: Rules
    val currentPlayer: Boolean
    val states: List<Game>

    /**
     * Adds the specified Rules to an existing Game.
     * @return a new Game with the given configurations.
     */
    infix fun changeRules(block: RulesBuilder.() -> Unit): Game

    /**
     * Attempts to execute a Movement on the Game's Board.
     * @return
     * - this Game when the Movement fails.
     * - a new Game when the Movement is successful.
     */
    infix fun moveFrom(block: () -> Pair<Coordinate, Coordinate>): Game
}
