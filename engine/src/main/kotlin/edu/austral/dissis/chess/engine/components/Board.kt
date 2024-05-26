package edu.austral.dissis.chess.engine.components

import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.data.Piece
import edu.austral.dissis.chess.engine.data.Size
import edu.austral.dissis.chess.engine.data.Tile
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.IBoard
import edu.austral.dissis.chess.engine.models.BoardData

class Board(override val size: Size, private val tiles: List<List<Piece?>>) : IBoard {
    override fun addPiece(
        piece: Piece,
        coordinate: Coordinate,
    ): IBoard {
        val newTiles: List<List<Piece?>> =
            tiles.mapIndexed { y, column ->
                column.mapIndexed { x, oldPiece ->
                    val destination = coordinate.x == x && coordinate.y == y
                    when {
                        destination -> piece
                        else -> oldPiece
                    }
                }
            }
        return Board(size, newTiles)
    }

    override infix fun getPiece(coordinate: Coordinate): Piece? {
        return tiles[coordinate.y][coordinate.x]
    }

    override fun getPieces(): Iterable<Tile> {
        return tiles.flatMapIndexed { y, column ->
            column.mapIndexedNotNull { x, piece ->
                piece?.let { Tile(it, P(x, y)) }
            }
        }
    }

    override infix fun getPiecesByType(type: String): Iterable<Tile> {
        return getPieces().filter { tile ->
            tile.piece.type == type
        }
    }

    override infix fun movePieceFrom(block: () -> Pair<Coordinate, Coordinate>): IBoard {
        val from = block().first
        val to = block().second
        val newTiles: List<List<Piece?>> =
            tiles.mapIndexed { y, column ->
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
        return Board(size, newTiles)
    }

    override fun toString(): String {
        val board: List<String> =
            tiles.reversed().map { column ->
                column.joinToString(", ") { piece ->
                    piece?.let { "${it.type} ${if (it.color) "white" else "black"}" } ?: "null"
                }
            }
        return board.joinToString("\n")
    }

    companion object {
        infix fun from(boardData: BoardData): Board {
            val size = Size(boardData.height, boardData.width)
            val tiles = createTiles(boardData)
            return Board(size, tiles)
        }

        private infix fun createTiles(boardData: BoardData): List<List<Piece?>> {
            val whiteArrangement =
                (0 until boardData.height).map { y ->
                    (0 until boardData.width).map { x ->
                        boardData.whiteArrangement.getOrNull(y)?.getOrNull(x)
                    }
                }
            val blackArrangement =
                if (boardData.mirror == true) {
                    whiteArrangement.reversed()
                } else {
                    (0 until boardData.height).map { y ->
                        (0 until boardData.width).map { x ->
                            boardData.blackArrangement!!.getOrNull(y)?.getOrNull(x)
                        }.reversed()
                    }.reversed()
                }
            return (0 until boardData.height).map { y ->
                (0 until boardData.width).map { x ->
                    val whitePiece = boardData.whiteArrangement.getOrNull(y)?.getOrNull(x)
                    val blackPiece = blackArrangement.getOrNull(y)?.getOrNull(x)
                    if (whitePiece != null && whitePiece != "null") {
                        Piece(whitePiece, true)
                    } else if (blackPiece != null && blackPiece != "null") {
                        Piece(blackPiece, false)
                    } else {
                        null
                    }
                }
            }
        }
    }
}
