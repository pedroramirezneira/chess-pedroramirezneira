package exam

import edu.austral.dissis.chess.engine.components.Board
import edu.austral.dissis.chess.engine.components.Game
import edu.austral.dissis.chess.engine.components.GameEnded
import edu.austral.dissis.chess.engine.data.Piece
import edu.austral.dissis.chess.engine.data.Rules
import edu.austral.dissis.chess.engine.data.Size
import edu.austral.dissis.chess.engine.engine.components.movements.Castling
import edu.austral.dissis.chess.engine.engine.components.movements.Promotion
import edu.austral.dissis.chess.engine.engine.components.validations.Check
import edu.austral.dissis.chess.engine.engine.components.winconditions.CheckMate
import edu.austral.dissis.chess.test.TestBoard
import edu.austral.dissis.chess.test.TestPosition
import edu.austral.dissis.chess.test.game.BlackCheckMate
import edu.austral.dissis.chess.test.game.TestMoveResult
import edu.austral.dissis.chess.test.game.TestMoveSuccess
import edu.austral.dissis.chess.test.game.WhiteCheckMate

object Util {
    fun createChess(): Game {
        Runner.state = null
        val resource = Game::class.java.getResourceAsStream("/config/config.json")!!
        val config = resource.readAllBytes().toString(Charsets.UTF_8)
        return Game fromJson config changeRules {
            add movement Castling()
            add movement Promotion()
            add validation Check()
            add winCondition CheckMate()
        }
    }

    fun toGame(board: TestBoard): Game {
        Runner.state = null
        val size = Size(board.size.cols, board.size.rows)
        val tiles: List<List<Piece?>> =
            (0 until size.height).map { y ->
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
        val chessBoard = Board(size, tiles)
        val game = Game(chessBoard, Rules.empty(), true)
        val resource = Game::class.java.getResourceAsStream("/config/config.json")!!
        val config = resource.readAllBytes().toString(Charsets.UTF_8)
        return game changeRules {
            add movement Castling()
            add movement Promotion()
            add validation Check()
            add winCondition CheckMate()
            add fromJson config
        }
    }

    fun notifyListeners(): TestMoveResult {
        return when {
            Runner.game is GameEnded && Runner.game.currentPlayer -> WhiteCheckMate(Runner.getBoard())

            Runner.game is GameEnded && !Runner.game.currentPlayer -> BlackCheckMate(Runner.getBoard())

            Runner.state != null -> TestMoveSuccess(Runner)

            else -> TestMoveSuccess(Runner)
        }
    }
}
