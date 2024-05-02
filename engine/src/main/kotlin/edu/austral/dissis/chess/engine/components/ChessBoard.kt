package edu.austral.dissis.chess.engine.components

import edu.austral.dissis.chess.engine.models.Options
import edu.austral.dissis.chess.engine.interfaces.Board
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.data.Piece
import edu.austral.dissis.chess.engine.data.Size
import edu.austral.dissis.chess.engine.data.Tile

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

    override fun toString(): String {
        val board: List<String> = tiles.reversed().map { column ->
            column.joinToString(", ") { piece ->
                piece?.let { "${it.type} ${it.color}" } ?: "null"
            }
        }
        return board.joinToString("\n")
    }

    companion object {
        infix fun from(options: Options): ChessBoard {
            val size = Size(options.height, options.width)
            val tiles = createTiles(options)
            return ChessBoard(size, tiles)
        }

        private infix fun createTiles(options: Options): List<List<Piece?>> {
            val whiteArrangement = (0 until options.height).map { y ->
                (0 until options.width).map { x ->
                    options.whiteArrangement.getOrNull(y)?.getOrNull(x)
                }
            }
            val blackArrangement = if (options.mirror == true) {
                whiteArrangement.map { it.reversed() }.reversed()
            } else {
                (0 until options.height).map { y ->
                    (0 until options.width).map { x ->
                        options.blackArrangement!!.getOrNull(y)?.getOrNull(x)
                    }
                }
            }
            return (0 until options.height).map { y ->
                (0 until options.width).map { x ->
                    val whitePiece = options.whiteArrangement.getOrNull(y)?.getOrNull(x)
                    val blackPiece = blackArrangement.getOrNull(y)?.getOrNull(x)
                    if (whitePiece != null) {
                        Piece(whitePiece, options.whiteColor)
                    } else if (blackPiece != null) {
                        Piece(blackPiece, options.blackColor)
                    } else {
                        null
                    }
                }
            }
        }
    }
}
