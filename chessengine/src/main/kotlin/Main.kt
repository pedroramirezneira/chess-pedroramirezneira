package edu.austral.dissis.chess.engine

import edu.austral.dissis.chess.engine.components.Game
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.engine.components.movements.Castling
import edu.austral.dissis.chess.engine.engine.components.movements.EnPassant
import edu.austral.dissis.chess.engine.engine.components.movements.Promotion
import edu.austral.dissis.chess.engine.engine.components.winconditions.checkMate
import edu.austral.dissis.chess.engine.engine.components.validations.check

private const val ZERO = 0
private const val ONE = 1
private const val THREE = 3
private const val FOUR = 4
private const val SIX = 6

fun main() {
    val resource = {}.javaClass.getResourceAsStream("/config/config.json")
    val config = resource!!.readAllBytes()!!.toString(Charsets.UTF_8)
    val game =
        Game fromJson config changeRules {
            add movement Castling()
            add movement Promotion()
            add movement EnPassant()
            add validation check
            add winCondition checkMate
        }
    println(game.board.toString())
    println()
    val state = game moveFrom { P(ZERO, ONE) to P(ZERO, THREE) }
    println(state.board.toString())
    println()
    val state2 = state moveFrom { P(ZERO, SIX) to P(ZERO, FOUR) }
    println(state2.board.toString())
}
