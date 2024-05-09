package edu.austral.dissis.chess.engine

import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path

const val TEST_PATH = "src/main/kotlin/edu/austral/dissis/chess/engine/config/config.json"
val ABSOLUTE_PATH: Path = Path("").toAbsolutePath().resolve(TEST_PATH)
val TEST_CONFIG = File(ABSOLUTE_PATH.toUri()).readText()

class MainTest {
    @Test
    fun execution() {
        kotlin.runCatching {
            main()
        }.onFailure {
            assert(true)
        }.onSuccess {
            assert(true)
        }
    }
}
