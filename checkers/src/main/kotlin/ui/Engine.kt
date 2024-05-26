package edu.austral.dissis.chess.ui

import edu.austral.dissis.chess.engine.components.Game
import edu.austral.dissis.chess.engine.components.GameEnded
import edu.austral.dissis.chess.engine.components.movements.KingJump
import edu.austral.dissis.chess.engine.components.movements.ManJump
import edu.austral.dissis.chess.engine.components.movements.Promotion
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.gui.BoardSize
import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.chess.gui.GameEngine
import edu.austral.dissis.chess.gui.GameOver
import edu.austral.dissis.chess.gui.InitialState
import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.dissis.chess.gui.Move
import edu.austral.dissis.chess.gui.MoveResult
import edu.austral.dissis.chess.gui.NewGameState
import edu.austral.dissis.chess.gui.PlayerColor
import edu.austral.dissis.chess.gui.Position

class Engine : GameEngine {
    private var game = createCheckers()

    override fun applyMove(move: Move): MoveResult {
        val from = move.from
        val to = move.to
        val size = game.board.size
        val xFrom = from.column - 1
        val yFrom = size.height - from.row
        val xTo = to.column - 1
        val yTo = size.height - to.row
        val state = game moveFrom { P(xFrom, yFrom) to P(xTo, yTo) }
        val playerColor = color(state.currentPlayer)
        val pieces = toPieces(state)
        return when (state) {
            game -> InvalidMove("Invalid move.")
            is GameEnded -> GameOver(playerColor)
            else -> {
                game = state
                NewGameState(pieces, playerColor)
            }
        }
    }

    override fun init(): InitialState {
        val size = game.board.size
        val boardSize = BoardSize(size.width, size.height)
        val pieces = toPieces(game)
        val currentPlayer = PlayerColor.WHITE
        return InitialState(boardSize, pieces, currentPlayer)
    }

    override fun redo(): NewGameState {
        TODO("Not yet implemented")
    }

    override fun undo(): NewGameState {
        TODO("Not yet implemented")
    }

    private fun createCheckers(): Game {
        val resource = object {}.javaClass.getResourceAsStream("/config/config.json")
        val config = resource!!.readAllBytes()!!.toString(Charsets.UTF_8)
        return Game fromJson config changeRules {
            add movement ManJump()
            add movement KingJump()
            add movement Promotion()
        }
    }

    private fun color(color: Boolean): PlayerColor {
        return when {
            color -> PlayerColor.WHITE
            else -> PlayerColor.BLACK
        }
    }

    private fun toPieces(game: IGame): List<ChessPiece> {
        return game.board.getPieces().map { tile ->
            val color = color(tile.piece.color)
            val coordinate = tile.coordinate
            val size = game.board.size
            val row = size.height - coordinate.y
            val column = coordinate.x + 1
            val position = Position(row, column)
            val id = System.identityHashCode(tile.piece).toString()
            ChessPiece(id, color, position, tile.piece.type)
        }
    }
}
