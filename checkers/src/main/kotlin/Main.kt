package edu.austral.dissis.chess

import edu.austral.dissis.chess.engine.components.Game

fun main() {
    println("Hello World!")
    val resource = {}.javaClass.getResourceAsStream("/config/config.json")
    val config = resource!!.readAllBytes()!!.toString(Charsets.UTF_8)
    val game = Game fromJson config
    println(game.board.toString())
}