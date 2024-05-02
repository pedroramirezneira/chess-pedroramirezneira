package edu.austral.dissis.chess.engine

import edu.austral.dissis.chess.engine.components.Chess
import edu.austral.dissis.chess.engine.components.movements.Castling
import edu.austral.dissis.chess.engine.components.validations.Check
import edu.austral.dissis.chess.engine.components.winconditions.CheckMate
import edu.austral.dissis.chess.engine.data.P
import java.io.File
import kotlin.io.path.Path

fun main() {
    val path = "engine/src/main/kotlin/edu/austral/dissis/chess/engine/config/config.json"
    val absolutePath = Path("").toAbsolutePath().resolve(path)
    val config = File(absolutePath.toUri()).readText()
    val chess = Chess `from json` config `change rules` {
        add movement Castling()
        add validation Check()
        add `win condition` CheckMate()
    }
    println(chess.board.toString())
    val state = chess `move from` { P(0, 1) to P(0, 3) }
    println(state.board.toString())
}
