package edu.austral.dissis.chess.engine.components

import edu.austral.dissis.chess.engine.models.BoardData
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
                piece?.let { "${it.type} ${if (it.color) "white" else "black"}" } ?: "   ◼   "
            }
        }
        return board.joinToString("\n")
    }

    companion object {
        infix fun from(boardData: BoardData): ChessBoard {
            val size = Size(boardData.height, boardData.width)
            val tiles = createTiles(boardData)
            return ChessBoard(size, tiles)
        }

        private infix fun createTiles(boardData: BoardData): List<List<Piece?>> {
            val whiteArrangement = (0 until boardData.height).map { y ->
                (0 until boardData.width).map { x ->
                    boardData.whiteArrangement.getOrNull(y)?.getOrNull(x)
                }
            }
            val blackArrangement = if (boardData.mirror == true) {
                whiteArrangement.map { it.reversed() }.reversed()
            } else {
                (0 until boardData.height).map { y ->
                    (0 until boardData.width).map { x ->
                        boardData.blackArrangement!!.getOrNull(y)?.getOrNull(x)
                    }
                }
            }
            return (0 until boardData.height).map { y ->
                (0 until boardData.width).map { x ->
                    val whitePiece = boardData.whiteArrangement.getOrNull(y)?.getOrNull(x)
                    val blackPiece = blackArrangement.getOrNull(y)?.getOrNull(x)
                    if (whitePiece != null) {
                        Piece(whitePiece, true)
                    } else if (blackPiece != null) {
                        Piece(blackPiece, false)
                    } else {
                        null
                    }
                }
            }
        }
    }
}
