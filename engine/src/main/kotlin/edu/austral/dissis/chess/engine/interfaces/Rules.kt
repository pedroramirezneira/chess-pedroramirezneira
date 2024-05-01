package edu.austral.dissis.chess.engine.interfaces

interface Rules {
    val movements: List<Movement>
    val validations: List<Validation>
    val `win conditions`: List<WinCondition>
}
