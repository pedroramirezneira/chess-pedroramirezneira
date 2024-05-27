package edu.austral.dissis.chess.components

import edu.austral.dissis.chess.gui.CachedImageResolver
import edu.austral.dissis.chess.gui.DefaultImageResolver
import edu.austral.dissis.chess.gui.GameView
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class GameApplication : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Chess Online"

        val root = GameView(imageResolver)

        root.addListener(MovementListener())

        SimpleClient.root = root

        primaryStage.scene = Scene(root)

        primaryStage.show()
    }
}
