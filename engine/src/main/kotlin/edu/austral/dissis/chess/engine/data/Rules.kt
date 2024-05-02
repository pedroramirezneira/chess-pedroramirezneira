package edu.austral.dissis.chess.engine.data

import edu.austral.dissis.chess.engine.components.movements.AttackMovement
import edu.austral.dissis.chess.engine.components.movements.InitialMovement
import edu.austral.dissis.chess.engine.components.movements.PeacefulMovement
import edu.austral.dissis.chess.engine.components.movements.StandardMovement
import edu.austral.dissis.chess.engine.models.PieceData
import edu.austral.dissis.chess.engine.interfaces.Movement
import edu.austral.dissis.chess.engine.interfaces.Validation
import edu.austral.dissis.chess.engine.interfaces.WinCondition

data class Rules(
    val movements: List<Movement>, val validations: List<Validation>, val `win conditions`: List<WinCondition>
) {
    companion object {
        infix fun from(pieces: List<PieceData>): Rules {
            val movements: List<Movement> = pieces.flatMap { piece ->
                piece.movementVectors.map { vector ->
                    val components = vector.split(", ")
                    val x = components[0].toInt()
                    val y = components[1].toInt()
                    if (piece.attackVectors != null) {
                        PeacefulMovement(piece.type, piece.movementDistance, P(x, y))
                    } else {
                        StandardMovement(piece.type, piece.movementDistance, P(x, y))
                    }
                } + (piece.attackVectors?.map { vector ->
                    val components = vector.split(", ")
                    val x = components[0].toInt()
                    val y = components[1].toInt()
                    AttackMovement(piece.type, piece.movementDistance, P(x, y))
                } ?: emptyList()) + if (piece.initialDistance != null) {
                    piece.movementVectors.map { vector ->
                        val components = vector.split(", ")
                        val x = components[0].toInt()
                        val y = components[1].toInt()
                        InitialMovement(piece.type, piece.initialDistance, P(x, y))
                    }
                } else {
                    emptyList()
                }
            }
            val validations: List<Validation> = emptyList()
            val winConditions: List<WinCondition> = emptyList()
            return Rules(movements, validations, winConditions)
        }
    }
}
