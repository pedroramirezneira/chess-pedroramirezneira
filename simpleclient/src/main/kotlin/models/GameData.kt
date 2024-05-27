package edu.austral.dissis.chess.models

data class GameData(val board: BoardData, val player: Boolean, val winner: String?, val ended: Boolean?)
