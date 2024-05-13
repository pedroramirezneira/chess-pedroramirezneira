package edu.austral.dissis.chess.engine

import edu.austral.dissis.chess.engine.components.Chess
import edu.austral.dissis.chess.engine.components.movements.Castling
import edu.austral.dissis.chess.engine.components.movements.EnPassant
import edu.austral.dissis.chess.engine.components.movements.Promotion
import edu.austral.dissis.chess.engine.components.validations.Check
import edu.austral.dissis.chess.engine.components.winconditions.CheckMate
import edu.austral.dissis.chess.engine.data.P

private const val ZERO = 0
private const val ONE = 1
private const val THREE = 3
private const val FOUR = 4
private const val SIX = 6

fun main() {
    val resource = Chess::class.java.getResourceAsStream("/config/config.json")
    val config = resource!!.readAllBytes()!!.toString(Charsets.UTF_8)
    val chess =
        Chess fromJson config changeRules {
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
