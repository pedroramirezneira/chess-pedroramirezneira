package exam

import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.test.TestBoard
import edu.austral.dissis.chess.test.TestPiece
import edu.austral.dissis.chess.test.TestPosition
import edu.austral.dissis.chess.test.TestSize
import edu.austral.dissis.chess.test.game.TestGameRunner
import edu.austral.dissis.chess.test.game.TestMoveFailure
import edu.austral.dissis.chess.test.game.TestMoveResult

object Runner : TestGameRunner {
    var game: IGame = Util.createChess()
    var state: IGame? = null

    override fun executeMove(
        from: TestPosition,
        to: TestPosition,
    ): TestMoveResult {
        val game = state ?: game
        val state = game moveFrom { P(from.col - 1, from.row - 1) to P(to.col - 1, to.row - 1) }
        return when (state) {
            this.state ?: this.game -> TestMoveFailure(getBoard())
            else -> {
                this.game = state
                this.state = null
                Util.notifyListeners()
            }
        }
    }

    override fun undo(): TestMoveResult {
        this.state =
            if (state == null) {
                game.states.last()
            } else {
                state!!.states.lastOrNull() ?: state
            }
        return Util.notifyListeners()
    }

    override fun redo(): TestMoveResult {
        val index = this.game.states.indexOf(this.state)
        this.state =
            if (index != -1 && index < this.game.states.size - 1) {
                this.game.states[index + 1]
            } else {
                null
            }
        return Util.notifyListeners()
    }

    override fun getBoard(): TestBoard {
        val game = this.state ?: this.game
        val size = game.board.size
        val testSize = TestSize(size.width, size.height)
        val testPieces: Map<TestPosition, TestPiece> =
            game.board.getPieces().associate { tile ->
                val coordinate = tile.coordinate
                val piece = tile.piece
                val testPosition = TestPosition(coordinate.y + 1, coordinate.x + 1)
                val testPiece = TestPiece(Symbol fromType piece.type, Symbol fromColor piece.color)
                testPosition to testPiece
            }
        return TestBoard(testSize, testPieces)
    }

    override fun withBoard(board: TestBoard): TestGameRunner {
        val chess = Util.toGame(board)
        this.game = chess
        return Runner
    }
}
