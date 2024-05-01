package edu.austral.dissis.chess.engine.interfaces

interface Board {
    val size: Size
    infix fun `get piece`(coordinate: Coordinate): Piece?
    infix fun `move piece`(block: () -> Pair<Coordinate, Coordinate>): Board
}
