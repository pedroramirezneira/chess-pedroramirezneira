package edu.austral.dissis.chess.engine.components

import com.google.gson.Gson
import edu.austral.dissis.chess.engine.models.ChessData
import edu.austral.dissis.chess.engine.interfaces.*
import edu.austral.dissis.chess.engine.data.Rules

open class Chess(
    override val board: Board,
    override val rules: Rules,
    override val `current player`: Boolean,
    override val states: List<Game> = listOf(),
) : Game {
    override infix fun `change rules`(block: RulesBuilder.() -> Unit): Chess {
        val builder = RulesBuilder()
        builder.block()
        val newRules = builder.build()
        val newMovements = rules.movements + newRules.movements
        val newValidations = rules.validations + newRules.validations
        val newWinConditions = rules.`win conditions` + newRules.`win conditions`
        val updatedRules = Rules(newMovements, newValidations, newWinConditions)
        return Chess(board, updatedRules, `current player`, states)
    }

    override infix fun `move from`(block: () -> Pair<Coordinate, Coordinate>): Chess {
        println(board.toString())
        if (block().first == block().second) return this
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
        val possibleGame = Chess(board, rules, `current player`, states + this)
        val isValid = possibleGame.rules.validations.all { validation ->
            validation verify possibleGame
        }
        if (!isValid) {
            return this
        }
        val verifiedWinCondition = possibleGame.rules.`win conditions`.find { condition ->
            condition verify possibleGame
        }
        if (verifiedWinCondition != null) {
            return ChessEnded(possibleGame)
        }
        return Chess(board, rules, !`current player`, states + this)
    }

    companion object {
        infix fun `from json`(json: String): Chess {
            val gson = Gson()
            val data = gson.fromJson(json, ChessData::class.java)
            val board = ChessBoard from data.board
            val rules = Rules from data.pieces
            return Chess(board, rules, true)
        }
    }
}
