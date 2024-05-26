package edu.austral.dissis.chess.engine.components.winconditions

import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.WinCondition

class NoPiecesLeft : WinCondition {
    override fun verify(game: IGame): Boolean {
        return game.board.getPieces().none { it.piece.color != game.currentPlayer }
    }
}
