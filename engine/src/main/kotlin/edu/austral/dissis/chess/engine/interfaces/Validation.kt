package edu.austral.dissis.chess.engine.interfaces

interface Validation {
    infix fun verify(game: Game): Boolean
}