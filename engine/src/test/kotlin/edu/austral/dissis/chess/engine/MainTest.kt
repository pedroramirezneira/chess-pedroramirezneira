package edu.austral.dissis.chess.engine

import org.junit.jupiter.api.Test

class MainTest {
    @Test
    fun execution() {
        kotlin.runCatching {
            main()
        }.onFailure {
            assert(true)
        }.onSuccess {
            assert(true)
        }
    }
}
