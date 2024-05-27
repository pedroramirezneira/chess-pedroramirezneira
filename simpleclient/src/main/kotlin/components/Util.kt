package edu.austral.dissis.chess.components

import edu.austral.dissis.chess.gui.Move

object Util {
    fun requestToJson(move: Move): Map<String, Any> {
        val from = "${move.from.column - 1}, ${SimpleClient.size!!.columns - move.from.row}"
        val to = "${move.to.column - 1}, ${SimpleClient.size!!.columns - move.to.row}"
        return mapOf(
            "move" to
                mapOf(
                    "from" to from,
                    "to" to to,
                ),
        )
    }
}
