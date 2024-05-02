package edu.austral.dissis.chess.engine

import edu.austral.dissis.chess.engine.components.Chess
import edu.austral.dissis.chess.engine.components.movements.Castling
import edu.austral.dissis.chess.engine.components.validations.Check
import edu.austral.dissis.chess.engine.components.winconditions.CheckMate

fun main() {
    val chess = Chess `from file` "config.json" `change rules` {
        add movement Castling()
        add validation Check()
        add `win condition` CheckMate()
    }
    println(chess.board.toString())
}
