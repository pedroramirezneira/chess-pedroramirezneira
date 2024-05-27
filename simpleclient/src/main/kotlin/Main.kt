package edu.austral.dissis.chess

import edu.austral.dissis.chess.components.GameApplication
import edu.austral.dissis.chess.components.SimpleClient
import javafx.application.Application.launch

fun main() {
    SimpleClient.connect()

    println("Client connected to http://localhost:8095")

    launch(GameApplication::class.java)
}
