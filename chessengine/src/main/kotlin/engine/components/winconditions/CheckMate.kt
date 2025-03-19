package edu.austral.dissis.chess.engine.engine.components.winconditions

import edu.austral.dissis.chess.engine.components.Game
import edu.austral.dissis.chess.engine.components.Util
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.engine.components.validations.check
import edu.austral.dissis.chess.engine.interfaces.WinCondition

val checkMate = WinCondition { game ->
    val nextTurn = Game(game.board, game.rules, !game.currentPlayer, game.states)
    when {
        check.verify(nextTurn) -> false
        else -> {
            val pieces = nextTurn.board.getPieces().filter { it.piece.color == nextTurn.currentPlayer }
            !pieces.any { tile ->
                (0 until nextTurn.board.size.height).any { y ->
                    (0 until nextTurn.board.size.width).any { x ->
                        val movements = Util.playerMovements(nextTurn)
                        val coordinates = tile.coordinate to P(x, y)
                        val verifiedMovement = Util.verifiedMovement(movements, coordinates, nextTurn)
                        val newBoard = verifiedMovement?.execute(coordinates, nextTurn)
                        val possibleGame =
                            newBoard?.let { Game(it, nextTurn.rules, nextTurn.currentPlayer, nextTurn.states + nextTurn) }
                        val isValid = possibleGame?.let { Util.isValid(it) }
                        isValid == true
                    }
                }
            }
        }
    }
}
