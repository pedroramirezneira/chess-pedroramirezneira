package edu.austral.dissis.chess.engine.interfaces

interface WinCondition {
    fun verify(game: Game): Boolean
}
