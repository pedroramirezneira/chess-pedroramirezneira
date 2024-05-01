package edu.austral.dissis.chess.engine.interfaces

interface Movement {
    fun verify(coordinates: Pair<Coordinate, Coordinate>, game: Game): Boolean
    fun execute(coordinates: Pair<Coordinate, Coordinate>, game: Game): Board
}
