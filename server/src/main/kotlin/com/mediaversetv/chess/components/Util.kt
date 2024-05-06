package com.mediaversetv.chess.components

import kotlin.random.Random

object Util {
    private const val DIGITS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"

    fun createGameCode(): String {
        val random = Random.Default
        val code = (1..6).map { DIGITS[random.nextInt(DIGITS.length)] }.joinToString("")
        if (Rooms.getRoom(code) != null) return createGameCode()
        return code
    }
}
