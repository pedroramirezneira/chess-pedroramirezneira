package edu.austral.dissis.chess.engine.data

import edu.austral.dissis.chess.engine.models.PieceData
import edu.austral.dissis.chess.engine.interfaces.Movement
import edu.austral.dissis.chess.engine.interfaces.Validation
import edu.austral.dissis.chess.engine.interfaces.WinCondition

data class Rules(
    val movements: List<Movement>,
    val validations: List<Validation>,
    val `win conditions`: List<WinCondition>
) {
    companion object {
        infix fun from(pieces: List<PieceData>): Rules {
            val movements = pieces.map { piece ->

            }
        }
    }
}
