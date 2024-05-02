package edu.austral.dissis.chess.engine

import edu.austral.dissis.chess.engine.components.Chess
import edu.austral.dissis.chess.engine.components.movements.Castling
import edu.austral.dissis.chess.engine.components.validations.Check
import edu.austral.dissis.chess.engine.components.winconditions.CheckMate
import edu.austral.dissis.chess.engine.data.P
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path

const val PATH = "engine/src/main/kotlin/edu/austral/dissis/chess/engine/config/config.json"
val ABSOLUTE_PATH: Path = Path("").toAbsolutePath().resolve(PATH)
val CONFIG = File(ABSOLUTE_PATH.toUri()).readText()

fun main() {
    val chess = Chess `from json` CONFIG `change rules` {
        add movement Castling()
        add validation Check()
        add `win condition` CheckMate()
    }
    println(chess.board.toString())
    println()
    val state = chess `move from` { P(0, 1) to P(0, 3) }
    println(state.board.toString())
    println()
    val state2 = state `move from` { P(0, 6) to P(0, 4) }
    println(state2.board.toString())
}
