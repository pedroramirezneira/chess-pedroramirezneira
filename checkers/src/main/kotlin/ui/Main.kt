package edu.austral.dissis.chess.ui

import edu.austral.dissis.chess.gui.CachedImageResolver
import edu.austral.dissis.chess.gui.DefaultImageResolver
import edu.austral.dissis.chess.gui.createGameViewFrom
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.stage.Stage

fun main() {
    launch(ChessGameApplication::class.java)
}

class ChessGameApplication : Application() {
    private val gameEngine = Engine()
    private val imageResolver = CachedImageResolver(DefaultImageResolver())

    companion object {
        const val GAME_TITLE = "Checkers"
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = GAME_TITLE

        val root = createGameViewFrom(gameEngine, imageResolver)

        primaryStage.scene = Scene(root)

        primaryStage.show()
    }
}
