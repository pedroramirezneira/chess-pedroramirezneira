package edu.austral.dissis.chess.engine.engine.components.validations

import edu.austral.dissis.chess.engine.components.Game
import edu.austral.dissis.chess.engine.data.Tile
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.interfaces.Validation

val check = Validation { game ->
    val nextTurn = Game(game.board, game.rules, !game.currentPlayer, game.states)
    val kings = getPlayerKings(game)
    when {
        kings.isEmpty() -> false
        kings.size > 1 -> true
        else -> kingIsSafe(kings.first(), nextTurn)
    }
}

private val kingIsSafe = { king: Tile, game: Game ->
    val pieces = game.board.getPieces().filter { it.piece.color == game.currentPlayer }
    pieces.all { tile ->
        val state = game moveFrom { tile.coordinate to king.coordinate }
        state == game
    }
}

private val getPlayerKings = { game: IGame ->
    (game.board getPiecesByType "king").filter {
        it.piece.color == game.currentPlayer
    }
}
