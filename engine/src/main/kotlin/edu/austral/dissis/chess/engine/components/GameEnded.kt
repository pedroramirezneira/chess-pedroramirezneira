package edu.austral.dissis.chess.engine.components

import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.IGame

class GameEnded(game: IGame) : Game(game.board, game.rules, game.currentPlayer, game.states) {
    override fun changeRules(block: RulesBuilder.() -> Unit): Game {
        return this
    }

    override fun moveFrom(block: () -> Pair<Coordinate, Coordinate>): Game {
        return this
    }
}
