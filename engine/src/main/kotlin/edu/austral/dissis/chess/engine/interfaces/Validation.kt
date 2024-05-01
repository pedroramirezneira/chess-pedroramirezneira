package edu.austral.dissis.chess.engine.interfaces

interface Validation {
    fun verify(game: Game): Boolean
}