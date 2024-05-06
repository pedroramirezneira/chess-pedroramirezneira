package com.mediaversetv.chess.models

import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.interfaces.Coordinate

class MovementRequest(private val from: String, private val to: String) {
    fun toCoordinates(): Pair<Coordinate, Coordinate> {
        val xFrom = from.split(", ")[0].toInt()
        val yFrom = from.split(", ")[1].toInt()
        val xTo = to.split(", ")[0].toInt()
        val yTo = to.split(", ")[1].toInt()
        return P(xFrom, yFrom) to P(xTo, yTo)
    }
}
