package edu.austral.dissis.chess.engine.engine.components.winconditions

import edu.austral.dissis.chess.engine.components.Game
import edu.austral.dissis.chess.engine.components.Util
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.engine.components.validations.Check
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.WinCondition

class CheckMate : WinCondition {
    override fun verify(game: IGame): Boolean {
        val nextTurn = Game(game.board, game.rules, !game.currentPlayer, game.states)
        if (Check().verify(nextTurn)) {
            return false
        }
        val pieces = nextTurn.board.getPieces().filter { it.piece.color == nextTurn.currentPlayer }
        return !pieces.any { tile ->
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
