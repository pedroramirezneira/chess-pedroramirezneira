package edu.austral.dissis.chess.engine.components

import com.google.gson.Gson
import edu.austral.dissis.chess.engine.data.Rules
import edu.austral.dissis.chess.engine.interfaces.Coordinate
import edu.austral.dissis.chess.engine.interfaces.IBoard
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.dissis.chess.engine.models.GameData

open class Game(
    override val board: IBoard,
    override val rules: Rules,
    override val currentPlayer: Boolean,
    override val states: List<IGame> = listOf(),
) : IGame {
    override infix fun changeRules(block: RulesBuilder.() -> Unit): Game {
        val builder = RulesBuilder()
        builder.block()
        val newRules = builder.build()
        val newMovements = newRules.movements + rules.movements
        val newValidations = newRules.validations + rules.validations
        val newWinConditions = newRules.winConditions + rules.winConditions
        val updatedRules = Rules(newMovements, newValidations, newWinConditions)
        return Game(board, updatedRules, currentPlayer, states)
    }

    override infix fun moveFrom(block: () -> Pair<Coordinate, Coordinate>): Game {
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

    private fun handleMovement(coordinates: Pair<Coordinate, Coordinate>): Game {
        val movements = Util.playerMovements(this)
        val verifiedMovement = Util.verifiedMovement(movements, coordinates, this)
        val newBoard = verifiedMovement?.execute(coordinates, this)
        val possibleGame = newBoard?.let { Game(it, rules, currentPlayer, states + this) }
        val isValid = possibleGame?.let { Util.isValid(it) }
        val winCondition = possibleGame?.let { verifiedWinCondition(it) }
        return when {
            verifiedMovement == null -> this
            !isValid!! -> this
            winCondition != null -> GameEnded(possibleGame)
            else -> Game(newBoard, rules, !currentPlayer, states + this)
        }
    }

    private fun verifiedWinCondition(game: Game) =
        game.rules.winConditions.find { condition ->
            condition verify game
        }

    companion object {
        infix fun fromJson(json: String): Game {
            val gson = Gson()
            val data = gson.fromJson(json, GameData::class.java)
            val board = Board from data.board
            val rules = Rules from data.pieces
            return Game(board, rules, true)
        }
    }
}
