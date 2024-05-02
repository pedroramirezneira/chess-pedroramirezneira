package edu.austral.dissis.chess.engine.interfaces

import edu.austral.dissis.chess.engine.data.Piece
import edu.austral.dissis.chess.engine.data.Size
import edu.austral.dissis.chess.engine.data.Tile

interface Board {
    val size: Size
    infix fun `get piece`(coordinate: Coordinate): Piece?
    fun `get pieces`(): Iterable<Tile>
    infix fun `get pieces by type`(type: String): Iterable<Tile>
    infix fun `move piece from`(block: () -> Pair<Coordinate, Coordinate>): Board
}
