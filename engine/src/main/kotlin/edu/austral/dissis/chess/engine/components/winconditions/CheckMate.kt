package edu.austral.dissis.chess.engine.components.winconditions

import edu.austral.dissis.chess.engine.components.Chess
import edu.austral.dissis.chess.engine.components.Util
import edu.austral.dissis.chess.engine.components.validations.Check
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.WinCondition

class CheckMate : WinCondition {
    override fun verify(game: Game): Boolean {
        val nextTurn = Chess(game.board, game.rules, !game.currentPlayer, game.states)
        if (!Check().verify(nextTurn)) {
            return false
        }
        val pieces = nextTurn.board.getPieces().filter { it.piece.color == nextTurn.currentPlayer }
        return !pieces.any { piece ->
            (0 until nextTurn.board.size.height).any { y ->
                (0 until nextTurn.board.size.width).any { x ->
                    val movements = Util.playerMovements(nextTurn)
                    val coordinates = piece.coordinate to P(x, y)
                    val verifiedMovement = Util.verifiedMovement(movements, coordinates, nextTurn)
                    val possibleBoard = verifiedMovement?.execute(piece.coordinate to P(x, y), nextTurn)
                    val possibleState = possibleBoard?.let { Chess(it, game.rules, game.currentPlayer, game.states) }
                    val isValid = possibleState?.let { Util.isValid(it) }
                    isValid == true
                }
            }
        }
    }
}
