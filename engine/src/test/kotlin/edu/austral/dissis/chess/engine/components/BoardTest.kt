package edu.austral.dissis.chess.engine.components

import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.data.Piece
import edu.austral.dissis.chess.engine.data.Size
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun constructor() {
        kotlin.runCatching {
            val size = Size(1, 1)
            val pieces = listOf(listOf(Piece("test", true)))
            Board(size, pieces)
        }.onFailure {
            assert(false)
        }.onSuccess {
            assert(true)
        }
    }

    @Test
    fun getPiece() {
        val size = Size(1, 1)
        val pieces = listOf(listOf(Piece("test", true)))
        val board = Board(size, pieces)
        val piece = board getPiece P(0, 0)
        assert(piece!!.type == "test")
    }

    @Test
    fun getPieces() {
        val size = Size(1, 1)
        val pieces = listOf(listOf(Piece("test", true)))
        val board = Board(size, pieces)
        val iterable = board.getPieces()
        assert(iterable.count() == 1)
    }

    @Test
    fun getPiecesByType() {
        val size = Size(1, 1)
        val pieces = listOf(listOf(Piece("test", true)))
        val board = Board(size, pieces)
        val iterable = board getPiecesByType "test"
        assert(iterable.count() == 1)
    }
}
