package edu.austral.dissis.chess.engine.components

import edu.austral.dissis.chess.engine.data.Rules
import edu.austral.dissis.chess.engine.interfaces.KeepTurnCondition
import edu.austral.dissis.chess.engine.interfaces.Movement
import edu.austral.dissis.chess.engine.interfaces.Validation
import edu.austral.dissis.chess.engine.interfaces.WinCondition

class RulesBuilder {
    private val movements: MutableList<Movement> = mutableListOf()
    private val validations: MutableList<Validation> = mutableListOf()
    private val winConditions: MutableList<WinCondition> = mutableListOf()
    private val keepTurnConditions: MutableList<KeepTurnCondition> = mutableListOf()
    val add = this

    infix fun movement(movement: Movement) {
        movements.add(movement)
    }

    infix fun validation(validation: Validation) {
        validations.add(validation)
    }

    infix fun winCondition(winCondition: WinCondition) {
        winConditions.add(winCondition)
    }

    infix fun keepTurnCondition(keepTurnCondition: KeepTurnCondition) {
        keepTurnConditions.add(keepTurnCondition)
    }

    infix fun fromJson(json: String) {
        val game = Game fromJson json
        movements.addAll(game.rules.movements)
        validations.addAll(game.rules.validations)
        winConditions.addAll(game.rules.winConditions)
        keepTurnConditions.addAll(game.rules.keepTurnConditions)
    }

    fun build(): Rules {
        return Rules(movements, validations, winConditions, keepTurnConditions)
    }
}
