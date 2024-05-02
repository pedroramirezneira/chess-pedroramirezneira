package edu.austral.dissis.chess.engine.components

import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.data.Rules

class ChessEnded(game: Game) : Chess(game.board, game.rules, game.`current player`, game.states) {
    override fun `change rules`(block: RulesBuilder.() -> Unit): Chess {
        return this
    }

    override fun `move from`(block: () -> Pair<Coordinate, Coordinate>): Chess {
        return this
    }
}
