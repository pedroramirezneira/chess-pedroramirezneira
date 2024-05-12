package edu.austral.dissis.chess.engine

import edu.austral.dissis.chess.engine.components.Chess
import edu.austral.dissis.chess.engine.components.movements.Castling
import edu.austral.dissis.chess.engine.components.movements.EnPassant
import edu.austral.dissis.chess.engine.components.movements.Promotion
import edu.austral.dissis.chess.engine.components.validations.Check
import edu.austral.dissis.chess.engine.components.winconditions.CheckMate
import edu.austral.dissis.chess.engine.data.P
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path

const val PATH = "engine/src/main/kotlin/edu/austral/dissis/chess/engine/config/config.json"
val ABSOLUTE_PATH: Path = Path("").toAbsolutePath().resolve(PATH)
val CONFIG = File(ABSOLUTE_PATH.toUri()).readText()
private const val ZERO = 0
private const val ONE = 1
private const val THREE = 3
private const val FOUR = 4
private const val SIX = 6

fun main() {
    val chess =
        Chess fromJson CONFIG changeRules {
            add movement Castling()
            add movement Promotion()
            add movement EnPassant()
            add validation Check()
            add winCondition CheckMate()
        }
    println(chess.board.toString())
    println()
    val state = chess moveFrom { P(ZERO, ONE) to P(ZERO, THREE) }
    println(state.board.toString())
    println()
    val state2 = state moveFrom { P(ZERO, SIX) to P(ZERO, FOUR) }
    println(state2.board.toString())
}
