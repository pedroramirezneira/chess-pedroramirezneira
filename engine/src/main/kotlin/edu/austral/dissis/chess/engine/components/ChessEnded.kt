package edu.austral.dissis.chess.engine.components

import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Game

class ChessEnded(game: Game) : Chess(game.board, game.rules, game.currentPlayer, game.states) {
    override fun changeRules(block: RulesBuilder.() -> Unit): Chess {
        return this
    }

    override fun moveFrom(block: () -> Pair<Coordinate, Coordinate>): Chess {
        return this
    }
}
