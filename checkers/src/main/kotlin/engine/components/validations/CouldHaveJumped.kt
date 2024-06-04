package edu.austral.dissis.chess.engine.components.validations

import edu.austral.dissis.chess.engine.components.movements.KingJump
import edu.austral.dissis.chess.engine.components.movements.ManJump
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.Validation
import kotlin.math.abs

class CouldHaveJumped : Validation {
    override fun verify(game: IGame): Boolean {
        if (game.states.isEmpty()) return true
        val previousState = game.states.last()
        val playerPieces = game.board.getPieces().filter { it.piece.color == game.currentPlayer }
        val previousPieces = previousState.board.getPieces().filter { it.piece.color == game.currentPlayer }
        val movedPosition =
            playerPieces.find { tile ->
                tile.piece !== previousState.board.getPiece(tile.coordinate)
            }!!
        val previousPosition =
            previousState.board.getPieces().find { tile ->
                tile.piece === movedPosition.piece
            }
        val hasJumped = abs(movedPosition.coordinate.x - (previousPosition?.coordinate?.x ?: 0)) > 1
        return when {
            previousPosition == null && playerPieces.size == previousPieces.size -> true
            previousPosition == null -> false
            else ->
                !previousState.board.getPieces().filter { it.piece.color == previousState.currentPlayer }
                    .any { tile ->
                        (0 until previousState.board.size.height).any { y ->
                            (0 until previousState.board.size.width).any { x ->
                                val coordinates = tile.coordinate to P(x, y)
                                val couldHaveJumped =
                                    ManJump().verify(coordinates, previousState) || KingJump().verify(coordinates, game)
                                couldHaveJumped && !hasJumped
                            }
                        }
                    }
        }
    }
}
