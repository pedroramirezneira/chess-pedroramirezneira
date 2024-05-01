package edu.austral.dissis.chess.engine.interfaces

import edu.austral.dissis.chess.engine.models.Rules

interface Game {
    val states: List<Game>
    val board: Board
    val rules: Rules
    val `current player`: String
    infix fun `change rules`(rules: Rules): Game
    infix fun `move from`(block: () -> Pair<Coordinate, Coordinate>): Game
}
