package edu.austral.dissis.chess.ui

import edu.austral.dissis.chess.engine.components.Chess
import edu.austral.dissis.chess.engine.components.ChessEnded
import edu.austral.dissis.chess.engine.components.movements.Castling
import edu.austral.dissis.chess.engine.components.validations.Check
import edu.austral.dissis.chess.engine.components.winconditions.CheckMate
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.Game
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
import java.io.File
import kotlin.io.path.Path

class Engine(private val path: String? = null) : GameEngine {
    private var game = path?.let { createChess(it) } ?: createChess()

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
            is ChessEnded -> GameOver(playerColor)
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

    private fun createChess(path: String = "engine/src/main/kotlin/edu/austral/dissis/chess/engine/config/config.json"): Chess {
        val absolutePath = Path("").toAbsolutePath().resolve(path)
        val config = File(absolutePath.toUri()).readText()
        return Chess fromJson config changeRules {
            add movement Castling()
            add validation Check()
            add winCondition CheckMate()
        }
    }

    private fun color(color: Boolean): PlayerColor {
        return when {
            color -> PlayerColor.WHITE
            else -> PlayerColor.BLACK
        }
    }

    private fun toPieces(game: Game): List<ChessPiece> {
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
