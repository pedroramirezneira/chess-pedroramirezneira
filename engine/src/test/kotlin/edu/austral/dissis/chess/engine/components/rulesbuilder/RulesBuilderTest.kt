package edu.austral.dissis.chess.engine.components.rulesbuilder

import edu.austral.dissis.chess.engine.components.RulesBuilder
import edu.austral.dissis.chess.engine.components.movements.StandardMovement
import edu.austral.dissis.chess.engine.data.P
import org.junit.jupiter.api.Test

class RulesBuilderTest {
    @Test
    fun constructor() {
        kotlin.runCatching {
            RulesBuilder()
        }.onFailure {
            assert(false)
        }.onSuccess {
            assert(true)
        }
    }

    @Test
    fun addMovement() {
        val builder = RulesBuilder()
        builder movement StandardMovement("test", P(0, 0))
        val rules = builder.build()
        assert(rules.movements.size == 1)
    }

    @Test
    fun addValidation() {
        val builder = RulesBuilder()
        builder validation DummyValidation()
        val rules = builder.build()
        assert(rules.validations.size == 1)
    }

    @Test
    fun addWinCondition() {
        val builder = RulesBuilder()
        builder winCondition DummyWinCondition()
        val rules = builder.build()
        assert(rules.winConditions.size == 1)
    }
}
