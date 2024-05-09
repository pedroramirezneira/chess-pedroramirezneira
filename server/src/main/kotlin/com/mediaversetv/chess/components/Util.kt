package com.mediaversetv.chess.components

import edu.austral.dissis.chess.engine.interfaces.Board
import kotlin.random.Random

object Util {
    private const val DIGITS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"

    fun createGameCode(): String {
        val random = Random.Default
        val code = (1..6).map { DIGITS[random.nextInt(DIGITS.length)] }.joinToString("")
        if (Rooms.getRoom(code) != null) return createGameCode()
        return code
    }

    fun boardToJson(board: Board): Map<String, Any> {
        return mapOf(
            "board" to mapOf(
                "height" to board.size.height,
                "width" to board.size.width,
                "pieces" to board.getPieces()
            )
        )
    }
}
