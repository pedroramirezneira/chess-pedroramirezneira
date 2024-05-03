package edu.austral.dissis.chess.engine.interfaces

import edu.austral.dissis.chess.engine.data.Piece
import edu.austral.dissis.chess.engine.data.Size
import edu.austral.dissis.chess.engine.data.Tile

interface Board {
    val size: Size

    infix fun getPiece(coordinate: Coordinate): Piece?

    fun getPieces(): Iterable<Tile>

    infix fun getPiecesByType(type: String): Iterable<Tile>

    infix fun movePieceFrom(block: () -> Pair<Coordinate, Coordinate>): Board
}
