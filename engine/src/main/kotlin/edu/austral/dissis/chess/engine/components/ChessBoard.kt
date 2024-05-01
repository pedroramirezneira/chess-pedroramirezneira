package edu.austral.dissis.chess.engine.components

import edu.austral.dissis.chess.engine.interfaces.Board
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Piece
import edu.austral.dissis.chess.engine.interfaces.Size

class ChessBoard(override val size: Size, private val tiles: List<List<Piece?>>) : Board {
    override fun `get piece`(coordinate: Coordinate): Piece? {
        return tiles[coordinate.y][coordinate.x]
    }

    override fun `move piece`(block: () -> Pair<Coordinate, Coordinate>): Board {
        val from = block().first
        val to = block().second
        val newTiles: List<List<Piece?>> = tiles.mapIndexed { y, column ->
            column.mapIndexed { x, piece ->
                val origin = from.x == x && from.y == y
                val destination = to.x == x && to.y == y
                when {
                    origin -> null
                    destination -> tiles[from.y][from.x]
                    else -> piece
                }
            }
        }
        return ChessBoard(size, newTiles)
    }
}
