package edu.austral.dissis.chess.engine.data

import edu.austral.dissis.chess.engine.components.movements.AttackMovement
import edu.austral.dissis.chess.engine.components.movements.InitialMovement
import edu.austral.dissis.chess.engine.components.movements.PeacefulMovement
import edu.austral.dissis.chess.engine.components.movements.StandardMovement
import edu.austral.dissis.chess.engine.interfaces.KeepTurnCondition
import edu.austral.dissis.chess.engine.interfaces.Movement
import edu.austral.dissis.chess.engine.interfaces.Validation
import edu.austral.dissis.chess.engine.interfaces.WinCondition
import edu.austral.dissis.chess.engine.models.PieceData

data class Rules(
    val movements: List<Movement>,
    val validations: List<Validation>,
    val winConditions: List<WinCondition>,
    val keepTurnConditions: List<KeepTurnCondition>,
) {
    companion object {
        infix fun from(pieces: List<PieceData>): Rules {
            val movements: List<Movement> =
                pieces.flatMap { piece ->
                    piece.movementVectors.map { vector ->
                        movement(piece, vector)
                    } + (
                        piece.attackVectors?.map { vector ->
                            val components = vector.split(", ")
                            val x = components[0].toInt()
                            val y = components[1].toInt()
                            AttackMovement(piece.type, P(x, y), piece.movementDistance)
                        } ?: emptyList()
                    ) +
                        if (piece.initialDistance != null) {
                            piece.movementVectors.map { vector ->
                                val movement = movement(piece, vector, piece.initialDistance)
                                InitialMovement(movement)
                            }
                        } else {
                            emptyList()
                        }
                }
            val validations: List<Validation> = emptyList()
            val winConditions: List<WinCondition> = emptyList()
            val keepTurnConditions: List<KeepTurnCondition> = emptyList()
            return Rules(movements, validations, winConditions, keepTurnConditions)
        }

        fun empty(): Rules {
            return Rules(emptyList(), emptyList(), emptyList(), emptyList())
        }

        private fun movement(
            piece: PieceData,
            vector: String,
            distance: Int? = null,
        ): Movement {
            val components = vector.split(", ")
            val x = components[0].toInt()
            val y = components[1].toInt()
            return if (piece.attackVectors != null) {
                PeacefulMovement(piece.type, P(x, y), distance ?: piece.movementDistance)
            } else {
                StandardMovement(piece.type, P(x, y), distance ?: piece.movementDistance)
            }
        }
    }
}
