package edu.austral.dissis.chess.engine.components

import com.google.gson.Gson
import edu.austral.dissis.chess.engine.models.ChessData
import edu.austral.dissis.chess.engine.interfaces.*
import edu.austral.dissis.chess.engine.data.P
import edu.austral.dissis.chess.engine.data.Rules
import java.io.File
import kotlin.io.path.Path

open class Chess(
    override val board: Board,
    override val rules: Rules,
    override val `current player`: String,
    override val states: List<Game> = listOf(),
) : Game {
    override infix fun `change rules`(rules: Rules): Chess {
        TODO("Not yet implemented")
    }

    override infix fun `move from`(block: () -> Pair<Coordinate, Coordinate>): Chess {
        val piece = (board `get piece` block().first) ?: return this
        val isPlayersPiece = piece.color == `current player`
        if (!isPlayersPiece) {
            return this
        }
        val verifiedMovement = rules.movements.find { movement ->
            movement.verify(block(), this)
        }
        if (verifiedMovement == null) {
            return this
        }
        val board = verifiedMovement.execute(block(), this)
        val possibleGame = Chess(board, rules, `current player`, states)
        val isValid = rules.validations.all { validation ->
            validation verify possibleGame
        }
        if (!isValid) {
            return this
        }
        val verifiedWinCondition = rules.`win conditions`.find { condition ->
            condition verify this
        }
        if (verifiedWinCondition != null) {
            return ChessEnded(this)
        }
        return Chess(board, rules, `current player`, states + this)
    }

    companion object {
        infix fun `from json`(json: String): Chess {
            val gson = Gson()
            val data = gson.fromJson(json, ChessData::class.java)
            val board = ChessBoard from data.board
            val rules = Rules from data.pieces
            return Chess(board, rules, data.board.whiteColor)
        }

        infix fun `from file`(file: String): Chess {
            val path = "engine/src/main/kotlin/edu/austral/dissis/chess/engine/config/$file"
            val absolutePath = Path("").toAbsolutePath().resolve(path)
            val config = File(absolutePath.toUri()).readText()
            return Chess `from json` config
        }
    }
}

fun main() {
    val chess = Chess `from file` "config.json"
    println(chess.board.toString())
    chess `move from` { P(0, 1) to P(0, 2) }
}
