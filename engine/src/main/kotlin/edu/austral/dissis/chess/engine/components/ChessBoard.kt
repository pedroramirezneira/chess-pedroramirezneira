package edu.austral.dissis.chess.engine.components

import edu.austral.dissis.chess.engine.interfaces.Board
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.models.P
import edu.austral.dissis.chess.engine.models.Piece
import edu.austral.dissis.chess.engine.models.Size
import edu.austral.dissis.chess.engine.models.Tile

class ChessBoard(override val size: Size, private val tiles: List<List<Piece?>>) : Board {
    override infix fun `get piece`(coordinate: Coordinate): Piece? {
        return tiles[coordinate.y][coordinate.x]
    }

    override fun `get pieces`(): Iterable<Tile> {
        return tiles.flatMapIndexed { y, column ->
            column.mapIndexedNotNull { x, piece ->
                piece?.let { Tile(it, P(x, y)) }
            }
        }
    }

    override infix fun `get pieces by type`(type: String): Iterable<Tile> {
        return `get pieces`().filter { tile ->
            tile.piece.type == type
        }
    }

    override infix fun `move piece from`(block: () -> Pair<Coordinate, Coordinate>): Board {
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
