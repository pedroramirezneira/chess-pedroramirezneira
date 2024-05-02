package edu.austral.dissis.chess.engine.interfaces

import edu.austral.dissis.chess.engine.data.Rules

interface Game {
    val board: Board
    val rules: Rules
    val `current player`: String
    val states: List<Game>
    infix fun `change rules`(rules: Rules): Game
    infix fun `move from`(block: () -> Pair<Coordinate, Coordinate>): Game
}
