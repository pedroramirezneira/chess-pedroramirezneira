package edu.austral.dissis.chess.engine.exam

import edu.austral.dissis.chess.engine.components.Chess
import edu.austral.dissis.chess.engine.components.ChessBoard
import edu.austral.dissis.chess.engine.components.ChessEnded
import edu.austral.dissis.chess.engine.components.movements.Castling
import edu.austral.dissis.chess.engine.components.validations.Check
import edu.austral.dissis.chess.engine.components.winconditions.CheckMate
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.data.Piece
import edu.austral.dissis.chess.engine.data.Rules
import edu.austral.dissis.chess.engine.data.Size
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.test.TestBoard
import edu.austral.dissis.chess.test.TestPiece
import edu.austral.dissis.chess.test.TestPosition
import edu.austral.dissis.chess.test.TestSize
import edu.austral.dissis.chess.test.game.*
import java.io.File
import kotlin.io.path.Path

class Runner(
    private val game: Game = createChess()
) : TestGameRunner {
    override fun executeMove(from: TestPosition, to: TestPosition): TestMoveResult {
        val state = game `move from` { P(from.col - 1, from.row - 1) to P(to.col - 1, to.row - 1) }
        return when {
            state == game -> TestMoveFailure(getBoard())
            state is ChessEnded && state.`current player` -> WhiteCheckMate(Runner(state).getBoard())
            state is ChessEnded && !state.`current player` -> BlackCheckMate(Runner(state).getBoard())
            else -> TestMoveSuccess(Runner(state))
        }
    }

    override fun getBoard(): TestBoard {
        val size = game.board.size
        val testSize = TestSize(size.width, size.height)
        val testPieces: Map<TestPosition, TestPiece> = game.board.`get pieces`().associate { tile ->
            val coordinate = tile.coordinate
            val piece = tile.piece
            val testPosition = TestPosition(coordinate.y + 1, coordinate.x + 1)
            val testPiece = TestPiece(Symbol fromType piece.type, Symbol fromColor piece.color)
            testPosition to testPiece
        }
        return TestBoard(testSize, testPieces)
    }

    override fun withBoard(board: TestBoard): TestGameRunner {
        val chess = Runner toGame board
        return Runner(chess)
    }

    companion object {
        infix fun toGame(board: TestBoard): Chess {
            val size = Size(board.size.cols, board.size.rows)
            val tiles: List<List<Piece?>> = (0 until size.height).map { y ->
                (0 until size.width).map { x ->
                    val position = TestPosition(y + 1, x + 1)
                    val piece = board.pieces[position]
                    if (piece == null) {
                        null
                    } else {
                        val type = Symbol toType piece.pieceTypeSymbol
                        val color = Symbol toColor piece.playerColorSymbol
                        Piece(type, color)
                    }
                }
            }
            val chessBoard = ChessBoard(size, tiles)
            val chess = Chess(chessBoard, Rules.empty(), true)
            val path = "src/main/kotlin/edu/austral/dissis/chess/engine/config/config.json"
            val absolutePath = Path("").toAbsolutePath().resolve(path)
            val config = File(absolutePath.toUri()).readText()
            return chess `change rules` {
                add `from json` config
                add movement Castling()
                add validation Check()
                add `win condition` CheckMate()
            }
        }
    }
}

fun createChess(): Chess {
    val path = "src/main/kotlin/edu/austral/dissis/chess/engine/config/config.json"
    val absolutePath = Path("").toAbsolutePath().resolve(path)
    val config = File(absolutePath.toUri()).readText()
    return Chess `from json` config `change rules` {
        add movement Castling()
        add validation Check()
        add `win condition` CheckMate()
    }
}
