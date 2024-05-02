package edu.austral.dissis.chess.engine.exam

import edu.austral.dissis.chess.test.TestPieceSymbols

object Symbol {
    private val types: Map<String, Char> = mapOf(
        "king" to TestPieceSymbols.KING,
        "queen" to TestPieceSymbols.QUEEN,
        "rook" to TestPieceSymbols.ROOK,
        "bishop" to TestPieceSymbols.BISHOP,
        "knight" to TestPieceSymbols.KNIGHT,
        "pawn" to TestPieceSymbols.PAWN,
        "archbishop" to TestPieceSymbols.ARCHBISHOP,
        "chancellor" to TestPieceSymbols.CHANCELLOR,
    )

    private val colors: Map<Boolean, Char> = mapOf(
        true to TestPieceSymbols.WHITE,
        false to TestPieceSymbols.BLACK,
    )

    infix fun fromType(type: String): Char {
        return types[type]!!
    }

    infix fun fromColor(color: Boolean): Char {
        return colors[color]!!
    }

    infix fun toType(symbol: Char): String {
        val types = types.map { (key, value) -> value to key }.toMap()
        return types[symbol]!!
    }

    infix fun toColor(symbol: Char): Boolean {
        val colors = colors.map { (key, value) -> value to key }.toMap()
        return colors[symbol]!!
    }
}
