package edu.austral.dissis.chess.engine.interfaces

import edu.austral.dissis.chess.engine.components.RulesBuilder
import edu.austral.dissis.chess.engine.data.Rules

/**
 * A Board Game.
 *
 * This interface should be implemented by all Board Games.
 */
interface IGame {
    val board: IBoard
    val rules: Rules
    val currentPlayer: Boolean
    val states: List<IGame>

    /**
     * Adds the specified Rules to an existing Game.
     * @return a new Game with the given configurations.
     */
    infix fun changeRules(block: RulesBuilder.() -> Unit): IGame

    /**
     * Attempts to execute a Movement on the Game's Board.
     * @return
     * - this Game when the Movement fails.
     * - a new Game when the Movement is successful.
     */
    infix fun moveFrom(block: () -> Pair<Coordinate, Coordinate>): IGame
}
