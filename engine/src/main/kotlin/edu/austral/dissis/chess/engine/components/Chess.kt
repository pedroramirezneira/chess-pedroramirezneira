package edu.austral.dissis.chess.engine.components

import edu.austral.dissis.chess.engine.interfaces.*
import edu.austral.dissis.chess.engine.models.P
import edu.austral.dissis.chess.engine.models.Rules

class Chess(
    override val states: List<Game> = listOf(),
    override val board: Board,
    override val rules: Rules,
    override val `current player`: String
) : Game {
    override infix fun `change rules`(rules: Rules): Chess {
        TODO("Not yet implemented")
    }

    override infix fun `move from`(block: () -> Pair<Coordinate, Coordinate>): Chess {
        val piece = (board `get piece` block().first) ?: return this
        val `is player's piece` = piece.color == `current player`
        if (!`is player's piece`) {
            return this
        }
        val `verified movement` = rules.movements.find { movement ->
            movement verify block()
        }
        if (`verified movement` == null) {
            return this
        }
        val board = `verified movement`.execute(block(), this)
        return Chess(states.plus(this), board, rules, `current player`)
    }

    companion object {
        fun `from json`(json: String): Chess {

        }
    }
}

fun main() {
    val chess = Chess()
    chess `move from` { P(1, 0) to P(1, 0) }
}
