package edu.austral.dissis.chess.engine.components

import com.google.gson.Gson
import edu.austral.dissis.chess.engine.data.Rules
import edu.austral.dissis.chess.engine.interfaces.Board
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.Game
import edu.austral.dissis.chess.engine.models.ChessData

open class Chess(
    override val board: Board,
    override val rules: Rules,
    override val currentPlayer: Boolean,
    override val states: List<Game> = listOf(),
) : Game {
    override infix fun changeRules(block: RulesBuilder.() -> Unit): Chess {
        val builder = RulesBuilder()
        builder.block()
        val newRules = builder.build()
        val newMovements = rules.movements + newRules.movements
        val newValidations = rules.validations + newRules.validations
        val newWinConditions = rules.winConditions + newRules.winConditions
        val updatedRules = Rules(newMovements, newValidations, newWinConditions)
        return Chess(board, updatedRules, currentPlayer, states)
    }

    override infix fun moveFrom(block: () -> Pair<Coordinate, Coordinate>): Chess {
        val coordinates = block()
        val from = coordinates.first
        val to = coordinates.second
        val hasMoved = from != to
        val piece = board getPiece from
        val isPlayersPiece = piece?.color == currentPlayer
        return when {
            !hasMoved -> this
            piece == null -> this
            !isPlayersPiece -> this
            else -> handleMovement(coordinates)
        }
    }

    private fun handleMovement(coordinates: Pair<Coordinate, Coordinate>): Chess {
        val movements = Util.playerMovements(this)
        val verifiedMovement = Util.verifiedMovement(movements, coordinates, this)
        val newBoard = verifiedMovement?.execute(coordinates, this)
        val possibleGame = newBoard?.let { Chess(it, rules, currentPlayer, states + this) }
        val isValid = possibleGame?.let { Util.isValid(it) }
        val winCondition = verifiedWinCondition(possibleGame)
        return when {
            verifiedMovement == null -> this
            !isValid!! -> this
            winCondition != null -> ChessEnded(possibleGame)
            else -> Chess(newBoard, rules, !currentPlayer, states + this)
        }
    }

    private fun verifiedWinCondition(game: Chess?) =
        game?.rules?.winConditions?.find { condition ->
            condition verify game
        }

    companion object {
        infix fun fromJson(json: String): Chess {
            val gson = Gson()
            val data = gson.fromJson(json, ChessData::class.java)
            val board = ChessBoard from data.board
            val rules = Rules from data.pieces
            return Chess(board, rules, true)
        }
    }
}
