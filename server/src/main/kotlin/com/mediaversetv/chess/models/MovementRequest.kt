package com.mediaversetv.chess.models

import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.Coordinate

data class MovementRequest(val move: MovementCoordinates) {
    fun toCoordinates(): Pair<Coordinate, Coordinate> {
        val xFrom = move.from.split(", ")[0].toInt()
        val yFrom = move.from.split(", ")[1].toInt()
        val xTo = move.to.split(", ")[0].toInt()
        val yTo = move.to.split(", ")[1].toInt()
        return P(xFrom, yFrom) to P(xTo, yTo)
    }
}

data class MovementCoordinates(val from: String, val to: String)
