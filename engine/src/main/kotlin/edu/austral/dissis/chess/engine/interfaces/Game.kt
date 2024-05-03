package edu.austral.dissis.chess.engine.interfaces

import edu.austral.dissis.chess.engine.components.RulesBuilder
import edu.austral.dissis.chess.engine.data.Rules

interface Game {
    val board: Board
    val rules: Rules
    val currentPlayer: Boolean
    val states: List<Game>

    infix fun changeRules(block: RulesBuilder.() -> Unit): Game

    infix fun moveFrom(block: () -> Pair<Coordinate, Coordinate>): Game
}
