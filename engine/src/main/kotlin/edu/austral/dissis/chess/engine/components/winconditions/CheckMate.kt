package edu.austral.dissis.chess.engine.components.winconditions

import edu.austral.dissis.chess.engine.components.Chess
import edu.austral.dissis.chess.engine.components.validations.Check
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.WinCondition

class CheckMate : WinCondition {
    override fun verify(game: Game): Boolean {
        val possibleGame = Chess(game.board, game.rules, !game.`current player`, game.states)
        if (!Check().verify(possibleGame)) {
            return false
        }
        val pieces = possibleGame.board.`get pieces`().filter { tile ->
            tile.piece.color == game.`current player`
        }
        return !pieces.any { piece ->
            (0 until game.board.size.height).any { y ->
                (0 until game.board.size.width).any { x ->
                    val movement = game.rules.movements.find { movement ->
                        movement.verify((piece.coordinate to P(x, y)), game)
                    }
                    val possibleBoard = possibleGame.board `move piece from` { piece.coordinate to P(x, y) }
                    val possibleState =
                        Chess(possibleBoard, possibleGame.rules, possibleGame.`current player`, possibleGame.states)
                    val isValid = possibleState.rules.validations.all { validation ->
                        validation verify possibleGame
                    }
                    isValid && movement != null
                }
            }
        }
    }
}
