package edu.austral.dissis.chess.engine.interfaces

import edu.austral.dissis.chess.engine.data.Piece
import edu.austral.dissis.chess.engine.data.Size
import edu.austral.dissis.chess.engine.data.Tile

/**
 * A Board.
 *
 * This interface should be implemented by all Boards.
 */
interface Board {
    val size: Size

    /**
     * @return The Piece at the given Coordinate.
     */
    infix fun getPiece(coordinate: Coordinate): Piece?

    /**
     * @return An Iterable containing the Pieces and their Coordinates.
     */
    fun getPieces(): Iterable<Tile>

    /**
     * @return An Iterable containing all Pieces of the specified type.
     */
    infix fun getPiecesByType(type: String): Iterable<Tile>

    /**
     * Moves the Piece at the first Coordinate to the second Coordinate.
     * @return a new updated Board.
     */
    infix fun movePieceFrom(block: () -> Pair<Coordinate, Coordinate>): Board
}
