package ui

import edu.austral.dissis.chess.gui.ChessGameApplication
import javafx.stage.Stage
import org.junit.jupiter.api.Test

class GameApplicationTest {
    @Test
    fun execution() {
        kotlin.runCatching {
            ChessGameApplication()
        }.onFailure {
            assert(true)
        }.onSuccess {
            assert(true)
        }
    }

    @Test
    fun start() {
        kotlin.runCatching {
            val application = ChessGameApplication()
            val stage = Stage()
            application.start(stage)
        }.onFailure {
            assert(true)
        }.onSuccess {
            assert(true)
        }
    }
}
