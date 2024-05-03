package edu.austral.dissis.chess.engine.components.winconditions

import edu.austral.dissis.chess.engine.components.Chess
import edu.austral.dissis.chess.engine.components.validations.Check
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.interfaces.WinCondition

class CheckMate : WinCondition {
    override fun verify(game: Game): Boolean {
        println(game.board.toString())
        val possibleGame = Chess(game.board, game.rules, !game.currentPlayer, game.states)
        if (!Check().verify(possibleGame)) {
            return false
        }
        println("${possibleGame.currentPlayer} in check")
        val pieces =
            possibleGame.board.getPieces().filter { tile ->
                tile.piece.color == possibleGame.currentPlayer
            }
        return !pieces.any { piece ->
            (0 until possibleGame.board.size.height).any { y ->
                (0 until possibleGame.board.size.width).any { x ->
                    val movements =
                        possibleGame.rules.movements.map { movement ->
                            if (possibleGame.currentPlayer) {
                                movement
                            } else {
                                movement.inverse()
                            }
                        }
                    val movement =
                        movements.find { movement ->
                            movement.verify((piece.coordinate to P(x, y)), possibleGame)
                        }
                    val possibleBoard = movement?.execute(piece.coordinate to P(x, y), possibleGame)
                    val possibleState =
                        possibleBoard?.let {
                            Chess(
                                it,
                                possibleGame.rules,
                                possibleGame.currentPlayer,
                                possibleGame.states,
                            )
                        }
                    val isValid =
                        possibleState?.rules?.validations?.all { validation ->
                            validation verify possibleGame
                        }
                    isValid == true
                }
            }
        }
    }
}
