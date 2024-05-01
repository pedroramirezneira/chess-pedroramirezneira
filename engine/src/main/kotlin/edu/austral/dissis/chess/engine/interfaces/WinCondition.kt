package edu.austral.dissis.chess.engine.interfaces

interface WinCondition {
    infix fun verify(game: Game): Boolean
}
