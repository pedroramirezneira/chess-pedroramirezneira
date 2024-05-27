package edu.austral.dissis.chess.engine.components.keepturnconditions

import edu.austral.dissis.chess.engine.components.movements.KingJump
import edu.austral.dissis.chess.engine.components.movements.ManJump
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.KeepTurnCondition
import kotlin.math.abs

class CanJumpAgain : KeepTurnCondition {
    override fun verify(game: IGame): Boolean {
        val previousState = game.states.last()
        val tile =
            game.board.getPieces().find { tile ->
                tile.piece != previousState.board getPiece tile.coordinate
            }!!
        val previousTile =
            previousState.board.getPieces().find { previousTile ->
                previousTile.piece === tile.piece
            }
        if (previousTile != null && abs(tile.coordinate.x - previousTile.coordinate.x) < 2) return false
        return (0 until game.board.size.height).any { y ->
            (0 until game.board.size.width).any { x ->
                val coordinates = tile.coordinate to P(x, y)
                when (tile.piece.type) {
                    "king" -> KingJump().verify(coordinates, game)
                    "pawn" -> ManJump().verify(coordinates, game)
                    else -> false
                }
            }
        }
    }
}
